package com.microsoft.mailservice.adapters;

import java.util.List;
import microsoft.exchange.services.odata.model.Contact;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.microsoft.mailservice.R;

public class ContactItemAdapter extends BaseAdapter{

	/** The inflater. */
	private static LayoutInflater inflater = null;
	private List<Contact> mContacts;
	private Activity mActivity;
	
	public ContactItemAdapter(Activity activity, List<Contact> contacts) {
		mContacts = contacts;
		mActivity = activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mContacts.size();
	}

	@Override
	public Object getItem(int position) {
		return mContacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.activity_contact_list_item, null);
		
		Contact contact = mContacts.get(position);

		((TextView) view.findViewById(R.id.contact_name)).setText(contact.getDisplayName());
		//((TextView) view.findViewById(R.id.contact_image)).setText(contact.get);
		((TextView) view.findViewById(R.id.contact_job_title)).setText(contact.getJobTitle());
	
		return view;
	}
}