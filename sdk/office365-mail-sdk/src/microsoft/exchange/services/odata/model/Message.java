package  microsoft.exchange.services.odata.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

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
	private ItemBody body;

	@SerializedName("Importance")
	@Expose
	private String importance;

	@SerializedName("Categories")
	@Expose
	private List<Object> categories = new ArrayList<Object>(); // review this

	@SerializedName("HasAttachments")
	@Expose
	private Boolean hasAttachments;
	@SerializedName("ParentFolderId")
	@Expose
	private String parentFolderId;
	@SerializedName("From")
	@Expose
	private Recipient from;
	@SerializedName("Sender")
	@Expose
	private Recipient sender;
	@SerializedName("ToRecipients")
	@Expose
	private List<Recipient> toRecipients = new ArrayList<Recipient>();
	@SerializedName("CcRecipients")
	@Expose
	private List<Recipient> ccRecipients = new ArrayList<Recipient>();
	@SerializedName("BccRecipients")
	@Expose
	private List<Recipient> bccRecipients = new ArrayList<Recipient>();
	@SerializedName("ReplyTo")
	@Expose
	private List<Recipient> replyTo = new ArrayList<Recipient>();
	@SerializedName("ConversationId")
	@Expose
	private String conversationId;
	@SerializedName("DateTimeReceived")
	@Expose
	private String dateTimeReceived;
	@SerializedName("DateTimeSent")
	@Expose
	private String dateTimeSent;
	@SerializedName("IsDeliveryReceiptRequested")
	@Expose
	private Boolean isDeliveryReceiptRequested;
	@SerializedName("IsReadReceiptRequested")
	@Expose
	private Boolean isReadReceiptRequested;
	@SerializedName("IsDraft")
	@Expose
	private Boolean isDraft;
	@SerializedName("IsRead")
	@Expose
	private Boolean isRead;
	@SerializedName("EventId")
	@Expose
	private String eventId;
	@SerializedName("MeetingMessageType")
	@Expose
	private String meetingMessageType;

	@SerializedName("DateTimeCreated")
	@Expose
	private String dateTimeCreated; // to date

	@SerializedName("LastModifiedTime")
	@Expose
	private String lastModifiedTime; // to date

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

	public ItemBody getBody() {
		return body;
	}

	public void setBody(ItemBody body) {
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

	public String getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public Recipient getFrom() {
		return from;
	}

	public void setFrom(Recipient from) {
		this.from = from;
	}

	public Recipient getSender() {
		return sender;
	}

	public void setSender(Recipient sender) {
		this.sender = sender;
	}

	public List<Recipient> getToRecipients() {
		return toRecipients;
	}

	public void setToRecipients(List<Recipient> toRecipients) {
		this.toRecipients = toRecipients;
	}

	public List<Recipient> getCcRecipients() {
		return ccRecipients;
	}

	public void setCcRecipients(List<Recipient> ccRecipients) {
		this.ccRecipients = ccRecipients;
	}

	public List<Recipient> getBccRecipients() {
		return bccRecipients;
	}

	public void setBccRecipients(List<Recipient> bccRecipients) {
		this.bccRecipients = bccRecipients;
	}

	public List<Recipient> getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(List<Recipient> replyTo) {
		this.replyTo = replyTo;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getDateTimeReceived() {
		return dateTimeReceived;
	}

	public void setDateTimeReceived(String dateTimeReceived) {
		this.dateTimeReceived = dateTimeReceived;
	}

	public String getDateTimeSent() {
		return dateTimeSent;
	}

	public void setDateTimeSent(String dateTimeSent) {
		this.dateTimeSent = dateTimeSent;
	}

	public Object getIsDeliveryReceiptRequested() {
		return isDeliveryReceiptRequested;
	}

	public void setIsDeliveryReceiptRequested(Boolean isDeliveryReceiptRequested) {
		this.isDeliveryReceiptRequested = isDeliveryReceiptRequested;
	}

	public Boolean getIsReadReceiptRequested() {
		return isReadReceiptRequested;
	}

	public void setIsReadReceiptRequested(Boolean isReadReceiptRequested) {
		this.isReadReceiptRequested = isReadReceiptRequested;
	}

	public Boolean getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Object getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getMeetingMessageType() {
		return meetingMessageType;
	}

	public void setMeetingMessageType(String meetingMessageType) {
		this.meetingMessageType = meetingMessageType;
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