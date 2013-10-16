package com.github.mule.google.wrapper;

import java.io.Serializable;
import java.util.Calendar;

import org.mule.api.annotations.param.Optional;

public class CalendarEventRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4697962219105356828L;
	
	private String calendarId = "";
	private String eventId = "";
	private String summary = "";
	@Optional
	private String description = "";
	private java.util.Calendar start;
	private java.util.Calendar end;
	@Optional
	private String location;
//	@Optional
//	private List<Attendee> attendees;
	
	public CalendarEventRequest() {
	}
	
	

	public CalendarEventRequest(String calendarId, String eventId,
			String summary, String description, Calendar start, Calendar end,
			String location) {
		super();
		this.calendarId = calendarId;
		this.eventId = eventId;
		this.summary = summary;
		this.description = description;
		this.start = start;
		this.end = end;
		this.location = location;
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

//	public List<Attendee> getAttendees() {
//		return attendees;
//	}
//
//	public void setAttendees(List<Attendee> attendees) {
//		this.attendees = attendees;
//	}
	
	@Override
	public String toString() {
		return "CalendarEvent [calendarId=" + calendarId + ", eventId="
				+ eventId + ", summary=" + summary + ", description="
				+ description + ", start=" + start + ", end=" + end
				+ ", location=" + location + 
				//", attendees=" + attendees + 
				"]";
	}
}