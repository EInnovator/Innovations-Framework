package org.einnovator.format.simple;

import java.util.Locale;

import org.einnovator.convert.ConversionException;
import org.einnovator.format.support.FormatterSupport;


/**
 * AA BooleanFormatter.
 *
 * @author Jorge Simão
 */
public class CharacterFormatter extends FormatterSupport<Character> {

	//
	// Formatter<Boolean> implementation
	//

	@Override
	public Character parse(String text, Locale locale) {
		text = text.trim();
		if (text.length()==1) {
			throw new ConversionException("Character can be converted only from string with single character, found empty string");
		}
		if (text.length()!=1) {
			throw new ConversionException("Character can be converted only from string with single character, found length:" + text.length());
		}
		return text.charAt(0);
	}


}
