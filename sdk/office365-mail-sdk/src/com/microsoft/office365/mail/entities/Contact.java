package com.microsoft.office365.mail.entities;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

	@SerializedName("Id")
	@Expose
	private String id;
	
	@SerializedName("ChangeKey")
	@Expose
	private String changeKey;
	
	@SerializedName("ClassName")
	@Expose
	private String className;
	
	@SerializedName("Subject")
	@Expose
	private String subject;
	
	@SerializedName("BodyPreview")
	@Expose
	private String bodyPreview;
	
	@SerializedName("Body")
	@Expose
	private Body body;
	
	@SerializedName("Importance")
	@Expose
	private String importance;
	
	@SerializedName("Categories")
	@Expose
	private List<Object> categories = new ArrayList<Object>();
	
	@SerializedName("HasAttachments")
	@Expose
	private boolean hasAttachments;
	
	@SerializedName("ParentFolderId")
	@Expose
	private String parentFolderId;
	
	@SerializedName("Birthday")
	@Expose
	private String birthday;
	
	@SerializedName("FileAs")
	@Expose
	private String fileAs;
	
	@SerializedName("DisplayName")
	@Expose
	private String displayName;
	
	@SerializedName("GivenName")
	@Expose
	private String givenName;
	
	@SerializedName("Initials")
	@Expose
	private String initials;
	
	@SerializedName("MiddleName")
	@Expose
	private String middleName;
	
	@SerializedName("NickName")
	@Expose
	private String nickName;
	
	@SerializedName("Surname")
	@Expose
	private String surname;
	
	@SerializedName("Title")
	@Expose
	private String title;
	
	@SerializedName("Generation")
	@Expose
	private Object generation;
	
	@SerializedName("EmailAddress1")
	@Expose
	private String emailAddress1;
	
	@SerializedName("EmailAddress2")
	@Expose
	private String emailAddress2;
	
	@SerializedName("ImAddress1")
	@Expose
	private String imAddress1;
	
	@SerializedName("ImAddress2")
	@Expose
	private String imAddress2;
	
	@SerializedName("ImAddress3")
	@Expose
	private String imAddress3;
	
	@SerializedName("JobTitle")
	@Expose
	private String jobTitle;
	
	@SerializedName("CompanyName")
	@Expose
	private String companyName;
	
	@SerializedName("Department")
	@Expose
	private String department;
	
	@SerializedName("OfficeLocation")
	@Expose
	private String officeLocation;
	
	@SerializedName("Profession")
	@Expose
	private String profession;
	
	@SerializedName("BusinessHomePage")
	@Expose
	private String businessHomePage;
	
	@SerializedName("AssistantName")
	@Expose
	private String assistantName;
	
	@SerializedName("Manager")
	@Expose
	private String manager;
	
	@SerializedName("HomePhone1")
	@Expose
	private String homePhone1;
	
	@SerializedName("HomePhone2")
	@Expose
	private String homePhone2;
	
	@SerializedName("BusinessPhone1")
	@Expose
	private String businessPhone1;
	
	@SerializedName("BusinessPhone2")
	@Expose
	private String businessPhone2;
	
	@SerializedName("MobilePhone1")
	@Expose
	private String mobilePhone1;
	
	@SerializedName("OtherPhone")
	@Expose
	private String otherPhone;
	
	@SerializedName("DateTimeCreated")
	@Expose
	private String dateTimeCreated;
	
	@SerializedName("LastModifiedTime")
	@Expose
	private String lastModifiedTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChangeKey() {
		return changeKey;
	}

	public void setChangeKey(String changeKey) {
		this.changeKey = changeKey;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBodyPreview() {
		return bodyPreview;
	}

	public void setBodyPreview(String bodyPreview) {
		this.bodyPreview = bodyPreview;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public List<Object> getCategories() {
		return categories;
	}

	public void setCategories(List<Object> categories) {
		this.categories = categories;
	}

	public boolean isHasAttachments() {
		return hasAttachments;
	}

	public void setHasAttachments(boolean hasAttachments) {
		this.hasAttachments = hasAttachments;
	}

	public String getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getFileAs() {
		return fileAs;
	}

	public void setFileAs(String fileAs) {
		this.fileAs = fileAs;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getGeneration() {
		return generation;
	}

	public void setGeneration(Object generation) {
		this.generation = generation;
	}

	public String getEmailAddress1() {
		return emailAddress1;
	}

	public void setEmailAddress1(String emailAddress1) {
		this.emailAddress1 = emailAddress1;
	}

	public String getEmailAddress2() {
		return emailAddress2;
	}

	public void setEmailAddress2(String emailAddress2) {
		this.emailAddress2 = emailAddress2;
	}

	public String getImAddress1() {
		return imAddress1;
	}

	public void setImAddress1(String imAddress1) {
		this.imAddress1 = imAddress1;
	}

	public String getImAddress2() {
		return imAddress2;
	}

	public void setImAddress2(String imAddress2) {
		this.imAddress2 = imAddress2;
	}

	public String getImAddress3() {
		return imAddress3;
	}

	public void setImAddress3(String imAddress3) {
		this.imAddress3 = imAddress3;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getBusinessHomePage() {
		return businessHomePage;
	}

	public void setBusinessHomePage(String businessHomePage) {
		this.businessHomePage = businessHomePage;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getHomePhone1() {
		return homePhone1;
	}

	public void setHomePhone1(String homePhone1) {
		this.homePhone1 = homePhone1;
	}

	public String getHomePhone2() {
		return homePhone2;
	}

	public void setHomePhone2(String homePhone2) {
		this.homePhone2 = homePhone2;
	}

	public String getBusinessPhone1() {
		return businessPhone1;
	}

	public void setBusinessPhone1(String businessPhone1) {
		this.businessPhone1 = businessPhone1;
	}

	public String getBusinessPhone2() {
		return businessPhone2;
	}

	public void setBusinessPhone2(String businessPhone2) {
		this.businessPhone2 = businessPhone2;
	}

	public String getMobilePhone1() {
		return mobilePhone1;
	}

	public void setMobilePhone1(String mobilePhone1) {
		this.mobilePhone1 = mobilePhone1;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	public String getDateTimeCreated() {
		return dateTimeCreated;
	}

	public void setDateTimeCreated(String dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}
