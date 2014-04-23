package microsoft.exchange.services.odata.model;

public class Attachment {
	
	private String id;
	private String Name;
	private String ContentType;
	private Integer Size;
	private Boolean IsInline;
	private java.util.Calendar LastModifiedTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() { 
		return Name; 
	}
	public void setName(String p) { 
		Name = p;
	}

	public String getContentType() { 
		return ContentType;
	}
	public void setContentType(String p) { 
		ContentType = p;
	}

	public Integer getSize() { 
		return Size; 
	}
	public void setSize(Integer p) { 
		Size = p; 
	}

	public Boolean getIsInline() {
		return IsInline; 
	}
	
	public void setIsInline(Boolean p) { 
		IsInline = p; 
	}

	public java.util.Calendar getLastModifiedTime() {
		return LastModifiedTime; 
	}
	
	public void setLastModifiedTime(java.util.Calendar p) {
		LastModifiedTime = p;
	}
}
