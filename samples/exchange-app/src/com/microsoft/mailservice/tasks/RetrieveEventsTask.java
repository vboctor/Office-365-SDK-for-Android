/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.adapters.EventItemAdapter;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.MailClient;
import com.microsoft.office365.mail.entities.Event;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveEventsTask.
 */
public class RetrieveEventsTask extends AsyncTask<String, Void, List<Event>> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private MainActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;

	static Credentials mCredentials;
	
	public RetrieveEventsTask(MainActivity activity, Credentials crendential) {
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

		mDialog.setTitle("Retrieving Events...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(List<Event> events) {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
			mActivity.setRequestedOrientation(mStoredRotation);
		}

		if (events != null) {
			EventItemAdapter adapter = new EventItemAdapter(mActivity, events);
			mActivity.setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			Toast.makeText(mContext, "Finished loading events", Toast.LENGTH_LONG).show();
		} else {
			//mApplication.handleError(mThrowable);
		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected List<Event> doInBackground(final String... args) {
		List<Event> events = new ArrayList<Event>();
		try {
			MailClient mc = new MailClient(mCredentials);

			events = mc.getEvents().get();		
			
		} catch (Exception e) {
		}

		return events;
	}
}
