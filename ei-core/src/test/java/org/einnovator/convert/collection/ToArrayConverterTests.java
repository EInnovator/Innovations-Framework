/**
 * 
 */
package org.einnovator.convert.collection;

import static org.junit.Assert.*;

import org.einnovator.convert.ConversionService;
import org.einnovator.convert.DefaultConversionService;
import org.einnovator.convert.collection.ToArrayConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * A {@code ToArrayConverterTests}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class ToArrayConverterTests {

	 
	ConversionService conversionService;
	
	ToArrayConverter converter;
	
	@Before
	public void setup() {
		conversionService = new DefaultConversionService();
		converter = new ToArrayConverter(conversionService);
	}
	@Test
	public void test() {
		assertArrayEquals(new String[]{"1"}, converter.convert(1L, String[].class));
		assertArrayEquals(new String[]{"1","2"}, converter.convert(new Integer[]{1,2}, String[].class));
		assertArrayEquals(new String[]{"1"}, conversionService.convert(1L, String[].class));
		assertArrayEquals(new String[]{"1","2"}, conversionService.convert(new Integer[]{1,2}, String[].class));

		assertArrayEquals(new Object[]{1L}, conversionService.convert(1L, Object[].class));
		assertArrayEquals(new Object[]{1,2}, conversionService.convert(new Integer[]{1,2}, Object[].class));
		
	}

}
