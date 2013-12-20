package org.einnovator.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@code Validator} chain.
 * 
 * Effectively a composite {@code Validator}, but is more intuitive to thing as a chain 
 * since all {@code Validator} contribute to detect constraint violations add errors.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ValidatorChain<T> implements Validator<T> {

	private List<Validator<T>> validators = new ArrayList<Validator<T>>();
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ValidatorChain}.
	 *
	 */
	public ValidatorChain() {
	}

	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code validators}.
	 *
	 * @return the validators
	 */
	public List<Validator<T>> getValidators() {
		return validators;
	}

	/**
	 * Set the value of property {@code validators}.
	 *
	 * @param validators the validators to set
	 */
	public void setValidators(List<Validator<T>> validators) {
		this.validators = validators;
	}
	
	//
	// Collections
	//
	
	/**
	 * Add a {@code Validator} to the chain.
	 * 
	 * @param validator
	 */
	public void addValidator(Validator<T> validator) {
		validators.add(validator);
	}

	//
	// Fluent API
	//

	public ValidatorChain<T> add(Validator<T> validator) {
		addValidator(validator);
		return this;
	}

	
	//
	// Validator mplementation
	//
	
	/**
	 * @see org.einnovator.validation.Validator#validate(java.lang.Object, org.einnovator.validation.Errors)
	 */
	public boolean validate(T obj, ValidationContext context) {
		boolean valid = true;
		for (Validator<T> validator: validators) {
			if (!validator.validate(obj, context)) {
				valid = false;
			}
		}
		return valid;
	}
}
