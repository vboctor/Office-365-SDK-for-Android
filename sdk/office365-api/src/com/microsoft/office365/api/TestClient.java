package com.microsoft.office365.api;

import java.util.List;

import com.microsoft.exchange.services.odata.model.DefaultFolder;
import com.microsoft.exchange.services.odata.model.types.IContact;
import com.microsoft.office365.api.BaseOfficeClient.Builder;
import com.microsoft.office365.http.OAuthCredentials;

/**
 * The Class TestClient.
 */
public class TestClient {

	MailClient mMailClient;
	ContactClient mContactClient;

	/**
	 * Instantiates a new test client.
	 */
	public TestClient() {

		OAuthCredentials credentials = new OAuthCredentials("foobar");
		
		mMailClient = new MailClient.Builder()
									.setCredentials(credentials)
							        .setOdataEndpoint("foo")
							        .setResourceId("bar")
							        .build();
		
		Builder builder = new ContactClient.Builder()
										   .setCredentials(credentials)
									       .setOdataEndpoint("foo")
									       .setResourceId("bar");
		
		mContactClient = new ContactClient.Builder(credentials,"foo", "bar")
										  .build();
		
		mContactClient = new ContactClient.Builder()
										  .setCredentials(credentials)
										  .setOdataEndpoint("foo")
										  .setResourceId("bar").build();
		
		mContactClient = new ContactClient(builder);
	}

	/**
	 * Can create mail.
	 */
	public void canCreateMail() {
		mMailClient.newMessage(DefaultFolder.DRAFTS);
	}
	
	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public void getContacts(){
		
		List<IContact> contacts = mContactClient.getContacts();
		
	}
}
