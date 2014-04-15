package com.microsoft.mailservice;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.mailservice.tasks.SendEmailTask;
import com.microsoft.office365.mail.entities.Body;
import com.microsoft.office365.mail.entities.MailAddress;
import com.microsoft.office365.mail.entities.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class SendMailActivity  extends FragmentActivity{

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
				sentEmail();
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
		
		Body body = new Body();
		body.setContentType("HTML");
		body.setContent((((EditText)findViewById(R.id.textBody)).getText().toString()));
		
		List<MailAddress> toRecipients = new ArrayList<MailAddress>();
		
		String [] mails = ((EditText)findViewById(R.id.textTo)).getText().toString().split(";");
		
		for(String m : mails){
			MailAddress mail = new MailAddress();
			mail.setAddress(m);
			toRecipients.add(mail);
		}

		message.setToRecipients(toRecipients);
		
		List<MailAddress> ccRecipients = new ArrayList<MailAddress>();
		
		String [] mailsCc = ((EditText)findViewById(R.id.textCC)).getText().toString().split(";");
		
		for(String m : mailsCc){
			MailAddress mail = new MailAddress();
			mail.setAddress(m);
			ccRecipients.add(mail);
		}
	
		message.setCcRecipients(ccRecipients);
		
		message.setSubject(((EditText)findViewById(R.id.textSubject)).getText().toString());
		message.setSubject(((EditText)findViewById(R.id.textSubject)).getText().toString());
		message.setBody(body);
		
		new SendEmailTask(this, Authentication.getCurrentCredentials()).execute(message);
	}
}