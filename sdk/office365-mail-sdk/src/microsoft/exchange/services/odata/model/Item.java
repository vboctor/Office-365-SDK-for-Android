package microsoft.exchange.services.odata.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

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
	protected ItemBody body;
	@SerializedName("Importance")
	@Expose
	protected String importance;
	@SerializedName("Categories")
	@Expose
	private List<Object> categories = new ArrayList<Object>();
	
	@SerializedName("Attachments")
	@Expose
	private List<Attachment> attachments =  new ArrayList<Attachment>();

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

	public List<Object> getCategories() {
		return categories;
	}

	public void setCategories(List<Object> categories) {
		this.categories = categories;
	}
	
	public void setAttachments(List<Attachment> attachments){
		this.attachments = attachments;
	}
	
	public List<Attachment> getAttachments(){
		return this.attachments;
	}

}
