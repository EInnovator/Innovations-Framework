package org.einnovator.resource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AA {@code CompositeResourceFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class CompositeResourceFactory implements ResourceFactory {

	private Map<String, ResourceFactory> factoryMap = new LinkedHashMap<String, ResourceFactory>();

	private ResourceFactory defaultResourceFactory;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code CompositeResourceFactory}.
	 *
	 */
	public CompositeResourceFactory() {
	}
	
	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code factoryMap}.
	 *
	 * @return the factoryMap
	 */
	public Map<String, ResourceFactory> getFactoryMap() {
		return factoryMap;
	}

	/**
	 * Set the value of property {@code factoryMap}.
	 *
	 * @param factoryMap the factoryMap to set
	 */
	public void setFactoryMap(Map<String, ResourceFactory> factoryMap) {
		this.factoryMap = factoryMap;
	}
		
	/**
	 * Get the value of property {@code defaultResourceFactory}.
	 *
	 * @return the defaultResourceFactory
	 */
	public ResourceFactory getDefaultResourceFactory() {
		return defaultResourceFactory;
	}

	/**
	 * Set the value of property {@code defaultResourceFactory}.
	 *
	 * @param defaultResourceFactory the defaultResourceFactory to set
	 */
	public void setDefaultResourceFactory(ResourceFactory defaultResourceFactory) {
		this.defaultResourceFactory = defaultResourceFactory;
	}
	
	//
	// Collections
	//
	public void addResourceFactory(String prefix, ResourceFactory factory) {
		this.factoryMap.put(prefix, factory);
	}

	public void addResourceFactory(ResourceFactory factory, String prefix) {
		addResourceFactory(prefix, factory);
	}

	public void addResourceFactories(ResourceFactory factory, String... prefixes) {
		for (String prefix: prefixes) {
			addResourceFactory(prefix, factory);
		}
	}

	//
	// Fluent API
	//
	
	public CompositeResourceFactory add(String prefix, ResourceFactory factory) {
		addResourceFactory(prefix, factory);
		return this;
	}

	public CompositeResourceFactory add(ResourceFactory factory, String prefix) {
		addResourceFactory(factory, prefix);
		return this;
	}

	public CompositeResourceFactory add(ResourceFactory factory, String... prefixes) {
		addResourceFactories(factory, prefixes);
		return this;
	}

	//
	// ResourceFactory Implementation
	//
	
	/**
	 * @see org.einnovator.resource.ResourceFactory#createResource(java.lang.String)
	 */
	@Override
	public ResourceBase createResource(String uri) {
		String prefix = ResourceUtil.getResourcePrefix(uri);
		ResourceFactory factory = factoryMap.get(prefix);
		if (factory==null) {
			factory = defaultResourceFactory;
		}
		if (factory!=null) {
			return factory.createResource(uri);
		}
		throw new ResourceException("ResourceFactory not found for: " + uri);
	}

}
