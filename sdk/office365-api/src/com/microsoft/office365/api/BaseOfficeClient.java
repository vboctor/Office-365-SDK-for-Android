package com.microsoft.office365.api;

import com.microsoft.office.core.Configuration;
import com.microsoft.office.core.auth.method.IAuthenticator;
import com.microsoft.office.core.net.NetworkException;
import com.microsoft.office365.http.OAuthCredentials;
import com.msopentech.org.apache.http.client.HttpClient;
import com.msopentech.org.apache.http.client.methods.HttpUriRequest;

public abstract class BaseOfficeClient {

	private final String odataEndpoint;
	private final String resourceId;
	
	protected BaseOfficeClient(Builder builder) {

		odataEndpoint = builder.getOdataEndpoint();
		resourceId = builder.getResourceId();

		initialize(builder);
	}

	protected void initialize(final Builder builder) {

		//TODO:
		//Check for precondiciones.
		//Cannot initialize if all the builder setting are not already set.
		
		Configuration.setServerBaseUrl(resourceId + odataEndpoint);
		Configuration.setAuthenticator(new IAuthenticator() {

			@Override
			public void prepareClient(HttpClient client) throws NetworkException {
				// TODO Auto-generated method stub
			}

			@Override
			public void prepareRequest(HttpUriRequest request) {
				request.addHeader("Authorization", "Bearer " + builder.getCredentials().getToken());
			}
		});
	}

	public abstract static class Builder {

		private OAuthCredentials mCredentials;
		private String mResourceId;
		private String mOdataEndpoint;

		protected Builder(final OAuthCredentials credentials, String resourceId, String odataEndpoint) {

			mCredentials = credentials;
			mResourceId = resourceId;
			mOdataEndpoint = odataEndpoint;
		}

		public Builder() {
		}

		public abstract BaseOfficeClient build();

		public Builder setResourceId(String resourceId) {
			mResourceId = resourceId;
			return this;
		}

		public String getResourceId() {
			return mResourceId;
		}

		public Builder setOdataEndpoint(String odataEndpoint) {
			mOdataEndpoint = odataEndpoint;
			return this;
		}

		public Builder setCredentials(OAuthCredentials credentials) {
			mCredentials = credentials;
			return this;
		}

		public String getOdataEndpoint() {
			return mOdataEndpoint;
		}

		public OAuthCredentials getCredentials() {
			return mCredentials;
		}
	}
}
