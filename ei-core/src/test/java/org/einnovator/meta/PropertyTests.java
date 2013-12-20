package org.einnovator.meta;

import static org.junit.Assert.*;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.Property;
import org.junit.Test;


public class PropertyTests {
	
	@Test
	public void propertySetTest()  {
		MetaClass<A> mcA = MetaOperations.getMetaClass(A.class);
		Property<String> property = mcA.getProperty("name", String.class);
		A a = mcA.newInstance();
		property.setValue(a, "ABZ");
		assertEquals("ABZ", property.getValue(a));
	}
	
}
