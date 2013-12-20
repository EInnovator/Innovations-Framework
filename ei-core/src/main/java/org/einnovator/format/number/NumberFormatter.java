package org.einnovator.format.number;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.einnovator.convert.NumberType;
import org.einnovator.format.Formatter;


/**
 * A NumberFormatter.
 *
 * @author Jorge Simão
 */
public class NumberFormatter implements Formatter<Number> {

	protected NumberType type = NumberType.NUMBER;

	protected String pattern;
		
	protected Currency currency;
	
	protected int maxFractionDigits = -1;
	
	protected int minFractionDigits = -1;

	protected int maxIntegerDigits = -1;
	
	protected int minIntegerDigits = -1;

	protected boolean integerOnly;

	protected Boolean grouping;
	
	protected RoundingMode roundingMode;

	protected Map<Locale, NumberFormat> localeMap;
	
	protected Map<String, NumberFormat> formatMap;


	/**
	 * Create instance of NumberFormatter.
	 *
	 */
	public NumberFormatter() {
	}

	/**
	 * Create instance of NumberFormatter.
	 *
	 * @param type
	 */
	public NumberFormatter(NumberType type) {
		this.type = type;
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of type.
	 *
	 * @return the type
	 */
	public NumberType getType() {
		return type;
	}

	/**
	 * Set the value of type.
	 *
	 * @param type the type to set
	 */
	public void setType(NumberType type) {
		this.type = type;
	}

	/**
	 * Get the value of pattern.
	 *
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Set the value of pattern.
	 *
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * Get the value of currency.
	 *
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Set the value of currency.
	 *
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * Get the value of grouping.
	 *
	 * @return the grouping
	 */
	public Boolean getGrouping() {
		return grouping;
	}

	/**
	 * Set the value of grouping.
	 *
	 * @param grouping the grouping to set
	 */
	public void setGrouping(Boolean grouping) {
		this.grouping = grouping;
	}

	/**
	 * Get the value of maxFractionDigits.
	 *
	 * @return the maxFractionDigits
	 */
	public int getMaxFractionDigits() {
		return maxFractionDigits;
	}

	/**
	 * Set the value of maxFractionDigits.
	 *
	 * @param maxFractionDigits the maxFractionDigits to set
	 */
	public void setMaxFractionDigits(int maxFractionDigits) {
		this.maxFractionDigits = maxFractionDigits;
	}

	/**
	 * Get the value of minFractionDigits.
	 *
	 * @return the minFractionDigits
	 */
	public int getMinFractionDigits() {
		return minFractionDigits;
	}

	/**
	 * Set the value of minFractionDigits.
	 *
	 * @param minFractionDigits the minFractionDigits to set
	 */
	public void setMinFractionDigits(int minFractionDigits) {
		this.minFractionDigits = minFractionDigits;
	}

	/**
	 * Get the value of maxIntegerDigits.
	 *
	 * @return the maxIntegerDigits
	 */
	public int getMaxIntegerDigits() {
		return maxIntegerDigits;
	}

	/**
	 * Set the value of maxIntegerDigits.
	 *
	 * @param maxIntegerDigits the maxIntegerDigits to set
	 */
	public void setMaxIntegerDigits(int maxIntegerDigits) {
		this.maxIntegerDigits = maxIntegerDigits;
	}

	/**
	 * Get the value of minIntegerDigits.
	 *
	 * @return the minIntegerDigits
	 */
	public int getMinIntegerDigits() {
		return minIntegerDigits;
	}

	/**
	 * Set the value of minIntegerDigits.
	 *
	 * @param minIntegerDigits the minIntegerDigits to set
	 */
	public void setMinIntegerDigits(int minIntegerDigits) {
		this.minIntegerDigits = minIntegerDigits;
	}
	

	/**
	 * Get the value of integerOnly.
	 *
	 * @return the integerOnly
	 */
	public boolean isIntegerOnly() {
		return integerOnly;
	}

	/**
	 * Set the value of integerOnly.
	 *
	 * @param integerOnly the integerOnly to set
	 */
	public void setIntegerOnly(boolean integerOnly) {
		this.integerOnly = integerOnly;
	}

	/**
	 * Get the value of roundingMode.
	 *
	 * @return the roundingMode
	 */
	public RoundingMode getRoundingMode() {
		return roundingMode;
	}

	/**
	 * Set the value of roundingMode.
	 *
	 * @param roundingMode the roundingMode to set
	 */
	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}

