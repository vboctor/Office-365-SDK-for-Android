package com.microsoft.mailservice;

import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.adal.AuthenticationCallback;
import com.microsoft.adal.AuthenticationContext;
import com.microsoft.adal.AuthenticationResult;
import com.microsoft.adal.AuthenticationSettings;
import com.microsoft.adal.PromptBehavior;
import com.microsoft.adal.UserInfo;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.http.OAuthCredentials;

public class Authentication {

	/** The m credentials. */
	private static Credentials mCredentials;

	private static String mLogedInUser;

	/**
	 * Authenticate.
	 * 
	 * @param activity
	 *            the activity
	 * @return 
	 */
	public static SettableFuture<Credentials> authenticate(final Activity activity, final String resourceId) {
		final SettableFuture<Credentials> result = SettableFuture.create();

		getAuthenticationContext(activity).acquireToken(activity, resourceId, Constants.CLIENT_ID,
				Constants.REDIRECT_URL, PromptBehavior.Auto, "", new AuthenticationCallback<AuthenticationResult>() {

			@Override
			public void onSuccess(AuthenticationResult authenticationResult) {
				// once succeeded we create a credentials instance
				// using
				// the token from ADAL

				mCredentials = new OAuthCredentials(authenticationResult.getAccessToken());
				UserInfo ui = authenticationResult.getUserInfo();
				SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
				if(ui != null){

					mLogedInUser =ui.getUserId();
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("UserId", mLogedInUser);
					editor.commit();
				}else{

					mLogedInUser = sharedPref.getString("UserId", "");
				}

				result.set(mCredentials);
			}

			@Override
			public void onError(Exception exc) {
				Log.d("","");
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
			Log.e("Asset", t.getMessage());
		}
		return context;
	}	

	public static void ResetToken(Activity activity) {
		getAuthenticationContext(activity).getCache().removeAll();
	}

	static void createEncryptionKey(Context applicationContext) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);

		if (!preferences.contains(Constants.ENCRYPTION_KEY)) {
			//generate a random key
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

	public static Credentials getCurrentCredentials(){
		return mCredentials;
	}

	public static String getLogedUser(){
		return mLogedInUser;
	}

}