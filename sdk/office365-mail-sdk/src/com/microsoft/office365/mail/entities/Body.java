package com.microsoft.office365.mail.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {

	@SerializedName("ContentType")
	@Expose
	private String contentType;
	
	@SerializedName("Content")
	@Expose
	private String content;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}