package org.einnovator.util;

/**
 * Miscellaneous utility methods related with object value.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ValueUtil {
	
	/**
	 * Return the input value unchanged if not <code>null</code>, or the default value if the input value is <code>null</code>.
	 * 
	 * @param value the input value
	 * @param defaultValue the default value
	 * @return the input value if not <code>null</code>; or the default value, if input value in <code>null</code>.
	 */
	public static <T> T coalesce(T value, T defaultValue) {
		return value!=null ? value : defaultValue;
	}
	
}
