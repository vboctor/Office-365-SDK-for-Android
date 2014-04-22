package com.microsoft.office365.exchange;

import java.util.List;
import microsoft.exchange.services.odata.model.Event;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;

public class EventClient extends BaseClient<Event>{

	public EventClient(Credentials credentials) {
		super(credentials);
	}

	public ListenableFuture<List<Event>> getEvents(Query query) {

		String url = Constants.BASE_URL + Constants.EVENTS_URL;

		return getList(url, Event[].class, query);
	}

	public ListenableFuture<Event> create(Event event) {

		String url = Constants.BASE_URL + Constants.EVENTS_URL;

		return execute(url,new Gson().toJson(event), Event.class, Constants.METHOD_POST, null);
	}

	public ListenableFuture<Event> update(Event event) {

		String url = Constants.BASE_URL + Constants.EVENTS_URL;

		return execute(url,new Gson().toJson(event), Event.class, Constants.METHOD_PATCH, null);
	}

	public ListenableFuture<Event> delete(String eventId){
		String url = Constants.BASE_URL + "Events('" + eventId + "')";
		
		return execute(url, null, null, Constants.METHOD_DELETE, null);
	}
	
	public ListenableFuture<Event> accept(String eventId, String comments) {

		String url = Constants.BASE_URL + String.format(Constants.EVENT_BY_ID, eventId) + Constants.ACTION_ACCEPT;

		JsonObject jObject = new JsonObject();
		jObject.addProperty("Comment", comments);	

		return execute(url, new Gson().toJson(jObject), Event.class, Constants.METHOD_POST, null);
	}

	public ListenableFuture<Event> decline(String eventId, String comments)  {

		String url = Constants.BASE_URL + String.format(Constants.EVENT_BY_ID, eventId) + Constants.ACTION_DECLINE;

		JsonObject jObject = new JsonObject();
		jObject.addProperty("Comment", comments);	

		return execute(url, new Gson().toJson(jObject), Event.class, Constants.METHOD_POST, null);
	}

	public ListenableFuture<Event> tentative(String eventId, String comments) {

		String url = Constants.BASE_URL + String.format(Constants.EVENT_BY_ID, eventId) + Constants.ACTION_TENTATIVE;

		JsonObject jObject = new JsonObject();
		jObject.addProperty("Comment", comments);	

		return execute(url, new Gson().toJson(jObject), Event.class, Constants.METHOD_POST, null);
	}
}