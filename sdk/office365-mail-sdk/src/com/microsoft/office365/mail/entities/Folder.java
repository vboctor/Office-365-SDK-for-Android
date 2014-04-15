package com.microsoft.office365.mail.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Folder {

	@SerializedName("Id")
	@Expose
	private String id;
	
	@SerializedName("ParentFolderId")
	@Expose
	private String parentFolderId;
	
	@SerializedName("DisplayName")
	@Expose
	private String displayName;
	
	@SerializedName("ClassName")
	@Expose
	private String className;
	
	@SerializedName("TotalCount")
	@Expose
	private Integer totalCount;
	
	@SerializedName("ChildFolderCount")
	@Expose
	private Integer childFolderCount;
	
	@SerializedName("UnreadItemCount")
	@Expose
	private Integer unreadItemCount;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getChildFolderCount() {
		return childFolderCount;
	}

	public void setChildFolderCount(Integer childFolderCount) {
		this.childFolderCount = childFolderCount;
	}

	public Integer getUnreadItemCount() {
		return unreadItemCount;
	}

	public void setUnreadItemCount(Integer unreadItemCount) {
		this.unreadItemCount = unreadItemCount;
	}
}