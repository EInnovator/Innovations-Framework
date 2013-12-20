package org.einnovator.environment;

/**
 * An {@code Environment} theat resolves to the user-application environment.
 *
 * User application environment is looked-up with {@link System#getenv()}.
 * 
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class UserEnvironment extends EnvironmentSupport {

	//
	// Constructors
	//
	
	public UserEnvironment() {
	}
	
	//
	// EnvironmentSupport Implementation
	//
	
	/**
	 * @see org.einnovator.environment.Environment#getValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getValue(String key, Object defaultValue) {
		Object value = System.getenv(key);
		return value!=null ? value : defaultValue;
	}

	//
	// Static Utilities
	//
	
	private static UserEnvironment instance;
	
	/**
	 * Get a singleton instance of {@code UserEnvironment}.
	 * 
	 * @return the singleton {@code UserEnvironment}
	 */
	public static UserEnvironment getInstance() {
		if (instance==null) {
			instance = new UserEnvironment();
		}
		return instance;
	}

	
}
