package com.microsoft.office365.exchange;

import java.util.List;

import microsoft.exchange.services.odata.model.Attachment;
import microsoft.exchange.services.odata.model.Event;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;

public class CalendarClient extends BaseClient<Event>{

	public CalendarClient(Credentials credentials) {
		super(credentials);
		//setAttachmentUrl(Constants.BASE_URL + Constants.EVENT_BY_ID);
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

	@Override
	public ListenableFuture<Event> copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<Event> move(String itemToMoveId, String moveToId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<String> delete(String eventId){
		String url = Constants.BASE_URL + "Events('" + eventId + "')";
		
		return execute(url, null, Constants.METHOD_DELETE);
	}

	@Override
	public ListenableFuture<List<Attachment>> getAttachments(Event item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<Attachment> getAttachment(Event item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<Attachment> addAttachment(Attachment attachment,
			String itemId) {
		// TODO Auto-generated method stub
		return null;
	}
}