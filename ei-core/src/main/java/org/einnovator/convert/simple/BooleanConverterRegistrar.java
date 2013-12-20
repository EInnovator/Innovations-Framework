package org.einnovator.convert.simple;

import org.einnovator.convert.Converter;
import org.einnovator.convert.ConverterRegistrar;
import org.einnovator.convert.ConverterRegistry;


/**
 * AA BooleanConverter.
 *
 * @author Jorge Simão
 */
public class BooleanConverterRegistrar implements ConverterRegistrar {

	//
	// Constructors
	//
	

	/**
	 * Create instance of BooleanConverterRegistrar.
	 *
	 */
	public BooleanConverterRegistrar() {
	}
	
	//
	// ConverterRegistrar implementation
	//
	
	@Override
	public void registerConverters(ConverterRegistry registry) {
		registry.addConverter(new NumberToBooleanConverter(), Number.class, Boolean.class);
		registry.addConverter(new NumberToBooleanConverter(), Number.class, Boolean.TYPE);
	}
	
	public static class NumberToBooleanConverter implements Converter<Number, Boolean> {
		@Override
		public Boolean convert(Number value) {
			return value.intValue()!=0;
		}
	}


}
