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
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;
import com.microsoft.office365.exchange.MailClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveMessagesTask.
 */
public class RetrieveMessageTask extends AsyncTask<String, Void, Message> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private Activity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;
	
	static Credentials mCredentials;
	
	String mFolderId;

	Query mQuery;
	
	public RetrieveMessageTask(Activity activity, Credentials crendential, Query query) {
		mActivity = activity;
		mContext = activity;
		mDialog = new ProgressDialog(mContext);
		mCredentials = crendential;
		mQuery = query;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		mDialog.setTitle("Retrieving Message...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Message message) {
//		if (mDialog.isShowing()) {
//			mDialog.dismiss();
//			mActivity.setRequestedOrientation(mStoredRotation);
//		}
//
//		if (message != null) {
//			MessageItemAdapter adapter = new MessageItemAdapter(mActivity, messages);
//			mActivity.setListAdapter(adapter);
//			adapter.notifyDataSetChanged();
//			Toast.makeText(mContext, "Finished loading message", Toast.LENGTH_LONG).show();
//		} else {
//			//mApplication.handleError(mThrowable);
//		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected Message doInBackground(final String... args) {
		Message message = new Message();
		try {
			MailClient client = new MailClient(mCredentials);
			
			message = client.getMessage(args[0], mQuery).get();
			
			
		} catch (Exception e) {
		}

		return message;
	}
}
