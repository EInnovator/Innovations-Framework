/**
 * 
 */
package org.einnovator.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.meta.MetaDescriptor;


/**
 * AA CompositeValidatorFactory.
 *
 * @author Jorge Simão
 */
public class CompositeValidatorFactory implements ValidatorFactory {

	private List<ValidatorFactory> validatorFactories = new ArrayList<ValidatorFactory>();
	
	//
	// Constructors
	//

	/**
	 * Create instance of CompositeValidatorFactory.
	 *
	 */
	public CompositeValidatorFactory() {
	}
	
	//
	// Getters and setters
	//
	

	//
	// Public methods
	//
	
	public void addValidatorFactory(ValidatorFactory validatorFactory) {
		validatorFactories.add(validatorFactory);
	}

	//
	// Fluent API
	//

	public CompositeValidatorFactory add(ValidatorFactory validatorFactory) {
		addValidatorFactory(validatorFactory);
		return this;
	}
	
	public CompositeValidatorFactory addAll(ValidatorFactory... validatorFactories) {
		for (ValidatorFactory validatorFactory: validatorFactories) {
			addValidatorFactory(validatorFactory);
		}
		return this;
	}

	public CompositeValidatorFactory addAll(Collection<ValidatorFactory> validatorFactories) {
		for (ValidatorFactory validatorFactory: validatorFactories) {
			addValidatorFactory(validatorFactory);
		}
		return this;
	}

	public CompositeValidatorFactory addAll(List<ValidatorFactory> validatorFactories) {
		for (ValidatorFactory validatorFactory: validatorFactories) {
			addValidatorFactory(validatorFactory);
		}
		return this;
	}

	public CompositeValidatorFactory addAll(ValidatorFactoryProvider provider) {
		List<ValidatorFactory> validatorFactories = provider.getValidatorFactories();
		if (validatorFactories!=null) {
			addAll(validatorFactories);			
		}
		return this;
	}
	
	//
	// ValidatorFactory implementation
	//

	@Override
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		Validator<?> validator0 = null;
		for (ValidatorFactory validatorFactory: validatorFactories) {
			Validator<?> validator = validatorFactory.createValidator(descriptor);			
			if (validator!=null) {
				validator0 = mergeValidators(validator0, validator);
			}
		}
		return validator0;
	}
	
	@SuppressWarnings("unchecked")
	public static Validator<?> mergeValidators(Validator<?> validator0, Validator<?> validator) {
		if (validator!=null) {
			if (validator0!=null) {
				if (validator instanceof ValidatorChain) {
					return ((ValidatorChain<Object>)validator).add((Validator<Object>)validator);
				} else {
					return new ValidatorChain<Object>()
							.add((Validator<Object>)validator0)
							.add((Validator<Object>)validator);
				}
			} else {
				return validator;
			}				
		} else {
			return validator0;
		}
	}

}
