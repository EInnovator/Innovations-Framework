package org.einnovator.environment;

/**
 * An {@code Environment} that supports setting of property values.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface WritableEnvironment extends Environment {

	/**
	 * Set the value of the environment property (variable) with the given key.
	 * 
	 * @param key the key
	 * @param value the value
	 */
	void setValue(String key, Object value);
}
