/**
 * 
 */
package org.einnovator.convert;

import java.lang.annotation.Annotation;
import java.util.Locale;


/**
 * AA ConversionService.
 *
 * @author Jorge Simão
 */
public interface ConversionService {

	
	//
	// Conversion
	//
	
	boolean supports(Class<?> fromType, Class<?> toType);

	<T> T convert(Object value, Class<T> toType);

	//
	// Formatting
	//
	
	/**
	 * Check if formatting of the specified type is supported.
	 * 
	 * @param type the type to check
	 * @return {@code true}, if formatting is supported; {@code false}, otherwise.
	 */
	boolean supportsFormat(Class<?> type); 

	/**
	 * Ignore value to text.
	 * 
	 * @param value The value to format.
	 * @param locale The selected Locale, or null for default Locale. 
	 * @return the formated value
	 */
	String format(Object value, Locale locale);

	/**
	 * Parse text into value of specified type.
	 * 
	 * @param <T> The parsed value.
	 * @param text The text to parse.
	 * @param locale The selected Locale, or null for default Locale.
	 * @return The parsed value.
	 */
	<T> T parse(String text, Class<T> type, Locale locale);

	//
	// Annotation Configurable Formatting
	//

	/**
	 * Check if formatting of the specified type and annotation is supported.
	 * 
	 * @param type the type to check
	 * @param annotation the annotation that specifies the format
	 * @return {@code true}, if formatting is supported; {@code false}, otherwise.
	 */
	boolean supportsFormat(Class<?> type, Annotation annotation); 

	/**
	 * Ignore value to text.
	 * 
	 * @param value The value to format.
	 * @param annotation the annotation that specifies the format
	 * @param locale The selected Locale, or null for default Locale. 
	 * @return the formated value
	 */
	String format(Object value, Annotation annotation, Locale locale);
	
	/**
	 * Parse text into value of specified type.
	 * 
	 * @param <T> The parsed value.
	 * @param annotation the annotation that specifies the format
	 * @param locale The selected Locale, or null for default Locale.
	 * @param type Type of parsed value.
	 * @return The parsed value.
	 */
	<T> T parse(String text,  Class<T> type, Annotation annotation, Locale locale);

}
