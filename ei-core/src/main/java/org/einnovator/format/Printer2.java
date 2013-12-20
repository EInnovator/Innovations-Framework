/**
 * 
 */
package org.einnovator.format;

/**
 * A printer of values that is aware of the {@code FormatContext}.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
public interface Printer2<T> extends Printer<T> {

	/**
	 * Print a value by returning a string representation.
	 * 
	 * @param value the value
	 * @param context the {@code FormatContext}
	 * @return the string representation of the value.
	 */
	String print(T value, FormatContext context);
}
