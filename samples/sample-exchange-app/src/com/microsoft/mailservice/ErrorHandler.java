/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.mailservice;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class ErrorHandler {
	
	private static final String UNKNOWN_ERROR = "Unknown error";

	public static void handleError(Throwable e, final Activity activity) {
		String message = UNKNOWN_ERROR;
		if (e != null) {
			message = e.getMessage();
			if (message == null) {
				message = UNKNOWN_ERROR;
			}
		}
		
		final String finalMessage = message;
		Log.e("exchange-sample-error", finalMessage);
		
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(activity, "Error: " + finalMessage, Toast.LENGTH_LONG).show();
			}
		});
	}
}
