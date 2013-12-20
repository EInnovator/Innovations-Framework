/**
 * 
 */
package org.einnovator.convert;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.einnovator.convert.ConversionServiceImpl;
import org.einnovator.convert.DateFormat;
import org.einnovator.convert.DefaultConversionService;
import org.einnovator.convert.NumberFormat;
import org.einnovator.convert.NumberType;
import org.einnovator.format.datetime.DateTimeFormatter;
import org.einnovator.format.datetime.DateTimeFormatter2;
import org.einnovator.format.number.NumberFormatter2;
import org.einnovator.format.simple.ColorFormatter;
import org.junit.Before;
import org.junit.Test;


/**
 * A DefaultConversionServiceTests.
 *
 * @author Jorge Simão
 */
public class DefaultConversionServiceTests {

	enum Sex {
		MALE,
		FEMALE
	};
	
	ConversionServiceImpl conversionService;
	
	@Before
	public void init() {
		conversionService = new DefaultConversionService(true);
		//System.out.println("Converters:");
		//print(conversionService.getConverterMap());
		//System.out.println("Formatters:");
		//print(conversionService.getFormatterMap());
		//System.out.println("AnnotationFormatters:");
		//print(conversionService.getAnnotationFormatterMap());
	}
	
	void print(Map<?,?> map) {
		for (Map.Entry<?, ?> e: map.entrySet()) {
			System.out.println(e.getKey() + "=" + e.getValue());
		}
	}

	public void handleDate1(@DateFormat(pattern="yyyy-MM-dd") Date date) {}
	public void handleDate2(@DateFormat(name="DMY") Date date) {}
	public void handleDate3(@DateFormat(style="S") Date date) {}

	public void handleNumber1(@NumberFormat(type=NumberType.NUMBER) Number n) {}
	public void handleNumber2(@NumberFormat(type=NumberType.CURRENCY, currency="USD") Number n) {}
	public void handleNumber3(@NumberFormat(type=NumberType.PERCENTAGE, roundingMode=RoundingMode.CEILING) Number n) {}
	public void handleNumber4(@NumberFormat(integerOnly=true, roundingMode=RoundingMode.DOWN) Number n) {}

	@Test
	public void formatterTests() {
		assertEquals(DateTimeFormatter2.class, conversionService.getFormatter(Date.class).getClass());
		assertEquals(NumberFormatter2.class, conversionService.getFormatter(Long.class).getClass());
		assertEquals(NumberFormatter2.class, conversionService.getFormatter(BigDecimal.class).getClass());
		assertEquals(NumberFormatter2.class, conversionService.getFormatter(BigInteger.class).getClass());

		assertEquals(ColorFormatter.class, conversionService.getFormatter(Color.class).getClass());

	}

	@Test
	public void simpleParseTests() {
		Locale locale = Locale.getDefault();
		assertEquals("A", conversionService.parse("A", String.class, locale));
		assertEquals(new Long(1L), conversionService.parse("1", Long.class, locale));		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void dateTimeTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		Date date = new Date(2001-1900, 0, 1, 0, 0, 0);
		
		assertEquals("2001-01-01", conversionService.format(date, getDateAnnotation("handleDate1"), locale));
		assertEquals("01-01-2001", conversionService.format(date, getDateAnnotation("handleDate2"), locale));
		assertEquals("1/1/01", conversionService.format(date, getDateAnnotation("handleDate3"), locale));

		assertEquals(date, conversionService.parse("2001-01-01", Date.class, getDateAnnotation("handleDate1"), locale));
		assertEquals(date, conversionService.parse("01-01-2001", Date.class, getDateAnnotation("handleDate2"), locale));
		assertEquals(date, conversionService.parse("1/1/01", Date.class, getDateAnnotation("handleDate3"), locale));		

		((DateTimeFormatter)conversionService.getFormatter(Date.class)).setPattern("dd-MM-yy");
		assertEquals(date, conversionService.parse("01-01-2001", Date.class, locale));		
	
	}
	
	@Test
	public void numberTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals("123", conversionService.format(123L, locale));
		assertEquals("123.45", conversionService.format(123.45, locale));

		assertEquals(new Long(123), conversionService.parse("123", Long.class, locale));
		assertEquals(new Double(123.45), conversionService.parse("123.45", Double.class, locale));		
		assertEquals(new BigDecimal(123.45), conversionService.parse("123.45", BigDecimal.class, locale));		
		assertEquals(new BigInteger("123"), conversionService.parse("123.45", BigInteger.class, locale));		


		assertEquals("123.45", conversionService.format(123.45, getNumberAnnotation("handleNumber1"), locale));
		assertEquals("$123.45", conversionService.format(123.45, getNumberAnnotation("handleNumber2"), locale));
		assertEquals("99%", conversionService.format(0.987, getNumberAnnotation("handleNumber3"), locale));
		assertEquals("123", conversionService.format(123.45, getNumberAnnotation("handleNumber4"), locale));

