package com.microsoft.office365.exchange;

import java.util.List;

import microsoft.exchange.services.odata.model.Attachment;
import microsoft.exchange.services.odata.model.Folder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;

public class FolderClient extends BaseClient<Folder>{
	
	public FolderClient(Credentials credentials) {
		super(credentials);
	}
	
	public ListenableFuture<Folder> getRootFolder(Query query) {
		String url = Constants.BASE_URL + Constants.FOLDER_ROOT_FOLDER;

		return execute(url, null, Folder.class, Constants.METHOD_GET, query);
	}
	
	public ListenableFuture<Folder> getInbox(Query query)  {
		String url = Constants.BASE_URL + Constants.FOLDER_INBOX;

		return execute(url, null, Folder.class, Constants.METHOD_GET, query);
	}
	
	public ListenableFuture<Folder> getDraftsFolder(Query query)  {
		String url = Constants.BASE_URL + Constants.FOLDER_DRAFTS;

		return execute(url, null, Folder.class, Constants.METHOD_GET, query);
	}
	
	public ListenableFuture<Folder> getSentItemsFolder(Query query) {
		String url = Constants.BASE_URL + Constants.FOLDER_SEND_ITEMS;

		return execute(url, null, Folder.class, Constants.METHOD_GET, query);
	}
	
	public ListenableFuture<Folder> getDeletedItemsFolder(Query query) {
		String url = Constants.BASE_URL + Constants.FOLDER_DELETED_ITEMS;

		return execute(url, null, Folder.class, Constants.METHOD_GET, query);
	}

	public ListenableFuture<List<Folder>> getFolder(String folderId ,Query query) {

		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderId);

		return getList(url, Folder[].class, query);
	}
	
	public ListenableFuture<List<Folder>> getChildFolders(Folder folder ,Query query) {

		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folder.getId()) + Constants.CHILDFOLDERS_URL;

		return getList(url, Folder[].class, query);
	}
	
	public ListenableFuture<List<Folder>> getFolders(Query query) {

		String url = Constants.BASE_URL + Constants.ROOTFOLDER_URL + Constants.CHILDFOLDERS_URL;

		return getList(url, Folder[].class, query);
	}

	public ListenableFuture<Folder> create(String parentFolderId, String displayName){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, parentFolderId) + Constants.CHILDFOLDERS_URL;
		JsonObject jObject = new JsonObject();
		jObject.addProperty("DisplayName", displayName);

		return execute(url, new Gson().toJson(jObject), Folder.class, "POST", null);
	}
	
	public ListenableFuture<Folder> update(Folder folder){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folder.getId());

		return execute(url, new Gson().toJson(folder), Folder.class, "PATCH", null);
	}

	@Override
	public ListenableFuture<Folder> move(String folderId, String toFolderId){
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderId) + Constants.ACTION_MOVE;
		JsonObject jObject = new JsonObject();
		jObject.addProperty("DestinationId", toFolderId);

		return execute(url, new Gson().toJson(jObject), Folder.class, "POST", null);
	}

	@Override
	public ListenableFuture<Folder> copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<String> delete(String folderId) {
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderId);

		return execute(url, null, Constants.METHOD_DELETE);		
	}

	@Override
	public ListenableFuture<List<Attachment>> getAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<Attachment> getAttachment(Folder item) {
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