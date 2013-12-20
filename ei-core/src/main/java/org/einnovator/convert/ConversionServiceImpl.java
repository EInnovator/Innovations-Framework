package org.einnovator.convert;

import java.lang.annotation.Annotation;
import java.util.Locale;

import org.einnovator.convert.collection.ToArrayConverter;
import org.einnovator.format.AnnotationFormatter;
import org.einnovator.format.Formatter;
import org.einnovator.format.collection.ArrayFormatter;
import org.einnovator.format.simple.EnumFormatter;
import org.einnovator.format.support.AnnotationFormatterRegistrySupport;
import org.einnovator.util.types.TypeUtil;


/**
 * AA CompositeConversionService.
 *
 * @author Jorge Simão
 */
public class ConversionServiceImpl extends AnnotationFormatterRegistrySupport implements ConversionService {

	public static final String NULL = "null";
	
	protected Locale locale = null;

	protected ArrayFormatter arrayFormatter;

	protected ToArrayConverter toArrayConverter;
	
	protected EnumFormatter enumFormatter;

	protected ConversionService parent;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of CompositeConversionService.
	 *
	 */
	public ConversionServiceImpl() {
		arrayFormatter = new ArrayFormatter(this);
		toArrayConverter = new ToArrayConverter(this);
		enumFormatter = new EnumFormatter();
	}

	/**
	 * Create instance of ConversionServiceImpl.
	 *
	 * @param parent
	 */
	public ConversionServiceImpl(ConversionService parent) {
		this();
		this.parent = parent;
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of parent.
	 *
	 * @return the parent
	 */
	public ConversionService getParent() {
		return parent;
	}

	/**
	 * Set the value of parent.
	 *
	 * @param parent the parent to set
	 */
	public void setParent(ConversionService parent) {
		this.parent = parent;
	}

	
	
	//
	// ConversionService implementation
	//

	//
	// Conversion
	//

	
	@Override
	public boolean supports(Class<?> fromType, Class<?> toType) {
		if (Object.class.equals(toType)) {
			return true;
		}
		if (toType.isArray()) {
			return true;
		}
		Converter<?,?> converter = getConverter(fromType, toType);
		if (converter!=null) {
			return true;
		}
		if (String.class.equals(fromType)) {
			if (supportsFormat(fromType)) {
				return true;
			}
		}
		if (String.class.equals(toType)) {
			if (supportsFormat(toType)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Convert value to specified type.
	 * 
	 * Uses registered converters to convert specified value.
	 * If not conversion is possible:
	 * fall-backs to registered parsers is the type of value is {@java.lang.String}
	 * fall-backs to registered formatter is the target type is {@java.lang.String}
	 * 
	 * @param value the value to convert
	 * @param toType the target type
	 * @return the converted value
	 */
	/**
	 * @see org.einnovator.convert.ConversionService#convert(java.lang.Object, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(Object value, Class<T> toType) {
		if (value==null) {
			return null;
		}
		Class<?> fromType = value.getClass();
		if (toType.isAssignableFrom(fromType)) {
			return (T)value;
		}
		
		if (value instanceof String) {
			return parse((String)value,  toType, locale);
		}

		if (String.class.equals(toType)) {
			return (T)format(value, locale);
		}
		if (toType.isArray()) {
			return toArrayConverter.convert(value, toType);
		}
		
		if (TypeUtil.unbox(fromType).equals(TypeUtil.unbox(toType))) {
			return (T)value;
		}
		
		Converter<Object,T> converter = (Converter<Object,T>)getConverter(fromType, toType);
		if (converter!=null) {
			return converter.convert(value);
		}

		throw new ConverterNotFoundException("To convert from '" + fromType + " to '" + toType);
	}

	//
	// Formatting
	//

	@Override
	public boolean supportsFormat(Class<?> type) {
		if (type.isArray() && supportsFormat(type.getComponentType())) {
			return true;
		}
		if (type.isEnum()) {
			return true;
		}
		if (String.class.equals(type)) {
			return true;
		}
		return getFormatter(type)!=null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String format(Object value, Locale locale) {
		if (value==null) {
			return "null";
		}
		if (value instanceof String) {
			return (String)value;
		}
		Class<?> type = value.getClass();
		while (type!=null && !Object.class.equals(type)) {
			if (type.isArray()) {
				return arrayFormatter.format(value, locale);
			}
			if (type.isEnum()) {
				return enumFormatter.format((Enum<?>)value, locale);
			}
			Formatter<Object> formatter = (Formatter<Object>)getFormatter(type);
			if (formatter!=null) {
				return formatter.print(value, locale);
			}
			if (parent!=null) {
				return parent.format(value, locale);
			}
			type = type.getSuperclass();
		}
		throw new FormatterNotFoundException("For '" + value.getClass() + "'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(String text, Class<T> type, Locale locale) {
		if (text==null) {
			return null;
		}
		if (text.equals(NULL)) {
			return null;
		}
		if (type.isArray()) {
			return (T)arrayFormatter.parse(text, type, locale);
		}
		if (type.isEnum()) {
			return (T)enumFormatter.parse(text, type, locale);
		}
		if (Object.class.equals(type)) {
			return (T)text;
		}
		if (String.class.equals(type)) {
			return (T)text;
		}
		Formatter<Object> formatter = (Formatter<Object>)getFormatter(type);
		if (formatter!=null) {
			Object value = formatter.parse(text, locale);
			if (value instanceof String) {
				return (T)value;
			}
			return convert(value, type);
		}
		if (parent!=null) {
			return parent.parse(text, type, locale);
		}
		throw new FormatterNotFoundException("For '" + type + "'");
	}

	//
	//
	//
	
	@Override
	public boolean supportsFormat(Class<?> type, Annotation annotation) {
		return annotationFormatterMap.get(annotation.annotationType())!=null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String format(Object value, Annotation annotation, Locale locale) {
		if (value==null) {
			return "null";
		}
		if (value instanceof String) {
			return (String)value;
		}
		if (value.getClass().isArray()) {
			return arrayFormatter.format(value, locale);
		}

		AnnotationFormatter<Object,Annotation> formatter = 
				(AnnotationFormatter<Object,Annotation>)getAnnotationFormatter(annotation);

		if (formatter!=null) {
			return formatter.format(value, annotation, locale);
		}
		if (parent!=null) {
			return parent.format(value, annotation, locale);
		}
		throw new FormatterNotFoundException("For format annotation of type '" + annotation.annotationType() + "'");
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T> T parse(String text, Class<T> type, Annotation annotation, Locale locale) {
		if (text==null) {
			return null;
		}
		if (text.equals(NULL)) {
			return null;
		}
		if (type.isArray()) {
			return (T)arrayFormatter.parse(text, type, annotation, locale);
		}

		AnnotationFormatter<Object,Annotation> formatter = 
				(AnnotationFormatter<Object,Annotation>)getAnnotationFormatter(annotation);


		if (formatter!=null) {
			Object value = formatter.parse(text, annotation, locale);
			return convert(value, type);
		}
		if (parent!=null) {
			return parent.parse(text, type, annotation, locale);
		}
		throw new FormatterNotFoundException("For format annotation of type '" + annotation.annotationType() + "'");
	}




}
