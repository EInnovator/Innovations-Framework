/**
 * 
 */
package org.einnovator.format.simple;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.Date;
import java.util.Locale;

import org.einnovator.convert.DateFormat;
import org.einnovator.format.simple.ColorFormatter;
import org.junit.Before;
import org.junit.Test;


/**
 * AA DateFormatterTests.
 *
 * @author Jorge Simão
 */
public class ColorFormatterTests {

	ColorFormatter  formatter;
	
	@Before
	public void init() {
		formatter = new ColorFormatter();
	}

	public void handleDate1(@DateFormat(pattern="yyyy-MM-dd") Date date) {}
	public void handleDate2(@DateFormat(name="DMY") Date date) {}
	public void handleDate3(@DateFormat(style="S") Date date) {}

	@Test
	public void formatTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		Color c = new Color(255, 0, 0);
		assertEquals("#ff0000", formatter.print(c, locale));
		assertEquals(c, formatter.parse("#ff0000", locale));
		c = new Color(0, 255, 0);
		assertEquals("#00ff00", formatter.print(c, locale));
		assertEquals(c, formatter.parse("#00ff00", locale));
		c = new Color(0, 0, 255);
		assertEquals("#0000ff", formatter.print(c, locale));
		assertEquals(c, formatter.parse("#0000ff", locale));

	}

	/*
	@Test
	public void annotationTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		
		assertEquals("2001-01-01", formatter.format(date, getAnnotation("handleDate1"), locale));
		assertEquals(date, formatter.parse("2001-01-01", getAnnotation("handleDate1"), locale));
	}
	
	DateFormat getAnnotation(String methodName) throws SecurityException, NoSuchMethodException {
		return (DateFormat)getClass().getDeclaredMethod(methodName, Date.class).getParameterAnnotations()[0][0];
	}*/
	
}
