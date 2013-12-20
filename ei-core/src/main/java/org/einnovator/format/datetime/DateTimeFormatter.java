package org.einnovator.format.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.einnovator.convert.ConversionException;
import org.einnovator.convert.TemporalStyle;
import org.einnovator.format.Formatter;



/**
 * A {@code DateTimeFormatter}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class DateTimeFormatter implements Formatter<Date> {
	public static final int DEFAULT_DATE_STYLE = DateFormat.DEFAULT;

	public static final int DEFAULT_TIME_STYLE = DateFormat.DEFAULT;
	
	protected int dateStyle = DEFAULT_DATE_STYLE;
	
	protected int timeStyle = DEFAULT_TIME_STYLE;
	
	protected String pattern;

	protected Map<Locale, DateFormat> localeMap;
	
	protected Map<String, DateFormat> formatMap;

	//
	// Constructors
	//
	
	/**
	 * Create instance of DateTimeTextConverter.
	 *
	 */
	public DateTimeFormatter() {
	}
	
	/**
	 * Create instance of {@code DateTimeFormatter}.
	 *
	 * @param pattern
	 */
	public DateTimeFormatter(String pattern) {
		this.pattern = pattern;
	}
	
	//
	// Getters and setters
	//
	
	/**
	 * Get the value of dateStyle.
	 *
	 * @return the dateStyle
	 */
	public int getDateStyle() {
		return dateStyle;
	}

	/**
	 * Set the value of dateStyle.
	 *
	 * @param dateStyle the dateStyle to set
	 */
	public void setDateStyle(int dateStyle) {
		this.dateStyle = dateStyle;
	}

	/**
	 * Get the value of timeStyle.
	 *
	 * @return the timeStyle
	 */
	public int getTimeStyle() {
		return timeStyle;
	}

	/**
	 * Set the value of timeStyle.
	 *
	 * @param timeStyle the timeStyle to set
	 */
	public void setTimeStyle(int timeStyle) {
		this.timeStyle = timeStyle;
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
	 * Get the value of formatMap.
	 *
	 * @return the formatMap
	 */
	public Map<String, DateFormat> getFormatMap() {
		return formatMap;
	}

	/**
	 * Set the value of formatMap.
	 *
	 * @param formatMap the formatMap to set
	 */
	public void setFormatMap(Map<String, DateFormat> formatMap) {
		this.formatMap = formatMap;
	}


	public void setTimeStyle(TemporalStyle timeStyle) {
		this.timeStyle = timeStyle!=null ? timeStyle.mapToDateFormat() : -1;
	}

	public void setDateStyle(TemporalStyle dateStyle) {
		this.dateStyle = dateStyle!=null ? dateStyle.mapToDateFormat() : -1;
	}

	public void setStyle(String style) {
		style = style.trim();
		int dateStyle = parseStyle(style.substring(0,1));
		int timeStyle = parseStyle(style.substring(1));
		if (dateStyle==-2 || timeStyle==-2 || (dateStyle<0 && timeStyle<0)) {
			throw new ConversionException("Invalid format style '" + style + "' expected '[SLMFx][SLM]??'");
		}
		this.dateStyle = dateStyle;
		this.timeStyle = timeStyle;
	}

	//
	// DateFormatt map
	//
	
	/**
	 * Set the value of formatMap.
	 *
	 * @param formatMap the formatMap to set
	 */
	public void addNamedFormat(String name, DateFormat format) {
		if (formatMap==null) {
			formatMap = new HashMap<String, DateFormat>();
		}
		formatMap.put(name, format);
	}

	public DateFormat getNamedFormat(String name) {
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
	// Formatter<Date> 
	//

	@Override
	public String print(Date date, Locale locale) {
		DateFormat dateFormat = getDateFormat(locale);
		return dateFormat.format(date);
	}

	@Override
	public Date parse(String text, Locale locale) {
		DateFormat dateFormat = getDateFormatForParsing(text, locale);
		try {
			return dateFormat.parse(text);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
	}

	protected DateFormat getDateFormatForParsing(String text, Locale locale) {
		return getDateFormat(locale);
	}
	
	protected DateFormat getDateFormat(Locale locale) {
		DateFormat format = null;
		if (localeMap!=null) {
			format = localeMap.get(locale);
		}
		if (format==null) {
			format = createDateFormat(locale);
			if (localeMap==null) {
				localeMap = new HashMap<Locale, DateFormat>();
			}
			localeMap.put(locale, format);
		}
		return format;
	}
	
	protected DateFormat createDateFormat(Locale locale) {
		return createRequiredDateFormat(pattern, dateStyle, timeStyle, locale);
	}

	//
	// Static utility
	//
	
	public static DateFormat createRequiredDateFormat(String pattern, int dateStyle, int timeStyle, Locale locale) {
		DateFormat format = createDateFormat(pattern, dateStyle, timeStyle, locale);
		if (format!=null) {
			return format;
		}
		return DateFormat.getDateTimeInstance();
	}
		
	public static DateFormat createDateFormat(String pattern, int dateStyle, int timeStyle, Locale locale) {
		if (pattern!=null && !pattern.isEmpty()) {
			return locale!=null ? new SimpleDateFormat(pattern, locale) : new SimpleDateFormat(pattern);
		}
		return createDateFormat(dateStyle, timeStyle, locale);
	}

	public static DateFormat createDateFormat(String pattern, TemporalStyle dateStyle, TemporalStyle timeStyle, String dateTimeStyle, Locale locale) {
		DateFormat dateFormat = createDateFormat(pattern,  dateStyle, timeStyle, locale);
		if (dateFormat!=null) {
			return dateFormat;
		}
		return createDateFormat(dateTimeStyle, locale);
	}

	public static DateFormat createDateFormat(String pattern, TemporalStyle dateStyle, TemporalStyle timeStyle, Locale locale) {
		if (pattern!=null && !pattern.isEmpty()) {
			return locale!=null ? new SimpleDateFormat(pattern, locale) : new SimpleDateFormat(pattern);
		}
		return createDateFormat(dateStyle, timeStyle, locale);
	}

	public static DateFormat createDateFormat(int dateStyle, int timeStyle, Locale locale) {
		if (locale==null) {
			locale = Locale.getDefault();
		}
		if (dateStyle>=0 && timeStyle>=0) {
			return DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
		}
		if (dateStyle>=0) {
			return DateFormat.getDateInstance(dateStyle, locale);						
		}
		if (timeStyle>=0){
			return DateFormat.getTimeInstance(timeStyle, locale);
		}
		return null;
	}
	
	public static DateFormat createDateFormat(TemporalStyle dateStyle, TemporalStyle timeStyle, Locale locale) {
		return createDateFormat(null,  dateStyle!=null ? dateStyle.mapToDateFormat() : -1, 
				timeStyle!=null ? timeStyle.mapToDateFormat() : -1, locale);
	}
	
		
	public static DateFormat createDateFormat(String style, Locale locale) {
		style = style.trim();
		int dateStyle = parseStyle(style.substring(0,1));
		int timeStyle = parseStyle(style.substring(1));
		if (dateStyle==-2 || timeStyle==-2 || (dateStyle<0 && timeStyle<0)) {
			throw new ConversionException("Invalid format style '" + style + "' expected '[SLMFx][SLM]??'");
		}
		return createDateFormat(null, dateStyle, timeStyle, locale);		
	}

	public static int parseStyle(String s) {
		if (s.isEmpty()) {
			return -1;
		}
		if ("S".equalsIgnoreCase(s)) {
			return DateFormat.SHORT;
		}
		if ("L".equalsIgnoreCase(s)) {
			return DateFormat.LONG;
		}
		if ("M".equalsIgnoreCase(s)) {
			return DateFormat.MEDIUM;
		}
		if ("F".equalsIgnoreCase(s)) {
			return DateFormat.FULL;
		}
		if ("x".equalsIgnoreCase(s)) {
			return -1;
		}
		return -2;
	}

}
