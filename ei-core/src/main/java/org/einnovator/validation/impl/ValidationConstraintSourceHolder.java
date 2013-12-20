package org.einnovator.validation.impl;

import org.einnovator.meta.MetaDataSource;

/**
 * A holder for a singleton {@code ValidationConstraintSource}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 */
public class ValidationConstraintSourceHolder {

	//
	// Static utilities
	//
	
	public static MetaDataSource instance;
	
	/**
	 * Get singleton instance of a {@code ValidationConstraintSource}.
	 * 
	 * This method does not create the singleton. 
	 * Method {@link #getRequiredInstance()} or {@link #setInstance(ValidationConstraintSource)} 
	 * should be called before hand.
	 * 
	 * To return a non-<code>null</code> value required
	 * @return the {@code ValidationConstraintSource} instance; or <code>null</code>, if none was set.
	 * @see #getRequiredInstance()
	 */
	public static MetaDataSource getInstance() {
		return instance;
	}
	
	
	/**
	 * Get singleton instance of a {@code ValidationConstraintSource}.
	 * 
	 * If none was set or created before a {@code ValidationConstraintSource} is created and returned.

	 * @return the {@code ValidationConstraintSource} instance
	 * @see #setInstance(ValidationConstraintSource)
	 */
	public static MetaDataSource getRequiredInstance() {
		if (instance==null) {
			instance = new DefaultValidationConstraintSource();
		}
		return instance;
	}

	/**
	 * Get the singleton instance of a {@code ValidationConstraintSource}.
	 * 
	 * @param instance the {@code ValidationConstraintSource} instance
	 * @see #getInstance()
	 * @see #getRequiredInstance()
	 */
	public static void setInstance(MetaDataSource instance) {
		ValidationConstraintSourceHolder.instance = instance;
	}

}
