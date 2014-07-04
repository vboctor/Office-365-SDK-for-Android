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

import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.ICalendar;
import com.microsoft.exchange.services.odata.model.types.IEvent;
import com.microsoft.exchange.services.odata.model.types.IEventCollection;
import com.microsoft.mailservice.CalendarEventsActivity;
import com.microsoft.mailservice.ErrorHandler;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.EventItemAdapter;
import com.msopentech.odatajclient.proxy.api.Query;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveEventsTask.
 */
public class RetrieveEventsTask extends AsyncTask<String, Void, List<IEvent>> {

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	/** The m activity. */
	private CalendarEventsActivity mActivity;

	/** The m stored rotation. */
	private int mStoredRotation;

	public RetrieveEventsTask(CalendarEventsActivity activity) {
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

		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		mDialog.setTitle("Retrieving Events...");
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
	protected void onPostExecute(List<IEvent> events) {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
			mActivity.setRequestedOrientation(mStoredRotation);
		}

		if (events != null) {
			EventItemAdapter adapter = new EventItemAdapter(mActivity, events);
			((ListView) mActivity.findViewById(R.id.event_list)).setAdapter(adapter);
			adapter.notifyDataSetChanged();
			Toast.makeText(mContext, "Finished loading events", Toast.LENGTH_LONG).show();
		} else {
			// mApplication.handleError(mThrowable);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected List<IEvent> doInBackground(final String... args) {
		List<IEvent> events = new ArrayList<IEvent>();

		try {

			ICalendar calendar = Me.getCalendar();
			Query<IEvent, IEventCollection> query = calendar.getEvents().createQuery();
			query.setMaxResults(10);
			events = new ArrayList<IEvent>(query.getResult());

		} catch (Exception e) {
			ErrorHandler.handleError(e, mActivity);
		}
		return events;
	}
}
