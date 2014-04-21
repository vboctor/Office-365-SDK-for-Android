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
 * The Class MoveEmailTask.
 */
public class MoveEmailTask extends AsyncTask<String, Void, String[]> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private MainActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;
	
	private String mMessageDisplay;

	static Credentials mCredentials;

	public MoveEmailTask(MainActivity activity, Credentials crendential, String messageDisplay) {
		mActivity = activity;
		mContext = activity;
		mDialog = new ProgressDialog(mContext);
		mCredentials = crendential;
		mMessageDisplay = messageDisplay;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		mDialog.setTitle(mMessageDisplay);
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

		Toast.makeText(mContext, args[3], Toast.LENGTH_LONG).show();
		mActivity.deleteMessage(args[0], args[1]);
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 * args[0] = current message folder id
	 * args[1] = message Id
	 * args[2] = folder to move
	 * args[3] = message to display on onPostExecute
	 */
	protected String[] doInBackground(final String... args) {
		try {
			MessageClient client = new MessageClient(mCredentials);

			client.moveTo(args[1], args[2]).get();

		} catch (Exception e) {
			Log.d(e.getMessage(), e.getStackTrace().toString());
		}
		return args;
	}
}
