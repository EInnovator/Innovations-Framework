/**
 * 
 */
package org.einnovator.util;

import static org.einnovator.util.PatternUtil.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * A PatternUtilTests.
 *
 * @author Jorge Simão
 */
public class PatternUtilTests {

	@Test
	public void textMatchTests() {
		assertTrue(matchText("abc", "abc"));
		assertFalse(matchText("abc", "abz"));
		assertTrue(matchText("*abc", "abc"));		
		assertTrue(matchText("abc*", "abc"));
		assertTrue(matchText("*bc", "abc"));
		assertTrue(matchText("ab*", "abc"));		
		assertFalse(matchText("*bc", "abz"));
		assertFalse(matchText("ab*", "zbc"));
		assertTrue(matchText("*bc", "aabc"));
		assertTrue(matchText("ab*", "abcc"));		
		assertTrue(matchText("*b*", "abc"));
		assertTrue(matchText("a*c", "abc"));
	}

}
