/**
 * 
 */
package org.einnovator.util;

/**
 * A TextPatternUtil.
 *
 * @author Jorge Simão
 */
public class PatternUtil {

	/**
	 * Match pattern with wildcards with text.
	 * 
	 * Wildcard '*' matches any text.
	 * e.g. '*' matches 'abc' and 'abz'; 
	 * 	'*c' matches 'abc' and 'bc';
	 *  '*b*' matches 'abc' and 'zb' and 'b'
	 *  'a*c' matches 'abc' and 'azc'
	 * 
	 * @param pattern pattern to match
	 * @param s string to match
	 * @return
	 */
	public static boolean matchText(String pattern, String s) {
		if (pattern.isEmpty()) {
			return s.isEmpty();
		}
		if (pattern.equals(s) || pattern.equals("*")) {
			return true;
		}
		int i = pattern.indexOf("*");
		if (i==0) {
			String patternTail = pattern.substring(i+1);
			for (int j=0; j<s.length(); j++) {
				if (matchText(patternTail, s.substring(j))) {
					return true;
				}
			}
			return false;			
		} else if (i>0) {
			String patternHead = pattern.substring(0,i);
			if (!s.startsWith(patternHead)) {
				return false;
			}
			String patternTail = pattern.substring(i);
			return matchText(patternTail, s.substring(i));
		}
		return false;
	}
	

}
