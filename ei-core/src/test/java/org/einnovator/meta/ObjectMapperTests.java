/**
 * 
 */
package org.einnovator.meta;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.einnovator.format.FormatUtil;
import org.junit.Before;
import org.junit.Test;


/**
 * A {@code ObjectMapperTests}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ObjectMapperTests {

	@Before
	public void setup() {
	}
	
	static class A {
		int id = 1;

		String text;
		
		@Assign(false)
		int immutable;

		@Assign(overwrite=false)
		private String notOverwriten = "NOT_OVERWRITTEN";

		List<String> list = new ArrayList<String>();

		List<String> mergedList = new ArrayList<String>();

		A a2;

		String[] array;

		String nullable = "NULLABLE";
		
		@Assign(overwriteWithNull=false)
		String notNull = "NOT_NULL";

		@Assign(overwriteWithEmpty=false)
		String[] nonEmptyArray = {"a", "b", "c"};

		@Assign(overwriteWithEmpty=false)
		List<String> nonEmpty = Arrays.asList(new String[]{"a", "b", "c"});

		@Assign(merge=true)
		String[] mergedArray = {"a", "b"};

		//@Assign(projection=@Projection(includeOnly={"id", "text"}))
		List<A> complexList = new ArrayList<A>();

		public A() {
			list.addAll(Arrays.asList("A", "B", "C"));
			mergedList.addAll(Arrays.asList("A", "B"));
			complexList.addAll(Arrays.asList(new A[]{new A(1), new A(2), new A(3)}));
		}
		
		public A(int id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			return FormatUtil.toString(this);
		}		
	}
		
	@Test
	public void test1() {
		A a = new A(1);
		a.a2 = new A(2);
		a.array = new String[]{"a", "b", "c"};
		A b = new A();
		a.immutable = 1;
		b.immutable = 2;
		a.text = "text";
		a.array = new String[]{"a", "b", "c"};
		a.nonEmptyArray = new String[]{"X", "Y"};
		a.notNull = null;
		a.nullable = null;
		a.nonEmptyArray = new String[0];
		a.nonEmpty = new ArrayList<String>();
		MetaOperations.assign(b, a);
		assertTrue(a!=b);
		System.out.println(a);
		System.out.println(b);
		assertEquals(a.id, b.id);
		assertEquals(2, b.immutable);
		assertEquals(a.text, b.text);
		assertTrue(a.a2!=b.a2);
		assertEquals(a.a2.id, b.a2.id);
		assertTrue(a.array!=b.array);
		assertEquals(a.array.length, b.array.length);
		assertEquals(a.array[0], b.array[0]);
		assertEquals(a.array[1], b.array[1]);
		assertEquals(a.array[2], b.array[2]);	
		assertEquals(3, b.array.length);
		assertNull(b.nullable);
		assertNotNull(b.notNull);
		assertTrue(b.nonEmptyArray.length>0);		
		assertTrue(b.nonEmpty.size()>0);
	}
	
}
