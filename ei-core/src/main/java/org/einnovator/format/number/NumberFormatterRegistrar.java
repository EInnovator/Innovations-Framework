/**
 * 
 */
package org.einnovator.format.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;

import org.einnovator.convert.NumberFormat;
import org.einnovator.format.AnnotationFormatter;
import org.einnovator.format.AnnotationFormatterRegistry;
import org.einnovator.format.Formatter;
import org.einnovator.format.FormatterRegistrar2;
import org.einnovator.format.FormatterRegistry;


/**
 * A NumberFormatterRegistrar.
 *
 * @author Jorge Simão
 */
public class NumberFormatterRegistrar implements FormatterRegistrar2 {

	@Override
	public void registerFormatters(FormatterRegistry registry) {
		NumberFormatter2 formatter = new NumberFormatter2();
		registerFormatter(formatter, registry);
		
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		registry.addFormatter(currencyFormatter, Currency.class);
	}
		
	@Override
	public void registerFormatter(Formatter<?> formatter, FormatterRegistry registry) {	
		registry.addFormatter(formatter, Number.class);
		registry.addFormatter(formatter, Integer.class);
		registry.addFormatter(formatter, Integer.TYPE);
		registry.addFormatter(formatter, Long.class);
		registry.addFormatter(formatter, Long.TYPE);
		registry.addFormatter(formatter, Short.class);
		registry.addFormatter(formatter, Short.TYPE);
		registry.addFormatter(formatter, Byte.class);
		registry.addFormatter(formatter, Byte.TYPE);		
		registry.addFormatter(formatter, BigInteger.class);		

		registry.addFormatter(formatter, Double.class);
		registry.addFormatter(formatter, Double.TYPE);
		registry.addFormatter(formatter, Float.class);
		registry.addFormatter(formatter, Float.TYPE);
		registry.addFormatter(formatter, BigDecimal.class);

		if (registry instanceof AnnotationFormatterRegistry &&
				formatter instanceof AnnotationFormatter) {
			AnnotationFormatterRegistry registry2 = (AnnotationFormatterRegistry)registry;
			registry2.addAnnotationFormatter((AnnotationFormatter<?,?>)formatter, NumberFormat.class);
		}

	}

}
