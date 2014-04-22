/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;
import microsoft.exchange.services.odata.model.Contact;
import com.microsoft.mailservice.ContactsActivity;
//import com.microsoft.mailservice.adapters.ContactItemAdapter;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.exchange.ContactClient;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveContactsTask.
 */
public class RetrieveContactsTask extends AsyncTask<String, Void, List<Contact>> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private ContactsActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;
	
	static Credentials mCredentials;
	
	public RetrieveContactsTask(ContactsActivity activity, Credentials crendential) {
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
	protected void onPostExecute(List<Contact> contacts) {
//		if (mDialog.isShowing()) {
//			mDialog.dismiss();
//			mActivity.setRequestedOrientation(mStoredRotation);
//		}
//
//		if (contacts != null) {
//			ContactItemAdapter adapter = new ContactItemAdapter(mActivity, contacts);
//			mActivity.setListAdapter(adapter);
//			adapter.notifyDataSetChanged();
//			Toast.makeText(mContext, "Finished loading contacts", Toast.LENGTH_LONG).show();
//		} else {
//			//mApplication.handleError(mThrowable);
//		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected List<Contact> doInBackground(final String... args) {
		List<Contact> contacts = new ArrayList<Contact>();
		try {
			ContactClient client = new ContactClient(mCredentials);

//			contacts = client.getContacts().get();		
			
		} catch (Exception e) {
		}

		return contacts;
	}
}
