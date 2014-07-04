/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;

import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.IMessage;
import com.microsoft.exchange.services.odata.model.types.IMessageCollection;
import com.microsoft.mailservice.Constants;
import com.microsoft.mailservice.ErrorHandler;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.msopentech.odatajclient.proxy.api.Query;

public class RetrieveMoreMessageTask extends AsyncTask<String, Void, List<IMessage>> {

	Activity mActivity;
	MessageItemAdapter mAdapter;

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	public RetrieveMoreMessageTask(Activity activity, MessageItemAdapter adapter) {
		mActivity = activity;
		mAdapter = adapter;

		mContext = activity;
		mDialog = new ProgressDialog(mContext);
	}

	@Override
	protected void onPostExecute(List<IMessage> result) {

		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}

		mAdapter.addMoreItems(result);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onPreExecute() {

		mDialog.setTitle("Retrieving messages...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();

		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}

	@Override
	protected List<IMessage> doInBackground(String... args) {
		List<IMessage> messages = new ArrayList<IMessage>();

		try {
			String folderId = args[1];
			Query<IMessage, IMessageCollection> query = Me.getFolders().get(folderId).getMessages().createQuery();
			query.setMaxResults(Constants.TOP_VALUE);
			query.setFirstResult(Integer.parseInt(args[0]));
			messages = new ArrayList<IMessage>(query.getResult());
		} catch (Exception e) {
			ErrorHandler.handleError(e, mActivity);
		}
		return messages;
	}
}