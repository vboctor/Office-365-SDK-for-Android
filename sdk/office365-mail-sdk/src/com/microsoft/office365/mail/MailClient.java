/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information.
 ******************************************************************************/
package com.microsoft.office365.mail;

import java.util.List;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.entities.Contact;
import com.microsoft.office365.mail.entities.Event;
import com.microsoft.office365.mail.entities.Folder;
import com.microsoft.office365.mail.entities.Message;

/**
 * The Class MailClient.
 */
public class MailClient {

	Credentials mCredentials;

	public MailClient(Credentials credentials){
		mCredentials = credentials;
	}

	public ListenableFuture<List<Folder>> getFolders() {

		String url = Constants.BASE_URL + Constants.ROOTFOLDER_URL + Constants.CHILDFOLDERS_URL;

		return new ExchangeClient<Folder>(mCredentials).getList(url, null, Folder[].class);
	}

	public ListenableFuture<List<Contact>> getContacts() {
		return getContacts(null);
	}

	public ListenableFuture<List<Contact>> getContacts(String filter) {
		String url = Constants.BASE_URL + Constants.CONTACTS_URL;

		return new ExchangeClient<Contact>(mCredentials).getList(url, null, Contact[].class);
	}

	public ListenableFuture<List<Event>> getEvents() {
		return getEvents(null);
	}

	public ListenableFuture<List<Event>> getEvents(String filter) {

		String url = Constants.BASE_URL + Constants.EVENTS_URL;

		return new ExchangeClient<Event>(mCredentials).getList(url, filter, Event[].class);
	}
	
	public ListenableFuture<List<Message>> getMessages(Folder folder) {
		return getMessages(folder.getId());
	}

	public ListenableFuture<List<Message>> getMessages(String folderNameOrId) {
		String url = Constants.BASE_URL + String.format(Constants.FOLDER_URL, folderNameOrId) + Constants.MESSAGES_URL;

		return new ExchangeClient<Message>(mCredentials).getList(url, null, Message[].class);
	}

	public ListenableFuture<String> createMessage(Message entity) {
		String url = Constants.BASE_URL + Constants.CREATE_MESSAGE_URL;

		return new ExchangeClient<Message>(mCredentials).post(url,  entity);
	}

	public ListenableFuture<String> sendMessage(String messageId) {

		String url = Constants.BASE_URL + String.format(Constants.SEND_MESSAGE_WITH_ID, messageId);
		
		return new ExchangeClient<Message>(mCredentials).post(url,  null);
	}

	public ListenableFuture<String> sendMessage(Message message) {

		String url = Constants.BASE_URL + Constants.SEND_MESSAGE;
		
		return new ExchangeClient<Message>(mCredentials).post(url,  message);
	}
	
	public ListenableFuture<Void> deleteMessage(String messageId){
			String url = Constants.BASE_URL + Constants.MESSAGES_URL + "(" + messageId +")";
		
		return new ExchangeClient<Message>(mCredentials).execute(url, null, null, "DELETE");
	}
}