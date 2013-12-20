/**
 * 
 */
package org.einnovator.format;

import java.util.Locale;

import org.einnovator.log.Level;
import org.einnovator.meta.ProjectionOptions;

/**
 * Utilities to format print objects.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FormatUtil {
	//public static PrinterBase fieldFormater = new ReflectivePrinter();
	
	public static ObjectPrinter printer;


	//
	// Getters and Setters
	//

	/**
	 * Set the value of property {@code printer}.
	 *
	 * @param printer the printer to set
	 */
	public static void setPrinter(ObjectPrinter printer) {
		FormatUtil.printer = printer;
	}

	/**
	 * Get the value of property {@code printer}.
	 *
	 * @return the printer
	 */
	public static ObjectPrinter getPrinter() {
		return printer;
	}

	/**
	 * Get the value of property {@code printer}.
	 *
	 * @return the printer
	 */
	public static ObjectPrinter getRequiredPrinter() {
		if (printer==null) {
			printer = new ObjectPrinter();
		}
		return printer;
	}

	
	//
	// Object format
	//

	public static String toString(Object obj) {
		return getRequiredPrinter().print(obj, (Locale)null);
	}

	public static String toString(Object obj, Level level) {
		return getRequiredPrinter().print(obj, new FormatContext().level(level));
	}
	
	public static String toString(Object obj, FormatContext context) {
		return getRequiredPrinter().print(obj, context);
	}

	public static String toString(Object obj, boolean inline) {
		return getRequiredPrinter().print(obj, (Locale)null);
	}

	public static String toString(Object obj, String... include) {
		return getRequiredPrinter().print(obj, (Locale)null, true, include, null);
	}
	
	public static String toString(Object obj, boolean complete, String... include) {
		return getRequiredPrinter().print(obj, (Locale)null, complete, include, null);
	}

	public static String toString(Object obj, String[] include, String[] exclude) {
		return getRequiredPrinter().print(obj, (Locale)null, true, include, exclude);
	}
	
	public static String toString(Object obj, ProjectionOptions options) {
		return getRequiredPrinter().print(obj, (Locale)null, options);
	}

}
