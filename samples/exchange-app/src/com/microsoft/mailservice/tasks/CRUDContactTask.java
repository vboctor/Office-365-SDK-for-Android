/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import microsoft.exchange.services.odata.model.Contact;
import com.microsoft.mailservice.ContactsActivity;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.exchange.ContactClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateContactTask.
 */
public class CRUDContactTask extends AsyncTask<Contact, Void, Void> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private Activity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;

	static Credentials mCredentials;
	static String mAction;
	static String[] mMessage;
	
	public CRUDContactTask(Activity activity, Credentials crendential,String[] args) {
		mActivity = activity;
		mContext = activity;
		mDialog = new ProgressDialog(mContext);
		mCredentials = crendential;
		mAction = args[0];
		mMessage = new String[]{args[1], args[2],args[3]};
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		mDialog.setTitle(mMessage[0]);
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

		Toast.makeText(mContext, mMessage[2], Toast.LENGTH_SHORT).show();

		NavUtils.navigateUpTo(mActivity,new Intent(mActivity, ContactsActivity.class));
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected Void doInBackground(final Contact... args) {
		try {
			ContactClient client = new ContactClient(mCredentials);

			if(mAction.equals("create"))
				client.create(args[0]).get();
			else if(mAction.equals("delete"))
				client.delete(args[0].getId()).get();
			else client.update(args[0]).get();
			
		} catch (Exception e) {
			Toast.makeText(mContext, mMessage[1] + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		return null;
	}
}
