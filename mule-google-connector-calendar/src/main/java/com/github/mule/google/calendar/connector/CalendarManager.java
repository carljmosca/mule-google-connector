package com.github.mule.google.calendar.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.mule.google.calendar.connector.utility.Utility;
import com.github.mule.google.wrapper.Attendee;
import com.github.mule.google.wrapper.CalendarEvent;
import com.github.mule.google.wrapper.OperationResult;
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
			System.getProperty("user.home"),
			".store/google_calendar_data_store");

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

	/**
	 * 
	 * @param calendarEvent
	 * @return
	 */
	public OperationResult createEvent(CalendarEvent calendarEvent) {
		OperationResult result = new OperationResult();
		Calendar calendar = null;
		try {
			calendar = getCalendar(calendarEvent.getCalendarId());
			if (calendar != null) {
				addEvent(calendarEvent);
				result.setSuccess(true);
			} else {
				LOGGER.debug("Could not getCalendar: " + calendarEvent.toString());
			}
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param calendarEvent
	 * @return
	 */
	public OperationResult updateEvent(CalendarEvent calendarEvent) {
		OperationResult result = new OperationResult();
		Calendar calendar = null;
		Event event = null;
		try {
			Event originalEvent = getCalendarEvent(
					calendarEvent.getCalendarId(), calendarEvent.getEventId());
			if (originalEvent != null) {
				calendar = getCalendar(calendarEvent.getCalendarId());

				if (calendar != null) {
					event = addEvent(calendarEvent);
					result.setId(event.getId());
					result.setSuccess(event != null);
					if (result.isSuccess()) {
						deleteEvent(calendarEvent);
					}
				} else {
					LOGGER.debug("Could not getCalendar: " + calendarEvent.toString());
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public OperationResult createCalendar(String calendarSummary) {
		OperationResult result = new OperationResult();
		try {
			Calendar calendar = addCalendar(calendarSummary);
			result.setId(calendar.getId());
			result.setSuccess(true);
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public OperationResult deleteCalendar(String calendarId) {
		OperationResult result = new OperationResult();
		try {
			client.calendars().delete(calendarId).execute();
			result.setSuccess(true);
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public OperationResult deleteEvent(CalendarEvent calendarEvent) {
		OperationResult result = new OperationResult();
		try {
			List<Event> events = getCalendarEvents(calendarEvent
					.getCalendarId());
			for (Event event : events) {
				if (calendarEvent.getEventId().equals(event.getId())) {
					client.events()
							.delete(calendarEvent.getCalendarId(),
									event.getId()).execute();
					result.setSuccess(true);
					return result;
				}
			}
		} catch (IOException e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public OperationResult clearCalendar(String calendarId) {
		OperationResult result = new OperationResult();
		try {
			List<Event> events = getCalendarEvents(calendarId);
			for (Event event : events) {
				client.events().delete(calendarId, event.getId()).execute();
			}
			result.setSuccess(true);
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	private List<Event> getCalendarEvents(String calendarId) {
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
				result.add(event);
			}
			pageToken = events.getNextPageToken();
		} while (pageToken != null);
		return result;
	}

	private Event getCalendarEvent(String calendarId, String eventId) {
		List<Event> events = getCalendarEvents(calendarId);
		for (Event event : events) {
			if (event.getId().equals(eventId)) {
				return event;
			}
		}
		return null;
	}

	public OperationResult calendarExists(String calendarId) {
		OperationResult result = new OperationResult();
		try {
			Calendar calendar = getCalendar(calendarId);
			result.setSuccess(calendar != null);
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	private static Calendar addCalendar(String calendarSummary)
			throws IOException {
		Calendar entry = new Calendar();
		entry.setSummary(calendarSummary);
		Calendar result = client.calendars().insert(entry).execute();
		return result;
	}

	private static Calendar getCalendar(String calendarId) throws IOException {
		Calendar result = client.calendars().get(calendarId).execute();
		return result;
	}

	public com.github.mule.google.wrapper.Calendar findCalendar(String summary) {
		com.github.mule.google.wrapper.Calendar result = null;
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
					result = new com.github.mule.google.wrapper.Calendar();
					result.setId(calendarListEntry.getId());
					result.setDescription(calendarListEntry.getDescription());
					result.setSummary(calendarListEntry.getSummary());
					return result;
				}
			}
			pageToken = calendarList.getNextPageToken();
		} while (pageToken != null);
		return result;
	}

	public List<Event> searchEvent(CalendarEvent calendarEvent) {
		return searchEvent(calendarEvent, "yyyy-MM-dd HH:mm:ss");
	}

	public List<Event> searchEvent(CalendarEvent calendarEvent,
			String dateFormat) {
		List<Event> result = new ArrayList<Event>(0);
		List<Event> events = getCalendarEvents(calendarEvent.getCalendarId());
		for (Event event : events) {
			boolean found = false;
			if (calendarEvent.getEventId() != null
					&& calendarEvent.getEventId().length() > 0) {
				found = event.getId().equals(calendarEvent.getEventId());
				if (!found)
					break;
			}
			if (calendarEvent.getSummary() != null
					&& calendarEvent.getSummary().length() > 0) {
				found = event.getSummary().indexOf(calendarEvent.getSummary()) >= 0;
				if (!found)
					break;
			}
			if (!found && calendarEvent.getDescription() != null
					&& calendarEvent.getDescription().length() > 0) {
				found = event.getDescription().indexOf(
						calendarEvent.getDescription()) >= 0;
				if (!found)
					break;
			}
			if (calendarEvent.getStart() != null) {
				found = Utility.compareDates(new Date(event.getStart()
						.getDateTime().getValue()), calendarEvent.getStart()
						.getTime(), dateFormat);
				if (!found)
					break;
			}
			if (found) {
				result.add(event);
			}
		}
		return result;
	}

	private static Event addEvent(CalendarEvent calendarEvent)
			throws IOException {
		Event event = newEvent(calendarEvent.getStart(), calendarEvent.getEnd());
		event.setSummary(calendarEvent.getSummary());
		event.setDescription(calendarEvent.getDescription());
		event.setLocation(calendarEvent.getLocation());
		boolean sendNotificaitons = false;
		if (calendarEvent.getAttendees() != null
				&& !calendarEvent.getAttendees().isEmpty()) {
			List<EventAttendee> eventAttendees = new ArrayList<EventAttendee>(0);
			for (Attendee attendee : calendarEvent.getAttendees()) {
				EventAttendee ea = new EventAttendee();
				if (attendee.getEmail() != null)
					ea.setEmail(attendee.getEmail());
				if (attendee.getName() != null)
					ea.setDisplayName(attendee.getName());
				eventAttendees.add(ea);
				sendNotificaitons = true;
			}
			event.setAttendees(eventAttendees);
		}
		Event result = client.events()
				.insert(calendarEvent.getCalendarId(), event)
				.setSendNotifications(sendNotificaitons).execute();
		return result;
	}

	private static Event newEvent(java.util.Calendar startDate,
			java.util.Calendar endDate) {
		Event event = new Event();
		event.setSummary("New Event");
		DateTime start = new DateTime(startDate.getTime(),
				startDate.getTimeZone());
		event.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate.getTime(), endDate.getTimeZone());
		event.setEnd(new EventDateTime().setDateTime(end));
		return event;
	}

	/**
	 * Authorizes the installed application to access user's protected data.
	 */
	private static Credential authorize() throws Exception {
		// load client secrets
		GoogleAuthorizationCodeFlow flow = null;
		FileInputStream CLIENT_SECRETS_FILE = new FileInputStream(
				System.getProperty("user.home") + "/.store/client_secrets.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY, new InputStreamReader(CLIENT_SECRETS_FILE));
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
