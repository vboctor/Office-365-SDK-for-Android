/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice;

import android.app.Activity;
import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.microsoft.office365.api.CalendarClient;
import com.microsoft.office365.api.ContactClient;
import com.microsoft.office365.api.MailClient;
import com.microsoft.office365.http.OAuthCredentials;

// TODO: Auto-generated Javadoc
/**
 * The Class ExchangeAPIApplication.
 */
public class ExchangeAPIApplication extends Application {

	private AppPreferences mPreferences;
	private OAuthCredentials mCredentials;

	/*
	 * (non-Javadoc)
	 * 
	 * /* (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {

		Log.d("Asset Management", "onCreate");
		super.onCreate();
		mPreferences = new AppPreferences(PreferenceManager.getDefaultSharedPreferences(this));
	}
	
	public void setOauthCredentials(OAuthCredentials credentials){
		mCredentials = credentials;
	}
	
	public CalendarClient getCalendarClient(){
		
		return new CalendarClient.Builder()						
								   .setCredentials(mCredentials)
								   .setOdataEndpoint(Constants.ODATA_ENDPOINT)
								   .setResourceId(Constants.RESOURCE_ID)
								   .build();	}
		
	public ContactClient getContactClient()
	{
		return new ContactClient.Builder()
								.setCredentials(mCredentials)
								.setOdataEndpoint(Constants.ODATA_ENDPOINT)
								.setResourceId(Constants.RESOURCE_ID)
								.build();
	}
	
	public MailClient getMailClient()
	{
		return new MailClient.Builder()
							 .setCredentials(mCredentials)
							 .setOdataEndpoint(Constants.ODATA_ENDPOINT)
						  	 .setResourceId(Constants.RESOURCE_ID)
							 .setMaxResults(Constants.TOP_VALUE)
							 .build();
	}
	
	public AppPreferences getAppPreferences() {
		return mPreferences;
	}

	private boolean isNullOrEmpty(String value) {

		return value == null || value.length() == 0;
	}

	public boolean hasConfiguration() {

		if (isNullOrEmpty(mPreferences.getClientId()))
			return false;

		if (isNullOrEmpty(mPreferences.getRedirectUrl()))
			return false;

		return true;
	}

	/**
	 * Handle error.
	 * 
	 * @param throwable
	 *            the throwable
	 */
	public void handleError(Throwable throwable) {
		Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
		Log.e("Asset", throwable.toString());
	}

	/**
	 * Clear preferences.
	 */
	public void clearPreferences(Activity activity) {
		CookieSyncManager syncManager = CookieSyncManager.createInstance(this);
		if (syncManager != null) {
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.removeAllCookie();
			CookieSyncManager.getInstance().sync();
			Authentication.resetToken(activity);
		}
	}
}
