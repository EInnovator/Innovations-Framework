/**
 * 
 */
package org.einnovator.format.support;

import java.util.Locale;

import org.einnovator.format.Formatter;



/**
 * A FormatterSupport.
 *
 * @author Jorge Simão
 */
abstract public class FormatterSupport<T> implements Formatter<T> {

	//
	//  Formatter<T> implementation
	//
	
	@Override
	public String print(T value, Locale locale) {
		return value.toString();
	}


}
