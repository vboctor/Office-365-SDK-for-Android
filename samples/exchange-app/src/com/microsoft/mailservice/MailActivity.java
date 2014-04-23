package com.microsoft.mailservice;

import microsoft.exchange.services.odata.model.ItemBody;
import microsoft.exchange.services.odata.model.Message;
import org.json.JSONException;
import org.json.JSONObject;
import com.microsoft.office365.Query;
import com.microsoft.office365.exchange.MailClient;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MailActivity  extends BaseActivity{

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail_display);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String data = bundle.getString("data");
			if (data != null) {
				try {
					JSONObject payload = new JSONObject(data);
					final int position = Integer.parseInt(payload.getString("position"));

					Message currentMessage = (Message)MainActivity.mMailListView.getItemAtPosition(position);

					((TextView) findViewById(R.id.mail_sender)).setText(currentMessage.getSender().getName());
					((TextView) findViewById(R.id.mail_subject)).setText(currentMessage.getSubject());
					((TextView) findViewById(R.id.mail_sendOn)).setText(currentMessage.getDateTimeSent());

					ItemBody body = currentMessage.getBody();

					if(body == null)
					{
						AsyncTask<String,Void,Message> t = new AsyncTask<String,Void,Message>(){
							ProgressDialog mDialog = new ProgressDialog(MailActivity.this);

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
								((Message)MainActivity.mMailListView.getItemAtPosition(position)).setBody(result.getBody());
								WebView wv =(WebView) findViewById(R.id.mail_body);
								wv.loadData(result.getBody().getContent(),"text/html", "utf-8");
								Toast.makeText(MailActivity.this, "Finished loading message", Toast.LENGTH_LONG).show();
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
						};

						t.execute(currentMessage.getId());
					}
					else{

						WebView wv =(WebView) findViewById(R.id.mail_body);
						wv.loadData(body.getContent(),"text/html", "utf-8");
					}
				}
				catch (JSONException e) {
					Log.e("Asset", e.getMessage());
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.context_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_delete:
		//	removeRow();
		//	mode.finish();
			return true;
		case R.id.menu_reply:
			//reply();
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public void deleteMessage(String folderId, String messageId) {
		// TODO Auto-generated method stub

	}	
}