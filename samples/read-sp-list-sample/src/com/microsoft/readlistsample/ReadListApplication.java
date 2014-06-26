package com.microsoft.readlistsample;

import android.app.Application;
import android.preference.PreferenceManager;

public class ReadListApplication extends Application {
	private AppPreferences mPreferences;

	@Override
	public void onCreate() {

		super.onCreate();
		mPreferences = new AppPreferences(
				PreferenceManager.getDefaultSharedPreferences(this));
	}

	public AppPreferences getAppPreferences() {
		return mPreferences;
	}

	public boolean hasConfiguration() {

		if (isNullOrEmpty(mPreferences.getClientId()))
			return false;

		if (isNullOrEmpty(mPreferences.getRedirectUrl()))
			return false;

		if (isNullOrEmpty(mPreferences.getSharepointUrl()))
			return false;

		return true;
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.length() == 0;
	}

}
