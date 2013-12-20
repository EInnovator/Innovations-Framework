package org.einnovator.format;

import java.lang.annotation.Annotation;
import java.util.Locale;

/**
 * AA AnnotationFormatter.
 *
 * @author Jorge Simão
 * @param <T>
 * @param <AA>
 */
public interface AnnotationFormatter<T, A extends Annotation> {


	/**
	 * Ignore value to text.
	 * 
	 * @param value The value to format.
	 * @param annotation the annotation that specifies the format
	 * @param locale The selected Locale, or null for default Locale. 
	 * @return the formated value
	 */
	String format(T value, A annotation, Locale locale);
	
	/**
	 * Parse text into value of specified type.
	 * 
	 * @param <T> The parsed value
	 * @param text The text to parse
	 * @param annotation the annotation that specifies the format
	 * @param locale The selected Locale, or null for default Locale
	 * @param type Type of parsed value
	 * @return The parsed value.
	 */
	T parse(String text, A annotation, Locale locale);
	

}
