/**
 * 
 */
package org.einnovator.util;

import static org.junit.Assert.*;

import org.einnovator.util.CharacterUtil;
import org.junit.Test;


public class CharacterUtilTests {

	@Test
	public void caseTests() {
		assertTrue(CharacterUtil.isUpperCase('A'));
		assertTrue(CharacterUtil.isUpperCase('Z'));
		assertFalse(CharacterUtil.isUpperCase('a'));
		assertFalse(CharacterUtil.isUpperCase('z'));
		assertFalse(CharacterUtil.isUpperCase('!'));
		assertFalse(CharacterUtil.isUpperCase(' '));
		assertFalse(CharacterUtil.isUpperCase('@'));

		assertTrue(CharacterUtil.isLowerCase('a'));
		assertTrue(CharacterUtil.isLowerCase('z'));
		assertFalse(CharacterUtil.isLowerCase('A'));
		assertFalse(CharacterUtil.isLowerCase('Z'));
		assertFalse(CharacterUtil.isLowerCase('!'));
		assertFalse(CharacterUtil.isLowerCase(' '));
		assertFalse(CharacterUtil.isLowerCase('@'));

		assertEquals('A', CharacterUtil.toUpperCase('a'));
		assertEquals('A', CharacterUtil.toUpperCase('A'));
		assertEquals('Z', CharacterUtil.toUpperCase('z'));
		assertEquals('Z', CharacterUtil.toUpperCase('Z'));
		assertEquals('!', CharacterUtil.toUpperCase('!'));
		assertEquals(' ', CharacterUtil.toUpperCase(' '));
		assertEquals('@', CharacterUtil.toUpperCase('@'));

		assertEquals('a', CharacterUtil.toLowerCase('A'));
		assertEquals('a', CharacterUtil.toLowerCase('a'));
		assertEquals('z', CharacterUtil.toLowerCase('Z'));
		assertEquals('z', CharacterUtil.toLowerCase('z'));
		assertEquals('!', CharacterUtil.toLowerCase('!'));
		assertEquals(' ', CharacterUtil.toLowerCase(' '));
		assertEquals('@', CharacterUtil.toLowerCase('@'));

	}
}
