package org.einnovator.i18n;

import java.util.List;

import org.einnovator.util.ServiceLoader;

/**
 * AA {@code ClasspathEnvironmentProvider}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ClasspathMessageResolverProvider implements MessageResolverProvider {
	private ServiceLoader<MessageResolver> serviceLoader;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ClasspathEnvironmentProvider}.
	 *
	 */
	public ClasspathMessageResolverProvider() {
		serviceLoader = new ServiceLoader<MessageResolver>(MessageResolver.class);
	}

	//
	// ClasspathEnvironmentProvider implementation
	//
	
	/**
	 * @return
	 */
	@Override
	public List<MessageResolver> getMessageResolvers() {
		return serviceLoader.getServices();
	}

}
