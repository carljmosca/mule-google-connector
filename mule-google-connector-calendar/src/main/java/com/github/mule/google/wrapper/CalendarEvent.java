package com.github.mule.google.wrapper;

import java.util.List;

public class CalendarEvent {

	private String calendarId;
	private String eventId;
	private String summary;
	private String description;
	private java.util.Calendar start;
	private java.util.Calendar end;
	private String location;
	private List<Attendee> attendees;
	
	public CalendarEvent() {
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
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

	public java.util.Calendar getStart() {
		return start;
	}

	public void setStart(java.util.Calendar start) {
		this.start = start;
	}

	public java.util.Calendar getEnd() {
		return end;
	}

	public void setEnd(java.util.Calendar end) {
		this.end = end;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Attendee> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}
	
}