package com.microsoft.office365.api;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.exchange.services.odata.model.IContacts;
import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.types.IContact;
import com.microsoft.exchange.services.odata.model.types.IContactCollection;
import com.microsoft.office365.http.OAuthCredentials;
import com.msopentech.odatajclient.proxy.api.Query;

public class ContactClient extends BaseOfficeClient {

	protected ContactClient(BaseOfficeClient.Builder builder) {
		super(builder);
	}

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

	public static final class Builder extends BaseOfficeClient.Builder {

		public Builder() {
			super();
		}

		public Builder(OAuthCredentials credentials, String resourceId, String odataEndpoint) {
			super(credentials, resourceId, odataEndpoint);
		}

		@Override
		public ContactClient build() {
			return new ContactClient(this);
		}

		@Override
		public Builder setCredentials(OAuthCredentials credentials) {
			return (Builder) super.setCredentials(credentials);
		}

		@Override
		public Builder setOdataEndpoint(String odataEndpoint) {
			return (Builder) super.setOdataEndpoint(odataEndpoint);
		}

		@Override
		public Builder setResourceId(String resourceId) {
			return (Builder) super.setResourceId(resourceId);
		}
	}

}
