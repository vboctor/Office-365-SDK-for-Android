package com.microsoft.office365.exchange;

import java.util.List;

import microsoft.exchange.services.odata.model.Attachment;
import microsoft.exchange.services.odata.model.Contact;
import microsoft.exchange.services.odata.model.ContactFolder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.Query;

public class ContactClient extends BaseClient<Contact>{

	public ContactClient(Credentials credentials) {
		super(credentials);
	}

	public ListenableFuture<Contact> getContact(String contactId, Query query) {
		String url = Constants.BASE_URL + String.format(Constants.CONTACT_BY_ID, contactId);

		return execute(url, null, Contact.class, Constants.METHOD_GET, query);
	}	

	public ListenableFuture<List<Contact>> getContacts(Query query) {
		String url = Constants.BASE_URL + Constants.CONTACTS_URL;

		return getList(url, Contact[].class, query);
	}

	public ListenableFuture<Contact> create(Contact contact){
		String url = Constants.BASE_URL + Constants.CONTACTS_URL;

		return execute(url, new Gson().toJson(contact), Contact.class, Constants.METHOD_POST, null);
	}

	public ListenableFuture<Contact> update(Contact contact){
		String url = Constants.BASE_URL + Constants.CONTACTS_URL;

		return execute(url, new Gson().toJson(contact), Contact.class, Constants.METHOD_PATCH, null);
	}

	public ListenableFuture<Contact> delete(String contactId){
		String url = Constants.BASE_URL + String.format(Constants.CONTACT_BY_ID, contactId);

		return execute(url, null, null, Constants.METHOD_PATCH, null);
	}

	public class ContactFolderClient extends BaseClient<ContactFolder>{
		
		public ContactFolderClient(Credentials credentials) {
			super(credentials);
		}

		public ListenableFuture<List<ContactFolder>> getDefaultContactFolders(){
			String url = Constants.BASE_URL + String.format(Constants.CONTACTS_FOLDER_BY_ID, "Contacts");

			return getList(url, ContactFolder[].class, null);
		}
		
		public ListenableFuture<List<ContactFolder>> getContactFolders(String contactFolderId){
			String url = Constants.BASE_URL + String.format(Constants.CONTACTS_FOLDER_BY_ID, contactFolderId);

			return getList(url, ContactFolder[].class, null);
		}
		
		public ListenableFuture<ContactFolder> create(ContactFolder contactFolder){
			String url = Constants.BASE_URL + Constants.CONTACTS_FOLDER;

			return execute(url, new Gson().toJson(contactFolder), ContactFolder.class, Constants.METHOD_POST, null);
		}
		
		public ListenableFuture<ContactFolder> update(ContactFolder contactFolder){
			String url = Constants.BASE_URL + Constants.CONTACTS_FOLDER;

			return execute(url, new Gson().toJson(contactFolder), ContactFolder.class, Constants.METHOD_PATCH, null);
		}
		
		public ListenableFuture<ContactFolder> delete(String contactFolderId){
			String url = Constants.BASE_URL + String.format(Constants.CONTACTS_FOLDER_BY_ID, contactFolderId);

			return execute(url, null, ContactFolder.class, Constants.METHOD_DELETE, null);
		}

		@Override
		public ContactFolder copy() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ContactFolder move() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void delete(ContactFolder item) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<Attachment> getAttachments() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Attachment getAttachment(ContactFolder item) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public Contact copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contact move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Contact item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Attachment> getAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attachment getAttachment(Contact item) {
		// TODO Auto-generated method stub
		return null;
	}
}