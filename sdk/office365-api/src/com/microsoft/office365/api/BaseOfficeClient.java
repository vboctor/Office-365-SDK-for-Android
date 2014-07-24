package com.microsoft.office365.api;

import com.microsoft.office.core.Configuration;
import com.microsoft.office.core.auth.method.IAuthenticator;
import com.microsoft.office.core.net.NetworkException;
import com.microsoft.office365.http.OAuthCredentials;
import com.msopentech.org.apache.http.client.HttpClient;
import com.msopentech.org.apache.http.client.methods.HttpUriRequest;

/**
 * The Class BaseOfficeClient.
 */
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
		//Check for preconditions.
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

	/**
	 * The Class Builder.
	 */
	public abstract static class Builder {

		private OAuthCredentials mCredentials;
		private String mResourceId;
		private String mOdataEndpoint;

		protected Builder(final OAuthCredentials credentials, String resourceId, String odataEndpoint) {

			mCredentials = credentials;
			mResourceId = resourceId;
			mOdataEndpoint = odataEndpoint;
		}

		/**
		 * Instantiates a new builder.
		 */
		public Builder() {
		}

		/**
		 * Builds the.
		 *
		 * @return the base office client
		 */
		public abstract BaseOfficeClient build();

		/**
		 * Sets the resource id.
		 *
		 * @param resourceId the resource id
		 * @return the builder
		 */
		public Builder setResourceId(String resourceId) {
			mResourceId = resourceId;
			return this;
		}

		/**
		 * Gets the resource id.
		 *
		 * @return the resource id
		 */
		public String getResourceId() {
			return mResourceId;
		}

		/**
		 * Sets the odata endpoint.
		 *
		 * @param odataEndpoint the odata endpoint
		 * @return the builder
		 */
		public Builder setOdataEndpoint(String odataEndpoint) {
			mOdataEndpoint = odataEndpoint;
			return this;
		}

		/**
		 * Sets the credentials.
		 *
		 * @param credentials the credentials
		 * @return the builder
		 */
		public Builder setCredentials(OAuthCredentials credentials) {
			mCredentials = credentials;
			return this;
		}

		/**
		 * Gets the odata endpoint.
		 *
		 * @return the odata endpoint
		 */
		public String getOdataEndpoint() {
			return mOdataEndpoint;
		}

		/**
		 * Gets the credentials.
		 *
		 * @return the credentials
		 */
		public OAuthCredentials getCredentials() {
			return mCredentials;
		}
	}
}
