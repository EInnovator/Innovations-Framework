/**
 * 
 */
package org.einnovator.meta;

import static org.junit.Assert.*;

import org.einnovator.meta.PropertyPath;
import org.junit.Test;

/**
 * A {@code PropertyPathTests}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class PropertyPathTests {

	public static class A {
		Long id;
		String username;
		B b;

		public A(Long id, String username, B b) {
			this.id = id;
			this.username = username;
			this.b = b;
		}
		
	}

	public static class B {
		Long id;
		String code;

		public B(Long id, String code) {
			this.id = id;
			this.code = code;
		}
		
		
	}
	

	@Test
	public void test() {
		A a = new A(1L, "aa", new B(2L, "X123"));
		assertEquals(1L, new PropertyPath("id").getValue(a));

		PropertyPath path = new PropertyPath("username");
		assertEquals(1, path.length());
		assertEquals("aa", path.getValue(a));
		path.setValue(a, "bb");
		assertEquals("bb", path.getValue(a));
		
		path = new PropertyPath("b.id");
		assertEquals(2, path.length());
		assertEquals(2L, path.getValue(a));
		path.setValue(a, 3L);
		assertEquals(3L, path.getValue(a));

		assertEquals("X123", new PropertyPath("b.code").getValue(a));

	}
}
