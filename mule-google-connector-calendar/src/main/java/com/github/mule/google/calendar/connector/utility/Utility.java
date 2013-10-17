package com.github.mule.google.calendar.connector.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class Utility {
	
	private static Logger LOGGER = Logger.getLogger(Utility.class);
	
	public static Date stringToDate(String value) {
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
	
	public static boolean compareDates(Date date1, Date date2, String dateFormat) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			result = sdf.format(date1).equals(sdf.format(date2)); 
		} catch (Exception e) {
			
		}
		return result;
	}
	
	public static boolean notEmptyEqualStrings(String value1, String value2) {
		return StringUtils.isNotEmpty(value1) && StringUtils.isNotEmpty(value2) && value1.equals(value2);
	}

}
