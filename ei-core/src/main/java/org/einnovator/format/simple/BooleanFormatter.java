package org.einnovator.format.simple;

import java.util.Locale;

import org.einnovator.format.support.FormatterSupport;
import org.einnovator.util.types.TypeUtil;


/**
 * AA BooleanFormatter.
 *
 * @author Jorge Simão
 */
public class BooleanFormatter extends FormatterSupport<Boolean> {

	//
	// Formatter<Boolean> implementation
	//

	@Override
	public Boolean parse(String text, Locale locale) {
		return TypeUtil.isTrue(text);
	}


}
