package org.einnovator.environment;

/**
 * An {@code Environment} theat resolves to sysytem properties.
 *
 * System properties are looked-up with {@link System#getProperty(String)}.
 * 
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class SystemEnvironment extends EnvironmentSupport {

	//
	// Constructors
	//
	
	public SystemEnvironment() {
	}
	
	//
	// EnvironmentSupport Implementation
	//
	
	/**
	 * @see org.einnovator.environment.Environment#getValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getValue(String key, Object defaultValue) {
		Object value = System.getProperty(key);
		return value!=null ? value : defaultValue;
	}

	//
	// Static Utilities
	//
	
	private static SystemEnvironment instance;
	
	/**
	 * Get a singleton instance of {@code SystemEnvironment}.
	 * 
	 * @return the singleton {@code SystemEnvironment}
	 */
	public static SystemEnvironment getInstance() {
		if (instance==null) {
			instance = new SystemEnvironment();
		}
		return instance;
	}
	
}
