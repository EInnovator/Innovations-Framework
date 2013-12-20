/**
 * 
 */
package org.einnovator.meta;

/**
 * A NoSuchMethodException.
 *
 * @author Jorge Simão
 */
public class NoSuchMethodException extends MetaException {
	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of NoSuchMethodException.
	 *
	 */
	public NoSuchMethodException() {
		super();
	}

	/**
	 * Create instance of NoSuchMethodException.
	 *
	 * @param message
	 * @param cause
	 */
	public NoSuchMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of NoSuchMethodException.
	 *
	 * @param message
	 */
	public NoSuchMethodException(String message) {
		super(message);
	}

	/**
	 * Create instance of NoSuchMethodException.
	 *
	 * @param cause
	 */
	public NoSuchMethodException(Throwable cause) {
		super(cause);
	}
	

}
