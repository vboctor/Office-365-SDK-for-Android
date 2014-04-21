package com.microsoft.office365.exchange;

import java.util.List;

import microsoft.exchange.services.odata.model.Folder;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.office365.Credentials;

public class FolderClient extends BaseClient<Folder>{

	public FolderClient(Credentials credentials) {
		super(credentials);
	}

	public ListenableFuture<List<Folder>> getFolders() {

		String url = Constants.BASE_URL + Constants.ROOTFOLDER_URL + Constants.CHILDFOLDERS_URL;

		return getList(url, null, Folder[].class);
	}

	public ListenableFuture<Folder> create(String parentFolderId, String displayName){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, parentFolderId) + Constants.CHILDFOLDERS_URL;
		JsonObject jObject = new JsonObject();
		jObject.addProperty("DisplayName", displayName);

		return execute(url, new Gson().toJson(jObject), Folder.class, "POST");
	}
	
	public ListenableFuture<Folder> move(String folderId, String toFolderId){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderId) + Constants.ACTION_MOVE;
		JsonObject jObject = new JsonObject();
		jObject.addProperty("DestinationId", toFolderId);

		return execute(url, new Gson().toJson(jObject), Folder.class, "POST");
	}
	
	public ListenableFuture<Folder> copy(String folderId, String toFolderId){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderId) + Constants.ACTION_COPY;
		JsonObject jObject = new JsonObject();
		jObject.addProperty("DestinationId", toFolderId);

		return execute(url, new Gson().toJson(jObject), Folder.class, "POST");
	}

	public ListenableFuture<Folder> update(Folder folder){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folder.getId());

		return execute(url, new Gson().toJson(folder), Folder.class, "PATCH");
	}
	
	public ListenableFuture<Folder> delete(String folderId){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderId);

		return execute(url, null, Folder.class, "DELETE");
	}
}