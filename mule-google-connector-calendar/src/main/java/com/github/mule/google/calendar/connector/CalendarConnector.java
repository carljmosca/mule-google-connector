package com.github.mule.google.calendar.connector;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;

/**
 * Google Calendar Connector
 *
 * @author moscac
 */
@Connector(name = "google-calendar", schemaVersion = "1.0-SNAPSHOT")
public class CalendarConnector {

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
	 * 			  timeZone of event
	 * @param summary
	 * 			  summary of event
	 * @param description
	 *            description of event
	 * @param location
	 * 			  location of event
	 * @param guestList
	 * 			  list of guests to invite           
	 * @return string
	 * */
	@Processor
	public String createEvent(String calendarId, Date startDate,
			Date endDate, String timeZone, String summary, String description,
			String location, List<String> guestList) {
		CalendarManager manager = new CalendarManager();
		TimeZone tz = TimeZone.getTimeZone(timeZone);
		return manager.createEvent(calendarId, startDate, endDate, tz,
				summary, description, location, guestList);
	}
	
	/**
	 * createCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:create-calendar}
	 * 
	 * @param summary
	 * 				summary (name) of calendar
	 * @return string
	 * */
	@Processor
	public String createCalendar(String summary) {
		CalendarManager manager = new CalendarManager();
		return manager.createCalendar(summary);
	}
	
	/**
	 * findCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:find-calendar}
	 * 
	 * @param summary
	 * 				summary (name) of calendar
	 * @return string
	 * */
	@Processor
	public String findCalendar(String summary) {
		CalendarManager manager = new CalendarManager();
		return manager.findCalendar(summary);
	}
	
	/**
	 * deleteCalendar processor
	 * 
	 * {@sample.xml ../../../doc/Calendar-connector.xml.sample google-calendar:delete-calendar}
	 * 
	 * @param calendarId
	 * 				calendarId of calendar
	 * @return boolean
	 * */
	@Processor
	public Boolean deleteCalendar(String calendarId) {
		CalendarManager manager = new CalendarManager();
		return manager.deleteCalendar(calendarId);
	}
}
