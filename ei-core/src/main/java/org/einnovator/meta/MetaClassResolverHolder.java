/**
 * 
 */
package org.einnovator.meta;

import org.einnovator.meta.metaclass.DefaultMetaClassRegistry;
import org.einnovator.meta.metaclass.build.DefaultMetaClassFactory;

/**
 * A MetaClassDirectoryUtil.
 *
 * @author Jorge Simão
 */
public class MetaClassResolverHolder {
	private MetaClassResolver resolver;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code MetaClassResolverHolder}.
	 *
	 * @param resolver
	 */
	public MetaClassResolverHolder(MetaClassResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * Create instance of {@code MetaClassResolverHolder}.
	 *
	 */
	public MetaClassResolverHolder() {
		this(new DefaultMetaClassRegistry(new DefaultMetaClassFactory()));
	}
	
	//
	// Getters and setters
	//
	
	public MetaClassResolver getMetaClassResolver() {
		return resolver;
	}
	
	public void setMetaClassResolver(MetaClassResolver resolver) {
		this.resolver = resolver;
	}

	//
	// Static utility
	//
	
	private static MetaClassResolverHolder instance;
	
	public static MetaClassResolverHolder getInstance() {
		if (instance==null) {
			instance = new MetaClassResolverHolder();
		}
		return instance;
	}
}
