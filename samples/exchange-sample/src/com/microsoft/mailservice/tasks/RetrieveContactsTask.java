/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.microsoft.exchange.services.odata.model.IContacts;
import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.IContact;
import com.microsoft.exchange.services.odata.model.types.IContactCollection;
import com.microsoft.mailservice.ContactsActivity;
import com.microsoft.mailservice.ErrorHandler;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.ContactItemAdapter;
import com.msopentech.odatajclient.proxy.api.Query;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveContactsTask.
 */
public class RetrieveContactsTask extends AsyncTask<String, Void, List<IContact>> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private ContactsActivity mActivity;

	public RetrieveContactsTask(ContactsActivity activity) {
		mActivity = activity;
		mContext = activity;
		mDialog = new ProgressDialog(mContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {

		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		mDialog.setTitle("Retrieving Contacts...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(List<IContact> contacts) {

		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}

		if (contacts != null) {
			ListView contactListView = (ListView) mActivity.findViewById(R.id.contact_list);
			ContactItemAdapter adapter = new ContactItemAdapter(mActivity, contacts);
			contactListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			Toast.makeText(mContext, "Finished loading contacts", Toast.LENGTH_LONG).show();
		} else {
			// mApplication.handleError(mThrowable);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected List<IContact> doInBackground(final String... args) {
		List<IContact> contacts = new ArrayList<IContact>();
		try {
			
			IContacts tempContacts = Me.getContacts();
			Query<IContact, IContactCollection> query = tempContacts.createQuery();
			query.setMaxResults(10);
			contacts = new ArrayList<IContact>(query.getResult());

		} catch (Exception e) {
			ErrorHandler.handleError(e, mActivity);
		}
		return contacts;
	}
}
