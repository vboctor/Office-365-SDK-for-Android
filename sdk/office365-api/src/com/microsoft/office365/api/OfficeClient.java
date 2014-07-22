package com.microsoft.office365.api;

import com.microsoft.office365.files.FileClient;
import com.microsoft.office365.http.OAuthCredentials;
import com.microsoft.office365.lists.SharepointListsClient;

public class OfficeClient {

	protected OAuthCredentials mCredentials;
	private FileClient mFileClient;

	private SharepointListsClient mSharepointClient;

	public OfficeClient(OAuthCredentials credentials) {
		mCredentials = credentials;
	}
	
	public SharepointListsClient createListClient() {
		mSharepointClient = new SharepointListsClient("some-server", "some-relative", mCredentials);
		return mSharepointClient;
	}

	public FileClient createFileClient() {
		mFileClient = new FileClient("some-server", "someRelativeUrl", mCredentials);
		return mFileClient;
	}

	/*
	public MailClient getMailClient(String resourceId, String odataEndpoint) {
		mMailClient = new MailClient(mCredentials, resourceId, odataEndpoint);
		return mMailClient;
	}
	*/
}
