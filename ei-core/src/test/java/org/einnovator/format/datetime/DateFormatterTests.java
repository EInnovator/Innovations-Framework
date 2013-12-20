/**
 * 
 */
package org.einnovator.format.datetime;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Locale;

import org.einnovator.convert.DateFormat;
import org.einnovator.format.datetime.DateFormatterUtil;
import org.einnovator.format.datetime.DateTimeFormatter2;
import org.junit.Before;
import org.junit.Test;


/**
 * A DateFormatterTests.
 *
 * @author Jorge Simão
 */
public class DateFormatterTests {

	DateTimeFormatter2 formatter;
	
	@Before
	public void init() {
		formatter = new DateTimeFormatter2();
		DateFormatterUtil.addCommonFormats(formatter);
	}

	public void handleDate1(@DateFormat(pattern="yyyy-MM-dd") Date date) {}
	public void handleDate2(@DateFormat(name="DMY") Date date) {}
	public void handleDate3(@DateFormat(style="S") Date date) {}

	@Test
	public void formatTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		@SuppressWarnings("deprecation")
		Date date = new Date(2001-1900, 0, 1, 0, 0, 0);		
		formatter.setPattern("dd-MM-yyyy");
		formatter.clearLocaleCache();
		assertEquals(date, formatter.parse("01-01-2001", locale));		
	}

	@Test
	public void annotationTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		@SuppressWarnings("deprecation")
		Date date = new Date(2001-1900, 0, 1, 0, 0, 0);
		
		assertEquals("2001-01-01", formatter.format(date, getAnnotation("handleDate1"), locale));
		assertEquals("01-01-2001", formatter.format(date, getAnnotation("handleDate2"), locale));
		assertEquals("1/1/01", formatter.format(date, getAnnotation("handleDate3"), locale));

		assertEquals(date, formatter.parse("2001-01-01", getAnnotation("handleDate1"), locale));
		assertEquals(date, formatter.parse("01-01-2001", getAnnotation("handleDate2"), locale));
		assertEquals(date, formatter.parse("1/1/01", getAnnotation("handleDate3"), locale));		

	}
	
	DateFormat getAnnotation(String methodName) throws SecurityException, NoSuchMethodException {
		return (DateFormat)getClass().getDeclaredMethod(methodName, Date.class).getParameterAnnotations()[0][0];
	}
	
}
