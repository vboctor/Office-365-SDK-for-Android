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

import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.IFolder;
import com.microsoft.mailservice.ErrorHandler;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.FolderItemAdapter;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveFodersTask.
 */
public class RetrievePrimaryFoldersTask extends AsyncTask<String, Void, Map<String, List<IFolder>>> {

	/** The m activity. */
	private MainActivity mActivity;

	public RetrievePrimaryFoldersTask(MainActivity activity) {
		mActivity = activity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		Log.d("Folder task", "Retrieving Folders");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Map<String, List<IFolder>> folders) {

		if (folders != null) {
			FolderItemAdapter primaryAdapter = new FolderItemAdapter(mActivity, folders.get("Primary"));
			FolderItemAdapter secondAdapter = new FolderItemAdapter(mActivity, folders.get("Secondary"));
			((ListView) mActivity.findViewById(R.id.list_primary_foders)).setAdapter(primaryAdapter);
			((ListView) mActivity.findViewById(R.id.list_secondary_foders)).setAdapter(secondAdapter);
			primaryAdapter.notifyDataSetChanged();
			secondAdapter.notifyDataSetChanged();
			Log.d("App", "Finished loading Folders");
			Log.d("App", "error loading Folders");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected Map<String, List<IFolder>> doInBackground(final String... args) {
		Map<String, List<IFolder>> folders = new HashMap<String, List<IFolder>>();
		try {

			List<IFolder> auxFolders = new ArrayList<IFolder>(Me.getRootFolder().getChildFolders());
			IFolder inbox = null, draft = null, sentItems = null, deletedItems = null;

			folders.put("Primary", new ArrayList<IFolder>());
			folders.put("Secondary", new ArrayList<IFolder>());

			for (IFolder folder : auxFolders) {

				String display = folder.getDisplayName();
				if (display.equals("Inbox")) {
					inbox = folder;
				} else if (display.equals("Drafts")) {
					draft = folder;
				} else if (display.equals("Sent Items")) {
					sentItems = folder;
				} else if (display.equals("Deleted Items")) {
					deletedItems = folder;
				} else {
					folders.get("Secondary").add(folder);
				}
			}

			folders.get("Primary").add(inbox);
			folders.get("Primary").add(draft);
			folders.get("Primary").add(deletedItems);
			folders.get("Primary").add(sentItems);

			mActivity.setFolders(folders);
		} catch (Exception e) {
			ErrorHandler.handleError(e, mActivity);
		}

		return folders;
	}

	List<IFolder> orderFolders(List<IFolder> folders) {
		List<IFolder> orderedFolder = new ArrayList<IFolder>();

		for (IFolder folder : folders) {
			if (folder.getDisplayName().equals("Inbox")) {
				orderedFolder.add(folder);
			}
			folders.remove(folder);
			break;
		}

		for (IFolder folder : folders) {
			if (folder.getDisplayName().equals("Inbox")) {
				orderedFolder.add(folder);
			}
			folders.remove(folder);
			break;
		}

		return orderedFolder;
	}
}
