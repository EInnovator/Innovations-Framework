package org.einnovator.i18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * AA composite {@code Environment}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class CompositeMessageResolver implements MessageResolver {

	private List<MessageResolver> resolvers;
	
	//
	// Constructors
	//
	
	public CompositeMessageResolver() {
		resolvers = new ArrayList<MessageResolver>();
	}
	
	/**
	 * Create instance of {@code CompositeEnvironment}.
	 *
	 * @param resolvers
	 */
	public CompositeMessageResolver(List<MessageResolver> resolvers) {
		this.resolvers = resolvers;
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param resolvers the resolvers
	 */
	public void addResolver(MessageResolver resolver) {
		this.resolvers.add(resolver);
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param resolvers the resolvers
	 */
	public void addResolvers(MessageResolver... resolvers) {
		for (MessageResolver resolver: resolvers) {
			addResolver(resolver);
		}
	}

	/**
	 * Add a collection of {@code Environment}
	 * 
	 * @param resolvers the resolvers
	 */
	public void addResolvers(Collection<MessageResolver> resolvers) {
		for (MessageResolver resolver: resolvers) {
			addResolver(resolver);
		}
	}

	//
	// Fluent API
	//

	/**
	 * Add a resolver to this chain
	 * 
	 * @param resolver the resolver
	 */
	public CompositeMessageResolver add(MessageResolver resolver) {
		addResolver(resolver);
		return this;
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param resolvers the resolvers
	 */
	public CompositeMessageResolver addAll(MessageResolver... resolvers) {
		addResolvers(resolvers);
		return this;
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param resolvers the resolvers
	 */
	public CompositeMessageResolver addAll(Collection<MessageResolver> resolvers) {
		addResolvers(resolvers);
		return this;
	}

	/**
	 * Add all {@code Environment} returned by the specified {@code EnvironmentProvider}.
	 * 
	 * @param provider the {@code EnvironmentProvider}
	 */
	public CompositeMessageResolver addAll(MessageResolverProvider provider) {
		List<MessageResolver> resolvers = provider.getMessageResolvers();
		if (resolvers!=null) {
			resolvers.addAll(resolvers);
		}
		return this;
	}

	//
	// Environment Implementation
	//
	
	/**
	 * @see org.einnovator.i18n.MessageResolver#getMessage(java.lang.String, java.util.Locale, java.lang.String)
	 */
	@Override
	public String getMessage(String key, Locale locale, String defaultValue) {
		for (MessageResolver resolver: resolvers) {
			String msg = resolver.getMessage(key, locale, null);
			if (msg!=null) {
				return msg;
			}
		}
		return defaultValue;
	}

}
