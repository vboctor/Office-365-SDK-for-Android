package com.microsoft.mailservice;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class MailActivity  extends Activity{

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
					((TextView) findViewById(R.id.mail_sender)).setText(payload.getString("sender"));
					((TextView) findViewById(R.id.mail_subject)).setText(payload.getString("subject"));
					((TextView) findViewById(R.id.mail_sendOn)).setText(payload.getString("date"));
					WebView wv =(WebView) findViewById(R.id.mail_body);
					wv.loadData(payload.getString("body"),"text/html", "utf-8");//.setText(Html.fromHtml(payload.getString("body")));
				} 
				catch (JSONException e) {
					Log.e("Asset", e.getMessage());
				}
			}
		}
	}	
}