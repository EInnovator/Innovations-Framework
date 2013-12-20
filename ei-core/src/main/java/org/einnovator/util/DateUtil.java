package org.einnovator.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static int diffMillis(Date dateA, Date dateB) {
		Calendar a = Calendar.getInstance();
		a.setTime(dateA);
		Calendar b = Calendar.getInstance();
		b.setTime(dateB);
		return a.compareTo(b);
	}

	public static int diffSec(Date dateA, Date dateB) {
		return diffMillis(dateA, dateB) / (1000);
	}
	
	public static int diffMin(Date dateA, Date dateB) {
		return diffMillis(dateA, dateB) / (1000*60);
	}

	public static int diffHour(Date dateA, Date dateB) {
		return diffMillis(dateA, dateB) / (1000*3600);
	}
	
	public static int diffDay(Date dateA, Date dateB) {
		return diffMillis(dateA, dateB) / (1000*24*3600);
	}
	
}
