package com.microsoft.office365.exchange;

public class Constants {

	public static final String BASE_URL = "https://outlook.office365.com/EWS/OData/Me";

	public static final String FOLDER_URL = "/Folders('%s')";

	public static final String FOLDER_INBOX = "/Inbox";
	
	public static final String FOLDER_DRAFTS = "/Drafts";
	
	public static final String FOLDER_ROOT_FOLDER = "/RootFolder";
	
	public static final String FOLDER_DELETED_ITEMS = "/DeletedItems";
	
	public static final String FOLDER_SEND_ITEMS = "/SentItems";
	
	public static final String ROOTFOLDER_URL = "/RootFolder";

	public static final String CHILDFOLDERS_URL = "/ChildFolders";

	public static final String MESSAGES_URL = "/Messages";

	public static final String CONTACTS_URL = "/Contacts";

	public static final String CONTACT_BY_ID = "/Contacts('%s')";
	
	public static final String CONTACTS_FOLDER = "/ContactFolder";
	
	public static final String CONTACTS_FOLDER_BY_ID = "/ContactFolder('%s')";
	
	public static final String EVENTS_URL = "/Calendar/Events";

	public static final String CREATE_MESSAGE_URL = "/Drafts/Messages";

	public static final String MESSAGE_BY_ID = "/Messages('%s')";

	public static final String EVENT_BY_ID = "/Events('%s')";
	
	public static final String SEND_MESSAGE = "/SentItems/Messages?MessageDisposition=SendAndSaveCopy";

	public static final String ACTION_MOVE = "/Move";

	public static final String ACTION_COPY = "/Copy";
	
	public static final String ACTION_SEND = "/Send";
	
	public static final String ACTION_REPLY = "/Reply";
	
	public static final String ACTION_CREATE_REPLY = "/CreateReply";
	
	public static final String ACTION_CREATE_FORWARD = "/CreateForward";
	
	public static final String ACTION_REPLY_ALL = "/ReplyAll";
	
	public static final String ACTION_ACCEPT = "/Accept?MessageDisposition=SendAndSaveCopy";
	
	public static final String ACTION_DECLINE = "/Decline?MessageDisposition=SendAndSaveCopy";
	
	public static final String ACTION_TENTATIVE = "/Tentative?MessageDisposition=SendAndSaveCopy";
	
	public static final String METHOD_DELETE =  "DELETE";
	
	public static final String METHOD_POST = "POST";
	
	public static final String METHOD_GET = "GET";
	
	public static final String METHOD_PATCH = "PATCH";
}