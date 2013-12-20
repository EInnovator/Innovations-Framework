/**
 * 
 */
package org.einnovator.meta;

import static org.junit.Assert.*;

import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.Path;
import org.junit.Before;
import org.junit.Test;

/**
 * A {@code PropertyPathTests}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class PathTests {

	public static class A extends ObjectSupport {
		Long id;
		String username;
		B b;

		public A(Long id, String username, B b) {
			this.id = id;
			this.username = username;
			this.b = b;
		}
		
	}

	public static class B extends ObjectSupport {
		Long idx;
		String code;

		public B(Long idx, String code) {
			this.idx = idx;
			this.code = code;
		}	
	}
	
	@Before
	public void setup() {
	}

	@Test
	public void test() {
		Path path;
		A a = new A(1L, "aa", new B(2L, "X123"));

		path = Path.newInstance("id", A.class);
		assertEquals(1, path.length());
		assertEquals(1L, path.getValue(a));
		assertEquals(Long.class, path.getType());
		assertEquals("id", path.toString());
		
		path = Path.newInstance("username", A.class);
		assertEquals(1, path.length());
		assertEquals(String.class, path.getType());
		assertEquals("username", path.toString());
		assertEquals("aa", path.getValue(a));
		path.setValue(a, "bb");
		assertEquals("bb", path.getValue(a));
		
		path = Path.newInstance("b.idx", A.class);
		System.out.println(path.getProperties());
		assertEquals(2, path.length());
		assertEquals(Long.class, path.getType());
		assertEquals(B.class, path.getHead().getType());
		assertEquals("b.idx", path.toString());
		assertEquals(2L, path.getValue(a));
		path.setValue(a, 3L);
		assertEquals(3L, path.getValue(a));

		assertEquals(Path.newInstance("id", A.class), Path.newInstance("id", A.class));
		assertEquals(Path.newInstance("username", A.class), Path.newInstance("username", A.class));
		assertEquals(Path.newInstance("b.idx", A.class), Path.newInstance("b.idx", A.class));
		assertEquals(Path.newInstance("b.code", A.class), Path.newInstance("b.code", A.class));

		assertEquals(Path.newInstance("id", A.class).hashCode(), Path.newInstance("id", A.class).hashCode());
		assertEquals(Path.newInstance("username", A.class).hashCode(), Path.newInstance("username", A.class).hashCode());
		assertEquals(Path.newInstance("b.idx", A.class).hashCode(), Path.newInstance("b.idx", A.class).hashCode());
		assertEquals(Path.newInstance("b.code", A.class).hashCode(), Path.newInstance("b.code", A.class).hashCode());

	}
}
