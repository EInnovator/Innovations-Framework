package org.einnovator.format.collection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Locale;

import org.einnovator.convert.ConversionService;


/**
 * An ArrayFormatter.
 *
 * @author Jorge Simão
 */
public class ArrayFormatter extends CollectionFormatterSupport {
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of ArrayTextConverter.
	 *
	 * @param conversionService
	 */
	public ArrayFormatter(ConversionService conversionService) {
		super(conversionService);
	}

	/**
	 * Create instance of ArrayTextConverter.
	 *
	 * @param conversionService
	 * @param separator
	 */
	public ArrayFormatter(ConversionService conversionService, String separator) {
		super(conversionService, separator);
	}

	
	//
	// MultiFormatter implementation
	//
	

	public String format(Object value, Locale locale) {
		int n = Array.getLength(value);
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<n; i++) {
			Object v = Array.get(value, i);			
			String s = conversionService.format(v, locale);
			sb.append(s);
			if (i<n-1) {
				sb.append(separator);				
			}
		}
		return sb.toString();
	}

	public String format(Object value, Annotation annotation, Locale locale) {
		int n = Array.getLength(value);
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<n; i++) {
			Object v = Array.get(value, i);			
			String s = conversionService.format(v, annotation, locale);
			sb.append(s);
			if (i<n-1) {
				sb.append(separator);				
			}
		}
		return sb.toString();		
	}

	public Object parse(String text, Class<?> type, Locale locale) {
		if (text==null) {
			return null;
		}
		text = text.trim();
		if (text.isEmpty()) {
			return Array.newInstance(type.getComponentType(), 0);
		}
		String[] a = text.split(separator);
		Object array = Array.newInstance(type.getComponentType(), a.length);
		for (int i=0; i<a.length; i++) {
			String s = a[i].trim();
			Object value = conversionService.parse(s, type.getComponentType(), locale);
			Array.set(array, i, value);
		}
		return array;
	}

	public Object parse(String text, Class<?> type, Annotation annotation, Locale locale) {
		String[] a = text.split(separator);
		Object array = Array.newInstance(type.getComponentType(), a.length);
		for (int i=0; i<a.length; i++) {
			Object value = conversionService.parse(a[i], type.getComponentType(), locale);
			Array.set(array, i, value);
		}
		return array;
	}

}
