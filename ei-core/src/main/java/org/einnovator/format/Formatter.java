package org.einnovator.format;

import java.util.Locale;

/**
 * A Formatter.
 *
 * @author Jorge Simão
 */
public interface Formatter<T> extends Printer<T> {

	/**
	 * Parse text into value of specified type.
	 * 
	 * @param <T> The parsed value.
	 * @param text The text to parse.
	 * @param locale The selected Locale, or null for default Locale.
	 * @return The parsed value.
	 */
	T parse(String text, Locale locale);

}
