/**
 * 
 */
package org.einnovator.convert;

/**
 * AA ConversionException.
 *
 * @author Jorge Simão
 */
public class ConversionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of ConversionException.
	 *
	 */
	public ConversionException() {
		super();
	}

	/**
	 * Create instance of ConversionException.
	 *
	 * @param message
	 * @param cause
	 */
	public ConversionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Create instance of ConversionException.
	 *
	 * @param message
	 */
	public ConversionException(String message) {
		super(message);
	}

	/**
	 * Create instance of ConversionException.
	 *
	 * @param cause
	 */
	public ConversionException(Throwable cause) {
		super(cause);
	}

}
