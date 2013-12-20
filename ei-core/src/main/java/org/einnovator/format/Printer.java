/**
 * 
 */
package org.einnovator.format;

import java.util.Locale;

/**
 * A printer of values.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface Printer<T> {

	/**
	 * Print a value by returning a string representation.
	 * 
	 * @param value the value
	 * @param locale the {@code Locale}
	 * @return the string representation of the value.
	 */
	String print(T value, Locale locale);
}
