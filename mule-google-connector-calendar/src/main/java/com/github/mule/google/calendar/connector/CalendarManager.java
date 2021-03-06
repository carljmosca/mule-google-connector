package com.github.mule.google.calendar.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.mule.google.calendar.connector.utility.Utility;
import com.github.mule.google.wrapper.Attendee;
import com.github.mule.google.wrapper.CalendarEventRequest;
import com.github.mule.google.wrapper.CalendarRequest;
import com.github.mule.google.wrapper.CalendarResponse;
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
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.AclRule.Scope;
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
	 * Creates a new event
	 * 
	 * @param calendarEvent
	 * @return an instance of CalendarResponse
	 */
	public CalendarResponse createEvent(CalendarEventRequest calendarEvent) {
		CalendarResponse result = new CalendarResponse();
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
	 * Updates existing event as given or create new event and calendar if specified 
	 * 
	 * @param calendarEventRequest
	 * @return an instance of CalendarResponse
	 */
	public CalendarResponse updateEvent(CalendarRequest calendarRequest, boolean createIfNotFound) {
		CalendarResponse result = new CalendarResponse();
		Calendar calendar = null;
		Event event = null;
		try {
			CalendarRequest existingCalendar = findCalendar(calendarRequest);
			if (existingCalendar == null) {
				CalendarResponse response = createCalendar(calendarRequest);
				if (response.isSuccess()) {
					calendarRequest.setId(response.getCalendarId());
				} else {
					LOGGER.debug(response.getMessage());
				}
			} else {
				calendarRequest.setId(existingCalendar.getId());
			}
			Event originalEvent = null;
			if (!StringUtils.isEmpty(calendarRequest.getCalendarEventRequest().getEventId())) {
				originalEvent = getCalendarEvent(calendarRequest.getId(), calendarRequest.getCalendarEventRequest().getEventId());
			} else {
				List<Event> events = findEvents(calendarRequest.getCalendarEventRequest());
				if (events.size() > 0)
					originalEvent = events.get(0);
			}
			if (originalEvent != null || createIfNotFound) {
				calendar = getCalendar(calendarRequest.getId());
				if (calendar != null) {
					calendarRequest.getCalendarEventRequest().setCalendarId(calendar.getId());
					event = addEvent(calendarRequest.getCalendarEventRequest());
					result.setEventId(event.getId());
					result.setSuccess(event != null);
					if (result.isSuccess() && originalEvent != null) {
						deleteEvent(calendarRequest);
					}
				} else {
					result.setMessage("Could not getCalendar: " + calendarRequest.toString());
					LOGGER.debug(result.getMessage());
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * Creates new calendar as given without checking for uniqueness (i.e. summary, description)
	 * 
	 * @param calendarRequest
	 * @return an instance of CalendarResponse indication success, id, and messages as appropriate
	 */
	public CalendarResponse createCalendar(CalendarRequest calendarRequest) {
		CalendarResponse result = new CalendarResponse();
		try {			
			Calendar entry = new Calendar();
			if (StringUtils.isEmpty(calendarRequest.getSummary())) {
				entry.setSummary("New Calendar");
			} else {
				entry.setSummary(calendarRequest.getSummary());
			}			
			if (calendarRequest.getDescription() != null) {
				entry.setDescription(calendarRequest.getDescription());
			}
			entry = client.calendars().insert(entry).execute();			
			result.setCalendarId(entry.getId());
//			result.setSuccess(true);
			result.setSuccess(setCalendarAccess(entry.getId()).isSuccess());			
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	private CalendarResponse setCalendarAccess(String calendarId) {
		CalendarResponse result = new CalendarResponse();
		AclRule rule = new AclRule();
		Scope scope = new Scope();
		scope.setType("default");
		//scope.setValue("default");
		rule.setScope(scope);
		rule.setRole("reader");
		try {
			AclRule createdRule = client.acl().insert("primary", rule).execute();
			result.setSuccess(createdRule != null && !StringUtils.isEmpty(createdRule.getId()));
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	/**
	 * Updates an existing calendar optionally creating if not found
	 * 
	 * @param calendarRequest
	 * @param createIfNotFound
	 * @return an instance of CalendarResponse
	 */
	public CalendarResponse updateCalendar(CalendarRequest calendarRequest, boolean createIfNotFound) {
		CalendarResponse result = new CalendarResponse();
		CalendarRequest calendar = findCalendar(calendarRequest);
		if (calendar != null) {
			calendarRequest.setId(calendar.getId());
			result = _updateCalendar(calendarRequest);
		} else if (createIfNotFound) {
			result = createCalendar(calendarRequest);
		}
		return result;
	}
	
	/**
	 * 
	 * @param calendarRequest
	 * @return
	 */
	public CalendarResponse _updateCalendar(CalendarRequest calendarRequest) {
		
		CalendarResponse response = new CalendarResponse();
		response.setSuccess(false);
		Calendar calendar = null;
		
		if (!StringUtils.isEmpty(calendarRequest.getId())) {
			try {
			calendar = client.calendars().get(calendarRequest.getId()).execute();
			if (calendar == null) {
				response.setMessage("Could not find calendar with id: " + calendarRequest.getId());
			}
			} catch (IOException e) {
				response.setMessage(e.getMessage());
			}
		} else {
			response.setMessage("id is required to update calendar");
		}
		if (calendar != null) {
			try {			
				calendar.setSummary(calendarRequest.getSummary());
				calendar.setDescription(calendarRequest.getDescription());
				client.calendars().update(calendar.getId(), calendar).execute();
				response.setCalendarId(calendar.getId());
				response.setSuccess(true);
			} catch (IOException e) {
				LOGGER.error(e);
				response.setMessage(e.getMessage());
			}
		}
		return response;
	}

	/**
	 * Deletes existing calendar by id
	 * 
	 * @param calendarId
	 * @return an instance of Calendar response
	 */
	public CalendarResponse deleteCalendar(String calendarId) {
		CalendarResponse result = new CalendarResponse();
		try {
			client.calendars().delete(calendarId).execute();
			result.setSuccess(true);
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	public CalendarResponse deleteEvent(CalendarRequest calendarRequest) {
		CalendarResponse result = new CalendarResponse();
		List<CalendarRequest> calendarRequests = findCalendars(calendarRequest);
		for (CalendarRequest request : calendarRequests) {
			calendarRequest.setId(request.getId());
			List<Event> events = findEvents(calendarRequest.getCalendarEventRequest());
			for (Event event : events) {
				try {
					client.events()
							.delete(calendarRequest.getId(), event.getId())
							.execute();
					result.setSuccess(true);
				} catch (IOException e) {
					result.setSuccess(false);
					result.setMessage(e.getMessage());
					break;
				}
			}
		}
		return result;
	}

	public CalendarResponse clearCalendar(String calendarId) {
		CalendarResponse result = new CalendarResponse();
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
				break;
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

	public CalendarResponse calendarExists(String calendarId) {
		CalendarResponse result = new CalendarResponse();
		try {
			Calendar calendar = getCalendar(calendarId);
			result.setSuccess(calendar != null);
		} catch (IOException e) {
			LOGGER.error(e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	private static Calendar getCalendar(String calendarId) throws IOException {
		Calendar result = client.calendars().get(calendarId).execute();
		return result;
	}

	public com.github.mule.google.wrapper.CalendarRequest findCalendar(CalendarRequest calendarRequest) {
		List<com.github.mule.google.wrapper.CalendarRequest> list = _findCalendars(calendarRequest, true);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	public List<com.github.mule.google.wrapper.CalendarRequest> findCalendars(CalendarRequest calendarRequest) {
		return _findCalendars(calendarRequest, false);
	}

	public List<com.github.mule.google.wrapper.CalendarRequest> _findCalendars(CalendarRequest calendarRequest, boolean firstOnly) {
		List<com.github.mule.google.wrapper.CalendarRequest> list = new ArrayList<com.github.mule.google.wrapper.CalendarRequest>(0);
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
				if (!StringUtils.isEmpty(calendarRequest.getId())) {
					if (calendarListEntry.getId().equals(calendarRequest.getId())) {
						list.add(new CalendarRequest(
								calendarListEntry.getId(),
								calendarListEntry.getSummary(),
								calendarListEntry.getDescription()));
					}					
				} else if (Utility.notEmptyEqualStrings(calendarListEntry.getDescription(), calendarRequest.getDescription())) {
					list.add(new CalendarRequest(
							calendarListEntry.getId(),
							calendarListEntry.getSummary(),
							calendarListEntry.getDescription()));
				}
				if (firstOnly && list.size() > 0) {
					return list;
				}
			}
			pageToken = calendarList.getNextPageToken();
		} while (pageToken != null);
		return list;
	}

	public List<Event> findEvents(CalendarEventRequest calendarEvent) {
		return findEvents(calendarEvent, "yyyy-MM-dd HH:mm:ss");
	}

	public List<Event> findEvents(CalendarEventRequest calendarEvent,
			String dateFormat) {
		List<Event> result = new ArrayList<Event>(0);
		List<Event> events = getCalendarEvents(calendarEvent.getCalendarId());
		for (Event event : events) {
			boolean found = false;
			if (!StringUtils.isEmpty(calendarEvent.getEventId())) {
				found = event.getId().indexOf(calendarEvent.getEventId()) >= 0;
				if (!found)
					continue;
			}
			if (!StringUtils.isEmpty(event.getSummary())) {
				found = event.getSummary().indexOf(calendarEvent.getSummary()) >= 0;
				if (!found)
					continue;
			}
			if (!StringUtils.isEmpty(event.getDescription())) {
				found = event.getDescription().indexOf(calendarEvent.getDescription()) >= 0;
				if (!found)
					continue;
			}
			found = Utility.compareDates(new Date(event.getStart()
						.getDateTime().getValue()), calendarEvent.getStart()
						.getTime(), dateFormat);
			if (!found)
				continue;
			found = Utility.compareDates(new Date(event.getEnd()
					.getDateTime().getValue()), calendarEvent.getEnd()
					.getTime(), dateFormat);
			if (!found)
				continue;
			if (found) {
				result.add(event);
			}
		}
		return result;
	}

	private static Event addEvent(CalendarEventRequest calendarEvent)
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
