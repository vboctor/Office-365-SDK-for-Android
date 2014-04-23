/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import microsoft.exchange.services.odata.model.Folder;
import microsoft.exchange.services.odata.model.Message;
import org.json.JSONObject;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.adapters.EventItemAdapter;
import com.microsoft.mailservice.adapters.FolderItemAdapter;
import com.microsoft.mailservice.adapters.MessageItemAdapter;
import com.microsoft.mailservice.tasks.RetrieveEventsTask;
import com.microsoft.mailservice.tasks.RetrieveFoldersTask;
import com.microsoft.mailservice.tasks.RetrieveMessagesTask;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;
import com.microsoft.office365.exchange.MailClient;
import android.support.v4.widget.SwipeRefreshLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

	public static ListView mMailListView;
	ListView mListPrimaryFolderView;
	ListView mListSecondaryFolderView;
	Folder mLastSelectedFolder;
	TextView mFolderTextView;
	SwipeRefreshLayout swipeRefreshLayout;
	
	static Map<String,List<Message>> mMessages = new HashMap<String,List<Message>>();
	static Map<String,List<Folder>> mFolders;

	DrawerLayout mDrawerLayout;
	ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Authentication.createEncryptionKey(getApplicationContext());
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		setListMenu();
		
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_to_refresh);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
				android.R.color.white, android.R.color.holo_blue_light,
				android.R.color.white);
		
		mMailListView = (ListView)findViewById(R.id.mail_list);
		mMailListView.setOnItemClickListener(mailListOnItemClick());
		mMailListView.setOnScrollListener(maillistOnScroll());

		mMailListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

				view.setBackgroundResource(R.color.cyan);
				startActionMode(new ActionModeCallback(MainActivity.this,view,position));
				return true;
			}
		});

		mListPrimaryFolderView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

				setSelectedItemStyle(view);
				mLastSelectedFolder =(Folder) mListPrimaryFolderView.getItemAtPosition(position);
				retrieveMesages(mLastSelectedFolder.getId());
			}				
		});

		mListSecondaryFolderView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				setSelectedItemStyle(arg1);
				mLastSelectedFolder =(Folder) mListSecondaryFolderView.getItemAtPosition(position);
				retrieveMesages(mLastSelectedFolder.getId());

			}
		});

		retrieveMesages("Inbox");	

		setDrawerIconEvent();
	}

	private OnScrollListener maillistOnScroll() {
		return new OnScrollListener() {

			boolean isRetrivingData;
			boolean shouldCall = true;
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState == SCROLL_STATE_TOUCH_SCROLL){

				}
			}
			
			@Override
			public void onScroll(final AbsListView view, int firstVisibleItem,
					int visibleItemCount, final int totalItemCount) {

				if(totalItemCount > 0 && (firstVisibleItem + visibleItemCount)>= totalItemCount){

					if(!isRetrivingData && shouldCall){
						isRetrivingData = true;
						AsyncTask<Void, Void, List<Message>> task = new AsyncTask<Void, Void, List<Message>>(){

							@Override
							protected void onPostExecute(List<Message> result) {
								super.onPostExecute(result);

								MessageItemAdapter adapter = (MessageItemAdapter)view.getAdapter();
								adapter.addMoreItems(result);
								adapter.notifyDataSetChanged();
								isRetrivingData = false;
								findViewById(R.id.load_more).setVisibility(8);
								
								if(result.size() == 0)shouldCall = false;
							}
							
							@Override
							protected void onPreExecute() {
								super.onPreExecute();
								
								findViewById(R.id.load_more).setVisibility(0);
							}

							@Override
							protected List<Message> doInBackground(Void... v) {
								List<Message> messages = new ArrayList<Message>();

								try {
									MailClient client = new MailClient(Authentication.getCurrentCredentials());
									Query query = new Query();

									query = query.top(40).skip(totalItemCount).select(Constants.FIELDS_TO_SELECT);
									String folder = mLastSelectedFolder != null ? mLastSelectedFolder.getDisplayName() : "Inbox";
									messages = client.getMessages(folder, query).get();

								} catch (Exception e) {
									isRetrivingData = false;
									Toast.makeText(MainActivity.this, "Error getting Messsages", Toast.LENGTH_SHORT).show();
								}

								return messages;
							}
						};
					
						task.execute();
					}
				}
			}
		};
	}

	OnItemClickListener mailListOnItemClick() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
				
				if(((Message)MainActivity.mMailListView.getItemAtPosition(position)).getId() != ""){
					return;
				}
				
				Intent intent = new Intent(MainActivity.this, MailActivity.class);
				arg1.setBackgroundResource(R.color.cyan);
				JSONObject payload = new JSONObject();
				try {

					payload.put("position", position);
					intent.putExtra("data", payload.toString());
					startActivity(intent);
				} catch (Throwable t) {
				}				
			}
		};
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Authentication.context.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		try {
			switch (item.getItemId() ) {
			case R.id.menu_clear_credentials:
				((ExchangeAPIApplication)getApplication()).clearPreferences(this);
				break;
			case R.id.menu_refresh_messages:
				String folder = mLastSelectedFolder != null ? mLastSelectedFolder.getDisplayName() : "Inbox";
				getMessagesListActivity(folder);
				break;
			case R.id.menu_get_contacts:
				//getContactListActivity();
				break;
			case R.id.menu_get_events:
				getEventListActivity();
				break;
			case R.id.menu_new_mail:
				newMailActivity();
				break;
			case R.id.menu_delete_message:
				break;

			default:
				if (mDrawerToggle.onOptionsItemSelected(item)) {
					return true;
				}else{
					return super.onOptionsItemSelected(item);
				}
			}

		} catch (Throwable t) {
			Log.e("Asset", t.getMessage());
		}
		return true;
	}

	public void setMessages(String folderId,List<Message> messages){
		mMessages.put(folderId, messages);
	}

	public void setFolders(Map<String,List<Folder>> folders){
		mFolders = folders;
	}

	@Override
	public void deleteMessage(String folderId, String messageId){

		Map<String,List<Message>> messages = new HashMap<String,List<Message>>();

		for(String f : mMessages.keySet()){
			messages.put(f, new ArrayList<Message>());
			List<Message> currentMessages = mMessages.get(f);
			for(Message message : currentMessages){
				if(!message.getId().equals(messageId)){
					messages.get(f).add(message);
				}
			}
		}

		mMessages = messages;

		retrieveMesages(folderId);
	}

	public void setListAdapter(MessageItemAdapter adapter) {		
		mMailListView.setAdapter(adapter);		
	}

	public void setListAdapter(EventItemAdapter adapter) {
		mMailListView.setAdapter(adapter);			
	}

	public void setListAdapter(FolderItemAdapter adapter, FolderItemAdapter secondAdapter) {
		mListPrimaryFolderView.setAdapter(adapter);		
		mListSecondaryFolderView.setAdapter(secondAdapter);	
	}

	//	void getContactListActivity() {
	//		new RetrieveContactsTask(MainActivity.this, Authentication.getCurrentCredentials()).execute();
	//	}

	void getEventListActivity() {
		new RetrieveEventsTask(MainActivity.this, Authentication.getCurrentCredentials()).execute();
	}

	void newMailActivity() {
		Intent intent = new Intent(MainActivity.this, SendMailActivity.class);
		startActivity(intent);		
	}

	void setSelectedItemStyle(View arg1) {

		if(mFolderTextView != null)
		{
			mFolderTextView.setBackgroundResource(R.color.white);
			mFolderTextView.setTextColor(Color.parseColor("#282828"));
		}
		else{
			TextView inbox =(TextView)mListPrimaryFolderView.getChildAt(0);
			inbox.setBackgroundResource(R.color.white);
			inbox.setTextColor(Color.parseColor("#282828"));
		}

		mFolderTextView = (TextView)arg1;
		mFolderTextView.setBackgroundResource(R.color.cyan);
		mFolderTextView.setTextColor(Color.parseColor("#FFFFFF"));
	}	

	void retrieveMesages(String folder) {

		if(!mMessages.containsKey(folder))
			getMessagesListActivity(folder);
		else{
			mMailListView.setAdapter(new MessageItemAdapter(this,mMessages.get(folder)));
		}

		((TextView) findViewById(R.id.Calendar)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectedItemStyle(v);
				getEventListActivity();
			}
		});

		if(mDrawerToggle != null){
			mDrawerLayout.closeDrawers();
			mDrawerToggle.syncState();			
		}
	}

	void setDrawerIconEvent() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle =  new ActionBarDrawerToggle(this,
				mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();

				if(mLastSelectedFolder != null){
					getActionBar().setTitle(mLastSelectedFolder.getDisplayName());
				}
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);		
	}

	void getFolderListActivity() {
		ListenableFuture<Credentials> future = Authentication.authenticate(this, Constants.RESOURCE_ID);

		Futures.addCallback(future, new FutureCallback<Credentials>() {
			@Override
			public void onFailure(Throwable t) {
				Log.e("Asset", t.getMessage());
			}

			@Override
			public void onSuccess(Credentials credentials) {
				((TextView)findViewById(R.id.user)).setText(Authentication.getLogedUser());
				new RetrieveFoldersTask(MainActivity.this, credentials).execute();
			}
		});
	}

	void setListMenu() {
		mListPrimaryFolderView = (ListView)findViewById(R.id.list_primary_foders);
		mListSecondaryFolderView = (ListView)findViewById(R.id.list_secondary_foders);

		if(mFolders == null)
			getFolderListActivity();
		else{
			mListPrimaryFolderView.setAdapter(new FolderItemAdapter(this,mFolders.get("Primary")));
			mListSecondaryFolderView.setAdapter(new FolderItemAdapter(this,mFolders.get("Secondary")));
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
				Query query = new Query();

				query = query.top(40).select(Constants.FIELDS_TO_SELECT);
				new RetrieveMessagesTask(MainActivity.this, credentials,query).execute(folder);
			}
		});
	}

	@Override
	public void onRefresh() {
	
		AsyncTask<Void, Void, List<Message>> task = new AsyncTask<Void, Void, List<Message>>(){

			@Override
			protected void onPostExecute(List<Message> result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				MessageItemAdapter adapter = (MessageItemAdapter)mMailListView.getAdapter();
				adapter.addMoreItemsToTop(result);
				adapter.notifyDataSetChanged();
		
				//findViewById(R.id.load_more).setVisibility(8);
				swipeRefreshLayout.setRefreshing(false);
			}
			
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				//findViewById(R.id.load_more).setVisibility(0);
			}

			@Override
			protected List<Message> doInBackground(Void... v) {
				List<Message> messages = new ArrayList<Message>();

				try {
					MailClient client = new MailClient(Authentication.getCurrentCredentials());
					Query query = new Query();

					query = query.top(40).select(Constants.FIELDS_TO_SELECT);
					
					query.setQueryText("$filter=LastModifiedTime%20gt%20" + ((Message)mMailListView.getItemAtPosition(0)).getLastModifiedTime() + "&");
					String folder = mLastSelectedFolder != null ? mLastSelectedFolder.getDisplayName() : "Inbox";
					messages = client.getMessages(folder, query).get();

				} catch (Exception e) {
					//isRetrivingData = false;
					Toast.makeText(MainActivity.this, "Error getting Messsages", Toast.LENGTH_SHORT).show();
				}

				return messages;
			}
		};
	
		task.execute();
		
//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				stopSwipeRefresh();
//			}
//		}, REFRESH_TIME_IN_SECONDS * 1000);
	}
	private void stopSwipeRefresh() {
		
	}
}