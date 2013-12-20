/**
 * 
 */
package org.einnovator.meta;

/**
 * A factory for {@code MetaClass}.
 * 
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface MetaClassFactory {
	
	/**
	 * Create a {@code MetaClass}.
	 * 
	 * Different implementation will typically return different sub-classes of {@code MetaClass}.
	 * 
	 * @param parent the parent {@code MetaClass}
	 * @param theClass the Java class for this {@code MetaClass}
	 * @return the created {@code MetaClass}
	 */
	<T> MetaClass<T> createMetaClass(MetaClass<?> parent, Class<T> theClass);	
}
