/**
 * 
 */
package org.einnovator.validation.impl;

import java.lang.reflect.Member;

import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.MetaDataSource;
import org.einnovator.meta.MetaDescriptor;
import org.einnovator.meta.MetaUtil;
import org.einnovator.validation.ValidatorFactory;

/**
 * Abstract class to support implementation of {@code ValidatorFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class ValidatorFactorySupport extends ObjectSupport implements ValidatorFactory {
	
	private MetaDataSource metaDataSource;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code ValidatorFactorySupport}.
	 *
	 * @param metaDataSource
	 */
	public ValidatorFactorySupport(MetaDataSource metaDataSource) {
		this.metaDataSource = metaDataSource;
	}

	/**
	 * Create instance of {@code ValidatorFactorySupport}.
	 *
	 */
	public ValidatorFactorySupport() {
	}

	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code metaDataSource}.
	 *
	 * @return the metaDataSource
	 */
	public MetaDataSource getMetaDataSource() {
		return metaDataSource;
	}

	/**
	 * Set the value of property {@code metaDataSource}.
	 *
	 * @param metaDataSource the metaDataSource to set
	 */
	public void setMetaDataSource(MetaDataSource metaDataSource) {
		this.metaDataSource = metaDataSource;
	}
	
	
	/**
	 * Get a validation descriptor of the specified type.
	 * 
	 * Delegate to the set {@code MetaDataSource}.
	 * 
	 * @param descriptor the {@code MetaDescriptor}
	 * @param type the type of the validation descriptor
	 * @return the validation descriptor; or <code>null</code>, if none is found.
	 */
	protected <T> T getValidationDescriptor(MetaDescriptor descriptor, Class<T> type) {
		return metaDataSource.getMetaData(descriptor, type);
	}

	public static String getValidationConstraintPrefix(MetaDescriptor descriptor, Class<?> type) {
		Member member = descriptor.getMember();
		if (member!=null) {
			return member.getDeclaringClass() + "." + MetaUtil.getPropertyName(member) + "." + type.getSimpleName();			
		}
		return null;
	}

}
