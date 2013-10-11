package com.github.mule.google.calendar.connector;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.mule.google.calendar.connector.utility.Utility;
import com.github.mule.google.wrapper.Calendar;
import com.github.mule.google.wrapper.CalendarEvent;
import com.github.mule.google.wrapper.OperationResult;

public class CalendarManagerTest {

	@Test
	public void testCalendarManager() {
		// fail("Not yet implemented");
	}

	@Test
	public void testCreateEvent() throws ParseException {
		OperationResult result = new OperationResult();
		CalendarManager calendarManager = new CalendarManager();
		String calendarId = "";
		result = calendarManager.createCalendar("Test Calendar");
		if (!result.isSuccess()) {
			System.out.println("could not create Test Calendar");
		} else {
			calendarId = result.getId();
			result = calendarManager.calendarExists(result.getId());
		}
		if (result.isSuccess()) {
			try {
				CalendarEvent calendarEvent = new CalendarEvent();
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
		OperationResult result = new OperationResult();
		CalendarManager calendarManager = new CalendarManager();
		Calendar calendar = calendarManager.findCalendar("Test Calendar");
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
			Calendar calendar = calendarManager.findCalendar("Test Calendar");
			if (calendar != null && calendar.getId() != null) {
				OperationResult result = calendarManager
						.deleteCalendar(calendar.getId());
				if (!result.isSuccess())
					break;
			} else {
				break;
			}
		}
	}

}
