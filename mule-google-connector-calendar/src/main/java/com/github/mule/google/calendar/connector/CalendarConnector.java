package com.github.mule.google.calendar.connector;

import java.util.ArrayList;
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
import org.mule.api.annotations.param.Optional;

import com.github.mule.google.wrapper.Calendar;
import com.github.mule.google.wrapper.CalendarEvent;
import com.github.mule.google.wrapper.OperationResult;
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
	 *            name of calendar to be processed
	 * @param startDate
	 *            start date/time of event
	 * @param endDate
	 *            end date/time of event
	 * @param timeZone
	 *            timeZone of event
	 * @param summary
	 *            summary of event
	 * @param description
	 *            description of event
	 * @param location
	 *            location of event
	 * @param guestList
	 *            list of guests to invite
	 * @return string
	 * */
	@Processor
	public OperationResult createEvent(CalendarEvent calendarEvent) {
		CalendarManager manager = new CalendarManager();
		return manager.createEvent(calendarEvent);
	}

	/**
	 * updateEvent processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:update-event}
	 * 
	 * @param calendarEvent
	 *            calendarEvent to be processed
	 * @return string
	 * */
	@Processor
	public OperationResult updateEvent(CalendarEvent calendarEvent) {
		CalendarManager manager = new CalendarManager();
		OperationResult result = manager.updateEvent(calendarEvent);
		LOGGER.debug("updateEvent: " + calendarEvent.getCalendarId());
		return result;
	}

	/**
	 * createCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:create-calendar}
	 * 
	 * @param summary
	 *            summary (name) of calendar
	 * @return string
	 * */
	@Processor
	public OperationResult createCalendar(String summary) {
		CalendarManager manager = new CalendarManager();
		return manager.createCalendar(summary);
	}

	/**
	 * findCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:find-calendar}
	 * 
	 * @param summary
	 *            summary (name) of calendar
	 * @return calendar
	 * */
	@Processor
	public Calendar findCalendar(String summary) {
		CalendarManager manager = new CalendarManager();
		return manager.findCalendar(summary);
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
	public List<Event> findEvents(CalendarEvent calendarEvent) {
		List<Event> result = new ArrayList<Event>(0);
		CalendarManager manager = new CalendarManager();
		return manager.searchEvent(calendarEvent);
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
	public OperationResult clearCalendar(String calendarId) {
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
	public OperationResult deleteCalendar(String calendarId) {
		CalendarManager manager = new CalendarManager();
		return manager.deleteCalendar(calendarId);
	}
	
	/**
	 * deleteEvent processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:delete-event}
	 * 
	 * @param calendarId
	 *            calendarId of calendar
	 * @param eventId
	 * 			  eventId to delete           
	 * @return boolean
	 * */
	@Processor
	public OperationResult deleteEvent(CalendarEvent calendarEvent) {
		CalendarManager manager = new CalendarManager();
		return manager.deleteEvent(calendarEvent);
	}
}
