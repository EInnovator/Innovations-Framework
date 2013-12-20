/**
 * 
 */
package org.einnovator.meta;


/**
 * A resolver for instances of {@code MetaClass}.
 *
 * @author Jorge Simão
 */
public interface MetaClassResolver {
	
	/**
	 * Get the {@code MetaClass} for a Java class.
	 * 
	 * @param theClass the Java class
	 * @return the {@code MetaClass}
	 */
	<T> MetaClass<T> getMetaClass(Class<T> theClass);

}
