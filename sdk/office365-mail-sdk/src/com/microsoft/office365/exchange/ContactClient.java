package com.microsoft.office365.exchange;

import java.util.List;
import microsoft.exchange.services.odata.model.Contact;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.microsoft.office365.Credentials;

public class ContactClient extends BaseClient<Contact>{

	public ContactClient(Credentials credentials) {
		super(credentials);
	}

	public ListenableFuture<List<Contact>> getContacts() {
		return getContacts(null);
	}

	public ListenableFuture<List<Contact>> getContacts(String filter) {
		String url = Constants.BASE_URL + Constants.CONTACTS_URL;

		return getList(url, null, Contact[].class);
	}
	
	public ListenableFuture<Contact> get(String contactId) {
		String url = Constants.BASE_URL + String.format(Constants.CONTACT_BY_ID, contactId);

		return execute(url, null, Contact.class, Constants.METHOD_GET);
	}
	
	public ListenableFuture<Contact> create(Contact contact){
		String url = Constants.BASE_URL + Constants.CONTACTS_URL;

		return execute(url, new Gson().toJson(contact), Contact.class, Constants.METHOD_POST);
	}
	
	public ListenableFuture<Contact> update(Contact contact){
		String url = Constants.BASE_URL + Constants.CONTACTS_URL;

		return execute(url, new Gson().toJson(contact), Contact.class, Constants.METHOD_PATCH);
	}
	
	public ListenableFuture<Contact> delete(String contactId){
		String url = Constants.BASE_URL + String.format(Constants.CONTACT_BY_ID, contactId);

		return execute(url, null, null, Constants.METHOD_PATCH);
	}
}