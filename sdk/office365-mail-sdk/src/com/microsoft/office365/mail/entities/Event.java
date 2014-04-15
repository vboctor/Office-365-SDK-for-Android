package com.microsoft.office365.mail.entities;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

	public class Attendee {

		@SerializedName("Name")
		@Expose
		private String name;

		@SerializedName("Address")
		@Expose
		private String address;

		@SerializedName("Status")
		@Expose
		private Object status;

		@SerializedName("Type")
		@Expose
		private String type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Object getStatus() {
			return status;
		}

		public void setStatus(Object status) {
			this.status = status;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	@SerializedName("Id")
	@Expose
	private String id;
	
	@SerializedName("ChangeKey")
	@Expose
	private String changeKey;
	
	@SerializedName("Subject")
	@Expose
	private String subject;
	
	@SerializedName("BodyPreview")
	@Expose
	private Object bodyPreview;
	
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
	private Boolean hasAttachments;
	
	@SerializedName("Start")
	@Expose
	private String start;
	
	@SerializedName("End")
	@Expose
	private String end;
	
	@SerializedName("Location")
	@Expose
	private Object location;
	
	@SerializedName("ShowAs")
	@Expose
	private String showAs;
	
	@SerializedName("IsAllDay")
	@Expose
	private Boolean isAllDay;
	
	@SerializedName("IsCancelled")
	@Expose
	private Boolean isCancelled;
	
	@SerializedName("IsOrganizer")
	@Expose
	private Boolean isOrganizer;
	
	@SerializedName("ResponseRequested")
	@Expose
	private Boolean responseRequested;
	
	@SerializedName("Type")
	@Expose
	private String type;
	
	@SerializedName("SeriesId")
	@Expose
	private String seriesId;
	
	@SerializedName("Attendees")
	@Expose
	private List<Attendee> attendees = new ArrayList<Attendee>();
	
	@SerializedName("Recurrence")
	@Expose
	private String recurrence;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Object getBodyPreview() {
		return bodyPreview;
	}

	public void setBodyPreview(Object bodyPreview) {
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

	public Boolean getHasAttachments() {
		return hasAttachments;
	}

	public void setHasAttachments(Boolean hasAttachments) {
		this.hasAttachments = hasAttachments;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Object getLocation() {
		return location;
	}

	public void setLocation(Object location) {
		this.location = location;
	}

	public String getShowAs() {
		return showAs;
	}

	public void setShowAs(String showAs) {
		this.showAs = showAs;
	}

	public Boolean getIsAllDay() {
		return isAllDay;
	}

	public void setIsAllDay(Boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

	public Boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public Boolean getIsOrganizer() {
		return isOrganizer;
	}

	public void setIsOrganizer(Boolean isOrganizer) {
		this.isOrganizer = isOrganizer;
	}

	public Boolean getResponseRequested() {
		return responseRequested;
	}

	public void setResponseRequested(Boolean responseRequested) {
		this.responseRequested = responseRequested;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	public List<Attendee> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
}