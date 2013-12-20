/**
 * 
 */
package org.einnovator.validation;

import org.einnovator.meta.MetaDescriptor;


/**
 * A SingletonValidatorFactory.
 *
 * @author Jorge Simão
 */
public class SingletonValidatorFactory implements ValidatorFactory {
	
	protected Validator<?> validator;
	
	/**
	 * Create instance of SingletonValidatorFactory.
	 *
	 * @validator validator
	 */
	public SingletonValidatorFactory(Validator<?> validator) {
		this.validator = validator;
	}

	//
	// ValidatorFactory implementation
	//
	
	@Override
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		return validator;
	}

}
