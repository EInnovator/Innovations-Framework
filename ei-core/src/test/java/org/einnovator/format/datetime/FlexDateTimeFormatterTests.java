package org.einnovator.format.datetime;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class FlexDateTimeFormatterTests {

	FlexDateTimeFormatter formatter;
	
	@Before
	public void setup() {
		formatter = new FlexDateTimeFormatter();
	}
	
	@Test
	public void parseTest() {
		Locale locale = Locale.getDefault();
		@SuppressWarnings("deprecation")
		Date date = new Date(2001-1900, 0, 1, 0, 0, 0);
		formatter.setPattern("dd-MM-yyyy");
		assertEquals(date, formatter.parse("01-01-2001", locale));
		assertEquals(date, formatter.parse("1/1/2001", locale));
		assertEquals(date, formatter.parse("1/2001", locale));
		assertEquals(date, formatter.parse("1/Jan/2001", locale));
		assertEquals(date, formatter.parse("1/January/2001", locale));
	}
}
