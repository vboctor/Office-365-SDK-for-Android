package com.microsoft.mailservice.adapters;

import java.util.List;
import microsoft.exchange.services.odata.model.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.microsoft.mailservice.MainActivity;
import com.microsoft.mailservice.R;

public class EventItemAdapter extends BaseAdapter{

	/** The inflater. */
	private static LayoutInflater inflater = null;
	private List<Event> mEvents;
	private MainActivity mActivity;
	
	public EventItemAdapter(MainActivity activity, List<Event> events) {
		mEvents = events;
		mActivity = activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mEvents.size();
	}

	@Override
	public Object getItem(int position) {
		return mEvents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.activity_event_list_item, null);
		Event event = mEvents.get(position);

		((TextView) view.findViewById(R.id.event_subject)).setText(event.getSubject());
		((TextView) view.findViewById(R.id.event_start)).setText(" Start On: " + event.getStart());
		((TextView) view.findViewById(R.id.event_end)).setText(" - End: " + event.getEnd());
		//((TextView) view.findViewById(R.id.contact_list_name)).setText(event.getLocation());
		//((TextView) view.findViewById(R.id.contact_list_name)).setText(event.get);
	
		return view;
	}
}