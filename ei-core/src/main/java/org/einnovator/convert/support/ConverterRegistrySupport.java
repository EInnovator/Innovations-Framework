/**
 * 
 */
package org.einnovator.convert.support;

import java.util.Map;
import java.util.HashMap;

import org.einnovator.convert.Converter;
import org.einnovator.convert.ConverterRegistrar;
import org.einnovator.convert.ConverterRegistry;


/**
 * AA ConverterRegistrySupport.
 *
 * @author Jorge Simão
 */
public class ConverterRegistrySupport implements ConverterRegistry {

	protected Map<Class<?>, Map<Class<?>, Converter<?,?>>> converterMap;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of ConverterRegistrySupport.
	 *
	 */
	public ConverterRegistrySupport() {
		converterMap = new HashMap<Class<?>, Map<Class<?>,Converter<?,?>>>();
	}
	
	//
	// Getters and setters
	//
	
	/**
	 * Get the value of converterMap.
	 *
	 * @return the converterMap
	 */
	public Map<Class<?>, Map<Class<?>, Converter<?, ?>>> getConverterMap() {
		return converterMap;
	}

	/**
	 * Set the value of converterMap.
	 *
	 * @param converterMap the converterMap to set
	 */
	public void setConverterMap(
			Map<Class<?>, Map<Class<?>, Converter<?, ?>>> converterMap) {
		this.converterMap = converterMap;
	}
	
	//
	// ConverterRegistry implementation
	//
	
	@Override
	public void addConverters(ConverterRegistrar registrar) {
		registrar.registerConverters(this);
	}
	

	@Override
	public void addConverter(Converter<?,?> converter, Class<?> fromType, Class<?> toType) {
		Map<Class<?>,Converter<?,?>> map = converterMap.get(fromType);
		if (map==null) {
			map = new HashMap<Class<?>,Converter<?,?>>();
			converterMap.put(fromType, map);
		}
		map.put(toType, converter);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T,U> Converter<T,U> getConverter(Class<T> fromType, Class<U> toType) {
		Map<Class<?>,Converter<?,?>> map = converterMap.get(fromType);
		if (map==null) {
			return null;
		}
		return (Converter<T,U>)map.get(toType);
	}

	@Override
	public void removeConverter(Converter<?, ?> converter) {
		converterMap.remove(converter);
	}

	@Override
	public void clear() {
		converterMap.clear();
	}


}
