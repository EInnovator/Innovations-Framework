/**
 * 
 */
package org.einnovator.util;

/**
 * Miscellaneous utility operations with characters.
 *
 * @author Jorge Simão
 */
public class CharacterUtil {

	/**
	 * Check if a character is upper-case letter.
	 * 
	 * @param c the character
	 * @return {@code true}, if the input character is a upper-case letter;
	 *  {@code false}, otherwise.
	 */
	static public boolean isUpperCase(char c) {
		return (c>='A' && c<='Z');
	}

	/**
	 * Check if a character is lower-case letter.
	 * 
	 * @param c the character
	 * @return {@code true}, if the input character is a lower-case letter;
	 *  {@code false}, otherwise.
	 */
	static public boolean isLowerCase(char c) {
		return (c>='a' && c<='z');
	}

	/**
	 * Convert character to upper-case.
	 * 
	 * @param c the character
	 * @return the upper-case character if the input was a lower-case letter;
	 *  or the input character, otherwise.
	 */
	static public char toUpperCase(char c) {
		if (isLowerCase(c)) {
			return (char)((int)c - ('a' - 'A'));
		} else {
			return c;
		}
	}

	/**
	 * Convert character to lower-case.
	 * 
	 * @param c the character
	 * @return the lower-case character if the input was an upper-case letter;
	 *  or the input character, otherwise.
	 */
	static public char toLowerCase(char c) {
		if (isUpperCase(c)) {
			return (char)((int)c + ('a' - 'A'));
		} else {
			return c;
		}
	}


	/**
	 * Check if a character is blank.
	 * 
	 * AA character is considered blank if it is a space or a tab.
	 * 
	 * @param c the character
	 * @return <code>true</code>, if the character is a blank; <code>false</code>, otherwise.
	 */
	public static boolean isBlank(char c) {
		return c==' ' || c=='\t';
	}

	/**
	 * Check if a character is a letter (lower or upper case).
	 * 
	 * @param c the character
	 * @return <code>true</code>, if the character is a character; <code>false</code>, otherwise.
	 */
	public static boolean isAlpha(char c) {
		return (c>='A' && c<='Z') || (c>='a' && c<='z');
	}

	/**
	 * Check if a character is a digit.
	 * 
	 * @param c the character
	 * @return <code>true</code>, if the character is a digits; <code>false</code>, otherwise.
	 */
	public static boolean isDigit(char c) {
		return c>='0' &&  c<='9';
	}
	
	/**
	 * Map a escape character code (second character) to a escape character.
	 * 
	 * The escape character code is defined as the second character in the Java expression
	 * that defined the character (e.g. 't' is mapped to '\t').
	 * 
	 * The following codes are accepted: {@code 't', 'n', 'b', 'r', 'f', '\'', '\"', '\\')
	 * @param c the escape character code
	 * @return the escape character; or 0 if the input character code is not valid.
	 */
	public static char mapEscape(int c) {
		switch (c) {
			case 't' : return '\t';
			case 'n' : return '\n';
			case 'b' : return '\b';		
			case 'r' : return '\r';
			case 'f' : return '\f';
			case '\'' : return '\'';
			case '\"' : return '\"';
			case '\\' : return '\\';				
			default:
				return 0;
		}
	}
	
}
