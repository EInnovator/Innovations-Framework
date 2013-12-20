/**
 * 
 */
package org.einnovator.util;

/**
 * AA ClassNotFoundException.
 *
 * @author Jorge Simão
 */
public class ClassNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of ClassNotFoundException.
	 *
	 */
	public ClassNotFoundException() {
		super();
	}

	/**
	 * Create instance of ClassNotFoundException.
	 *
	 * @param message
	 * @param cause
	 */
	public ClassNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of ClassNotFoundException.
	 *
	 * @param message
	 */
	public ClassNotFoundException(String message) {
		super(message);
	}

	/**
	 * Create instance of ClassNotFoundException.
	 *
	 * @param cause
	 */
	public ClassNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
