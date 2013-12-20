/**
 * 
 */
package org.einnovator.format.number;

import static org.junit.Assert.*;

import java.math.RoundingMode;
import java.util.Locale;

import org.einnovator.convert.NumberFormat;
import org.einnovator.convert.NumberType;
import org.einnovator.format.number.NumberFormatter2;
import org.junit.Before;
import org.junit.Test;


/**
 * A NumberFormatterTests.
 *
 * @author Jorge Simão
 */
public class NumberFormatterTests {

	NumberFormatter2 formatter;
	
	@Before
	public void init() {
		formatter = new NumberFormatter2();
	}

	public void handleNumber1(@NumberFormat(type=NumberType.NUMBER) Number n) {}
	public void handleNumber2(@NumberFormat(type=NumberType.CURRENCY, currency="USD") Number n) {}
	public void handleNumber3(@NumberFormat(type=NumberType.PERCENTAGE, roundingMode=RoundingMode.CEILING) Number n) {}
	public void handleNumber4(@NumberFormat(integerOnly=true, roundingMode=RoundingMode.DOWN) Number n) {}

	@Test
	public void formatTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals("123", formatter.print(123L, locale));
		assertEquals(123L, formatter.parse("123", locale));		

		assertEquals("123.45", formatter.print(123.45, locale));
		assertEquals(123.45, formatter.parse("123.45", locale));		

	}

	@Test
	public void annotationTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		
		assertEquals("123.45", formatter.format(123.45, getAnnotation("handleNumber1"), locale));
		assertEquals("$123.45", formatter.format(123.45, getAnnotation("handleNumber2"), locale));
		assertEquals("99%", formatter.format(0.987, getAnnotation("handleNumber3"), locale));
		assertEquals("123", formatter.format(123.45, getAnnotation("handleNumber4"), locale));

		assertEquals(123.45, formatter.parse("123.45", getAnnotation("handleNumber1"), locale));
		assertEquals(123.45, formatter.parse("$123.45", getAnnotation("handleNumber2"), locale));
		assertEquals(0.987, formatter.parse("98.7%", getAnnotation("handleNumber3"), locale));		
		assertEquals(123L, formatter.parse("123.45", getAnnotation("handleNumber4"), locale));		

	}
	
	NumberFormat getAnnotation(String methodName) throws SecurityException, NoSuchMethodException {
		return (NumberFormat)getClass().getDeclaredMethod(methodName, Number.class).getParameterAnnotations()[0][0];
	}
	
}
