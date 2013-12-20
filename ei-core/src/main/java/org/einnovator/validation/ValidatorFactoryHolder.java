package org.einnovator.validation;

/**
 * A holder for a singleton {@code ValidatorFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 */
public class ValidatorFactoryHolder {

	//
	// Static utilities
	//
	
	public static ValidatorFactory instance;
	
	/**
	 * Get singleton instance of a {@code ValidatorFactory}.
	 * 
	 * This method does not create the singleton. 
	 * Method {@link #getRequiredInstance()} or {@link #setInstance(ValidatorFactory)} 
	 * should be called before hand.
	 * 
	 * To return a non-<code>null</code> value required
	 * @return the {@code ValidatorFactory} instance; or <code>null</code>, if none was set.
	 * @see #getRequiredInstance()
	 */
	public static ValidatorFactory getInstance() {
		return instance;
	}
	
	
	/**
	 * Get singleton instance of a {@code ValidatorFactory}.
	 * 
	 * If none was set or created before a {@code DefaultValidatorFactory} is created and returned.

	 * @return the {@code ValidatorFactory} instance
	 * @see #setInstance(ValidatorFactory)
	 */
	public static ValidatorFactory getRequiredInstance() {
		if (instance==null) {
			instance = new DefaultValidatorFactory();
		}
		return instance;
	}

	/**
	 * Get the singleton instance of a {@code ValidatorFactory}.
	 * 
	 * @param instance the {@code ValidatorFactory} instance
	 * @see #getInstance()
	 * @see #getRequiredInstance()
	 */
	public static void setInstance(ValidatorFactory instance) {
		ValidatorFactoryHolder.instance = instance;
	}

}
