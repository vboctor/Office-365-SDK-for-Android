package com.microsoft.office365.api;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.exchange.services.odata.model.IContacts;
import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.IContact;
import com.microsoft.exchange.services.odata.model.types.IContactCollection;
import com.microsoft.office365.http.OAuthCredentials;
import com.msopentech.odatajclient.proxy.api.Query;

// TODO: Auto-generated Javadocd
/**
 * The Class ContactClient.
 */
public class ContactClient extends BaseOfficeClient {

	protected ContactClient(BaseOfficeClient.Builder builder) {
		super(builder);
	}
	
	/**
	 * New contact.
	 *
	 * @return the i contact
	 */
	public IContact newContact(){
		return Me.getContacts().newContact();
	}

	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<IContact> getContacts() {

		List<IContact> contacts = new ArrayList<IContact>();
		try {
			IContacts proxy = Me.getContacts();
			Query<IContact, IContactCollection> query = proxy.createQuery();
			query.setMaxResults(10);
			contacts = new ArrayList<IContact>(query.getResult());

		} catch (Exception e) {
			// Log
		}
		return contacts;
	}

	/**
	 * The Class Builder.
	 */
	public static final class Builder extends BaseOfficeClient.Builder {

		/**
		 * Instantiates a new builder.
		 */
		public Builder() {
			super();
		}

		/**
		 * Instantiates a new builder.
		 *
		 * @param credentials the credentials
		 * @param resourceId the resource id
		 * @param odataEndpoint the odata endpoint
		 */
		public Builder(OAuthCredentials credentials, String resourceId, String odataEndpoint) {
			super(credentials, resourceId, odataEndpoint);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#build()
		 */
		@Override
		public ContactClient build() {
			return new ContactClient(this);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setCredentials(com.microsoft.office365.http.OAuthCredentials)
		 */
		@Override
		public Builder setCredentials(OAuthCredentials credentials) {
			return (Builder) super.setCredentials(credentials);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setOdataEndpoint(java.lang.String)
		 */
		@Override
		public Builder setOdataEndpoint(String odataEndpoint) {
			return (Builder) super.setOdataEndpoint(odataEndpoint);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setResourceId(java.lang.String)
		 */
		@Override
		public Builder setResourceId(String resourceId) {
			return (Builder) super.setResourceId(resourceId);
		}
	}

}
