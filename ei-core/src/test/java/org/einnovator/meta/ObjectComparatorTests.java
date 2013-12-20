/**
 * 
 */
package org.einnovator.meta;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.einnovator.format.FormatUtil;
import org.junit.Before;
import org.junit.Test;


/**
 * A {@code ObjectComparatorTests}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ObjectComparatorTests {

	ObjectComparator comparator;
	
	@Before
	public void setup() {
		comparator = new ObjectComparator();
	}
	
	static class A {
		int id = 1;

		String text = "text";
		
		@Compare(false)
		int random = new Random().nextInt();

		String nullValue;
		
		A a2;

		String[] array = new String[]{"a", "b", "c"};

		List<String> list = new ArrayList<String>();

		List<A> complexList = new ArrayList<A>();

		public A() {
			list.addAll(Arrays.asList("A", "B", "C"));
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
		A a = new A();
		a.a2 = new A(2);
		A b = new A();
		b.a2 = new A(2);
		System.out.println(a);
		assertTrue(comparator.equals(b, a));
		b.a2.id = 3;
		assertFalse(comparator.equals(b, a));
		b.a2.id = 2;
		b.list.remove(0);
		assertFalse(comparator.equals(b, a));
		//check cycles
	}
	
}