	/**
	 * Get the value of formatMap.
	 *
	 * @return the formatMap
	 */
	public Map<String, NumberFormat> getFormatMap() {
		return formatMap;
	}

	/**
	 * Set the value of formatMap.
	 *
	 * @param formatMap the formatMap to set
	 */
	public void setFormatMap(Map<String, NumberFormat> formatMap) {
		this.formatMap = formatMap;
	}
	
	//
	// NumberFormat map
	//
	
	/**
	 * Set the value of formatMap.
	 *
	 * @param formatMap the formatMap to set
	 */
	public void addNamedFormat(String name, NumberFormat format) {
		if (formatMap==null) {
			formatMap = new HashMap<String, NumberFormat>();
		}
		formatMap.put(name, format);
	}

	public NumberFormat getNamedFormat(String name) {
		return formatMap!=null ? formatMap.get(name) : null;
	}


	public void clearLocaleCache() {
		if (localeMap!=null) {
			localeMap.clear();
		}
	}
	
	public void clearNamedFormats() {
		if (formatMap!=null) {
			formatMap.clear();
		}
	}

	//
	// Formatter<Number> implementation 
	//

	
	@Override
	public String print(Number value, Locale locale) {
		NumberFormat dateFormat = getNumberFormat(locale);
		return dateFormat.format(value);
	}

	@Override
	public Number parse(String text, Locale locale) {
		NumberFormat dateFormat = getNumberFormat(locale);
		try {
			return dateFormat.parse(text);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public NumberFormat getNumberFormat(Locale locale) {
		NumberFormat format = null;
		if (localeMap!=null) {
			format = localeMap.get(locale);
		}
		if (format==null) {
			format = createNumberFormat(locale);
			if (localeMap==null) {
				localeMap = new HashMap<Locale, NumberFormat>();
			}
			localeMap.put(locale, format);
		}
		return format;
	}

	public NumberFormat createNumberFormat(Locale locale) {
		NumberFormat format = createNumberFormat(type, pattern, integerOnly, locale);
		configNumberFormat(format);
		return format;
	}

	public void configNumberFormat(NumberFormat format) {
		configNumberFormat(format, currency, minFractionDigits, maxFractionDigits, minIntegerDigits, maxIntegerDigits, grouping, roundingMode);		
	}

	//
	// Static utility
	//


	public static void configNumberFormat(NumberFormat format,
			Currency currency,
			int minFractionDigits, int maxFractionDigits,
			int minIntegerDigits, int maxIntegerDigits, 
			Boolean grouping, RoundingMode roundingMode) {
		if (currency!=null) {
			format.setCurrency(currency);
		}
		if (minFractionDigits>=0) {
			format.setMaximumFractionDigits(minFractionDigits);
		}
		if (maxFractionDigits>=0) {
			format.setMaximumFractionDigits(maxFractionDigits);
		}
		if (minIntegerDigits>=0) {
			format.setMaximumIntegerDigits(minIntegerDigits);
		}
		if (maxIntegerDigits>=0) {
			format.setMaximumIntegerDigits(maxIntegerDigits);
		}
		if (grouping!=null) {
			format.setGroupingUsed(grouping.booleanValue());
		}
		if (roundingMode!=null) {
			format.setRoundingMode(roundingMode);
		}
	}

	public static NumberFormat createNumberFormat(NumberType type, String pattern, boolean integerOnly, Locale locale) {
		NumberFormat format = createNumberFormat(type, integerOnly, locale);
		if (pattern!=null && !pattern.isEmpty()) { 
			if (format instanceof DecimalFormat) {
				((DecimalFormat)format).applyPattern(pattern);
			} else {
				DecimalFormat format2 = new DecimalFormat(pattern);
				return format2;
			}
		}
		return format;
	}

	public static NumberFormat createNumberFormat(NumberType type, boolean integerOnly, Locale locale) {
		if (locale==null) {
			locale = Locale.getDefault();
		}
		switch (type) {
			case NUMBER:
			default:
				if (integerOnly) {
					return NumberFormat.getIntegerInstance(locale);					
				} else {
					return NumberFormat.getInstance(locale);
				}
			case CURRENCY:
				return NumberFormat.getCurrencyInstance(locale);
			case PERCENTAGE:
				return NumberFormat.getPercentInstance(locale);
		}
	}
	
}
