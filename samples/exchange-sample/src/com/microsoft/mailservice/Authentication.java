/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.adal.AuthenticationCallback;
import com.microsoft.adal.AuthenticationContext;
import com.microsoft.adal.AuthenticationResult;
import com.microsoft.adal.AuthenticationSettings;
import com.microsoft.adal.UserInfo;
import com.microsoft.office.core.Configuration;
import com.microsoft.office.core.auth.method.IAuthenticator;
import com.microsoft.office.core.net.NetworkException;
import com.msopentech.org.apache.http.client.HttpClient;
import com.msopentech.org.apache.http.client.methods.HttpUriRequest;

public class Authentication {

	private static String mLogedInUser;

	/**
	 * Authenticate.
	 * 
	 * @param activity
	 *            the activity
	 * @param mAppPreferences 
	 * @return
	 */
	public static SettableFuture<Void> authenticate(final Activity activity, AppPreferences preferences) {

		final SettableFuture<Void> result = SettableFuture.create();
		getAuthenticationContext(activity).acquireToken(activity, 
				Constants.RESOURCE_ID, 
				preferences.getClientId(),
				preferences.getRedirectUrl(), 
				"", new AuthenticationCallback<AuthenticationResult>() {

					@Override
					public void onSuccess(final AuthenticationResult authenticationResult) {
						if (authenticationResult != null && !TextUtils.isEmpty(authenticationResult.getAccessToken())) {

                            Configuration.setAuthenticator(new IAuthenticator() {
                                @Override
                                public void prepareRequest(HttpUriRequest request) {
                                    request.addHeader("Authorization", "Bearer " + authenticationResult.getAccessToken());
                                }

                                @Override
                                public void prepareClient(HttpClient client) throws NetworkException {}

                            });
                            
                            UserInfo ui = authenticationResult.getUserInfo();

    						SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
    						if (ui != null) {

    							mLogedInUser = ui.getUserId();
    							SharedPreferences.Editor editor = sharedPref.edit();
    							editor.putString("UserId", mLogedInUser);
    							editor.commit();

    						} else {
    							mLogedInUser = sharedPref.getString("UserId", "");
    						}
						}

						result.set(null);
					}

					@Override
					public void onError(Exception exc) {
						result.setException(exc);
					}
				});
		return result;
	}

	public static AuthenticationContext context = null;

	/**
	 * Gets AuthenticationContext for AAD.
	 * 
	 * @return authenticationContext, if successful
	 */
	public static AuthenticationContext getAuthenticationContext(Activity activity) {

		try {
			context = new AuthenticationContext(activity, Constants.AUTHORITY_URL, false);
		} catch (Throwable t) {
			ErrorHandler.handleError(t, activity);
		}
		return context;
	}

	public static void resetToken(Activity activity) {
		getAuthenticationContext(activity).getCache().removeAll();
		Configuration.setAuthenticator(new IAuthenticator() {
			
			@Override
			public void prepareRequest(HttpUriRequest arg0) {
			}
			
			@Override
			public void prepareClient(HttpClient arg0) throws NetworkException {
			}
		});
	}

	static void createEncryptionKey(Context applicationContext) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);

		if (!preferences.contains(Constants.ENCRYPTION_KEY)) {
			// generate a random key
			Random r = new Random();
			byte[] bytes = new byte[32];
			r.nextBytes(bytes);

			String key = Base64.encodeToString(bytes, Base64.DEFAULT);

			Editor editor = preferences.edit();
			editor.putString(Constants.ENCRYPTION_KEY, key);
			editor.commit();
		}

		AuthenticationSettings.INSTANCE.setSecretKey(getEncryptionKey(applicationContext));
	}

	static byte[] getEncryptionKey(Context applicationContext) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
		String key = preferences.getString(Constants.ENCRYPTION_KEY, null);

		if (key != null) {
			byte[] bytes = Base64.decode(key, Base64.DEFAULT);
			return bytes;
		} else {
			return new byte[32];
		}
	}

	public static String getLogedUser() {
		return mLogedInUser;
	}

}