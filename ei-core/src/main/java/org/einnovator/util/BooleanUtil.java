/**
 * 
 */
package org.einnovator.util;

/**
 * Miscellaneous boolean operations.
 *
 * @author Jorge Simão
 */
public class BooleanUtil {


	public static Boolean not(Boolean b) {
		return b!=null ? Boolean.TRUE.equals(b) : null;
	}
}
