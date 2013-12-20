package org.einnovator.meta;

import java.util.List;

import org.einnovator.util.ServiceLoader;

/**
 * AA {@code ClasspathEnvironmentProvider}.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
public class ClasspathObjectResolverProvider implements ObjectResolverProvider {
	private ServiceLoader<ObjectResolver> serviceLoader;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ClasspathEnvironmentProvider}.
	 *
	 */
	public ClasspathObjectResolverProvider() {
		serviceLoader = new ServiceLoader<ObjectResolver>(ObjectResolver.class);
	}

	//
	// ClasspathEnvironmentProvider implementation
	//
	
	/**
	 * @return
	 */
	@Override
	public List<ObjectResolver> getObjectResolvers() {
		return serviceLoader.getServices();
	}

}
