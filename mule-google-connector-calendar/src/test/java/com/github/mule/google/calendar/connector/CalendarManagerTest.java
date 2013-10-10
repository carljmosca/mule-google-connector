package com.github.mule.google.calendar.connector;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
		result = calendarManager.createCalendar("Test Calendar");
		if (result.isSuccess())
			result = calendarManager.calendarExists(result.getId());
		if (result.isSuccess()) {
			try {
				CalendarEvent calendarEvent = new CalendarEvent();
				calendarEvent.setCalendarId(result.getId());
				String startDate = "2013-9-13 11:00:00 AM";
				// String startDate = "2013-9-25 09:30AM";
				String endDate = "2013-9-25 09:45AM";
				String summary = "new item";
				String description = "Test calendar item";
				String location = "main conference room";
				List<String> guestList = new ArrayList<String>(0);
				result = calendarManager.createEvent(calendarEvent);
			} finally {
				calendarManager.deleteCalendar(result.getId());				
				result = calendarManager.calendarExists(result.getId());
			}
		}
		assertTrue(result.isSuccess());
	}

	@Test
	public void testClearCalendar() {
		OperationResult result = new OperationResult();
		CalendarManager calendarManager = new CalendarManager();
		Calendar calendar = calendarManager.findCalendar("Test Calendar");
		result = calendarManager.clearCalendar(calendar.getId());
		assertTrue(result.isSuccess());
	}

	@Test
	public void testCreateCalendar() {
		// fail("Not yet implemented");
	}

}
