package org.einnovator.resource;

/**
 * A {@code ResourceException}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ResourceException}.
	 *
	 */
	public ResourceException() {
		super();
	}

	/**
	 * Create instance of {@code ResourceException}.
	 *
	 * @param message
	 * @param throwable
	 */
	public ResourceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * Create instance of {@code ResourceException}.
	 *
	 * @param message
	 */
	public ResourceException(String message) {
		super(message);
	}

	/**
	 * Create instance of {@code ResourceException}.
	 *
	 * @param throwable
	 */
	public ResourceException(Throwable throwable) {
		super(throwable);
	}
	
}
