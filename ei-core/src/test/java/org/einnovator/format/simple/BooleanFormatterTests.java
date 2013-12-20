/**
 * 
 */
package org.einnovator.format.simple;

import static org.junit.Assert.*;

import java.util.Locale;

import org.einnovator.format.simple.BooleanFormatter;
import org.junit.Before;
import org.junit.Test;


/**
 * AA BooleanFormatterTests.
 *
 * @author Jorge Simão
 */
public class BooleanFormatterTests {

	BooleanFormatter  formatter;
	
	@Before
	public void init() {
		formatter = new BooleanFormatter();
	}

	@Test
	public void formatTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals("true", formatter.print(true, locale));
		assertEquals("false", formatter.print(false, locale));
		assertEquals(true, formatter.parse("true", locale));
		assertEquals(false, formatter.parse("false", locale));		
		assertEquals(true, formatter.parse("TRUE", locale));
		assertEquals(false, formatter.parse("FALSE", locale));		
	}
	
}
