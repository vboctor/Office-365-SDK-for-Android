/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import com.microsoft.mailservice.MainActivity;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.exchange.MessageClient;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteEmailTask.
 */
public class DeleteEmailTask extends AsyncTask<String, Void, String[]> {

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

		mDialog.setTitle("Deleting Message...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String[] args) {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
			mActivity.setRequestedOrientation(mStoredRotation);
		}

		Toast.makeText(mContext, "Message Deleted", Toast.LENGTH_LONG).show();
		mActivity.deleteMessage(args[0], args[1]);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected String[] doInBackground(final String... args) {
		try {
			MessageClient client = new MessageClient(mCredentials);

			client.delete(args[1]).get();

		} catch (Exception e) {
			Log.d(e.getMessage(), e.getStackTrace().toString());
		}
		return args;
	}
}