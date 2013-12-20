package org.einnovator.format;



/**
 * A FormatterRegistry.
 *
 * @author Jorge Simão
 */
public interface FormatterRegistry {

	void addFormatters(FormatterRegistrar registrar);

	void addFormatter(Formatter<?> formatter, Class<?> type);
	
	void addFormatter(Formatter<?> formatter);

	<T> Formatter<T> getFormatter(Class<T> type);

	void removeFormatter(Formatter<?> formatter);
	
	void clear();
}
