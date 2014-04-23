package com.microsoft.mailservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import microsoft.exchange.services.odata.model.Message;
import android.app.Activity;

public abstract class BaseActivity extends Activity{
	
	public abstract void deleteMessage(String folderId, String messageId);
}