package com.microsoft.office365.exchange;

import java.util.List;

import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.entities.Event;

public class EventClient extends BaseClient<Event>{

	public EventClient(Credentials credentials) {
		super(credentials);
	}

	public ListenableFuture<List<Event>> getEvents() {
		return getEvents(null);
	}

	public ListenableFuture<List<Event>> getEvents(String filter) {

		String url = Constants.BASE_URL + Constants.EVENTS_URL;

		return getList(url, filter, Event[].class);
	}
}