package com.microsoft.readlistsample;

import android.content.SharedPreferences;

public class AppPreferences {

	private SharedPreferences mPreferences;

	public AppPreferences(SharedPreferences preferences) {
		mPreferences = preferences;
	}

	public String getClientId() {
		String clientId = mPreferences.getString("prefAADClientId", null);
		return clientId;
	}

	public String getRedirectUrl() {
		String redirectUrl = mPreferences.getString("prefAADRedirectUrl", null);
		return redirectUrl;
	}

	public String getSharepointUrl() {
		String url = mPreferences.getString("prefSharepointUrl", null);
		return url;
	}
	
	public String getSharepointSite() {
		String site = mPreferences.getString("prefSharepointSite", null);
		return site;
	}
	
}