		assertEquals(new Double(123.45), conversionService.parse("123.45", Double.class, getNumberAnnotation("handleNumber1"), locale));
		assertEquals(new Double(123.45), conversionService.parse("$123.45", Double.class, getNumberAnnotation("handleNumber2"), locale));
		assertEquals(new Double(0.987), conversionService.parse("98.7%", Double.class, getNumberAnnotation("handleNumber3"), locale));		
		assertEquals(new Double(123L), conversionService.parse("123.45", Double.class, getNumberAnnotation("handleNumber4"), locale));		

		assertEquals(new BigDecimal(123.45), conversionService.parse("123.45", BigDecimal.class, getNumberAnnotation("handleNumber1"), locale));
		assertEquals(new BigDecimal(123.45), conversionService.parse("$123.45", BigDecimal.class, getNumberAnnotation("handleNumber2"), locale));
		assertEquals(new BigDecimal(0.987), conversionService.parse("98.7%", BigDecimal.class, getNumberAnnotation("handleNumber3"), locale));		
		assertEquals(new BigDecimal(123L), conversionService.parse("123.45", BigDecimal.class, getNumberAnnotation("handleNumber4"), locale));		

		assertEquals(new BigInteger("123"), conversionService.parse("123.45", BigInteger.class, getNumberAnnotation("handleNumber1"), locale));
		assertEquals(new BigInteger("123"), conversionService.parse("$123.45", BigInteger.class, getNumberAnnotation("handleNumber2"), locale));
		assertEquals(new BigInteger("0"), conversionService.parse("98.7%", BigInteger.class, getNumberAnnotation("handleNumber3"), locale));		
		assertEquals(new BigInteger("123"), conversionService.parse("123.45", BigInteger.class, getNumberAnnotation("handleNumber4"), locale));		

	}

	@Test
	public void primitiveNumberTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals(new Long(123), conversionService.parse("123", Long.TYPE, locale));
		assertEquals(new Integer(123), conversionService.parse("123", Integer.TYPE, locale));		
		assertEquals(new Double(123.45), conversionService.parse("123.45", Double.TYPE, locale));		
	}

	@Test
	public void stringTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertTrue(conversionService.supportsFormat(String.class));
		assertEquals("abc", conversionService.format("abc", locale));
		assertEquals("abc", conversionService.parse("abc", String.class, locale));		
	}

	@Test
	public void arrayTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals("1,2,3", conversionService.format(new Object[]{1,2,3}, locale));
		assertArrayEquals(new Integer[]{1,2,3}, conversionService.parse("1,2,3", Integer[].class, locale));
		assertArrayEquals(new String[]{"a","b","c"}, conversionService.parse("a,b,c", String[].class, locale));
	}

	@Test
	public void enumTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals("MALE", conversionService.format(Sex.MALE, locale));
		assertEquals(Sex.MALE, conversionService.parse("MALE", Sex.class, locale));
	}

	@Test
	public void colorTests() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		Color c = new Color(255, 0, 0);
		assertEquals("#ff0000", conversionService.format(c, locale));
		assertEquals(c, conversionService.parse("#ff0000", Color.class, locale));
	}

	@Test
	public void booleanTest() throws SecurityException, NoSuchMethodException {
		Locale locale = Locale.getDefault();
		assertEquals("true", conversionService.format(true, locale));
		assertEquals("false", conversionService.format(false, locale));
		assertNotNull(conversionService.getFormatter(Boolean.class));
		assertNotNull(conversionService.getFormatter(Boolean.TYPE));
		assertEquals(Boolean.TRUE, conversionService.parse("true", Boolean.class, locale));
		assertEquals(Boolean.FALSE, conversionService.parse("false", Boolean.class, locale));		
		assertEquals(Boolean.TRUE, conversionService.parse("TRUE", Boolean.class, locale));
		assertEquals(Boolean.FALSE, conversionService.parse("FALSE", Boolean.class, locale));		
		assertEquals(Boolean.TRUE, conversionService.parse("true", Boolean.TYPE, locale));
		assertEquals(Boolean.FALSE, conversionService.parse("false", Boolean.TYPE, locale));		
		assertEquals(Boolean.TRUE, conversionService.parse("TRUE", Boolean.TYPE, locale));
		assertEquals(Boolean.FALSE, conversionService.parse("FALSE", Boolean.TYPE, locale));		

	}


	Annotation getDateAnnotation(String methodName) throws SecurityException, NoSuchMethodException {
		return getClass().getDeclaredMethod(methodName, Date.class).getParameterAnnotations()[0][0];
	}

	Annotation getNumberAnnotation(String methodName) throws SecurityException, NoSuchMethodException {
		return getClass().getDeclaredMethod(methodName, Number.class).getParameterAnnotations()[0][0];
	}

}
