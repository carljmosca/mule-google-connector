package com.github.mule.google.wrapper;

import java.io.Serializable;


public class Calendar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3010767421694926623L;
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

	@Override
	public String toString() {
		return "Calendar [id=" + id + ", summary=" + summary + ", description="
				+ description + "]";
	}
	
	
}