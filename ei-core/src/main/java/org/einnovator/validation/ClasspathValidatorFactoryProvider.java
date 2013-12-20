package org.einnovator.validation;

import java.util.List;

import org.einnovator.util.ServiceLoader;

/**
 * AA {@code ClasspathValidatorFactoryProvider}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ClasspathValidatorFactoryProvider implements ValidatorFactoryProvider {
	private ServiceLoader<ValidatorFactory> serviceLoader;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ClasspathValidatorFactoryProvider}.
	 *
	 */
	public ClasspathValidatorFactoryProvider() {
		serviceLoader = new ServiceLoader<ValidatorFactory>(ValidatorFactory.class);
	}

	//
	// ClasspathValidatorFactoryProvider implementation
	//
	
	/**
	 * @return
	 */
	@Override
	public List<ValidatorFactory> getValidatorFactories() {
		return serviceLoader.getServices();
	}

}
