/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import microsoft.exchange.services.odata.model.Folder;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.adapters.FolderItemAdapter;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.exchange.FolderClient;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.util.Log;
// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveFodersTask.
 */
public class RetrievePrimaryFoldersTask extends AsyncTask<String, Void, Map<String,List<Folder>>> {


	/** The m activity. */
	private MainActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;
	
	static Credentials mCredentials;
	
	public RetrievePrimaryFoldersTask(MainActivity activity, Credentials crendential) {
		mActivity = activity;
		mCredentials = crendential;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		Log.d("Folder task", "Retrieving Folders");
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Map<String,List<Folder>> folders) {
			mActivity.setRequestedOrientation(mStoredRotation);

		if (folders != null) {
			FolderItemAdapter primaryAdapter = new FolderItemAdapter(mActivity, folders.get("Primary"));
			FolderItemAdapter secondAdapter = new FolderItemAdapter(mActivity, folders.get("Secondary"));
			mActivity.setListAdapter(primaryAdapter,secondAdapter);
			primaryAdapter.notifyDataSetChanged();
			secondAdapter.notifyDataSetChanged();
			Log.d("Folder task", "Finished loading Folders");
		} else {
			//mApplication.handleError(mThrowable);
		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected Map<String,List<Folder>> doInBackground(final String... args) {
		Map<String,List<Folder>> folders = new HashMap<String,List<Folder>>();
		try {
			FolderClient client = new FolderClient(mCredentials);

			List<Folder> auxFolders = client.getFolders(null).get();	
			
			Folder inbox = null, draft = null, sentItems = null, deletedItems = null;
			
			folders.put("Primary", new ArrayList<Folder>());
			folders.put("Secondary", new ArrayList<Folder>());
			for(Folder folder : auxFolders){
				String display = folder.getDisplayName();
				
				if(display.equals("Inbox")){
					inbox = folder;
				} else if ( display.equals("Drafts")){
					draft = folder;
				}else if (display.equals("Sent Items")){
					sentItems = folder;
				}else if (display.equals("Deleted Items")){
					deletedItems = folder;
				}
				else{
					folders.get("Secondary").add(folder);
				}
			}
			folders.get("Primary").add(inbox);
			folders.get("Primary").add(draft);
			folders.get("Primary").add(deletedItems);
			folders.get("Primary").add(sentItems);
			
			mActivity.setFolders(folders);
		} catch (Exception e) {
			Log.d(e.getMessage(), e.getStackTrace().toString());
		}

		return folders;
	}
	
	List<Folder> orderFolders(List<Folder> folders){
		List<Folder> orderedFolder = new ArrayList<Folder>();
		
		for(Folder folder : folders){
			if(folder.getDisplayName().equals("Inbox")){
				orderedFolder.add(folder);
			}
			folders.remove(folder);
			break;
		}
		
		for(Folder folder : folders){
			if(folder.getDisplayName().equals("Inbox")){
				orderedFolder.add(folder);
			}
			folders.remove(folder);
			break;
		}		
		
		return orderedFolder;
	}
}
