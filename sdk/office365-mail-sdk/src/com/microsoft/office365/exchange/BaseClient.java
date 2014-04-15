package com.microsoft.office365.exchange;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public abstract class BaseClient<V> extends OfficeClient {

	private GsonBuilder mBuilder = new GsonBuilder();

	public BaseClient(Credentials credentials) {
		super(credentials);
	}
/*
	public ListenableFuture<Void> execute(String url,String filter, final Class<V[]> type, String method) {
		final SettableFuture<Void> future = SettableFuture.create();

		if(filter != null) url += filter;

		ListenableFuture<JSONObject> requestFuture = this.executeRequestJson(url, method);

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
//					List<V> contact = Arrays.asList(gson.fromJson(json, type));
//					future.set(contact);
					future.set(null);
				} else {
					future.set(null);
				}
			}
		});
		return future;
	}*/
	
	public ListenableFuture<List<V>> getList(String url,String filter, final Class<V[]> type) {
		final SettableFuture<List<V>> future = SettableFuture.create();

		if(filter != null) url += filter;

		ListenableFuture<JSONObject> requestFuture = this.executeRequestJson(url, "GET");

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

	public ListenableFuture<String> post(String url,V entity) {
		final SettableFuture<String> future = SettableFuture.create();

		Gson gson = new Gson();
		String json = entity != null ? gson.toJson(entity) : null;

		Map<String, String> headers = new HashMap<String, String>();

		headers.put("Accept", "application/json;odata.metadata=full");
		headers.put("Content-Type", "application/json;odata.metadata=full");
		headers.put("Expect", "100-continue");

		ListenableFuture<JSONObject> requestFuture = this.executeRequestJson(url, "POST", headers, getBytes(json));//(url, "PUT",);

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
	
	public ListenableFuture<V> execute(String url, String json, final Class<V> type, String method) {
		final SettableFuture<V> future = SettableFuture.create();

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
}
