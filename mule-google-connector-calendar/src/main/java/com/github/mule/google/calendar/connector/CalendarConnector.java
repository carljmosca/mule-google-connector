package com.github.mule.google.calendar.connector;

import java.util.List;

import org.apache.log4j.Logger;
import org.mule.api.ConnectionException;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;

import com.github.mule.google.wrapper.CalendarEventRequest;
import com.github.mule.google.wrapper.CalendarRequest;
import com.github.mule.google.wrapper.CalendarResponse;
import com.google.api.services.calendar.model.Event;

/**
 * Google Calendar Connector
 * 
 * @author moscac
 */
@Connector(name = "google-calendar", schemaVersion = "1.0-SNAPSHOT")
public class CalendarConnector {

	private static Logger LOGGER = Logger.getLogger(CalendarConnector.class);

	/**
	 * Connect
	 * 
	 * @param username
	 *            A username
	 * @param password
	 *            A password
	 * @throws ConnectionException
	 */
	@Connect
	public void connect(@ConnectionKey String username, String password)
			throws ConnectionException {
		/*
		 * CODE FOR ESTABLISHING A CONNECTION GOES IN HERE
		 */
	}

	/**
	 * Disconnect
	 */
	@Disconnect
	public void disconnect() {
		/*
		 * CODE FOR CLOSING A CONNECTION GOES IN HERE
		 */
	}

	/**
	 * Are we connected
	 */
	@ValidateConnection
	public boolean isConnected() {
		return true;
	}

	/**
	 * Are we connected
	 */
	@ConnectionIdentifier
	public String connectionId() {
		return "001";
	}

	/**
	 * createEvent processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:create-event}
	 * 
	 * @param calendarId
	 * 			  id of calendar
	 * @param calendarEvent
	 *            calendarEvent to create
	 * @return operationResult
	 */
	@Processor
	public CalendarResponse createEvent(@Optional @Default("#[payload]")CalendarEventRequest calendarEvent) {
		CalendarManager manager = new CalendarManager();
		return manager.createEvent(calendarEvent);
	}

	/**
	 * updateEvent processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:update-event}
	 * 
	 * @param calendarRequest
	 *            calendarRequest to be processed
	 * @param createIfNotFound
	 * 			  createIfNotFound calendar if not found           
	 * @return string
	 * */
	@Processor
	public CalendarResponse updateCalendar(@Optional @Default("#[payload]") CalendarRequest calendarRequest, boolean createIfNotFound) {
		CalendarManager manager = new CalendarManager();
		CalendarResponse result = manager.updateCalendar(calendarRequest, createIfNotFound);
		LOGGER.debug("updateCalendar: " + calendarRequest.getId() + " " + calendarRequest.getSummary() + " " + calendarRequest.getDescription());
		return result;
	}
	
	/**
	 * updateEvent processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:update-event}
	 * 
	 * @param calendarEventRequest
	 *            calendarEvent to be processed
	 * @param createIfNotFound
	 * 			  createIfNotFound calendar if not found           
	 * @return string
	 * */
	@Processor
	public CalendarResponse updateEvent(@Optional @Default("#[payload]") CalendarEventRequest calendarEventRequest, boolean createIfNotFound) {
		CalendarManager manager = new CalendarManager();
		CalendarResponse result = manager.updateEvent(calendarEventRequest, createIfNotFound);
		LOGGER.debug("updateEvent: " + calendarEventRequest.getCalendarId() + " " + calendarEventRequest.getSummary() + " " + calendarEventRequest.getDescription());
		return result;
	}

	/**
	 * createCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:create-calendar}
	 * 
	 * @param calendarRequest
	 *            calendarRequest object
	 * @return string
	 * */
	@Processor
	public CalendarResponse createCalendar(@Optional @Default("#[payload]") CalendarRequest calendarRequest) {
		CalendarManager manager = new CalendarManager();
		return manager.createCalendar(calendarRequest);
	}

	/**
	 * findCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:find-calendar}
	 * 
	 * @param calendarRequest
	 *            calendar to find
	 * @return calendarRequest
	 * */
	@Processor
	public CalendarRequest findCalendar(@Optional @Default("#[payload]") CalendarRequest calendarRequest) {
		CalendarManager manager = new CalendarManager();
		return manager.findCalendar(calendarRequest);
	}
	
	/**
	 * findCalendars processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:find-calendar}
	 * 
	 * @param calendarRequest
	 *            calendar to find
	 * @return calendarRequest
	 * */
	@Processor
	public List<CalendarRequest> findCalendars(CalendarRequest calendarRequest) {
		CalendarManager manager = new CalendarManager();
		return manager.findCalendars(calendarRequest);
	}
	
	/**
	 * findEvents processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:find-events}
	 * 
	 * @param calendarEvent
	 * 			calendarEvent search values 
	 * @return list of events
	 */
	@Processor
	public List<Event> findEvents(CalendarEventRequest calendarEvent) {
		CalendarManager manager = new CalendarManager();
		return manager.findEvents(calendarEvent);
	}

	/**
	 * clearCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:clear-calendar}
	 * 
	 * @param calendarId
	 *            calendarId of calendar
	 * @return boolean
	 * */
	@Processor
	public CalendarResponse clearCalendar(String calendarId) {
		CalendarManager manager = new CalendarManager();
		return manager.clearCalendar(calendarId);
	}

	/**
	 * deleteCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:delete-calendar}
	 * 
	 * @param calendarId
	 *            calendarId of calendar
	 * @return boolean
	 * */
	@Processor
	public CalendarResponse deleteCalendar(String calendarId) {
		CalendarManager manager = new CalendarManager();
		return manager.deleteCalendar(calendarId);
	}
	
	/**
	 * deleteEvent processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:delete-event}
	 * 
	 * @param calendarEvent
	 *            calendarEvent of calendar        
	 * @return boolean
	 * */
	@Processor
	public CalendarResponse deleteEvent(CalendarEventRequest calendarEvent) {
		CalendarManager manager = new CalendarManager();
		return manager.deleteEvent(calendarEvent);
	}
}
