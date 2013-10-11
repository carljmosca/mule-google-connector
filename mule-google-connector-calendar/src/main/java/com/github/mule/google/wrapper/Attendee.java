package com.github.mule.google.wrapper;

import com.google.api.client.json.GenericJson;

public class Attendee extends GenericJson {

	private String email;
	private String mobile;
	private String name;
	
	public Attendee() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
