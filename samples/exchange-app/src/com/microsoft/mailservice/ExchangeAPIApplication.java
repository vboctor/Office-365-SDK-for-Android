/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class ExchangeAPIApplication.
 */
public class ExchangeAPIApplication extends Application {

	/*
	 * (non-Javadoc)
	 * 	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {

		Log.d("Asset Management", "onCreate");
		super.onCreate();
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
			Authentication.ResetToken(activity);
		}
	}
}
