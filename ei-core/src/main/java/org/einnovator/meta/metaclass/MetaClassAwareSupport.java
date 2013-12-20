package org.einnovator.meta.metaclass;

import org.einnovator.meta.MetaClass;


/**
 * A MetaClassAwareSupport.
 *
 * @author Jorge Simão, May 18, 2013
 * @param <T>
 */
abstract public class MetaClassAwareSupport<T> {

	protected MetaClass<T> metaClass;

	//
	// Constructors
	//

	/**
	 * Create instance of MetaClassAwareSupport.
	 *
	 * @param metaClass
	 */
	public MetaClassAwareSupport(MetaClass<T> mappedClass) {
		this.metaClass = mappedClass;
	}

	/**
	 * Create instance of MetaClassAwareSupport.
	 *
	 */
	public MetaClassAwareSupport() {
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of metaClass.
	 *
	 * @return the metaClass
	 */
	public MetaClass<T> getMetaClass() {
		return metaClass;
	}

	/**
	 * Set the value of metaClass.
	 *
	 * @param metaClass the metaClass to set
	 */
	public void setMetaClass(MetaClass<T> mappedClass) {
		this.metaClass = mappedClass;
	}
		
}
