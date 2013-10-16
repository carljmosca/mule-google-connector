package com.github.mule.google.wrapper;

import java.io.Serializable;


public class CalendarRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3010767421694926623L;
	private String id = "";
	private String summary = "";
	private String description = "";
	private CalendarEventRequest calendarEventRequest;
		
	public CalendarRequest() {

	}

	public CalendarRequest(String id, String summary, String description) {
		super();
		this.id = id;
		this.summary = summary;
		this.description = description;
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

	public CalendarEventRequest getCalendarEventRequest() {
		return calendarEventRequest;
	}

	public void setCalendarEventRequest(CalendarEventRequest calendarEventRequest) {
		this.calendarEventRequest = calendarEventRequest;
	}

	@Override
	public String toString() {
		return "Calendar [id=" + id + ", summary=" + summary + ", description="
				+ description + ", calendarEventRequest= " + calendarEventRequest.toString() + "]";
	}
	
	
}