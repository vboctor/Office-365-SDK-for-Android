/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information.
 ******************************************************************************/
package com.microsoft.office365.exchange;

import java.util.List;
import microsoft.exchange.services.odata.model.Folder;
import microsoft.exchange.services.odata.model.Message;
import microsoft.exchange.services.odata.model.Recipient;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.office365.Credentials;

/**
 * The Class MailClient.
 */
public class MessageClient extends BaseClient<Message>{

	public MessageClient(Credentials credentials){
		super(credentials);
	}
	
	public ListenableFuture<List<Message>> getInboxMessages() {
		String url = Constants.BASE_URL + Constants.FOLDER_INBOX + Constants.MESSAGES_URL;

		return getList(url, null, Message[].class);
	}
	
	public ListenableFuture<List<Message>> getDraftsMessages() {
		String url = Constants.BASE_URL + Constants.FOLDER_DRAFTS + Constants.MESSAGES_URL;

		return getList(url, null, Message[].class);
	}
	
	public ListenableFuture<List<Message>> getSendItemsMessages() {
		String url = Constants.BASE_URL + Constants.FOLDER_SEND_ITEMS + Constants.MESSAGES_URL;

		return getList(url, null, Message[].class);
	}
	
	public ListenableFuture<List<Message>> getDeletedMessages() {
		String url = Constants.BASE_URL + Constants.FOLDER_DELETED_ITEMS + Constants.MESSAGES_URL;

		return getList(url, null, Message[].class);
	}
	
	public ListenableFuture<List<Message>> get(Folder folder) {
		return get(folder.getId());
	}
	
	public ListenableFuture<Message> getMessage(String messageId) {
		String url = Constants.BASE_URL + String.format(Constants.MESSAGE_BY_ID, messageId);

		return execute(url, null, Message.class, Constants.METHOD_GET);
	}

	public ListenableFuture<List<Message>> get(String folderNameOrId) {
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderNameOrId) + Constants.MESSAGES_URL;

		return getList(url, null, Message[].class);
	}

	/**
	 * Get all the messages
	 * 
	 * @param folderNameOrId
	 *            the folder to get the messages
	 * @param skip
	 *            message from skip
	 * @param top 
	 * 			  max value to return, if is zero return all the messages         
	 */			  
	public ListenableFuture<List<Message>> get(String folderNameOrId, int skip, int top) {
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderNameOrId) 
				+ Constants.MESSAGES_URL;

		if(skip > 0){
			url += "?skip=" + skip;
			if(top > 0) url += "&$top" + top;
		}
		else if(top > 0) {
			url += "?top=" + top;
		}

		return getList(url, null, Message[].class);
	}

	public ListenableFuture<String> create(Message entity) {
		String url = Constants.BASE_URL + Constants.CREATE_MESSAGE_URL;

		return post(url,  entity);
	}

	public ListenableFuture<String> send(String messageId) {

		String url = Constants.BASE_URL + String.format(Constants.MESSAGE_BY_ID, messageId) + Constants.ACTION_SEND;

		return post(url,  null);
	}

	public ListenableFuture<Message> send(Message message) {

		String url = Constants.BASE_URL + Constants.SEND_MESSAGE;

		return execute(url, new Gson().toJson(message), Message.class, Constants.METHOD_POST);
	}

	public ListenableFuture<Message> moveTo(String messageId, String folder){
		String url = Constants.BASE_URL + String.format(Constants.MESSAGE_BY_ID, messageId) + Constants.ACTION_MOVE;

		JsonObject jObject = new JsonObject();
		jObject.addProperty("DestinationId", folder);

		return execute(url, new Gson().toJson(jObject), Message.class, Constants.METHOD_POST);
	}

	public ListenableFuture<Message> update(Message message){
		String url = Constants.BASE_URL + String.format(Constants.MESSAGE_BY_ID, message.getId());

		return execute(url, new Gson().toJson(message), Message.class, Constants.METHOD_PATCH);
	}

	public ListenableFuture<Message> delete(String messageId){
		String url = Constants.BASE_URL + Constants.MESSAGES_URL + "('" + messageId +"')";
		return execute(url, null, null, "DELETE");
	}

	public ListenableFuture<Message> reply(Message message){
		String url = Constants.BASE_URL + String.format(Constants.MESSAGE_BY_ID, message.getId());
		Message resultMessage = null;
		try {
			resultMessage = execute(url + Constants.ACTION_CREATE_REPLY , null, Message.class, Constants.METHOD_POST).get();
					
			resultMessage.setBody(message.getBody());
			resultMessage.setToRecipients(message.getToRecipients());
			resultMessage.setCcRecipients(message.getCcRecipients());

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return send(resultMessage);
	}

	public ListenableFuture<Message> forward(Message message, String comment, List<Recipient> toRecipients){
		String url = Constants.BASE_URL + String.format(Constants.MESSAGE_BY_ID, message.getId());
		Message resultMessage = null;
		try {
			resultMessage = execute(url + Constants.ACTION_CREATE_FORWARD , null, Message.class, Constants.METHOD_POST).get();
					
			resultMessage.setBody(message.getBody());
			resultMessage.setToRecipients(message.getToRecipients());
			resultMessage.setCcRecipients(message.getCcRecipients());

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return send(resultMessage);
	}
}