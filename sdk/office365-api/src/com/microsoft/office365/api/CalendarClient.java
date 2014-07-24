package com.microsoft.office365.api;

import com.microsoft.exchange.services.odata.model.Events;
import com.microsoft.exchange.services.odata.model.ICalendars;
import com.microsoft.exchange.services.odata.model.IEvents;
import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.ICalendar;
import com.microsoft.exchange.services.odata.model.types.IEvent;
import com.microsoft.office365.http.OAuthCredentials;

/**
 * The Class CalendarClient.
 */
public class CalendarClient extends BaseOfficeClient {
	
	Builder mBuilder;

	protected CalendarClient(Builder builder) {
		super(builder);
		
		mBuilder = builder;
	}

	/**
	 * New event.
	 *
	 * @param calendar the calendar
	 * @return the event
	 */
	public IEvent newEvent(ICalendar calendar) {

		if (calendar == null) {
			throw new IllegalArgumentException("calendar cannot be null");
		}
		IEvent event = Events.newEvent(calendar);
		return event;
	}
	

	/**
	 * Gets the events.
	 *
	 * @return the events
	 */
	public IEvents getEvents() {
		IEvents events = Me.getEvents();
		events.fetch();
		return events;
	}
	
	/**
	 * Gets the event.
	 *
	 * @param eventId the event id
	 * @return the event
	 */
	public IEvent getEvent(String eventId){
		IEvent event = Me.getEvents().get(eventId);
		return event;
	}
	
	/**
	 * Gets the calendars.
	 *
	 * @return the calendars
	 */
	public ICalendars getCalendars() {
		ICalendars calendars = Me.getCalendars();
		calendars.fetch();
		return calendars;
	}

	/**
	 * Gets the calendar.
	 *
	 * @param calendarId the calendar id
	 * @return the calendar
	 */
	public ICalendar getCalendar(String calendarId) {
		ICalendar calendar = Me.getCalendars().get(calendarId);
		return calendar;
	}
	
	/**
	 * The Class Builder.
	 */
	public static final class Builder extends BaseOfficeClient.Builder {

		/**
		 * Instantiates a new builder.
		 */
		public Builder() {
			super();
		}

		/**
		 * Instantiates a new builder.
		 *
		 * @param credentials the credentials
		 * @param resourceId the resource id
		 * @param odataEndpoint the odata endpoint
		 */
		public Builder(OAuthCredentials credentials, String resourceId, String odataEndpoint) {
			super(credentials, resourceId, odataEndpoint);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#build()
		 */
		@Override
		public CalendarClient build() {
			return new CalendarClient(this);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setCredentials(com.microsoft.office365.http.OAuthCredentials)
		 */
		@Override
		public Builder setCredentials(OAuthCredentials credentials) {
			return (Builder) super.setCredentials(credentials);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setOdataEndpoint(java.lang.String)
		 */
		@Override
		public Builder setOdataEndpoint(String odataEndpoint) {
			return (Builder) super.setOdataEndpoint(odataEndpoint);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setResourceId(java.lang.String)
		 */
		@Override
		public Builder setResourceId(String resourceId) {
			return (Builder) super.setResourceId(resourceId);
		}
	}
}
