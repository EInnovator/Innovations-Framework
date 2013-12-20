/**
 * 
 */
package org.einnovator.format.support;

import java.util.Map;
import java.util.HashMap;

import org.einnovator.convert.support.ConverterRegistrySupport;
import org.einnovator.format.Formatter;
import org.einnovator.format.FormatterRegistrar;
import org.einnovator.format.FormatterRegistry;
import org.einnovator.meta.GenericUtil;


/**
 * A FormatterRegistrySupport.
 *
 * @author Jorge Simão
 */
public class FormatterRegistrySupport extends ConverterRegistrySupport implements FormatterRegistry {

	protected Map<Class<?>, Formatter<?>> formatterMap = new HashMap<Class<?>, Formatter<?>>();

	//
	// Constructors
	//
	
	/**
	 * Create instance of FormatterRegistrySupport.
	 *
	 */
	public FormatterRegistrySupport() {
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of formatterMap.
	 *
	 * @return the formatterMap
	 */
	public Map<Class<?>, Formatter<?>> getFormatterMap() {
		return formatterMap;
	}

	/**
	 * Set the value of formatterMap.
	 *
	 * @param formatterMap the formatterMap to set
	 */
	public void setFormatterMap(Map<Class<?>, Formatter<?>> formatterMap) {
		this.formatterMap = formatterMap;
	}
	
	//
	// FormatterRegistry implementation
	//
	
	@Override
	public void addFormatters(FormatterRegistrar registrar) {
		registrar.registerFormatters(this);
	}


	@Override
	public void addFormatter(Formatter<?> formatter, Class<?> type) {
		formatterMap.put(type, formatter);
	}

	@Override
	public void addFormatter(Formatter<?> formatter) {
		Class<?> type = getFormatedType(formatter);
		if (type==null) {
			throw new IllegalArgumentException("Unable to determined formated type for formatter of type '" + formatter.getClass().getName() + "'");
		}
		formatterMap.put(type, formatter);
	}
	
	private Class<?> getFormatedType(Formatter<?> formatter) {
		return GenericUtil.getGenericInterfaceTypeParameter(formatter.getClass(), Formatter.class);
	}

	@Override
	public <T> Formatter<T> getFormatter(Class<T> type) {
		Class<?> type2= type;
		while (type2!=null && !Object.class.equals(type2)) {
			@SuppressWarnings("unchecked")
			Formatter<T> formatter = (Formatter<T>) formatterMap.get(type2);
			if (formatter!=null) {
				return formatter;
			}
			type2 = type2.getSuperclass();
		}
		return null;
	}
	
	@Override
	public void removeFormatter(Formatter<?> formatter) {
		formatterMap.remove(formatter);
	}

	@Override
	public void clear() {
		super.clear();
		formatterMap.clear();
	}
}
