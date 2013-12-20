/**
 * 
 */
package org.einnovator.format.simple;

import static org.junit.Assert.*;

import java.util.Locale;

import org.einnovator.format.simple.EnumFormatter;
import org.junit.Before;
import org.junit.Test;


/**
 * A EnumFormatterTests.
 *
 * @author Jorge Simão
 */
public class EnumFormatterTests {

	enum Sex {
		MALE,
		FEMALE,
		SOME_OTHER
	};

	EnumFormatter formatter;
	
	@Before
	public void init() {
		formatter = new EnumFormatter();
	}

	@Test
	public void formatTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals("MALE", formatter.format(Sex.MALE, locale));
		assertEquals(Sex.MALE, formatter.parse("MALE", Sex.class, locale));
		assertEquals(Sex.MALE, formatter.parse("male", Sex.class, locale));
		assertEquals(Sex.SOME_OTHER, formatter.parse("SOME_OTHER", Sex.class, locale));
		assertEquals(Sex.SOME_OTHER, formatter.parse("someOther", Sex.class, locale));
	}
	
}
