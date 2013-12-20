package org.einnovator.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class MetaOperationsUtil {

	private static class A {
		String name;
		
		int n;
	}
	
	@Test
	public void assignTest() {
		A a = new A();
		a.name = "A";
		a.n = 1;
		A b = new A();
		MetaOperations.assign(b, a);
		assertEquals(a.name, b.name);
		assertEquals(a.n, b.n);
		b = new A();
		MetaOperations.assignFrom(b, a);
		assertEquals(a.name, b.name);
		assertEquals(a.n, b.n);

	}
}
