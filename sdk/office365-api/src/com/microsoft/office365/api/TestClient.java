package com.microsoft.office365.api;

import com.microsoft.exchange.services.odata.model.DefaultFolder;
import com.microsoft.office365.http.OAuthCredentials;

public class TestClient {

	OfficeClient mOfficeClient;
	MailClient mMailClient;

	public TestClient() {

		mOfficeClient = new OfficeClient(new OAuthCredentials("fooToken"));
		mMailClient = mOfficeClient.getMailClient();

	}

	public void canCreateMail() {
		mMailClient.newMessage(DefaultFolder.DRAFTS);
		mMailClient.Service.flush();
	}
}
