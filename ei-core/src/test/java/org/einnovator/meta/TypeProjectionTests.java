package org.einnovator.meta;

import static org.junit.Assert.*;

import org.einnovator.format.ObjectSupport;
import org.junit.Test;

public class TypeProjectionTests {

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

	@Test
	public void includeOnlyTest() {
		Path path;
		
		ProjectionOptions projection0 = ProjectionOptions.newInstance().includeOnly("id","b.idx").required(true);
		
		TypeProjection projection = new TypeProjection(A.class, projection0, PathParser.getInstance());
		System.out.println(projection.getPathMap());
		
		path = Path.newInstance("id", A.class);
		Path path0 = projection.getPathMap().keySet().iterator().next();
		assertEquals(path, path0);
		assertEquals(path0.hashCode(),path.hashCode());
		assertTrue(projection.getPathMap().containsKey(path));
		assertTrue(projection.checkInclude(path, false, true));

		path = Path.newInstance("username", A.class);
		assertFalse(projection.checkInclude(path, false, true));

		path = Path.newInstance("b", A.class);
		assertTrue(projection.checkInclude(path, false, true));
		
		path = Path.newInstance("b.idx", A.class);
		assertTrue(projection.checkInclude(path, false, true));

		path = Path.newInstance("b.code", A.class);
		assertFalse(projection.checkInclude(path, false, true));

	}
	
	@Test
	public void excludeTest() {
		Path path;
		
		ProjectionOptions projection0 = ProjectionOptions.newInstance().exclude("username","b.code").required(true);
		
		TypeProjection projection = new TypeProjection(A.class, projection0, PathParser.getInstance());
		System.out.println(projection.getPathMap());
		
		path = Path.newInstance("id", A.class);
		assertTrue(projection.checkInclude(path, false, true));
		
		path = Path.newInstance("username", A.class);
		assertFalse(projection.checkInclude(path, false, true));

		path = Path.newInstance("b.idx", A.class);
		assertTrue(projection.checkInclude(path, false, true));
		
		path = Path.newInstance("b.code", A.class);
		assertFalse(projection.checkInclude(path, false, true));

	}
}
