package com.microsoft.office365.exchange;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import microsoft.exchange.services.odata.model.Attachment;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.OfficeClient;
import com.microsoft.office365.Query;

public abstract class BaseClient<V> extends OfficeClient {

	private GsonBuilder mBuilder = new GsonBuilder();

	public abstract ListenableFuture<V> copy();
	
	public abstract ListenableFuture<V> move(String itemToMoveId, String moveToId);
	
	public abstract ListenableFuture<String> delete(String itemId);
	
	public abstract ListenableFuture<List<Attachment>> getAttachments(V item);
	
	public abstract ListenableFuture<Attachment> getAttachment(V item);

	public abstract ListenableFuture<Attachment> addAttachment(Attachment attachment, String itemId);
	
	public BaseClient(Credentials credentials) {
		super(credentials);
	}

	public ListenableFuture<List<V>> getList(String url, final Class<V[]> type, Query query) {
		final SettableFuture<List<V>> future = SettableFuture.create();

		if(query != null) url += generateODataQueryString(query);

		Map<String, String> headers = new HashMap<String, String>();

		headers.put("Accept", "application/json;odata.metadata=full");
		headers.put("Content-Type", "application/json;odata.metadata=full");
		headers.put("Expect", "100-continue");

		ListenableFuture<JSONObject> requestFuture = this.executeRequestJson(url, "GET",headers,null);

		Futures.addCallback(requestFuture, new FutureCallback<JSONObject>() {
			@Override
			public void onFailure(Throwable error) {
				future.setException(error);
			}

			@Override
			public void onSuccess(JSONObject result) {
				if (result != null) {
					Gson gson = mBuilder.create();
					String json = null;
					try {
						json = result.getJSONArray("value").toString();
					} catch (JSONException e) {
						future.setException(e);
						return;
					}

					List<V> entity = Arrays.asList(gson.fromJson(json, type));
					future.set(entity);
				} else {
					future.set(null);
				}
			}
		});

		return future;
	}

	public ListenableFuture<String> execute(String url,V entity, String method) {
		final SettableFuture<String> future = SettableFuture.create();

		Gson gson = new Gson();
		String json = entity != null ? gson.toJson(entity) : null;

		Map<String, String> headers = new HashMap<String, String>();

		headers.put("Accept", "application/json;odata.metadata=full");
		headers.put("Content-Type", "application/json;odata.metadata=full");
		headers.put("Expect", "100-continue");

		ListenableFuture<JSONObject> requestFuture = this.executeRequestJson(url, method, headers, getBytes(json));//(url, "PUT",);

		Futures.addCallback(requestFuture, new FutureCallback<JSONObject>() {
			@Override
			public void onFailure(Throwable error) {
				future.setException(error);
			}

			@Override
			public void onSuccess(JSONObject result) {
				if (result != null) {
					String id = null;
					try {
						id = result.get("Id").toString();
					} catch (JSONException e) {
						future.setException(e);
						return;
					}

					future.set(id);
				} else {
					future.set("success");
				}
			}
		});

		return future;
	}

	public ListenableFuture<V> execute(String url, String json, final Class<V> type, String method, Query query) {
		final SettableFuture<V> future = SettableFuture.create();

		if(query != null) url += generateODataQueryString(query);

		Map<String, String> headers = new HashMap<String, String>();

		headers.put("Accept", "application/json;odata.metadata=full");
		headers.put("Content-Type", "application/json;odata.metadata=full");
		headers.put("Expect", "100-continue");

		ListenableFuture<JSONObject> requestFuture = this.executeRequestJson(url, method, headers, getBytes(json));//(url, "PUT",);

		Futures.addCallback(requestFuture, new FutureCallback<JSONObject>() {
			@Override
			public void onFailure(Throwable error) {
				future.setException(error);
			}

			@Override
			public void onSuccess(JSONObject result) {
				if (result != null) {
					Gson gson = mBuilder.create();
					String json = result.toString();

					V entity = (V) gson.fromJson(json, type);
					future.set(entity);
				} else {
					future.set(null);
				}
			}
		});

		return future;
	}

	private byte[] getBytes(String s) {
		if(s == null) return null;

		try {
			return s.getBytes(com.microsoft.office365.Constants.UTF8_NAME);
		} catch (UnsupportedEncodingException e) {
			return s.getBytes();
		}
	}	

	//TODO: Review the base query
	protected String generateODataQueryString(Query query) {
		StringBuilder sb = new StringBuilder();

		if (query != null) {
			String queryText = query.getQueryText();

			String rowSetModifiers = "?" + ((queryText != null || queryText != "") ? queryText : "")+ query.getRowSetModifiers().trim().substring(1);
			sb.append(rowSetModifiers);
		}

		return sb.toString();
	}
}