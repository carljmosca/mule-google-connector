package com.github.mule.google.calendar.connector;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.github.mule.google.calendar.connector.utility.Utility;
import com.github.mule.google.wrapper.Attendee;
import com.github.mule.google.wrapper.CalendarEventRequest;
import com.github.mule.google.wrapper.CalendarRequest;
import com.github.mule.google.wrapper.CalendarResponse;

public class CalendarManagerTest {

	private final String TEST_CALENDAR_NAME = "Test Calendar";
	private final String TEST_CALENDAR_DESCRIPTION = "really interesting description";
	
	@Test
	public void testCalendarManager() {
		CalendarManager calendarManager = new CalendarManager();
		assertTrue(calendarManager != null);
	}

	@Test
	public void testCreateEvent() throws ParseException {
		CalendarResponse result = new CalendarResponse();
		CalendarManager calendarManager = new CalendarManager();
		String calendarId = "";
		CalendarRequest calendarRequest = new CalendarRequest();
		calendarRequest.setSummary(TEST_CALENDAR_NAME);
		result = calendarManager.createCalendar(calendarRequest);
		if (!result.isSuccess()) {
			System.out.println("could not create Test Calendar");
		} else {
			calendarId = result.getCalendarId();
			result = calendarManager.calendarExists(result.getCalendarId());
		}
		if (result.isSuccess()) {
			try {
				CalendarEventRequest calendarEvent = new CalendarEventRequest();
				calendarEvent.setCalendarId(calendarId);
				calendarEvent.setStart(java.util.Calendar.getInstance());
				calendarEvent.setEnd(java.util.Calendar.getInstance());
				calendarEvent.getStart().setTime(
						Utility.stringToDate("2013-11-19 11:00:00 AM"));
				calendarEvent.getEnd().setTime(
						Utility.stringToDate("2013-11-19 11:30:00 AM"));
				calendarEvent.setSummary("new item");
				calendarEvent.setDescription("Test calendar item");
				calendarEvent.setLocation("main conference room");
				calendarEvent.setAttendees(getAttendees());
				result = calendarManager.createEvent(calendarEvent);
			} finally {
				calendarManager.deleteCalendar(calendarId);
				result = calendarManager.calendarExists(calendarId);
			}
		}
		assertTrue(!result.isSuccess());
	}
	
	private List<Attendee> getAttendees() {		
		List<Attendee> attendees = new ArrayList<Attendee>(0);
		Attendee attendee = new Attendee();
		attendee.setName("Test Person");
		attendee.setEmail("testperson@gee.male.com");
		attendees.add(attendee);
		return attendees;
	}
	
	@Test
	public void testUpdateEvent() {
		CalendarManager calendarManager = new CalendarManager();
		CalendarRequest calendarRequest = new CalendarRequest();
		calendarRequest.setSummary(TEST_CALENDAR_NAME);
		calendarRequest.setDescription(TEST_CALENDAR_DESCRIPTION);
		calendarRequest.getCalendarEventRequest().setSummary("event");
		calendarRequest.getCalendarEventRequest().setDescription("interesting description");
		calendarRequest.getCalendarEventRequest().setLocation("my location");
		calendarRequest.getCalendarEventRequest().setStart(Calendar.getInstance());
		calendarRequest.getCalendarEventRequest().setEnd(Calendar.getInstance());		
		calendarRequest.getCalendarEventRequest().setAttendees(getAttendees());
		CalendarResponse response = calendarManager.updateEvent(calendarRequest, true);
		if (response.isSuccess()) {
			calendarRequest.getCalendarEventRequest().setSummary("second event");
			calendarRequest.getCalendarEventRequest().setStart(Calendar.getInstance());
			calendarRequest.getCalendarEventRequest().setEnd(Calendar.getInstance());
			response = calendarManager.updateEvent(calendarRequest, true);
		}
		calendarRequest.setId("");
		response = calendarManager.deleteEvent(calendarRequest);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testClearCalendar() {
		// first we have to create a calendar in case none exist		
		CalendarResponse result = new CalendarResponse();
		CalendarManager calendarManager = new CalendarManager();
		CalendarRequest calendarRequest = new CalendarRequest();
		calendarRequest.setSummary(TEST_CALENDAR_NAME);
		result = calendarManager.createCalendar(calendarRequest);
		if (result.isSuccess()) {
			calendarRequest.setId(result.getCalendarId());
		}
		CalendarRequest calendar = calendarManager.findCalendar(calendarRequest);
		if (calendar != null)
			result = calendarManager.clearCalendar(calendar.getId());
		System.out.println("clearCalendar: " + result.getMessage());
		assertTrue(result.isSuccess());
	}

	@Test
	public void testCreateCalendar() {
		CalendarManager calendarManager = new CalendarManager();
		CalendarRequest calendarRequest = new CalendarRequest();
		calendarRequest.setSummary(TEST_CALENDAR_NAME);
		calendarRequest.setDescription(TEST_CALENDAR_DESCRIPTION);
		CalendarResponse response = calendarManager.createCalendar(calendarRequest);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testRemoveTestCalendars() {
		CalendarManager calendarManager = new CalendarManager();
		CalendarResponse result = new CalendarResponse();
		CalendarRequest calendarRequest = new CalendarRequest();
		calendarRequest.setSummary(TEST_CALENDAR_NAME);
		calendarRequest.setDescription(TEST_CALENDAR_DESCRIPTION);
		List<CalendarRequest> calendars = calendarManager.findCalendars(calendarRequest);
		for (CalendarRequest calendar : calendars) {
			result = calendarManager
					.deleteCalendar(calendar.getId());
			if (!result.isSuccess())
				break;
			else
				System.out.println("deleted: " + calendar.getId() + " " + calendar.getSummary());		
		}
		assertTrue(result.isSuccess());
	}	

}
