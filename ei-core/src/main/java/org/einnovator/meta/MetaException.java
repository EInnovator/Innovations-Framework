/**
 * 
 */
package org.einnovator.meta;

/**
 * A MetaException.
 *
 * @author Jorge Simão
 */
public class MetaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of MetaException.
	 *
	 */
	public MetaException() {
		super();
	}

	/**
	 * Create instance of MetaException.
	 *
	 * @param message
	 * @param cause
	 */
	public MetaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of MetaException.
	 *
	 * @param message
	 */
	public MetaException(String message) {
		super(message);
	}

	/**
	 * Create instance of MetaException.
	 *
	 * @param cause
	 */
	public MetaException(Throwable cause) {
		super(cause);
	}

	
}
