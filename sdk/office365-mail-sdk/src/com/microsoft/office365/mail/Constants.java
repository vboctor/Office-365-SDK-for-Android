package com.microsoft.office365.mail;

public class Constants {
	
	public static final String BASE_URL = "https://outlook.office365.com/EWS/OData/Me";

	public static final String FOLDER_URL = "/Folders('%s')";

	public static final String ROOTFOLDER_URL = "/RootFolder";

	public static final String CHILDFOLDERS_URL = "/ChildFolders";

	public static final String MESSAGES_URL = "/Messages";

	public static final String CONTACTS_URL = "/Contacts";

	public static final String EVENTS_URL = "/Calendar/Events";

	public static final String CREATE_MESSAGE_URL = "/Drafts/Messages";

	public static final String SEND_MESSAGE_WITH_ID = "/Messages('%s')/Send";

	public static final String SEND_MESSAGE = "/SentItems/Messages?MessageDisposition=SendAndSaveCopy";
}
