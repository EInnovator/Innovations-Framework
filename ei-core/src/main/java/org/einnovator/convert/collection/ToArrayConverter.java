/**
 * 
 */
package org.einnovator.convert.collection;

import java.lang.reflect.Array;

import org.einnovator.convert.ConversionService;

/**
 * A {@code ToArrayConverter}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class ToArrayConverter {

	protected ConversionService conversionService;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ToArrayConverter}.
	 *
	 */
	public ToArrayConverter(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@SuppressWarnings("unchecked")
	public <T> T convert(Object value, Class<T> toType) {
		if (value.getClass().isArray()) {
			int n = Array.getLength(value);
			Object array = Array.newInstance(toType.getComponentType(), n);
			for (int i=0; i<n; i++) {
				Object ivalue = Array.get(value, i);
				ivalue = conversionService.convert(ivalue, toType.getComponentType());
				Array.set(array, i, ivalue);
			}
			return (T)array;
		} else {
			Object array = Array.newInstance(toType.getComponentType(), 1);
			Object value2 = conversionService.convert(value, toType.getComponentType());
			Array.set(array, 0, value2);
			return (T)array;			
		}
	}
}
