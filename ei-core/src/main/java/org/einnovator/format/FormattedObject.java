package org.einnovator.format;

/**
 * An object that implements a custom {@code toString()} method sensitive to a {@code FormatContext}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see ObjectPrinter
 */
public interface FormattedObject {

	/**
	 * Get the string representation of this object.
	 * 
	 * @param context the {@code FormatContext}
	 * @return the string representation of this object
	 */
	String toString(FormatContext context);
}
