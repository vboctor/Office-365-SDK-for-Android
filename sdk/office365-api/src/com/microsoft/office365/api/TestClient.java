package com.microsoft.office365.api;

import java.util.List;

import com.microsoft.exchange.services.odata.model.DefaultFolder;
import com.microsoft.exchange.services.odata.model.types.IContact;
import com.microsoft.office365.api.AbstractOfficeClient.Builder;
import com.microsoft.office365.http.OAuthCredentials;

public class TestClient {

	OfficeClient mOfficeClient;
	MailClient mMailClient;
	ContactClient mContactClient;

	public TestClient() {

		OAuthCredentials credentials = new OAuthCredentials("foobar");
		
		mOfficeClient = new OfficeClient(new OAuthCredentials("fooToken"));
		
		mMailClient = new MailClient.Builder()
									.setCredentials(credentials)
							        .setOdataEndpoint("foo")
							        .setResourceId("bar").build();
		
		Builder builder = new ContactClient.Builder()
											.setCredentials(credentials)
									        .setOdataEndpoint("foo")
									        .setResourceId("bar");
		
		
		mContactClient = new ContactClient.Builder(credentials,"foo", "bar").build();
		
		mContactClient = new ContactClient.Builder()
										  .setCredentials(credentials)
										  .setOdataEndpoint("foo")
										  .setResourceId("bar").build();
		
		mContactClient = new ContactClient(builder);
		
	}

	public void canCreateMail() {
		mMailClient.newMessage(DefaultFolder.DRAFTS);
	}
	
	public void getContacts(){
		List<IContact> contacts = mContactClient.getContacts();
	}
}
