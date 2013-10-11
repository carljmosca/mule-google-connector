package com.github.mule.google.wrapper;

import com.google.api.client.json.GenericJson;

public class Calendar extends GenericJson {

	private String id;
	private String summary;
	private String description;
		
	public Calendar() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}