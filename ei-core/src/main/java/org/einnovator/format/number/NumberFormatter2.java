package org.einnovator.format.number;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.einnovator.convert.ConversionException;
import org.einnovator.format.AnnotationFormatter;


/**
 * A NumberFormatter2.
 *
 * @author Jorge Simão
 */
public class NumberFormatter2 extends NumberFormatter implements AnnotationFormatter<Number, org.einnovator.convert.NumberFormat> {

	protected Map<org.einnovator.convert.NumberFormat, NumberFormat> annotationMap;
	
	/**
	 * Create instance of NumberFormatter2.
	 *
	 */
	public NumberFormatter2() {
	}
	


	//
	// AnnotationFormatter<Number> implementation 
	//

	@Override
	public String format(Number value, org.einnovator.convert.NumberFormat formatAnnotation, Locale locale) {
		NumberFormat dateFormat = getNumberFormat(formatAnnotation, locale);
		return dateFormat.format(value);
	}
	
	@Override
	public Number parse(String text, org.einnovator.convert.NumberFormat formatAnnotation, Locale locale) {
		NumberFormat dateFormat = getNumberFormat(formatAnnotation, locale);
		try {
			return dateFormat.parse(text);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}

	protected NumberFormat getNumberFormat(org.einnovator.convert.NumberFormat formatAnnotation, Locale locale) {
		NumberFormat format = null;
		if (annotationMap!=null) {
			format = annotationMap.get(formatAnnotation);
		}
		if (format==null) {
			format = createNumberFormat(formatAnnotation, locale);
			if (annotationMap==null) {
				annotationMap = new HashMap<org.einnovator.convert.NumberFormat, NumberFormat>();
			}
			annotationMap.put(formatAnnotation, format);
		}
		return format;
	}

	public NumberFormat createNumberFormat(org.einnovator.convert.NumberFormat annotation, Locale locale) {
		String pattern = !annotation.pattern().isEmpty() ? annotation.pattern() : this.pattern;
		NumberFormat format = createNumberFormat(annotation.type(), pattern, annotation.integerOnly(), locale);
		Currency currency = !annotation.currency().isEmpty() ?
				getCurrency(annotation.currency()): this.currency;
		int minFractionDigits = annotation.minFractionDigits()>=0 ? 
				annotation.minFractionDigits() : this.minFractionDigits;
		int maxFractionDigits = annotation.maxFractionDigits()>=0 ? 
				annotation.maxFractionDigits() : this.maxFractionDigits;
		int minIntegerDigits = annotation.minIntegerDigits()>=0 ? 
				annotation.minIntegerDigits() : this.minIntegerDigits;
		int maxIntegerDigits = annotation.maxIntegerDigits()>=0 ? 
			annotation.maxIntegerDigits() : this.maxIntegerDigits;
		configNumberFormat(format, currency, minFractionDigits, maxFractionDigits, minIntegerDigits, maxIntegerDigits, 
				annotation.grouping(), annotation.roundingMode());
		return format;
	}
	

	private static Map<String, Currency> currencyMap = new HashMap<String, Currency>();
	
	public static Currency getCurrency(String currencyCode) {
		Currency currency = currencyMap.get(currencyCode);
		if (currency==null) {
			currency = Currency.getInstance(currencyCode);
			currencyMap.put(currencyCode, currency);
		}
		return currency;
	}
}
