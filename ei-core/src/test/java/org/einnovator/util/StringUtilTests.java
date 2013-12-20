/**
 * 
 */
package org.einnovator.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Test;

import static org.einnovator.util.StringUtil.*;


/**
 * A StringUtilTests.
 *
 * @author Jorge Simão
 */
public class StringUtilTests {

	@Test
	public void splitWordsTest() {
		String[] a = splitWords("AbcDefGhiXyz");
		System.out.println(Arrays.toString(a));
		assertEquals(4, a.length);
		assertEquals("Abc", a[0]);
		assertEquals("Def", a[1]);
		assertEquals("Ghi", a[2]);
		assertEquals("Xyz", a[3]);

		a = splitWords("abcDefGhiXyz");
		System.out.println(Arrays.toString(a));
		assertEquals(4, a.length);
		assertEquals("abc", a[0]);
		assertEquals("Def", a[1]);
		assertEquals("Ghi", a[2]);
		assertEquals("Xyz", a[3]);

		a = splitWords("abcDEfGhiXyz");
		System.out.println(Arrays.toString(a));
		assertEquals(4, a.length);
		assertEquals("abc", a[0]);
		assertEquals("DEf", a[1]);
		assertEquals("Ghi", a[2]);
		assertEquals("Xyz", a[3]);

	}

	@Test
	public void removeFirstWordTest() {
		String s = removeFirstWord("abcDef");
		assertEquals("Def", s);

		s = removeFirstWord("AbcDef");
		assertEquals("Def", s);

	}
	
	@Test
	public void sconcatTest() {
		assertEquals("/**", sconcat("/", "/**", "/"));
	}
	
	@Test
	public void countTest() {
		assertEquals(3, count("abcabcxabc", "abc"));
		assertEquals(2, count("11/11/11", "/"));
	}
	
	@Test
	public void splitTest() {
		assertArrayEquals(new Object[]{"1","2","3"}, "1,2,3".split(","));
		assertArrayEquals(new Object[]{"1","2","3"}, split("1,2,3", ","));
		assertArrayEquals(new Object[]{"a","b","c"}, "a.b.c".split("\\."));
		assertArrayEquals(new Object[]{"a","b","c"}, split("a.b.c", "\\."));

		assertArrayEquals(new Object[]{"1"}, "1".split(","));
		assertArrayEquals(new Object[]{"1"}, split("1", ","));

	}

}
