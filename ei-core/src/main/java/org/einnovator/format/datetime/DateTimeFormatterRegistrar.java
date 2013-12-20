/**
 * 
 */
package org.einnovator.format.datetime;

import java.util.Date;

import org.einnovator.convert.DateFormat;
import org.einnovator.format.AnnotationFormatter;
import org.einnovator.format.AnnotationFormatterRegistry;
import org.einnovator.format.Formatter;
import org.einnovator.format.FormatterRegistrar2;
import org.einnovator.format.FormatterRegistry;


/**
 * A DateFormatterRegistrar.
 *
 * @author Jorge Simão
 */
public class DateTimeFormatterRegistrar implements FormatterRegistrar2 {

	protected boolean namedFormats;
	
	/**
	 * Create instance of DateTimeFormatterRegistrar.
	 *
	 */
	public DateTimeFormatterRegistrar() {
	}

	/**
	 * Create instance of DateTimeFormatterRegistrar.
	 *
	 * @param namedFormats
	 */
	public DateTimeFormatterRegistrar(boolean namedFormats) {
		this.namedFormats = namedFormats;
	}
	
	//
	// Getters and setters
	//
	
	/**
	 * Get the value of namedFormats.
	 *
	 * @return the namedFormats
	 */
	public boolean isNamedFormats() {
		return namedFormats;
	}
	
	/**
	 * Set the value of namedFormats.
	 *
	 * @param namedFormats the namedFormats to set
	 */
	public void setNamedFormats(boolean namedFormats) {
		this.namedFormats = namedFormats;
	}
	
	//
	// FormatterRegistrar implementation
	//
	
	@Override
	public void registerFormatters(FormatterRegistry registry) {
		DateTimeFormatter2 formatter = new DateTimeFormatter2();
		registerFormatter(formatter, registry);
	}

	@Override
	public void registerFormatter(Formatter<?> formatter, FormatterRegistry registry) {
		if (namedFormats) {
			DateFormatterUtil.addCommonFormats((DateTimeFormatter)formatter);
		}			

		registry.addFormatter(formatter, Date.class);
		registry.addFormatter(formatter, java.sql.Date.class);
		registry.addFormatter(formatter, java.sql.Timestamp.class);
		
		if (registry instanceof AnnotationFormatterRegistry &&
				formatter instanceof AnnotationFormatter) {
			AnnotationFormatterRegistry registry2 = (AnnotationFormatterRegistry)registry;
			registry2.addAnnotationFormatter((AnnotationFormatter<?,?>)formatter, DateFormat.class);
		}

	}

}
