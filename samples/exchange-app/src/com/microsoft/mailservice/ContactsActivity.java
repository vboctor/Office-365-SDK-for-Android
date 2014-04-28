package com.microsoft.mailservice;

import microsoft.exchange.services.odata.model.Contact;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.microsoft.mailservice.tasks.RetrieveContactsTask;
import com.microsoft.office365.Query;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ContactsActivity extends Activity {

	ListView mContactListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		new RetrieveContactsTask(ContactsActivity.this, Authentication.getCurrentCredentials(),
				new Query().select(Constants.CONTACT_FIELDS_TO_SELECT))
		.execute();

		mContactListView = (ListView)findViewById(R.id.contact_list);
		mContactListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

				view.setBackgroundResource(R.color.cyan);
				startActionMode(createActionCallback(position));
				return true;
			}
		});

		mContactListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {

				startContactActivity(position, false, "edit");							
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

	private void startContactActivity(int position, boolean editMode, String action) {
		Intent intent = new Intent(ContactsActivity.this, ContactActivity.class);
		JSONObject payload = new JSONObject();
		
		try {
			if(position != -1){
				Contact contact = (Contact)mContactListView.getItemAtPosition(position);
				payload.put("contact", new Gson().toJson(contact));
			}
			
			payload.put("editmode", editMode);
			payload.put("action", action);

			intent.putExtra("data", payload.toString());
			startActivity(intent);
		} catch (Throwable t) {
			Log.d(t.getMessage(), t.getStackTrace().toString());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_new_contact:
			startContactActivity(-1, true, "create");
			return true;
		default:
			return false;
		}
	}

	ActionMode.Callback createActionCallback(final int pos){
		return new ActionMode.Callback(){

			@Override
			public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.menu_contacts_delete:
					startContactActivity(pos, true, "delete");
					return true;
				case R.id.menu_contacts_edit:
					startContactActivity(pos, true, "edit");	
					return true;
				default:
					return false;
				}
			}

			private void removeContact() {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.contacts_context, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}};
	}
}