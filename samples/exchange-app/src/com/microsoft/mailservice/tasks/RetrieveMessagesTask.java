/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;
import microsoft.exchange.services.odata.model.Message;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;
import com.microsoft.office365.exchange.MailClient;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveMessagesTask.
 */
public class RetrieveMessagesTask extends AsyncTask<String, Void, List<Message>> {

	/** The m activity. */
	private MainActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;
	
	static Credentials mCredentials;
	
	String mFolderId;

	Query mQuery;
	
	public RetrieveMessagesTask(MainActivity activity, Credentials crendential, Query query) {
		mActivity = activity;
		mCredentials = crendential;
		mQuery = query;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		mActivity.findViewById(R.id.load_more).setVisibility(0);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(List<Message> messages) {
		
		if (messages != null) {
			mActivity.setMessages(mFolderId,messages);
			MessageItemAdapter adapter = new MessageItemAdapter(mActivity, messages);
			mActivity.setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			
			mActivity.findViewById(R.id.load_more).setVisibility(8);
			
		} else {
			mActivity.findViewById(R.id.load_more).setVisibility(8);
		}
		mActivity.setRequestedOrientation(mStoredRotation);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected List<Message> doInBackground(final String... args) {
		List<Message> messages = new ArrayList<Message>();
		mFolderId = args[0];
		try {
			MailClient client = new MailClient(mCredentials);
			
			List<Message> auxMessages = client.getMessages(mFolderId, mQuery).get();
			
			for(Message m : auxMessages){
				messages.add(m);
				
			}
			messages.add(new Message());
			
		} catch (Exception e) {
		}

		return messages;
	}
}
