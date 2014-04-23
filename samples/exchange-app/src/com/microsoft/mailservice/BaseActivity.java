package com.microsoft.mailservice;

import android.app.Activity;

public abstract class BaseActivity extends Activity{
	
	public abstract void deleteMessage(String folderId, String messageId);
}