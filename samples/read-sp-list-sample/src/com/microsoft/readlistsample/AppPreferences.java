package com.microsoft.readlistsample;

import android.content.SharedPreferences;

public class AppPreferences {

	private SharedPreferences mPreferences;

	public AppPreferences(SharedPreferences preferences) {
		mPreferences = preferences;
	}

	public String getClientId() {
		String library = mPreferences.getString("prefAADClientId", null);
		return library;
	}

	public String getRedirectUrl() {
		String library = mPreferences.getString("prefAADRedirectUrl", null);
		return library;
	}

	public String getSharepointUrl() {
		String library = mPreferences.getString("prefSharepointUrl", null);
		return library;
	}
}
