package org.einnovator.convert;


import org.einnovator.convert.Converter;

/**
 * AA ConverterRegistry.
 *
 * @author Jorge Simão
 */
public interface ConverterRegistry {

	void addConverters(ConverterRegistrar registrar);
	
	void addConverter(Converter<?,?> converter, Class<?> fromType, Class<?> toType);
	
	<T,U> Converter<T,U> getConverter(Class<T> fromType, Class<U> toType);

	void removeConverter(Converter<?,?> converter);
	
	void clear();

}
