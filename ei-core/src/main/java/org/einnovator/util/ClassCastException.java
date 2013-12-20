/**
 * 
 */
package org.einnovator.util;

/**
 * AA ClassCastException.
 *
 * @author Jorge Simão
 */
public class ClassCastException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of ClassCastException.
	 *
	 */
	public ClassCastException() {
		super();
	}

	/**
	 * Create instance of ClassCastException.
	 *
	 * @param message
	 * @param cause
	 */
	public ClassCastException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of ClassCastException.
	 *
	 * @param message
	 */
	public ClassCastException(String message) {
		super(message);
	}

	/**
	 * Create instance of ClassCastException.
	 *
	 * @param cause
	 */
	public ClassCastException(Throwable cause) {
		super(cause);
	}

	
}
