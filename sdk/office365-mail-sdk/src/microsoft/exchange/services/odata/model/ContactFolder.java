package microsoft.exchange.services.odata.model;

import java.util.List;

public class ContactFolder{

	private String ParentFolderId;
	private String DisplayName;
	private List<Contact> Contacts;
	private List<ContactFolder> ChildFolders;

	public String getParentFolderId() {
		return ParentFolderId; 
	}

	public void setParentFolderId(String p) { 
		ParentFolderId = p; 
	}

	public String getDisplayName() { 
		return DisplayName; 
	}

	public void setDisplayName(String p) { 
		DisplayName = p; 
	}

	public List<Contact> getContacts() { 
		return Contacts; 
	}

	public void setContacts(List<Contact> p) { 
		Contacts = p; 
	}

	public List<ContactFolder> getChildFolders() {
		return ChildFolders; 
	}

	public void setChildFolders(List<ContactFolder> p) { 
		ChildFolders = p; 
	}
}