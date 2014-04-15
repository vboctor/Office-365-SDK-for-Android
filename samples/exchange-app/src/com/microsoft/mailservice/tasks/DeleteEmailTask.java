/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.SendMailActivity;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.MailClient;
import com.microsoft.office365.mail.entities.Message;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveMessagesTask.
 */
public class DeleteEmailTask extends AsyncTask<String, Void, Void> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private MainActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;
	
	static Credentials mCredentials;
	
	public DeleteEmailTask(MainActivity activity, Credentials crendential) {
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
	protected void onPostExecute(Void v) {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
			mActivity.setRequestedOrientation(mStoredRotation);
		}

		//if (message != null) {
			//MessageItemAdapter adapter = new MessageItemAdapter(mActivity, message);
			//mActivity.setListAdapter(adapter);
			//adapter.notifyDataSetChanged();
			Toast.makeText(mContext, "Message Deleted", Toast.LENGTH_LONG).show();

			NavUtils.navigateUpTo(mActivity,new Intent(mActivity, MainActivity.class));
	//	} else {
			//mApplication.handleError(mThrowable);
	//	}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected Void doInBackground(final String... args) {
		try {
			MailClient mc = new MailClient(mCredentials);

			mc.deleteMessage(args[0]).get();
			//mc.sendMessage(messageId).get();//sendMessage(args[0]).get();
			//messageSend = mc.sendMessage(args[0]).get();

		} catch (Exception e) {
		}
		return null;
	}
}
