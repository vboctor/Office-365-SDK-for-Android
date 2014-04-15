/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.exchange.MailClient;
import com.microsoft.office365.mail.entities.Message;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveMessagesTask.
 */
public class RetrieveMessagesTask extends AsyncTask<String, Void, List<Message>> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private MainActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;
	
	static Credentials mCredentials;
	
	String mFolderId;
	
	public RetrieveMessagesTask(MainActivity activity, Credentials crendential) {
		mActivity = activity;
		mContext = activity;
		mDialog = new ProgressDialog(mContext);
		mCredentials = crendential;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		mDialog.setTitle("Retrieving Messages...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(List<Message> messages) {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
			mActivity.setRequestedOrientation(mStoredRotation);
		}

		if (messages != null) {
			mActivity.setMessages(mFolderId,messages);
			MessageItemAdapter adapter = new MessageItemAdapter(mActivity, messages);
			mActivity.setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			Toast.makeText(mContext, "Finished loading messages", Toast.LENGTH_LONG).show();
		} else {
			//mApplication.handleError(mThrowable);
		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected List<Message> doInBackground(final String... args) {
		List<Message> messages = new ArrayList<Message>();
		mFolderId = args[0];
		try {
			MailClient client = new MailClient(mCredentials);
			
			messages = client.getMessages(mFolderId).get();
			
		} catch (Exception e) {
		}

		return messages;
	}
}
