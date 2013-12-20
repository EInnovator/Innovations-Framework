/**
 * 
 */
package org.einnovator.meta;

/**
 * A NoSuchFieldException.
 *
 * @author Jorge Simão
 */
public class NoSuchFieldException extends MetaException {
	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of NoSuchFieldException.
	 *
	 */
	public NoSuchFieldException() {
		super();
	}

	/**
	 * Create instance of NoSuchFieldException.
	 *
	 * @param message
	 * @param cause
	 */
	public NoSuchFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of NoSuchFieldException.
	 *
	 * @param message
	 */
	public NoSuchFieldException(String message) {
		super(message);
	}

	/**
	 * Create instance of NoSuchFieldException.
	 *
	 * @param cause
	 */
	public NoSuchFieldException(Throwable cause) {
		super(cause);
	}
	

}
