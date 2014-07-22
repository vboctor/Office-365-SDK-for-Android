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

import com.microsoft.exchange.services.odata.model.types.IMessage;
import com.microsoft.mailservice.ErrorHandler;
import com.microsoft.mailservice.ExchangeAPIApplication;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.office365.api.MailClient;

public class RetrieveMoreMessageTask extends AsyncTask<String, Void, List<IMessage>> {

	Activity mActivity;
	MessageItemAdapter mAdapter;
	ExchangeAPIApplication mApplication;

	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	public RetrieveMoreMessageTask(Activity activity, MessageItemAdapter adapter) {
		mActivity = activity;
		mAdapter = adapter;
		mApplication = (ExchangeAPIApplication) mActivity.getApplication();
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
			int first = Integer.parseInt(args[0]);
			MailClient mailClient = mApplication.getMailClient();
			messages = mailClient.getMessages(folderId, first);
		} catch (Exception e) {
			ErrorHandler.handleError(e, mActivity);
		}
		return messages;
	}
}