/**
 * 
 */
package org.einnovator.convert;

import java.text.DateFormat;

/**
 * A TemporalStyle.
 *
 * @author Jorge Simão
 */
public enum TemporalStyle {
	SHORT,
	MEDIUM,
	LONG,
	FULL,
	NONE,
	DEFAULT;
	
	public int mapToDateFormat() {
		switch(this) {
		case DEFAULT:
			return DateFormat.DEFAULT;
		case FULL:
			return DateFormat.FULL;
		case LONG:
			return DateFormat.LONG;
		case MEDIUM:
			return DateFormat.MEDIUM;
		case SHORT:
			return DateFormat.SHORT;
		case NONE:
		default:
			return -1;
		}
	}
	
	/**
	 * Create instance of TemporalStyle.
	 *
	 */
	public static TemporalStyle createForDateFormat(int value) {
		switch(value) {
		case DateFormat.FULL:
			return FULL;
		case DateFormat.LONG:
			return LONG;
		//case DateFormat.DEFAULT: //duplicate case
		case DateFormat.MEDIUM:
			return MEDIUM;
		case DateFormat.SHORT:
			return SHORT;
		default:
			return NONE;
		}
	}

}
