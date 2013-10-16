package com.github.mule.google.calendar.connector;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.mule.google.calendar.connector.utility.Utility;
import com.github.mule.google.wrapper.CalendarEventRequest;
import com.github.mule.google.wrapper.CalendarRequest;
import com.github.mule.google.wrapper.CalendarResponse;

public class CalendarManagerTest {

	private final String TEST_CALENDAR_NAME = "Test Calendar";
	
	@Test
	public void testCalendarManager() {
		// fail("Not yet implemented");
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
						Utility.stringToDate("2013-9-13 11:00:00 AM"));
				calendarEvent.getEnd().setTime(
						Utility.stringToDate("2013-9-25 09:45AM"));
				calendarEvent.setSummary("new item");
				calendarEvent.setDescription("Test calendar item");
				calendarEvent.setLocation("main conference room");
				List<String> guestList = new ArrayList<String>(0);
				result = calendarManager.createEvent(calendarEvent);
			} finally {
				calendarManager.deleteCalendar(calendarId);
				result = calendarManager.calendarExists(calendarId);
			}
		}
		assertTrue(!result.isSuccess());
	}

	@Test
	public void testClearCalendar() {
		CalendarResponse result = new CalendarResponse();
		CalendarManager calendarManager = new CalendarManager();
		CalendarRequest calendarRequest = new CalendarRequest();
		calendarRequest.setSummary(TEST_CALENDAR_NAME);
		CalendarRequest calendar = calendarManager.findCalendar(calendarRequest);
		if (calendar != null)
			result = calendarManager.clearCalendar(calendar.getId());
		System.out.println("clearCalendar: " + result.getMessage());
		assertTrue(result.isSuccess());
	}

	@Test
	public void testCreateCalendar() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRemoveTestCalendars() {
		CalendarManager calendarManager = new CalendarManager();
		while (true) {
			CalendarRequest calendarRequest = new CalendarRequest();
			calendarRequest.setSummary(TEST_CALENDAR_NAME);
			CalendarRequest calendar = calendarManager.findCalendar(calendarRequest);
			if (calendar != null && calendar.getId() != null) {
				CalendarResponse result = calendarManager
						.deleteCalendar(calendar.getId());
				if (!result.isSuccess())
					break;
			} else {
				break;
			}
		}
	}

}
