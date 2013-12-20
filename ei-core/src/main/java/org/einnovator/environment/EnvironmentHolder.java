package org.einnovator.environment;


/**
 * A holder for a singleton {@code Environment}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 */
public class EnvironmentHolder {

	//
	// Static utilities
	//
	
	public static Environment instance;
	
	/**
	 * Get singleton instance of a {@code Environment}.
	 * 
	 * This method does not create the singleton. 
	 * Method {@link #getRequiredInstance()} or {@link #setInstance(Environment)} 
	 * should be called before hand.
	 * 
	 * To return a non-<code>null</code> value required
	 * @return the {@code Environment} instance; or <code>null</code>, if none was set.
	 * @see #getRequiredInstance()
	 */
	public static Environment getInstance() {
		return instance;
	}
	
	
	/**
	 * Get singleton instance of a {@code Environment}.
	 * 
	 * If none was set or created before a {@code DefaultEnvironment} is created and returned.

	 * @return the {@code Environment} instance
	 * @see #setInstance(Environment)
	 */
	public static Environment getRequiredInstance() {
		if (instance==null) {
			instance = new DefaultEnvironment();
		}
		return instance;
	}

	/**
	 * Get the singleton instance of a {@code Environment}.
	 * 
	 * @param instance the {@code Environment} instance
	 * @see #getInstance()
	 * @see #getRequiredInstance()
	 */
	public static void setInstance(Environment instance) {
		EnvironmentHolder.instance = instance;
	}

}
