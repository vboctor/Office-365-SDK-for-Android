package com.microsoft.mailservice.adapters;

import java.util.List;
import microsoft.exchange.services.odata.model.Folder;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.MainActivity;

public class FolderItemAdapter extends BaseAdapter{

	/** The inflater. */
	private static LayoutInflater inflater = null;
	private List<Folder> mFolder;
	private MainActivity mActivity;
	
	public FolderItemAdapter(MainActivity activity, List<Folder> folders) {
		mFolder = folders;
		mActivity = activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mFolder.size();
	}

	@Override
	public Object getItem(int position) {
		return mFolder.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.drawer_list_item, null);
		Folder folder = mFolder.get(position);
		String count = "";
		
		TextView tv = (TextView)view.findViewById(R.id.folder_name);
		TextView tc = (TextView)view.findViewById(R.id.folder_item_count);

		
		if(folder.getDisplayName().equals("Inbox"))
		{tc.setBackgroundResource(R.color.soft_red);}
		if(folder.getDisplayName().equals("Drafts")){tc.setBackgroundResource(R.color.soft_orange);}
		else if(folder.getDisplayName().equals("Deleted Items")){tc.setBackgroundResource(R.color.soft_green);}
		else if(folder.getDisplayName().equals("Sent Items")){tc.setBackgroundResource(R.color.soft_violet);}
		
		if(folder.getDisplayName().equals("Inbox")){
			count = " (" + folder.getUnreadItemCount() + ")";
			tv.setBackgroundResource(R.color.cyan);
			tv.setTextColor(Color.parseColor("#FFFFFF"));
		}
		else if(folder.getDisplayName().equals("Deleted Items"))
			count = " (" + folder.getUnreadItemCount() + ")";
		else
			count =  " (" + folder.getTotalCount() + ")";
		
		tv.setText(folder.getDisplayName());
		tc.setText(count);
		return view;
	}
}