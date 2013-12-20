package org.einnovator.convert;

import org.einnovator.convert.number.NumberConverterRegistrar;
import org.einnovator.convert.simple.BooleanConverterRegistrar;
import org.einnovator.format.datetime.DateTimeFormatterRegistrar;
import org.einnovator.format.number.NumberFormatterRegistrar;
import org.einnovator.format.simple.SimpleFormatterRegistrar;

/**
 * A DefaultConversionService.
 *
 * @author Jorge Simão
 */
public class DefaultConversionService extends ConversionServiceImpl {
	
	//
	// Constructors
	//
	
	public DefaultConversionService(boolean namedFormats) {
		addConverters();
		addFormatters(namedFormats);
	}

	public DefaultConversionService() {
		this(false);
	}

	//
	// Default Converters and Formatters
	//
	
	protected void addConverters() {
		addConverters(new BooleanConverterRegistrar());
		addConverters(new NumberConverterRegistrar());
	}
	
	protected void addFormatters(boolean namedFormats) {
		addFormatters(new SimpleFormatterRegistrar());
		addFormatters(new NumberFormatterRegistrar());
		addFormatters(new DateTimeFormatterRegistrar(namedFormats));
	}
	
}
