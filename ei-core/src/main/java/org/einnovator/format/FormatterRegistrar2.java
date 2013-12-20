/**
 * 
 */
package org.einnovator.format;

/**
 * A FormatterRegistrar.
 *
 * @author Jorge Simão
 */
public interface FormatterRegistrar2 extends FormatterRegistrar {

	void registerFormatter(Formatter<?> formatter, FormatterRegistry registry);
}
