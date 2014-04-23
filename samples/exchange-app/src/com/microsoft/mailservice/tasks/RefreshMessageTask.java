package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;
import microsoft.exchange.services.odata.model.Message;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;
import com.microsoft.mailservice.Authentication;
import com.microsoft.mailservice.Constants;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.office365.Query;
import com.microsoft.office365.exchange.MailClient;

public class RefreshMessageTask extends AsyncTask<String, Void, List<Message>>{

	private Activity mActivity;
	private MessageItemAdapter mAdapter;
	SwipeRefreshLayout mSwipeRefreshLayout;
	
	public RefreshMessageTask(Activity activity,MessageItemAdapter adpater,SwipeRefreshLayout swipeRefreshLayout){
		mActivity = activity;
		mAdapter = adpater;
		mSwipeRefreshLayout = swipeRefreshLayout;
	}

	@Override
	protected void onPostExecute(List<Message> result) {
		super.onPostExecute(result);

		mAdapter.addMoreItemsToTop(result);
		mAdapter.notifyDataSetChanged();
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<Message> doInBackground(String... args) {
		List<Message> messages = new ArrayList<Message>();

		try {
			MailClient client = new MailClient(Authentication.getCurrentCredentials());
			Query query = new Query();

			query = query.top(Constants.TOP_VALUE).select(Constants.FIELDS_TO_SELECT);

			query.setQueryText("$filter=LastModifiedTime%20gt%20" + args[1] + "&");
	
			messages = client.getMessages(args[0], query).get();

		} catch (Exception e) {
			Toast.makeText(mActivity, "Error getting Messsages", Toast.LENGTH_SHORT).show();
		}

		return messages;
	}
}