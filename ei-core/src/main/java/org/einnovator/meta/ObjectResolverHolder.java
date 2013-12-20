package org.einnovator.meta;


/**
 * A holder for a singleton {@code ObjectResolver}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 */
public class ObjectResolverHolder {

	//
	// Static utilities
	//
	
	public static ObjectResolver instance;
	
	/**
	 * Get singleton instance of a {@code ObjectResolver}.
	 * 
	 * This method does not create the singleton. 
	 * Method {@link #getRequiredInstance()} or {@link #setInstance(ObjectResolver)} 
	 * should be called before hand.
	 * 
	 * To return a non-<code>null</code> value required
	 * @return the {@code ObjectResolver} instance; or <code>null</code>, if none was set.
	 * @see #getRequiredInstance()
	 */
	public static ObjectResolver getInstance() {
		return instance;
	}
	
	
	/**
	 * Get singleton instance of a {@code ObjectResolver}.
	 * 
	 * If none was set or created before a {@code DefaultObjectResolver} is created and returned.

	 * @return the {@code ObjectResolver} instance
	 * @see #setInstance(ObjectResolver)
	 */
	public static ObjectResolver getRequiredInstance() {
		if (instance==null) {
			instance = new DefaultObjectResolver();
		}
		return instance;
	}

	/**
	 * Get the singleton instance of a {@code ObjectResolver}.
	 * 
	 * @param instance the {@code ObjectResolver} instance
	 * @see #getInstance()
	 * @see #getRequiredInstance()
	 */
	public static void setInstance(ObjectResolver instance) {
		ObjectResolverHolder.instance = instance;
	}

}
