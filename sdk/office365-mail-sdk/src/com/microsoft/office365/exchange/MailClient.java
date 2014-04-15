/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information.
 ******************************************************************************/
package com.microsoft.office365.exchange;

import java.util.List;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.entities.Folder;
import com.microsoft.office365.mail.entities.Message;

/**
 * The Class MailClient.
 */
public class MailClient extends BaseClient<Message>{

	public MailClient(Credentials credentials){
		super(credentials);
	}

	public ListenableFuture<List<Message>> getMessages(Folder folder) {
		return getMessages(folder.getId());
	}

	public ListenableFuture<List<Message>> getMessages(String folderNameOrId) {
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderNameOrId) + Constants.MESSAGES_URL;

		return getList(url, null, Message[].class);
	}

	public ListenableFuture<String> createMessage(Message entity) {
		String url = Constants.BASE_URL + Constants.CREATE_MESSAGE_URL;

		return post(url,  entity);
	}

	public ListenableFuture<String> sendMessage(String messageId) {

		String url = Constants.BASE_URL + String.format(Constants.SEND_MESSAGE_WITH_ID, messageId);

		return post(url,  null);
	}

	public ListenableFuture<String> sendMessage(Message message) {

		String url = Constants.BASE_URL + Constants.SEND_MESSAGE;

		return post(url,  message);
	}

	public ListenableFuture<Message> moveMessage(String messageId, String folder){
		String url = Constants.BASE_URL + String.format(Constants.MOVE_MESSAGE, messageId);

		JsonObject jObject = new JsonObject();
		jObject.addProperty("DestinationId", folder);

		return execute(url, new Gson().toJson(jObject), Message.class, "POST");
	}

	public ListenableFuture<Message> updateMessage(Message message){
		String url = Constants.BASE_URL + String.format("/Messages('%s')", message.getId());

		return execute(url, new Gson().toJson(message), Message.class, "PATCH");
	}
	
	public ListenableFuture<Message> deleteMessage(String messageId){
		String url = Constants.BASE_URL + Constants.MESSAGES_URL + "('" + messageId +"')";
		return execute(url, null, null, "DELETE");
	}
}