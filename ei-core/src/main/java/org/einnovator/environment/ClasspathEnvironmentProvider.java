package org.einnovator.environment;

import java.util.List;

import org.einnovator.util.ServiceLoader;

/**
 * AA {@code ClasspathEnvironmentProvider}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ClasspathEnvironmentProvider implements EnvironmentProvider {
	private ServiceLoader<Environment> serviceLoader;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ClasspathEnvironmentProvider}.
	 *
	 */
	public ClasspathEnvironmentProvider() {
		serviceLoader = new ServiceLoader<Environment>(Environment.class);
	}

	//
	// ClasspathEnvironmentProvider implementation
	//
	
	/**
	 * @return
	 */
	@Override
	public List<Environment> getEnvironments() {
		return serviceLoader.getServices();
	}

}
