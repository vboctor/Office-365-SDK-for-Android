/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information.
 ******************************************************************************/
package com.microsoft.office365.exchange;

import java.util.List;
import microsoft.exchange.services.odata.model.Attachment;
import microsoft.exchange.services.odata.model.Folder;
import microsoft.exchange.services.odata.model.Message;
import microsoft.exchange.services.odata.model.MessageSummary;
import microsoft.exchange.services.odata.model.Recipient;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;

/**
 * The Class MailClient.
 */
public class MailClient extends BaseClient<Message> {

	public MailClient(Credentials credentials) {
		super(credentials);
		//setAttachmentUrl(Constants.BASE_URL + Constants.MESSAGE_BY_ID);
	}

	public ListenableFuture<List<Message>> getInboxMessages(Query query) {
		String url = Constants.BASE_URL + Constants.FOLDER_INBOX
				+ Constants.MESSAGES_URL;

		return getList(url, Message[].class, query);
	}

	public ListenableFuture<List<Message>> getDraftsMessages(Query query) {
		String url = Constants.BASE_URL + Constants.FOLDER_DRAFTS
				+ Constants.MESSAGES_URL;

		return getList(url, Message[].class, query);
	}

	public ListenableFuture<List<Message>> getSendItemsMessages(Query query) {
		String url = Constants.BASE_URL + Constants.FOLDER_SEND_ITEMS
				+ Constants.MESSAGES_URL;

		return getList(url, Message[].class, query);
	}

	public ListenableFuture<List<Message>> getDeletedMessages(Query query) {
		String url = Constants.BASE_URL + Constants.FOLDER_DELETED_ITEMS
				+ Constants.MESSAGES_URL;

		return getList(url, Message[].class, query);
	}

	public ListenableFuture<Message> getMessage(String messageId, Query query) {
		String url = Constants.BASE_URL
				+ String.format(Constants.MESSAGE_BY_ID, messageId);

		return execute(url, null, Message.class, Constants.METHOD_GET, query);
	}

	public ListenableFuture<List<Message>> getMessages() {
		String url = Constants.BASE_URL + Constants.MESSAGES_URL;

		return getList(url, Message[].class, null);
	}

	public ListenableFuture<List<Message>> getMessages(String folderNameOrId,
			Query query) {
		String url = Constants.BASE_URL
				+ String.format(Constants.FOLDER_URL, folderNameOrId)
				+ Constants.MESSAGES_URL;

		return getList(url, Message[].class, query);
	}

	public ListenableFuture<List<Message>> getMessages(Folder folder,
			Query query) {
		return getMessages(folder.getId(), query);
	}

	public ListenableFuture<Message> getMessage(String messageId) {
		return null;
	}

	public ListenableFuture<List<MessageSummary>> getMessages(Query query) {
		return null;
	}

	public ListenableFuture<Folder> getFolder(String folderId) {
		return null;
	}

	public ListenableFuture<List<Folder>> getFolders(Folder folder) {
		return null;
	}

	public ListenableFuture<List<Folder>> getFolders(String folderId) {
		return null;
	}

	public Message createReply() {
		return null;
	}

	public Message createReplyAll() {
		return null;
	}

	public Message createForward() {
		return null;
	}

	public Message replyAll() {
		return null;
	}

	public ListenableFuture<String> create(Message entity) {
		String url = Constants.BASE_URL + Constants.CREATE_MESSAGE_URL;

		return execute(url, entity, Constants.METHOD_POST);
	}

	// TODO
	public ListenableFuture<Folder> create(String displayName) {
		return null;
	}

	public ListenableFuture<String> send(String messageId) {

		String url = Constants.BASE_URL
				+ String.format(Constants.MESSAGE_BY_ID, messageId)
				+ Constants.ACTION_SEND;

		return execute(url, null, Constants.METHOD_POST);
	}

	public ListenableFuture<Message> send(Message message) {

		String url = Constants.BASE_URL + Constants.SEND_MESSAGE;

		return execute(url, new Gson().toJson(message), Message.class,
				Constants.METHOD_POST, null);
	}
		
	public ListenableFuture<Message> update(Message message) {
		String url = Constants.BASE_URL
				+ String.format(Constants.MESSAGE_BY_ID, message.getId());

		return execute(url, new Gson().toJson(message), Message.class,
				Constants.METHOD_PATCH, null);
	}
	
	public ListenableFuture<Message> reply(Message message) {
		String url = Constants.BASE_URL
				+ String.format(Constants.MESSAGE_BY_ID, message.getId());
		Message resultMessage = null;
		try {
			resultMessage = execute(url + Constants.ACTION_CREATE_REPLY, null,
					Message.class, Constants.METHOD_POST, null).get();

			resultMessage.setBody(message.getBody());
			resultMessage.setToRecipients(message.getToRecipients());
			resultMessage.setCcRecipients(message.getCcRecipients());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return send(resultMessage);
	}

	public ListenableFuture<Message> forward(Message message, String comment,
			List<Recipient> toRecipients) {
		String url = Constants.BASE_URL
				+ String.format(Constants.MESSAGE_BY_ID, message.getId());
		Message resultMessage = null;
		try {
			resultMessage = execute(url + Constants.ACTION_CREATE_FORWARD,
					null, Message.class, Constants.METHOD_POST, null).get();

			resultMessage.setBody(message.getBody());
			resultMessage.setToRecipients(message.getToRecipients());
			resultMessage.setCcRecipients(message.getCcRecipients());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return send(resultMessage);
	}

	@Override
	public ListenableFuture<Message> copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<Message> move(String messageId, String folderId) {
		String url = Constants.BASE_URL
				+ String.format(Constants.MESSAGE_BY_ID, messageId)
				+ Constants.ACTION_MOVE;

		JsonObject jObject = new JsonObject();
		jObject.addProperty("DestinationId", folderId);

		return execute(url, new Gson().toJson(jObject), Message.class,
				Constants.METHOD_POST, null);
	}

	@Override
	public ListenableFuture<String> delete(String messageId) {
		String url = Constants.BASE_URL + Constants.MESSAGES_URL + "('"
				+ messageId + "')";
		return execute(url, null, Constants.METHOD_DELETE);
	}

	@Override
	public ListenableFuture<List<Attachment>> getAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<Attachment> getAttachment(Message item) {
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