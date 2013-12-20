package org.einnovator.environment;

import org.einnovator.convert.ConversionServiceAwareSupport;

/**
 * Abstract class to support the implementation of {@code Environment} classes.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class EnvironmentSupport extends ConversionServiceAwareSupport implements Environment {

	//
	// Constructors
	//
	
	public EnvironmentSupport() {
	}
	
	//
	// Environment Implementation
	//
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue(String key, Class<T> type, T defaultValue) {
		Object value = getValue(key, null);
		if (value==null) {
			return null;
		}
		T value2;
		if (conversionService!=null && conversionService.supports(value.getClass(), type)) {
			value2 = conversionService.convert(value, type);
		} else {
			value2 = (T)value;
		}
		return value2!=null ? value2 : defaultValue;
	}

	
}
