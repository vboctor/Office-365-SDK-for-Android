package com.microsoft.mailservice;

import microsoft.exchange.services.odata.model.ItemBody;
import microsoft.exchange.services.odata.model.Message;
import org.json.JSONException;
import org.json.JSONObject;
import com.microsoft.mailservice.tasks.RetrieveBodyTask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

public class MailActivity  extends BaseActivity{

	Message mMessage;
	
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

					mMessage = (Message)MainActivity.mMailListView.getItemAtPosition(position);

					((TextView) findViewById(R.id.mail_sender)).setText(mMessage.getSender().getName());
					((TextView) findViewById(R.id.mail_subject)).setText(mMessage.getSubject());
					((TextView) findViewById(R.id.mail_sendOn)).setText(mMessage.getDateTimeSent());

					ItemBody body = mMessage.getBody();

					if(body == null)
					{
						new RetrieveBodyTask(this, position,R.id.mail_body).execute(mMessage.getId());
					}
					else{

						WebView wv =(WebView) findViewById(R.id.mail_body);
						wv.loadData(body.getContent(),"text/html", "utf-16");
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
				Intent intent = new Intent(MailActivity.this, MailActivity.class);
				JSONObject payload = new JSONObject();
				try {
					payload.put("position", 0);
					intent.putExtra("data", payload.toString());
					startActivity(intent);
				}
				catch (Throwable t) {
				}	
			
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