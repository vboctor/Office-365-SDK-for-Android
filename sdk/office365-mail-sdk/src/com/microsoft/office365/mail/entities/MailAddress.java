package com.microsoft.office365.mail.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MailAddress {
	@SerializedName("Name")
	@Expose
	private String name;
	
	@SerializedName("Address")
	@Expose
	private String address;

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
}
