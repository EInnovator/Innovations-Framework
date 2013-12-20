/**
 * 
 */
package org.einnovator.util.properties;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.einnovator.util.OrderedProperties;
import org.einnovator.util.Properties;
import org.junit.Test;


/**
 * A OrderedPropertiesTests.
 *
 * @author Jorge Simão
 */
public class OrderedPropertiesTests {

	@Test
	public void test() throws IOException {
		Properties properties = new OrderedProperties();
		int n = 3;
		for (int i=0; i<n; i++) {
			properties.setProperty("key" + i, "value" + i);
		}
		for (int i=0; i<n; i++) {
			assertEquals("value" + i, properties.getProperty("key" + i));
		}
		properties.list();

		System.out.println("-------");
		
		n = 4;
		String s = 
			"#comment\n" +
			"key0=value0\n" +
			"key1:value1\n" +
			"key2  =  value2\n" +
			"key3  =  value3\n"	
			;
		properties = new OrderedProperties();
		properties.load(new StringReader(s));
		
		assertEquals(n, properties.size());
		properties.list();
		for (int i=0; i<n; i++) {
			assertEquals("value" + i, properties.getProperty("key" + i));
		}
		
		System.out.println("-------");
		
		StringWriter out = new StringWriter();
		properties.store(out, "#comment");
		
		System.out.println(out.getBuffer());
	}
}
