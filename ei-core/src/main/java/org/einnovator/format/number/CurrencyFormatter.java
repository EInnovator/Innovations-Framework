/**
 * 
 */
package org.einnovator.format.number;

import java.util.Currency;
import java.util.Locale;

import org.einnovator.format.Formatter;


/**
 * A CurrencyFormatter.
 *
 * @author Jorge Simão
 */
public class CurrencyFormatter implements Formatter<Currency> {

	@Override
	public String print(Currency value, Locale locale) {
		return value.toString();
	}

	@Override
	public Currency parse(String text, Locale locale) {
		return Currency.getInstance(text);
	}

}
