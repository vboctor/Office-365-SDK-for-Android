package com.microsoft.office365.api;

import com.microsoft.exchange.services.odata.model.Events;
import com.microsoft.exchange.services.odata.model.ICalendars;
import com.microsoft.exchange.services.odata.model.IEvents;
import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.ICalendar;
import com.microsoft.exchange.services.odata.model.types.IEvent;

public class CalendarClient extends BaseOfficeClient {
	
	Builder mBuilder;

	protected CalendarClient(Builder builder) {
		super(builder);
		
		mBuilder = builder;
	}

	public IEvent newEvent(ICalendar calendar) {

		if (calendar == null) {
			throw new IllegalArgumentException("calendar cannot be null");
		}

		IEvent event = Events.newEvent(calendar);
		return event;
	}
	

	public IEvents getEvents() {
		IEvents events = Me.getEvents();
		events.fetch();
		return events;
	}
	
	public IEvent getEvent(String eventId){
		IEvent event = Me.getEvents().get(eventId);
		return event;
	}
	
	public ICalendars getCalendars() {
		ICalendars calendars = Me.getCalendars();
		calendars.fetch();
		return calendars;
	}

	public ICalendar getCalendar(String calendarId) {

		ICalendar calendar = Me.getCalendars().get(calendarId);
		return calendar;
	}
}
