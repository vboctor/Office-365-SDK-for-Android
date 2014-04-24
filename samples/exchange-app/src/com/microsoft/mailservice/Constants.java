package com.microsoft.mailservice;

public class Constants {

	public static final String AUTHORITY_URL = "https://login.windows.net/common";	
	public static final String CLIENT_ID = "a31be332-2598-42e6-97f1-d8ac87370367";
	//public static final String CLIENT_ID = "da146996-bb8c-45f4-a054-bdecba247cb6"; 
	public static final String REDIRECT_URL = "https://lagash.com/oauth";
	//public static final String REDIRECT_URL = "http://msopentechtest.com";
	public static final String RESOURCE_ID =  "https://outlook.office365.com/";	
	public static final String ODATA_ENDPOINT = "ews/odata";
	public static final String ENCRYPTION_KEY = "EncryptionKey";
	public static final String[] FIELDS_TO_SELECT = new String[]{"Id","Subject",
		"Sender",
		"ToRecipients", 
		"CcRecipients", 
		"DateTimeSent", 
		"ToRecipients",
	"LastModifiedTime"};
	
	public static final int TOP_VALUE = 40;

}