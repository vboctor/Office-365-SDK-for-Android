/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.exchange.services.odata.model.types.IMessage;
import com.microsoft.exchange.services.odata.model.types.Recipient;
import com.microsoft.mailservice.R;
import com.microsoft.mailservice.MainActivity;
import com.msopentech.odatajclient.engine.data.ODataTimestamp;

public class MessageItemAdapter extends BaseAdapter {

	/** The inflater. */
	private static LayoutInflater inflater = null;
	private List<IMessage> mIMessages;
	private MainActivity mActivity;

	public MessageItemAdapter(MainActivity activity, List<IMessage> IMessages) {
		mIMessages = IMessages;
		mActivity = activity;
		inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mIMessages.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= mIMessages.size()) {
			return null;
		}
		return mIMessages.get(position);
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

		IMessage IMessage = mIMessages.get(position);
		Recipient sender = IMessage.getSender();
		String subject = IMessage.getSubject();
		ODataTimestamp date = IMessage.getDateTimeSent(); // TODO:it was a
															// string.

		((TextView) view.findViewById(R.id.sender)).setText(sender == null ? "" : sender.getName());
		((TextView) view.findViewById(R.id.subject)).setText(sender == null ? "" : (subject.length() > 30 ? subject
				.substring(0, 30) + "..." : subject));
		((TextView) view.findViewById(R.id.sendOn)).setText(date == null ? "" : IMessage.getDateTimeSent().toString());

		if (sender != null && IMessage.getId() != null) {

			final Resources res = mActivity.getResources();
			final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

			final LetterTileProvider tileProvider = new LetterTileProvider(mActivity);
			final Bitmap letterTile = tileProvider.getLetterTile(sender.getName().substring(0, 1), sender.getName(),
					tileSize, tileSize);

			((ImageView) view.findViewById(R.id.initials)).setImageBitmap(letterTile);
		}
		return view;
	}

	public void addMoreItems(List<IMessage> result) {

		List<IMessage> IMessages = new ArrayList<IMessage>();

		for (IMessage m : mIMessages) {

			if (m.getId() != null && m.getId() != "")
				IMessages.add(m);
		}

		for (IMessage m : result) {
			IMessages.add(m);
		}

		// IMessages.add(new IMessage()); TODO:Review
		mIMessages = IMessages;
	}

	public void addMoreItemsToTop(List<IMessage> result) {

		if (result == null) {
			return;
		}
		List<IMessage> IMessages = new ArrayList<IMessage>();

		for (int i = 0; i < result.size() - 1; i++) {
			IMessages.add(result.get(i));
		}

		for (IMessage m : mIMessages) {
			if (m.getId() != null && m.getId() != "")
				IMessages.add(m);
		}

		// IMessages.add(new IMessage()); //TODO:Review

		mIMessages = IMessages;
	}

	public void clear() {
		mIMessages.clear();
	}
}