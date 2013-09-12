package com.github.mule.google.calendar.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

public class CalendarManager {
	

	private static Logger LOGGER = Logger.getLogger(CalendarManager.class);
	private static com.google.api.services.calendar.Calendar client;
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();
	private static HttpTransport httpTransport;
	private static final String APPLICATION_NAME = "CalendarDemo/1.0";
	private static FileDataStoreFactory dataStoreFactory;
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".store/google_calendar_data_store");

	public CalendarManager() {
		try {
			// initialize the transport
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			// initialize the data store factory
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

			// authorization
			Credential credential = authorize();

			// set up global Calendar instance
			client = new com.google.api.services.calendar.Calendar.Builder(
					httpTransport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();

		} catch (IOException e) {
			LOGGER.error(e);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public String createEvent(String calendarId, String startDate, String endDate,
			TimeZone timeZone, String summary, String description, String location,
			List<String> guestList) {
		String result = "";
		Calendar calendar = null;
		try {
			calendar = getCalendar(calendarId);
			if (calendar != null) {
				addEvent(calendar, startDate, endDate, timeZone, summary, description, location, guestList);
			}
		} catch (IOException e) {
			LOGGER.error(e);
			result = e.getMessage();
		}
		return result;
	}
	
	public String updateEvent(String calendarId, String eventId, String startDate, String endDate,
			TimeZone timeZone, String summary, String description, String location,
			List<String> guestList) {
		String result = "";
		Calendar calendar = null;
		Event event = null;
		try {
			calendar = getCalendar(calendarId);
			if (calendar != null) {
				updateEvent(calendar, event, startDate, endDate, timeZone, summary, description, location, guestList);
			}
		} catch (IOException e) {
			LOGGER.error(e);
			result = e.getMessage();
		}
		return result;
	}

	public String createCalendar(String calendarSummary) {
		String calendarId = "";
		try {
			Calendar calendar = addCalendar(calendarSummary);
			calendarId = calendar.getId();
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return calendarId;
	}

	public boolean deleteCalendar(String calendarId) {
		boolean result = false;
		try {
			client.calendars().delete(calendarId).execute();
			result = true;
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return result;
	}
	
	public boolean clearCalendar(String calendarId) {
		boolean result = false;
		try {
			client.calendars().clear(calendarId);
			result = true;
		} catch (IOException e) {
			LOGGER.error(e);
		}		
		return result;
	}
	
	public boolean calendarExists(String calendarId) {
		boolean found = false;
		try {
			Calendar calendar = getCalendar(calendarId);
			found = calendar != null;
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return found;
	}
	
	private static Calendar addCalendar(String calendarSummary)
			throws IOException {
		Calendar entry = new Calendar();
		entry.setSummary(calendarSummary);
		Calendar result = client.calendars().insert(entry).execute();
		return result;
	}

	private static Calendar getCalendar(String calendarId)
			throws IOException {
		Calendar result = client.calendars().get(calendarId).execute();
		return result;
	}
	
	public String findCalendar(String summary) {
		String result = null;
		String pageToken = null;
		do {
			CalendarList calendarList = null;
			try {
				calendarList = client.calendarList().list()
						.setPageToken(pageToken).execute();
			} catch (IOException e) {
			}
			List<CalendarListEntry> items = calendarList.getItems();

			for (CalendarListEntry calendarListEntry : items) {
				if (calendarListEntry.getSummary().equals(summary)) {
					return calendarListEntry.getId();
				}
			}
			pageToken = calendarList.getNextPageToken();
		} while (pageToken != null);
		return result;
	}

	public List<Event> searchEvent(String calendarId, String summary,
			String description) {
		List<Event> result = new ArrayList<Event>(0);
		String pageToken = null;
		do {
			Events events = null;
			try {
				events = client.events().list(calendarId)
						.setPageToken(pageToken).execute();
			} catch (IOException e) {
				LOGGER.error(e);
			}
			List<Event> items = events.getItems();
			for (Event event : items) {
				boolean found = false;
				if (summary != null && summary.length() > 0 && event.getSummary().indexOf(summary) >= 0) {
					found = true;
				}
				if (!found && description != null && description.length() > 0 && event.getDescription().indexOf(description) >= 0) {
					found = true;
				}
				if (found){
					result.add(event);
				}
			}
			pageToken = events.getNextPageToken();
		} while (pageToken != null);
		return result;
	}
	
	private static Event addEvent(Calendar calendar, String startDate,
			String endDate, TimeZone timeZone, String summary, String description, String location,
			List<String> guestList) throws IOException {
		Event event = newEvent(stringToDate(startDate), stringToDate(endDate), timeZone);
		event.setSummary(summary);
		event.setDescription(description);
		event.setLocation(location);
		boolean sendNotificaitons = false;
		if (guestList != null && !guestList.isEmpty()) {
			List<EventAttendee> attendees = new ArrayList<EventAttendee>(0);
			for (String email : guestList) {
				EventAttendee ea = new EventAttendee();
				ea.setEmail(email);
				attendees.add(ea);
				sendNotificaitons = true;
			}
			event.setAttendees(attendees);
		}
		Event result = client.events().insert(calendar.getId(), event).setSendNotifications(sendNotificaitons)
				.execute();	
		return result;
	}
	
	private static Event updateEvent(Calendar calendar, Event event, String startDate,
			String endDate, TimeZone timeZone, String summary, String description, String location,
			List<String> guestList) throws IOException {
		event.setSummary(summary);
		event.setDescription(description);
		event.setLocation(location);
		boolean sendNotificaitons = false;
		if (guestList != null && !guestList.isEmpty()) {
			List<EventAttendee> attendees = new ArrayList<EventAttendee>(0);
			for (String email : guestList) {
				EventAttendee ea = new EventAttendee();
				ea.setEmail(email);
				attendees.add(ea);
				sendNotificaitons = true;
			}
			event.setAttendees(attendees);
		}
		Event result = client.events().insert(calendar.getId(), event).setSendNotifications(sendNotificaitons)
				.execute();	
		return result;
	}

	private static Event newEvent(Date startDate, Date endDate, TimeZone timeZone) {
		Event event = new Event();
		event.setSummary("New Event");
		DateTime start = new DateTime(startDate, timeZone);
		event.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate, timeZone);
		event.setEnd(new EventDateTime().setDateTime(end));
		return event;
	}
	
	private static Date stringToDate(String value) {
		Date date = null;
		String[] simpleDateFormats = {"yyyy-MM-dd hh:mm:ss a", "yyyy-M-d hh:mma", "yyyy-MM-dd HH:mm:ss", "MM/dd/yy hh:mm:ss a", "MM/dd/yy hh:mm:ssa",
				"MM/dd/yyyy hh:mm:ss a", "MM/dd/yyyy HH:mm:ss"};
		for (String s : simpleDateFormats) {
			SimpleDateFormat sdf = new SimpleDateFormat(s);
			try {
				date = sdf.parse(value);
				return date;
			} catch (ParseException e) {
			}
		}
		LOGGER.error("Unsupported date format: " + value);
		return date;
	}

	/**
	 * Authorizes the installed application to access user's protected data.
	 */
	private static Credential authorize() throws Exception {
		// load client secrets
		GoogleAuthorizationCodeFlow flow = null;
		FileInputStream CLIENT_SECRETS_FILE = new FileInputStream(System.getProperty("user.home") + "/.store/client_secrets.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY,
				new InputStreamReader(CLIENT_SECRETS_FILE));
		if (clientSecrets.getDetails().getClientId().startsWith("Enter")
				|| clientSecrets.getDetails().getClientSecret()
						.startsWith("Enter ")) {

		} else {
			// set up authorization code flow
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
					JSON_FACTORY, clientSecrets,
					Collections.singleton(CalendarScopes.CALENDAR))
					.setDataStoreFactory(dataStoreFactory).build();
		}
		// authorize
		return new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
	}
}
