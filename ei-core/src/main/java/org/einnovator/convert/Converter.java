package org.einnovator.convert;

/**
 * AA Converter.
 *
 * @author Jorge Simão
 * @param <T>
 * @param <U>
 */
public interface Converter<T,U> {

	/**
	 * Convert input value to value of the converter output type.
	 * 
	 * @param value the value to convert
	 * @return the converted value
	 */
	U convert(T value);

}
