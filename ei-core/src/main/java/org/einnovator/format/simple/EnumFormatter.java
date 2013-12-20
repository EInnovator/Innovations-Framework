package org.einnovator.format.simple;

import java.util.Locale;

import org.einnovator.convert.ConversionException;
import org.einnovator.text.transforms.CamelCaseToUnderscoreTextTransform;


/**
 * Parses and format and enumerated value.
 * 
 * @author Jorge Simão
 */
public class EnumFormatter {
	
	/**
	 * Ignore an enumerated value.
	 * 
	 * @param value the enumerated value
	 * @param locale the locale (ignored)
	 * @return the string representation of the enumerated value
	 */
	public String format(Enum<?> value, Locale locale) {
		return value.toString();
	}
	
	/**
	 * Parse a string to a value of an enumerated type.
	 * 
	 * Matching is first done by string comparison (case ignored) between the input string
	 * and the text representation of each enumerated value.
	 * 
	 * If no matching is found, a transformation is applied to the input string
	 * to replace camel-case identifiers with underscore separated words before
	 * performing another matching round.
	 * 
	 * @param text the string to parse
	 * @param type the enumerated type
	 * @param locale the locale (ignored)
	 * @return the enumerated value
	 * @throws ConversionException if no matching value is found
	 */
	@SuppressWarnings("unchecked")
	public Enum<?> parse(String text, Class<?> type, Locale locale) {
		Enum<?>[] values = ((Class<Enum<?>>)type).getEnumConstants();
		Enum<?> value = findMatch(text, values);
		if (value!=null) {
			return value;
		}
		if (value==null) {
			text = CamelCaseToUnderscoreTextTransform.getInstance().transform(text);
			value = findMatch(text, values);
			if (value!=null) {
				return value;
			}
		}
		throw new ConversionException("Unable to parse text value '" + text + "' to enumerated type '" + type.getName() + "'");
	}


	/**
	 * Find the enumerated value with matching name (case ignored).
	 * 
	 * @param text the input string
	 * @param values the array of enumerated value
	 * @return
	 */
	private Enum<?> findMatch(String text, Enum<?>[] values) {
		for (Enum<?> v: values) {
			if (text.equalsIgnoreCase(v.toString())) {
				return (Enum<?>)v;
			}
		}
		return null;
	}

}
