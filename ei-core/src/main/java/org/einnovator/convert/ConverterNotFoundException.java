/**
 * 
 */
package org.einnovator.convert;

/**
 * AA ConverterNotFoundException.
 *
 * @author Jorge Simão
 */
public class ConverterNotFoundException extends ConversionException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of ConverterNotFoundException.
	 *
	 */
	public ConverterNotFoundException() {
		super();
	}

	/**
	 * Create instance of ConverterNotFoundException.
	 *
	 * @param message
	 * @param cause
	 */
	public ConverterNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of ConverterNotFoundException.
	 *
	 * @param message
	 */
	public ConverterNotFoundException(String message) {
		super(message);
	}

	/**
	 * Create instance of ConverterNotFoundException.
	 *
	 * @param cause
	 */
	public ConverterNotFoundException(Throwable cause) {
		super(cause);
	}

}
