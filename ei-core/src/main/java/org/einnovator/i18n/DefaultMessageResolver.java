package org.einnovator.i18n;

public class DefaultMessageResolver extends CompositeMessageResolver {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code DefaultEnvironment}.
	 *
	 */
	public DefaultMessageResolver() {
		addAll(new ClasspathMessageResolverProvider());
	}
	
}
