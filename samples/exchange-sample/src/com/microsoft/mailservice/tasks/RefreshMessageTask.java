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
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.IFolder;
import com.microsoft.exchange.services.odata.model.types.IMessage;
import com.microsoft.exchange.services.odata.model.types.IMessageCollection;
import com.microsoft.mailservice.Constants;
import com.microsoft.mailservice.ErrorHandler;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.msopentech.odatajclient.proxy.api.Query;

public class RefreshMessageTask extends AsyncTask<String, Void, List<IMessage>> {

	private Activity mActivity;
	private MessageItemAdapter mAdapter;
	SwipeRefreshLayout mSwipeRefreshLayout;

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	public RefreshMessageTask(Activity activity, MessageItemAdapter adpater, SwipeRefreshLayout swipeRefreshLayout) {
		mActivity = activity;
		mContext = mActivity;
		mAdapter = adpater;
		mSwipeRefreshLayout = swipeRefreshLayout;
		mDialog = new ProgressDialog(mContext);
	}

	@Override
	protected void onPostExecute(List<IMessage> result) {
		super.onPostExecute(result);

		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}

		if (mAdapter != null) {
			mAdapter.clear();
			mAdapter.addMoreItemsToTop(result);
			mAdapter.notifyDataSetChanged();
		}

		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	protected void onPreExecute() {

		mDialog.setTitle("Retrieving information...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
		super.onPreExecute();
	}

	@Override
	protected List<IMessage> doInBackground(String... args) {
		List<IMessage> messages = null;
		String folderId = args[0];

		try {
			IFolder folder = Me.getFolders().get(folderId);
			if (folder != null) {
				Query<IMessage, IMessageCollection> query = folder.getMessages().createQuery();
				query.setMaxResults(Constants.TOP_VALUE);
				messages = new ArrayList<IMessage>(query.getResult());
			}
			return messages;
		} catch (Exception e) {
			ErrorHandler.handleError(e, mActivity);
		}
		return messages;
	}
}