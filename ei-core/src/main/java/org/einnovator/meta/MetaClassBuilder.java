/**
 * 
 */
package org.einnovator.meta;

import java.util.Collection;
import java.util.List;

/**
 * A builder for a {@link MetaClass}.
 * 
 * @author Jorge Simão
 * @param <T> the type parameter for the wrapped Java {@link java.lang.Class}
 */
public interface MetaClassBuilder<T> {

	/**
	 * Get the parent {@link MetaClass}.
	 *
	 * @return the parent {@link MetaClass}
	 */
	MetaClass<?> getParent();

	/**
	 * Get the value of the wrapped Java {@link java.lang.Class} 
	 * as returned by method {@link MetaClass#getTheClass()}.
	 *
	 * @return the {@link java.lang.Class}
	 */
	public Class<T> getTheClass();

	/**
	 * Set the value of the wrapped Java {@link java.lang.Class} 
	 * as returned by method {@link MetaClass#getTheClass()}.
	 *
	 * @param theClass the wrapped {@link java.lang.Class}
	 */
	void setTheClass(Class<T> theClass);

	/**
	 * Get the list of declared {@link Property}.
	 *
	 * @return the list of declared {@link Property}.
	 */
	List<Property<?>> getDeclaredPropertyList();

	/**
	 * Add a {@link Property}.
	 * 
	 * @param property the {@link Property} to add.
	 */
	MetaClassBuilder<T> addProperty(Property<?> property);

	/**
	 * Add list of {@link Property}.
	 * 
	 * @param list of {@link Property} to add.
	 */
	MetaClassBuilder<T> addProperties(Collection<Property<?>> properties);

	/**
	 * Get property with specified name.
	 * 
	 * @param name
	 * @return
	 */
	Property<?> getProperty(String name);
	
	/**
	 * Clears the state of the builder.
	 * 
	 */
	public void clear();

	/**
	 * Build the {@link MetaClass}.
	 * 
	 * @return the built {@link MetaClass}.
	 */
	MetaClass<T> build();
}
