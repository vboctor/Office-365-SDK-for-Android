package com.microsoft.mailservice.tasks;

import microsoft.exchange.services.odata.model.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.Toast;
import com.microsoft.mailservice.Authentication;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.office365.Query;
import com.microsoft.office365.exchange.MailClient;

public class RetrieveBodyTask extends AsyncTask<String,Void,Message>{

	ProgressDialog mDialog;
	int mPosition;
	int mControlId;
	Activity mActivity;

	public RetrieveBodyTask(Activity activity, int position, int controlId){
		mActivity = activity;
		mDialog = new ProgressDialog(activity);
		mPosition = position;
		mControlId = controlId;
	}

	protected void onPreExecute() {

		mDialog.setTitle("Retrieving Message...");
		mDialog.setMessage("Please wait.");
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	@Override
	protected void onPostExecute(Message result) {
		mDialog.dismiss();
		super.onPostExecute(result);
		((Message)MainActivity.mMailListView.getItemAtPosition(mPosition)).setBody(result.getBody());
		WebView wv =(WebView) mActivity.findViewById(mControlId);
		wv.loadData(result.getBody().getContent(),"text/html", "utf-8");
		Toast.makeText(mActivity, "Finished loading message", Toast.LENGTH_LONG).show();
	}

	@Override
	protected Message doInBackground(String... params) {

		Message message = null;
		try {
			MailClient client = new MailClient(Authentication.getCurrentCredentials());
			Query query = new Query().select(new String[]{ "Body" });

			message = client.getMessage(params[0], query).get();

		} catch (Exception e) {
		}

		return message;
	}
}