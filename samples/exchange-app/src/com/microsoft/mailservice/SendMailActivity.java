package com.microsoft.mailservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import microsoft.exchange.services.odata.model.ItemBody;
import microsoft.exchange.services.odata.model.Message;
import microsoft.exchange.services.odata.model.Recipient;
import com.google.gson.Gson;
import com.microsoft.mailservice.tasks.ReplyEmailTask;
import com.microsoft.mailservice.tasks.RetrieveBodyTask;
import com.microsoft.mailservice.tasks.SendEmailTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

public class SendMailActivity  extends FragmentActivity{

	String mType;
	Message mMessage;
	EditText mTextTo;
	EditText mTextCC;
	EditText mTextSubject;
	WebView mWebViewBody;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_send_mail);

		mType = "";
		String toRecipients = "";
		String ccRecipients = "";
		mTextTo = (EditText)findViewById(R.id.textTo);
		mTextCC	= (EditText)findViewById(R.id.textCC);
		mTextSubject = (EditText)findViewById(R.id.textSubject);
		mWebViewBody =(WebView) findViewById(R.id.send_mail_body);

		List<Recipient> listRecipient;

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String data = bundle.getString("data");
			if (data != null) {
				try {
					JSONObject payload = new JSONObject(data);
					Gson gson = new Gson();
					mMessage = gson.fromJson(payload.getString("message"), Message.class);
					mType = payload.getString("action");

					if(mType.equals("reply")){
						setReplyItems(payload);
						toRecipients = mMessage.getSender().getAddress();
					}
					else if(mType.equals("reply_all")){
						setReplyItems(payload);
						toRecipients = mMessage.getSender().getAddress() + ";";
					}
					else if(mType.equals("forward")){
						setReplyItems(payload);
						mTextTo.setEnabled(true);
						mTextCC.setEnabled(true);
					}

					if(!mType.equals("reply") && !mType.equals("forward")){
						mWebViewBody.setVisibility(8);
						listRecipient = mMessage.getCcRecipients();

						for(int i = 0; i < listRecipient.size(); i++){
							ccRecipients += listRecipient.get(i).getAddress() + "; ";
						}

						listRecipient = mMessage.getToRecipients();
						for(int i = 0; i < listRecipient.size(); i++){
							toRecipients += listRecipient.get(i).getAddress() + "; ";
						}
					}

					mTextCC.setText(ccRecipients);
					mTextTo.setText(toRecipients);	
					mTextSubject.setText(mMessage.getSubject());
				} 
				catch (Exception e) {
					Toast.makeText(this,"Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private void setReplyItems(JSONObject payload) throws JSONException {
		mTextTo.setEnabled(false);
		mTextCC.setEnabled(false);
		mTextSubject.setEnabled(false);

		ItemBody body = mMessage.getBody();

		if(body == null)
		{   
			int position = Integer.parseInt(payload.getString("position"));
			new RetrieveBodyTask(this, position, R.id.send_mail_body).execute(mMessage.getId());
		}
		else{
			mWebViewBody.loadData(body.getContent(),"text/html", "utf-16");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.send_mail, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		try {
			switch (item.getItemId() ) {
			case R.id.menu_send_mail:
				if(mType.equals(""))
					sentEmail();				
				else 
					replay(mType);

				break;
			case R.id.menu_cancel_mail:
				NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
				break;
			default:
				return super.onOptionsItemSelected(item);
			}

		} catch (Throwable t) {
			Log.e("Asset", t.getMessage());
		}
		return true;
	}	

	void sentEmail(){
		Message message= new Message();

		ItemBody body = new ItemBody();
		body.setContentType("HTML");
		body.setContent((((EditText)findViewById(R.id.textBody)).getText().toString()));

		List<Recipient> toRecipients = new ArrayList<Recipient>();

		String [] mails = ((EditText)findViewById(R.id.textTo)).getText().toString().split(";");

		for(String m : mails){
			if(m.trim().length()>0){
				Recipient mail = new Recipient();
				mail.setAddress(m);
				toRecipients.add(mail);
			}
		}

		message.setToRecipients(toRecipients);

		List<Recipient> ccRecipients = new ArrayList<Recipient>();

		String [] mailsCc = ((EditText)findViewById(R.id.textCC)).getText().toString().split(";");

		for(String m : mailsCc){
			if(m.trim().length()>0){
				Recipient mail = new Recipient();
				mail.setAddress(m);
				ccRecipients.add(mail);
			}
		}

		message.setCcRecipients(ccRecipients);

		message.setSubject(((EditText)findViewById(R.id.textSubject)).getText().toString());
		message.setBody(body);

		new SendEmailTask(this, Authentication.getCurrentCredentials()).execute(message);
	}

	void replay(String action){
		String [] mails = ((EditText)findViewById(R.id.textTo)).getText().toString().split(";");
		List<Recipient> toRecipients = new ArrayList<Recipient>();
		for(String m : mails){
			if(m.trim().length()>0){
				Recipient mail = new Recipient();
				mail.setAddress(m);
				toRecipients.add(mail);
			}
		}
		
		new ReplyEmailTask(this, Authentication.getCurrentCredentials(), toRecipients)
		.execute(mMessage.getId(),((EditText)findViewById(R.id.textBody)).getText().toString(),action);
	}
}