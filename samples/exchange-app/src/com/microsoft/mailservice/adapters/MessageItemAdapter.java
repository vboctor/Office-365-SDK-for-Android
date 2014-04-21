package com.microsoft.mailservice.adapters;

import java.util.List;
import microsoft.exchange.services.odata.model.Message;
import microsoft.exchange.services.odata.model.Recipient;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.MainActivity;

public class MessageItemAdapter extends BaseAdapter{

	/** The inflater. */
	private static LayoutInflater inflater = null;
	private List<Message> mMessages;
	private MainActivity mActivity;
	
	public MessageItemAdapter(MainActivity activity, List<Message> messages) {
		mMessages = messages;
		mActivity = activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.activity_mail_list_item, null);
		Message message = mMessages.get(position);
		Recipient sender = message.getSender();
		
		String subject = message.getSubject();
		((TextView) view.findViewById(R.id.sender)).setText(sender == null ? "" : sender.getName());
		((TextView) view.findViewById(R.id.subject)).setText(subject.length() > 30 ? subject.substring(0, 30) + "..." : subject);
		((TextView) view.findViewById(R.id.sendOn)).setText(message.getDateTimeSent().toString());
	
		return view;
	}

}
