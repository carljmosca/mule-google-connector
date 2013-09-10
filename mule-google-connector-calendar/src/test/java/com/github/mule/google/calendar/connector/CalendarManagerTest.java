package com.github.mule.google.calendar.connector;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;

public class CalendarManagerTest {

	@Test
	public void testCalendarManager() {
//		fail("Not yet implemented");
	}

	@Test
	public void testCreateEvent() throws ParseException {
		boolean completed = false;
		CalendarManager calendarManager = new CalendarManager();
		String calendarId = calendarManager.createCalendar("Test Calendar");
		if (calendarManager.calendarExists(calendarId)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss a");
				Date startDate = sdf.parse("2013-10-01 12:30:00 PM");
				Date endDate = sdf.parse("2013-10-01 01:30:00 PM");
				TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
				String summary = "new item";
				String description = "Test calendar item";
				String location = "main conference room";
				List<String> guestList = new ArrayList<String>(0);
				calendarManager.createEvent(calendarId, startDate, endDate,
						timeZone, summary, description, location, guestList);
			} finally {
				calendarManager.deleteCalendar(calendarId);
				completed = !calendarManager.calendarExists(calendarId);
			}
		}
		assertTrue(completed);
	}

	@Test
	public void testCreateCalendar() {
//		fail("Not yet implemented");
	}

}
