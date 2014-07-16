package com.microsoft.office365.api;

import com.microsoft.office.core.Configuration;
import com.microsoft.office.core.auth.method.IAuthenticator;
import com.microsoft.office.core.net.NetworkException;
import com.microsoft.office365.files.FileClient;
import com.microsoft.office365.http.OAuthCredentials;
import com.microsoft.office365.lists.SharepointListsClient;
import com.msopentech.org.apache.http.client.HttpClient;
import com.msopentech.org.apache.http.client.methods.HttpUriRequest;

public class OfficeClient {

	private OAuthCredentials mCredentials;
	private MailClient mMailClient;
	private FileClient mFileClient;

	private SharepointListsClient mSharepointClient;

	public OfficeClient(OAuthCredentials credentials) {
		mCredentials = credentials;
		configureService();
	}

	private void configureService() {
		Configuration.setServerBaseUrl("serverBaseUrl");
		Configuration.setAuthenticator(new IAuthenticator() {

			@Override
			public void prepareRequest(HttpUriRequest request) {
				request.addHeader("Authorization", "Bearer " + mCredentials.getToken());
			}

			@Override
			public void prepareClient(HttpClient client) throws NetworkException {
			}

		});
	}

	public SharepointListsClient createListClient() {
		mSharepointClient = new SharepointListsClient("some-server", "some-relative", mCredentials);
		return mSharepointClient;
	}

	public FileClient createFileClient() {
		mFileClient = new FileClient("some-server", "someRelativeUrl", mCredentials);
		return mFileClient;
	}

	public MailClient getMailClient() {
		mMailClient = new MailClient();
		return mMailClient;
	}

}
