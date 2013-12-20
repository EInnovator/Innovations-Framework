/**
 * 
 */
package org.einnovator.convert;

import org.einnovator.format.Formatter;


/**
 * AA ConfigurableFormatter.
 *
 * @author Jorge Simão
 */
public interface ConfigurableFormatter<T> extends Formatter<T> {
	void setDefaultPattern(String pattern);
}
