/**
 * 
 */
package org.einnovator.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.util.Ordered;


/**
 * AA {@code CompositeObjectResolver}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class CompositeObjectResolver implements ObjectResolver {

	private List<ObjectResolver> resolvers;

	//
	// Constructors
	//
	
	/**
	 * Create instance of CompositeObjectResolver.
	 *
	 */
	public CompositeObjectResolver() {
		this.resolvers = new ArrayList<ObjectResolver>();
	}
	
	/**
	 * Create instance of CompositeObjectResolver.
	 *
	 * @param resolvers
	 */
	public CompositeObjectResolver(List<ObjectResolver> resolvers) {
		this.resolvers = resolvers;
	}	

	//
	// Getters and setters
	//

	
	/**
	 * Get the value of property {@code resolvers}.
	 *
	 * @return the resolvers
	 */
	public List<ObjectResolver> getResolvers() {
		return resolvers;
	}

	/**
	 * Set the value of property {@code resolvers}.
	 *
	 * @param resolvers the resolvers to set
	 */
	public void setResolvers(List<ObjectResolver> resolvers) {
		this.resolvers = resolvers;
	}
	
	/**
	 * Get the number of resolvers in this chain.
	 * 
	 * @return the number of resolvers
	 */
	public int size() {
		return resolvers.size();
	}

	//
	// Builders
	//
	/**
	 * Add a resolver to this chain
	 * 
	 * @param resolver the resolver
	 */
	public void addResolver(ObjectResolver resolver) {
		int order = 0;
		if (resolver instanceof Ordered) {
			order = ((Ordered)resolver).getOrder();
		}
		for (int i=0; i<resolvers.size(); i++) {
			ObjectResolver resolver2 = resolvers.get(i);
			int order2 = 0;
			if (resolver2 instanceof Ordered) {
				order2 = ((Ordered)resolver2).getOrder();
			} 
			if (order<order2) {
				resolvers.add(i, resolver);
				return;
			}
		}
		this.resolvers.add(resolver);
	}

	/**
	 * Add a variable number of {@code ObjectResolver}
	 * 
	 * @param resolvers the resolvers
	 */
	public void addResolvers(ObjectResolver... resolvers) {
		for (ObjectResolver resolver: resolvers) {
			addResolver(resolver);
		}
	}

	/**
	 * Add a collection of {@code ObjectResolver}
	 * 
	 * @param resolvers the resolvers
	 */
	public void addResolvers(Collection<ObjectResolver> resolvers) {
		for (ObjectResolver resolver: resolvers) {
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
	public CompositeObjectResolver add(ObjectResolver resolver) {
		addResolver(resolver);
		return this;
	}

	/**
	 * Add a variable number of {@code ObjectResolver}
	 * 
	 * @param resolvers the resolvers
	 */
	public CompositeObjectResolver addAll(ObjectResolver... resolvers) {
		addResolvers(resolvers);
		return this;
	}

	/**
	 * Add a variable number of {@code ObjectResolver}
	 * 
	 * @param resolvers the resolvers
	 */
	public CompositeObjectResolver addAll(Collection<ObjectResolver> resolvers) {
		addResolvers(resolvers);
		return this;
	}

	/**
	 * Add all {@code ObjectResolver} returned by the specified {@code ObjectResolverProvider}.
	 * 
	 * @param provider the {@code ObjectResolverProvider}
	 */
	public CompositeObjectResolver addAll(ObjectResolverProvider provider) {
		List<ObjectResolver> resolvers = provider.getObjectResolvers();
		if (resolvers!=null) {
			resolvers.addAll(resolvers);
		}
		return this;
	}

	//
	//
	// ObjectResolver implementation
	//

	/**
	 * @see org.einnovator.meta.ObjectResolver#getObject(org.einnovator.meta.ObjectRef)
	 */
	@Override
	public <T> T getObject(ObjectRef<T> ref) {
		for (ObjectResolver resolver: resolvers) {
			T obj = resolver.getObject(ref);
			if (obj!=null) {
				return obj;
			}
		}
		return null;
	}

}
