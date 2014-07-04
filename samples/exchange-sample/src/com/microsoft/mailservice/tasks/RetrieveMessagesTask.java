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

import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.IFolder;
import com.microsoft.exchange.services.odata.model.types.IMessage;
import com.microsoft.exchange.services.odata.model.types.IMessageCollection;
import com.microsoft.mailservice.Constants;
import com.microsoft.mailservice.ErrorHandler;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.msopentech.odatajclient.proxy.api.Query;

// TODO: Auto-generated Javadoc
/**
 * The Class RetrieveMessagesTask.
 */
public class RetrieveMessagesTask extends AsyncTask<String, Void, List<IMessage>> {

	/** The m activity. */
	private MainActivity mActivity;
	
	/** The m dialog. */
	private ProgressDialog mDialog;

	/** The m context. */
	private Context mContext;

	String mFolderId;

	public RetrieveMessagesTask(MainActivity activity) {
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
		
		mDialog.setTitle("Retrieving messages...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
		
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(List<IMessage> messages) {
		
		if (mDialog.isShowing()) {
			mDialog.dismiss();
		}
		
		if (messages != null) {
			mActivity.setMessages(mFolderId, messages);
			MessageItemAdapter adapter = new MessageItemAdapter(mActivity, messages);
			((ListView) mActivity.findViewById(R.id.mail_list)).setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	protected List<IMessage> doInBackground(final String... args) {
		List<IMessage> messages = null;
		mFolderId = args[0];
		try {  
			IFolder folder = Me.getFolders().get(mFolderId);
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
