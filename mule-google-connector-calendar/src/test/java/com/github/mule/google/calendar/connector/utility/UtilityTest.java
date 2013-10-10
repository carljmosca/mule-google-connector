package com.github.mule.google.calendar.connector.utility;

import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class UtilityTest {


	@Test
	public void testStringToDate() {
		Date value = null;
		value = Utility.stringToDate("2013-9-13 11:00:00 AM");
		assertTrue(value != null);
		value = Utility.stringToDate("2013-9-25 09:30AM");
		assertTrue(value != null);
	}

}
