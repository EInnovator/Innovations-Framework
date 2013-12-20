package org.einnovator.environment;

/**
 * An abstraction for an application environment.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface Environment {

	/**
	 * Get the value of the environment property with specified key.
	 * 
	 * @param key the key
	 * @param defaultValue default value to return
	 * @return the value matching the key, or {@code defaultValue} if if no value is defined in the environment matching the key
	 */
	Object getValue(String key, Object defaultValue);

	/**
	 * Get the typed value of the environment property with specified key.
	 * 
	 * @param key the key
	 * @param the type of the value to return
	 * @param defaultValue default value to return
	 * @return the value matching the key, or {@code defaultValue} if if no value is defined in the environment matching the key
	 * @see #getValue(String, Object)
	 */
	<T> T getValue(String key, Class<T> type, T defaultValue);
}
