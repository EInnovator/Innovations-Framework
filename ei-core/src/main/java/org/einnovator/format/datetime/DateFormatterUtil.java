/**
 * 
 */
package org.einnovator.format.datetime;

import java.util.HashMap;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.einnovator.format.datetime.DateTimeFormatter;



/**
 * A DateFormatterUtil.
 *
 * @author Jorge Simão
 */
public class DateFormatterUtil {

	public static final String ISO_TIME_FORMAT = "hh:mm:ss.SSSZ";

	public static final String ISO_TIME_NAME = "ISOt";

	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

	public static final String ISO_DATE_NAME = "ISOd";
	
	public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'Date'hh:mm:ss.SSSZ";

	public static final String ISO_DATE_TIME_NAME = "ISO";

	public static final String TIME_HMS_FORMAT = "hh:mm:ss";

	public static final String TIME_HMS_NAME = "HMS";
	
	public static final String DATE_DMY_FORMAT = "dd-MM-yyyy";

	public static final String DATE_DMY_NAME = "DMY";
	
	public static final String DATE_YMD_TIME_FORMAT = "yyyy-MM-dd'Date'hh:mm:ss";

	public static final String DATE_YMD_TIME_NAME = "YMT";
	

	public static DateTimeFormatter addCommonFormats(DateTimeFormatter dateFormatter) {
		return addExtraFormats(addISOFormats(dateFormatter));
	}

	public static DateTimeFormatter addISOFormats(DateTimeFormatter dateFormatter) {
		Map<String, DateFormat> formatMap = new HashMap<String, DateFormat>();
		addISOFormats(formatMap);
		dateFormatter.setFormatMap(formatMap);
		return dateFormatter;
	}

	public static DateTimeFormatter addExtraFormats(DateTimeFormatter dateFormatter) {
		Map<String, DateFormat> formatMap = new HashMap<String, DateFormat>();
		addExtraFormats(formatMap);
		dateFormatter.setFormatMap(formatMap);
		return dateFormatter;
	}

	public static void addCommonFormats(Map<String, DateFormat> formatterMap) {
		addISOFormats(formatterMap);
		addExtraFormats(formatterMap);
	}
	
	
	public static void addISOFormats(Map<String, DateFormat> formatterMap) {
		addFormat(formatterMap, ISO_DATE_NAME, ISO_DATE_FORMAT);
		addFormat(formatterMap, ISO_TIME_NAME, ISO_TIME_FORMAT);
		addFormat(formatterMap, ISO_DATE_TIME_NAME, ISO_DATE_TIME_FORMAT);
	}
	
	public static void addExtraFormats(Map<String, DateFormat> formatterMap) {
		addFormat(formatterMap, TIME_HMS_NAME, TIME_HMS_FORMAT);
		addFormat(formatterMap, DATE_DMY_NAME, DATE_DMY_FORMAT);
		addFormat(formatterMap, DATE_YMD_TIME_FORMAT, DATE_YMD_TIME_FORMAT);
	}
	
	private static void addFormat(Map<String, DateFormat> formatterMap,
			String name, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		formatterMap.put(name, format);		
	}

}
