package org.einnovator.meta;

import static org.junit.Assert.*;

import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.property.PropertyUtil;
import org.junit.Test;

public class ProjectionOptionsTests {

	interface Highlighted {
		void id();
	}
	
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

		ProjectionOptions projection = ProjectionOptions.newInstance().includeOnly("id", "b.idx", "idx");
		System.out.println(projection);
		path = Path.newInstance("id", A.class);
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

		ProjectionOptions projection = ProjectionOptions.newInstance().exclude("username", "b.code");
		
		path = Path.newInstance("id", A.class);
		assertTrue(projection.checkInclude(path, false, true));

		path = Path.newInstance("username", A.class);
		assertFalse(projection.checkInclude(path, false, true));

		path = Path.newInstance("b.idx", A.class);
		assertTrue(projection.checkInclude(path, false, true));

		path = Path.newInstance("b.code", A.class);
		assertFalse(projection.checkInclude(path, false, true));
	}

	@Test
	public void includeOnlyMatchingTest() {
		Path path;

		ProjectionOptions projection = ProjectionOptions.newInstance().includeOnlyMatching(Highlighted.class);
		System.out.println(projection);
		path = Path.newInstance("id", A.class);
		assertTrue(PropertyUtil.matchedBy(path.getProperty(0), Highlighted.class));
		assertTrue(projection.checkInclude(path, false, true));

		path = Path.newInstance("username", A.class);
		assertFalse(projection.checkInclude(path, false, true));

		path = Path.newInstance("b", A.class);
		assertFalse(projection.checkInclude(path, false, true));
		
		path = Path.newInstance("b.idx", A.class);
		assertFalse(projection.checkInclude(path, false, true));

		path = Path.newInstance("b.code", A.class);
		assertFalse(projection.checkInclude(path, false, true));
	}
	

}
