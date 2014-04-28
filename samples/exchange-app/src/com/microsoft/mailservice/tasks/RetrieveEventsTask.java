/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;
import microsoft.exchange.services.odata.model.Event;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.EventItemAdapter;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.exchange.CalendarClient;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.widget.ListView;
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
			((ListView)mActivity.findViewById(R.id.mail_list)).setAdapter(adapter);
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
			CalendarClient client = new CalendarClient(mCredentials);

			events = client.getEvents(null).get();		
			
		} catch (Exception e) {
		}

		return events;
	}
}
