/**
 * 
 */
package org.einnovator.meta;

import java.util.Collection;
import java.util.Map;

/**
 * A registry for {@code MetaClass} instances.
 *
 * @author Jorge Simão
 */
public interface MetaClassRegistry extends MetaClassResolver {

	/**
	 * Get the {@code MetaClass} from the name.
	 * 
	 * The name of the meta-class is the the qualified name of the
	 * wrapped Java class.
	 * 
	 * @param name the name of the meta-class
	 * @return the {@code MetaClass}
	 */
	MetaClass<?> forName(String name);


	/**
	 * Create a {@code MetaClass} for a Java class.
	 * 
	 * @param theClass the Java class
	 * @return the {@code MetaClass}
	 */
	<T> MetaClass<T> createMetaClassFor(Class<T> theClass);

	/**
	 * Get all registered {@code MetaClass}.
	 * 
	 * @return the collection of {@code MetaClass}
	 */
	Collection<MetaClass<?>> getMetaClasses();

	/**
	 * Add a {@code MetaClass} to this registry.
	 * 
	 * @param metaClass the {@code MetaClass}
	 */
	void addMetaClass(MetaClass<?> metaClass);

	/**
	 * Create a {@code MetaClass} the Java class and added to this registry.
	 * 
	 * @param metaClass the {@code MetaClass}
	 */
	<T> MetaClass<T> addMetaClassFor(Class<T> theClass);	
	
	/**
	 * Get the {@code MetaClassFactory} used to create {@code MetaClass} instances.
	 * 
	 * @return the factory
	 */
	MetaClassFactory getMetaClassFactory();
	
	/**
	 * Set the {@code MetaClassFactory} used to create {@code MetaClass} instances.
	 * 
	 * @param metaClassFactory the factory
	 */
	void setMetaClassFactory(MetaClassFactory metaClassFactory);
	
	/**
	 * Check if this registry contains a {@code MetaClass}.
	 * 
	 * @param metaClass the {@code MetaClass}
	 * @return <code>true</code>, if the {@code MetaClass} is found; <code>false</code>, otherwise.
	 */
	boolean contains(MetaClass<?> metaClass);

	/**
	 * Check if this registry contains a {@code MetaClass} for a Java class.
	 * 
	 * @param theClass the Java class
	 * @return <code>true</code>, if the {@code MetaClass} is found; <code>false</code>, otherwise.
	 */
	boolean contains(Class<?> theClass);

	/**
	 * Check if this registry contains a {@code MetaClass} with a given name.
	 * 
	 * @param name the name of the {@code MetaClass}
	 * @return <code>true</code>, if the {@code MetaClass} is found; <code>false</code>, otherwise.
	 */
	boolean contains(String name);
	
	/**
	 * Get a map from Java classes to {@code MetaClass}.
	 * 
	 * The returned map should be treated as immutable.
	 * 
	 * @return the map
	 */
	Map<Class<?>, MetaClass<?>> getMetaClassMap();

}
