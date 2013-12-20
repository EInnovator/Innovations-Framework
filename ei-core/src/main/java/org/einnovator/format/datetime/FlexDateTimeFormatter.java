package org.einnovator.format.datetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.einnovator.util.StringUtil;

public class FlexDateTimeFormatter extends DateTimeFormatter {

	private Map<Locale, DateFormat> slashes2Map, slashes1Map, slashes1Alpha3Map, slashes1AlphaMap,
	slashes2Alpha3Map, slashes2AlphaMap;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code FlexDateTimeFormatter}.
	 *
	 */
	public FlexDateTimeFormatter() {
		super();
	}

	/**
	 * Create instance of {@code FlexDateTimeFormatter}.
	 *
	 * @param pattern
	 */
	public FlexDateTimeFormatter(String pattern) {
		super(pattern);
	}

	//
	// DateTimeFormatter Overrides
	//

	@Override
	protected DateFormat getDateFormatForParsing(String text, Locale locale) {
		text = text.trim();
		if (text.isEmpty()) {
			return super.getDateFormat(locale);
		}
		if (locale==null) {
			locale = Locale.getDefault();
		}
		int nslashes = StringUtil.count(text, "/");
		if (nslashes==2)  {
			int i1 = text.indexOf("/");
			int i2 = text.indexOf("/", i1+1);
			if (Character.isAlphabetic(text.charAt(i1+1))) {
				if (i2-i1-1>3) {
					if (slashes2AlphaMap==null) {
						slashes2AlphaMap = new HashMap<Locale, DateFormat>();
					}
					DateFormat format = slashes2AlphaMap.get(locale);
					if (format==null) {
						format = new SimpleDateFormat("dd/MMMM/yyyy", locale);
						slashes2AlphaMap.put(locale, format);
					}
					return format;
				} else {
					if (slashes2Alpha3Map==null) {
						slashes2Alpha3Map = new HashMap<Locale, DateFormat>();
					}
					DateFormat format = slashes2Alpha3Map.get(locale);
					if (format==null) {
						format = new SimpleDateFormat("dd/MMM/yyyy", locale);
						slashes2Alpha3Map.put(locale, format);
					}
					return format;					
				}
			}
			if (slashes2Map==null) {
				slashes2Map = new HashMap<Locale, DateFormat>();
			}
			DateFormat format = slashes2Map.get(locale);
			if (format==null) {
				format = new SimpleDateFormat("dd/MM/yyyy", locale);
				slashes2Map.put(locale, format);
			}
			return format;
		} else if (nslashes==1)  {
			int i1 = text.indexOf("/");
			if (Character.isAlphabetic(text.charAt(i1+1))) {
				if (i1>3) {
					if (slashes1AlphaMap==null) {
						slashes1AlphaMap = new HashMap<Locale, DateFormat>();
					}
					DateFormat format = slashes1AlphaMap.get(locale);
					if (format==null) {
						format = new SimpleDateFormat("dd/MMMM/yyyy", locale);
						slashes1AlphaMap.put(locale, format);
					}
					return format;
				} else {
					if (slashes1Alpha3Map==null) {
						slashes1Alpha3Map = new HashMap<Locale, DateFormat>();
					}
					DateFormat format = slashes1Alpha3Map.get(locale);
					if (format==null) {
						format = new SimpleDateFormat("dd/MMM/yyyy", locale);
						slashes1Alpha3Map.put(locale, format);
					}
					return format;					
				}
			}

			if (slashes1Map==null) {
				slashes1Map = new HashMap<Locale, DateFormat>();
			}
			DateFormat format = slashes1Map.get(locale);
			if (format==null) {
				format = new SimpleDateFormat("MM/yyyy", locale);
				slashes1Map.put(locale, format);
			}
			return format;
		}
		return super.getDateFormat(locale);
	}

}
