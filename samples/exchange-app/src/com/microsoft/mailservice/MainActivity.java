/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.ContactItemAdapter;
import com.microsoft.mailservice.adapters.EventItemAdapter;
import com.microsoft.mailservice.adapters.FolderItemAdapter;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.mailservice.tasks.DeleteEmailTask;
import com.microsoft.mailservice.tasks.RetrieveContactsTask;
import com.microsoft.mailservice.tasks.RetrieveEventsTask;
import com.microsoft.mailservice.tasks.RetrieveFoldersTask;
import com.microsoft.mailservice.tasks.RetrieveMessagesTask;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.entities.Folder;
import com.microsoft.office365.mail.entities.Message;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {

	ListView mListView;
	ListView mListPrimaryFolderView;
	ListView mListSecondaryFolderView;
	TextView mLastSelectedItem;

	static Map<String,List<Message>> mMessages = new HashMap<String,List<Message>>();
	static Map<String,List<Folder>> mFolders;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	public void setMessages(String folderName,List<Message> messages){
		mMessages.put(folderName, messages);
	}

	public void setFolders(Map<String,List<Folder>> folders){
		mFolders = folders;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Authentication.createEncryptionKey(getApplicationContext());
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		setListMenu();
		mListView = (ListView)findViewById(R.id.mail_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {

//				MenuItem item = (MenuItem) findViewById(R.id.menu_delete_message);
//				item.setEnabled(true);
				
				Intent intent = new Intent(MainActivity.this, MailActivity.class);
				arg1.setBackgroundResource(R.color.cyan);
				JSONObject payload = new JSONObject();
				try {

					Message message =(Message) mListView.getItemAtPosition(position);
					payload.put("sender", message.getSender().getName());
					payload.put("subject", message.getSubject());
					payload.put("date", message.getDateTimeSent());
					payload.put("body", message.getBody().getContent());

					intent.putExtra("data", payload.toString());
					startActivity(intent);
				} catch (Throwable t) {
				}				
			}
		});
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

		    @Override
		    public boolean onItemLongClick(AdapterView<?> parent,
		            final View view, final int position, long id) {
		        removeRow(view, position);
		        return true;
		    }

			private void removeRow(final View view, final int position) {
				
				View view2 = new View(MainActivity.this);
		
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Delete Mail?")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Object obj = mListView.getItemAtPosition(position);
						new DeleteEmailTask(MainActivity.this, Authentication.getCurrentCredentials()).execute(
								((Message)obj).getId());
						
						mListView.removeView(view);
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				}).show();
				
			}
		});

		mListPrimaryFolderView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				setSelectedItemStyle(arg1);

				Folder folder =(Folder) mListPrimaryFolderView.getItemAtPosition(position);

				retrieveMesages(folder.getDisplayName());
			}				
		});

		mListSecondaryFolderView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				setSelectedItemStyle(arg1);

				Folder folder =(Folder) mListSecondaryFolderView.getItemAtPosition(position);

				retrieveMesages(folder.getDisplayName());
			}
		});

		retrieveMesages("Inbox");	

		setDrawerIconEvent();
	}

	void setSelectedItemStyle(View arg1) {
		if(mLastSelectedItem != null)
		{
			mLastSelectedItem.setBackgroundResource(R.color.white);
			mLastSelectedItem.setTextColor(Color.parseColor("#282828"));
		}
		else{
			TextView inbox =(TextView)mListPrimaryFolderView.getChildAt(0);
			inbox.setBackgroundResource(R.color.white);
			inbox.setTextColor(Color.parseColor("#282828"));
		}

		mLastSelectedItem = (TextView)arg1;
		mLastSelectedItem.setBackgroundResource(R.color.cyan);
		mLastSelectedItem.setTextColor(Color.parseColor("#FFFFFF"));
	}	

	void retrieveMesages(String folder) {
		if(!mMessages.containsKey(folder))
			getMessagesListActivity(folder);
		else{
			mListView.setAdapter(new MessageItemAdapter(this,mMessages.get(folder)));
		}

		((TextView) findViewById(R.id.Events)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectedItemStyle(v);
				getEventListActivity();

			}
		});
	}

	private void setDrawerIconEvent() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle =  new ActionBarDrawerToggle(this,
				mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();

				if(mLastSelectedItem == null){
					mLastSelectedItem = (TextView)mListPrimaryFolderView.getChildAt(0);
				}

				getActionBar().setTitle(mLastSelectedItem.getText());
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);		
	}

	private void getFolderListActivity() {
		ListenableFuture<Credentials> future = Authentication.authenticate(this, Constants.RESOURCE_ID);

		Futures.addCallback(future, new FutureCallback<Credentials>() {
			@Override
			public void onFailure(Throwable t) {
				Log.e("Asset", t.getMessage());
			}

			@Override
			public void onSuccess(Credentials credentials) {
				((TextView)findViewById(R.id.user)).setText(Authentication.getLogedUser());
				new RetrieveFoldersTask(MainActivity.this, Authentication.getCurrentCredentials()).execute();
			}
		});
	}

	private void setListMenu() {
		mListPrimaryFolderView = (ListView)findViewById(R.id.list_primary_foders);
		mListSecondaryFolderView = (ListView)findViewById(R.id.list_secondary_foders);
		if(mFolders == null)
			getFolderListActivity();
		else{
			mListPrimaryFolderView.setAdapter(new FolderItemAdapter(this,mFolders.get("Primary")));
			mListSecondaryFolderView.setAdapter(new FolderItemAdapter(this,mFolders.get("Secondary")));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		try {
			switch (item.getItemId() ) {
			case R.id.menu_clear_credentials:
				ClearCredentials();
				break;
			case R.id.menu_refresh_messages:
				getMessagesListActivity("");
				break;
			case R.id.menu_get_contacts:
				getContactListActivity();
				break;
			case R.id.menu_get_events:
				getEventListActivity();
				break;
			case R.id.menu_new_mail:
				newMailActivity();
				break;
			case R.id.menu_delete_message:
			
			default:
				return super.onOptionsItemSelected(item);
			}

		} catch (Throwable t) {
			Log.e("Asset", t.getMessage());
		}
		return true;
	}

	private void ClearCredentials() {
		CookieSyncManager syncManager = CookieSyncManager.createInstance(getApplicationContext());;
		if (syncManager != null) {
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.removeAllCookie();
			CookieSyncManager.getInstance().sync();
			Authentication.ResetToken(this);
		}		
	}	

	void getMessagesListActivity(final String folder){

		ListenableFuture<Credentials> future = Authentication.authenticate(this, Constants.RESOURCE_ID);

		Futures.addCallback(future, new FutureCallback<Credentials>() {
			@Override
			public void onFailure(Throwable t) {
				Log.e("Asset", t.getMessage());
			}

			@Override
			public void onSuccess(Credentials credentials) {

				new RetrieveMessagesTask(MainActivity.this, credentials).execute(folder);
			}
		});
	}

	void authenticate(){

		ListenableFuture<Credentials> future = Authentication.authenticate(this, Constants.RESOURCE_ID);

		Futures.addCallback(future, new FutureCallback<Credentials>() {
			@Override
			public void onFailure(Throwable t) {
				Log.e("Asset", t.getMessage());
			}

			@Override
			public void onSuccess(Credentials credentials) {
			}
		});
	}

	void getContactListActivity() {
		new RetrieveContactsTask(MainActivity.this, Authentication.getCurrentCredentials()).execute();
	}

	void getEventListActivity() {
		new RetrieveEventsTask(MainActivity.this, Authentication.getCurrentCredentials()).execute();
	}

	void newMailActivity() {
		Intent intent = new Intent(MainActivity.this, SendMailActivity.class);
		startActivity(intent);		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Authentication.context.onActivityResult(requestCode, resultCode, data);
	}

	public void setListAdapter(MessageItemAdapter adapter) {		
		mListView.setAdapter(adapter);		
	}

	public void setListAdapter(ContactItemAdapter adapter) {		
		mListView.setAdapter(adapter);		
	}

	public void setListAdapter(EventItemAdapter adapter) {
		mListView.setAdapter(adapter);			
	}
	public void setListAdapter(FolderItemAdapter adapter, FolderItemAdapter secondAdapter) {
		mListPrimaryFolderView.setAdapter(adapter);		
		mListSecondaryFolderView.setAdapter(secondAdapter);	
	}
}