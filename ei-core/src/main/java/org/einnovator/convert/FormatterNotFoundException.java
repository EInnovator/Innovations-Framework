/**
 * 
 */
package org.einnovator.convert;

/**
 * A FormatterNotFoundException.
 *
 * @author Jorge Simão
 */
public class FormatterNotFoundException extends ConversionException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of FormatterNotFoundException.
	 *
	 */
	public FormatterNotFoundException() {
		super();
	}

	/**
	 * Create instance of FormatterNotFoundException.
	 *
	 * @param message
	 * @param cause
	 */
	public FormatterNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of FormatterNotFoundException.
	 *
	 * @param message
	 */
	public FormatterNotFoundException(String message) {
		super(message);
	}

	/**
	 * Create instance of FormatterNotFoundException.
	 *
	 * @param cause
	 */
	public FormatterNotFoundException(Throwable cause) {
		super(cause);
	}

}
