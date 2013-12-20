package org.einnovator.environment;

/**
 * Default implementation for {@code Environment}.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
public class DefaultEnvironment extends CompositeEnvironment {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code DefaultEnvironment}.
	 *
	 */
	public DefaultEnvironment() {
		addAll(SystemEnvironment.getInstance(), UserEnvironment.getInstance());
		addAll(new ClasspathEnvironmentProvider());
	}
	
}
