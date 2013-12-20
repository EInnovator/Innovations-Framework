/**
 * 
 */
package org.einnovator.meta;

import org.einnovator.meta.MetaException;

/**
 * AA AssignException.
 *
 * @author Jorge Simão
 */
public class AssignException extends MetaException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create instance of AssignException.
	 *
	 */
	public AssignException() {
		super();
	}

	/**
	 * Create instance of AssignException.
	 *
	 * @param message
	 * @param cause
	 */
	public AssignException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create instance of AssignException.
	 *
	 * @param message
	 */
	public AssignException(String message) {
		super(message);
	}

	/**
	 * Create instance of AssignException.
	 *
	 * @param cause
	 */
	public AssignException(Throwable cause) {
		super(cause);
	}

	
}
