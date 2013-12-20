package org.einnovator.format.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.einnovator.convert.ConversionException;
import org.einnovator.convert.TemporalStyle;
import org.einnovator.format.AnnotationFormatter;
import org.einnovator.format.datetime.DateTimeFormatter;

/**
 * A {@code DateTimeFormatter2}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class DateTimeFormatter2 extends DateTimeFormatter implements AnnotationFormatter<Date,org.einnovator.convert.DateFormat> {
	
	protected Map<org.einnovator.convert.DateFormat, DateFormat> annotationMap;
	
	/**
	 * Create instance of DateFormatter2.
	 *
	 */
	public DateTimeFormatter2() {
	}
	
	/**
	 * Create instance of {@code DateTimeFormatter}.
	 *
	 * @param pattern
	 */
	public DateTimeFormatter2(String pattern) {
		super(pattern);
	}
	
	//
	// AnnotationFormatter<Date> implementation 
	//

	@Override
	public String format(Date date, org.einnovator.convert.DateFormat formatAnnotation, Locale locale) {
		DateFormat dateFormat = getDateFormat(formatAnnotation, locale);
		return dateFormat.format(date);
	}
	

	@Override
	public Date parse(String text, org.einnovator.convert.DateFormat formatAnnotation, Locale locale) {
		DateFormat dateFormat = getDateFormat(formatAnnotation, locale);
		try {
			return dateFormat.parse(text);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}
	
	protected DateFormat getDateFormat(org.einnovator.convert.DateFormat formatAnnotation, Locale locale) {
		DateFormat format = null;
		if (annotationMap!=null) {
			format = annotationMap.get(formatAnnotation);
		}
		if (format==null) {
			format = createDateFormat(formatAnnotation, locale);
			if (annotationMap==null) {
				annotationMap = new HashMap<org.einnovator.convert.DateFormat, DateFormat>();
			}
			annotationMap.put(formatAnnotation, format);
		}
		return format;
	}

	protected DateFormat createDateFormat(org.einnovator.convert.DateFormat formatAnnotation, Locale locale) {
		if (!formatAnnotation.pattern().isEmpty()) {
			return createDateFormat(formatAnnotation.pattern(), -1, -1, locale);
		} else if (!formatAnnotation.style().isEmpty()) {
			return createDateFormat(formatAnnotation.style(), locale);
		}  else if (formatAnnotation.dateStyle()!=TemporalStyle.NONE ||
				formatAnnotation.timeStyle()!=TemporalStyle.NONE ) {
			return createDateFormat(formatAnnotation.dateStyle(), formatAnnotation.timeStyle(), locale);
		} else if (!formatAnnotation.name().isEmpty()) {
			DateFormat format = getNamedFormat(formatAnnotation.name());
			if (format==null) {
				throw new ConversionException("Unknown DateFormat name '" + formatAnnotation.name() + "'");
			}
			return format;
		} else {
			return createDateFormat(locale);
		}
	}
	

	
}
