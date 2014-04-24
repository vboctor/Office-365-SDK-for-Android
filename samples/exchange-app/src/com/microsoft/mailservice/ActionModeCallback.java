package com.microsoft.mailservice;

import org.json.JSONObject;
import microsoft.exchange.services.odata.model.Folder;
import microsoft.exchange.services.odata.model.Message;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.google.gson.Gson;
import com.microsoft.mailservice.tasks.DeleteEmailTask;
import com.microsoft.mailservice.tasks.MoveEmailTask;

public class ActionModeCallback implements ActionMode.Callback {
	
	View mView;
	int mPosition;
	ListView mListView;
	BaseActivity mActivity;
	ListView mListPrimaryFolderView;

	public ActionModeCallback(BaseActivity activity, 
			final View view, final int position){
		
		mListPrimaryFolderView = ((ListView)activity.findViewById(R.id.list_primary_foders));
		mListView = ((ListView)activity.findViewById(R.id.mail_list));
		mView = view;
		mPosition = position;
		mActivity = activity;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		mode.getMenuInflater().inflate(R.menu.context_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_delete:
			removeRow();
			mode.finish();
			return true;
		case R.id.menu_reply:
			reply();
			return true;
		default:
			return false;
		}
	}
	
	void reply(){
			Intent intent = new Intent(mActivity, SendMailActivity.class);
			JSONObject payload = new JSONObject();
			try {
				Message message = (Message)mListView.getItemAtPosition(mPosition);
				payload.put("message", new Gson().toJson(message));
				payload.put("position", mPosition);
				payload.put("action", "replay");
				intent.putExtra("data", payload.toString());
				mActivity.startActivity(intent);
			} catch (Throwable t) {
				Log.d(t.getMessage(), t.getStackTrace().toString());
			}				
	}

	void removeRow() {

		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setMessage("Delete Mail?")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int id) {
				Message message = (Message)mListView.getItemAtPosition(mPosition);


				if(message.getParentFolderId().equals("DeletedItems")){
					new DeleteEmailTask(mActivity, Authentication.getCurrentCredentials())
					.execute(new String[]{ message.getParentFolderId() ,	message.getId()});
				}
				else{
					new MoveEmailTask(mActivity, Authentication.getCurrentCredentials(), "Deleting Message...")
					.execute(new String[]{message.getParentFolderId() ,
							message.getId(), ((Folder)mListPrimaryFolderView.getItemAtPosition(2)).getId(), 
					"Message Deleted."});
				}

			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				return;
			}
		}).show();
	}

	@Override
	public void onDestroyActionMode(ActionMode arg0) {
		// TODO Auto-generated method stub
	}
}