package com.microsoft.office365.exchange;

import java.util.List;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.entities.Contact;

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
}