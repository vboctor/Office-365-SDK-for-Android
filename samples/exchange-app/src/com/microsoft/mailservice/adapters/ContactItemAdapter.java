//package com.microsoft.mailservice.adapters;
//
//import java.util.List;
//
//import microsoft.exchange.services.odata.model.Contact;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//import com.microsoft.mailservice.ContactsActivity;
//import com.microsoft.mailservice.R;
//
//public class ContactItemAdapter extends FragmentStatePagerAdapter {
//
//	/** The inflater. */
//	private static LayoutInflater inflater = null;
//	private List<Contact> mContacts;
//	private ContactsActivity mActivity;
//	
//	public ContactItemAdapter(FragmentManager fm, ContactsActivity activity, List<Contact> contacts) 
//	{
//		super(fm);
//		mContacts = contacts;
//		mActivity = activity;
//		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	@Override
//	public int getCount() {
//		return mContacts.size();
//	}
//
//	@Override
//	public Fragment getItem(int position) {
//		
//		 Fragment fragment = new DemoObjectFragment();
//	        Bundle args = new Bundle();
//	        // Our object is just an integer :-P
//	        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
//	        fragment.setArguments(args);
//	        return fragment;
//	        
//		return ;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		
//		View view = convertView;
//		if (convertView == null)
//			view = inflater.inflate(R.layout.activity_contact_list_item, null);
//		Contact contact = mContacts.get(position);
//
//		((TextView) view.findViewById(R.id.contact_list_name)).setText(contact.getDisplayName());
//	
//		return view;
//	}
//}