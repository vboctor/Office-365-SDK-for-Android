package com.microsoft.mailservice.tasks;

import java.util.ArrayList;
import java.util.List;
import microsoft.exchange.services.odata.model.Message;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.widget.Toast;
import com.microsoft.mailservice.Authentication;
import com.microsoft.mailservice.Constants;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.office365.Query;
import com.microsoft.office365.exchange.MailClient;

public class RetrieveMoreMessageTask extends AsyncTask<String, Void, List<Message>>{

	Activity mActivity;
	MessageItemAdapter mAdapter;
	private int mStoredRotation;
	
	public RetrieveMoreMessageTask(Activity activity, MessageItemAdapter adapter){
		mActivity = activity;
		mAdapter = adapter;
	}

	@Override
	protected void onPostExecute(List<Message> result) {
		super.onPostExecute(result);
		mAdapter.addMoreItems(result);
		mAdapter.notifyDataSetChanged();
		mActivity.setRequestedOrientation(mStoredRotation);
		mActivity.findViewById(R.id.load_more).setVisibility(8);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mStoredRotation = mActivity.getRequestedOrientation();
		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		mActivity.findViewById(R.id.load_more).setVisibility(0);
	}

	@Override
	protected List<Message> doInBackground(String... args) {
		List<Message> messages = new ArrayList<Message>();

		try {
			MailClient client = new MailClient(Authentication.getCurrentCredentials());
			Query query = new Query();

			query = query.top(Constants.TOP_VALUE).skip(Integer.parseInt(args[0]) - 1).select(Constants.MAIL_FIELDS_TO_SELECT);
			//String folder = mLastSelectedFolder != null ? mLastSelectedFolder.getDisplayName() : "Inbox";
			messages = client.getMessages(args[1], query).get();

		} catch (Exception e) {
			Toast.makeText(mActivity, "Error getting Messsages", Toast.LENGTH_SHORT).show();
		}

		return messages;
	}
}