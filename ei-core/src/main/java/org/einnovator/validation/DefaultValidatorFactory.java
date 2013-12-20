/**
 * 
 */
package org.einnovator.validation;

import org.einnovator.validation.impl.BuiltinValidationFactory;


/**
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class DefaultValidatorFactory extends CompositeValidatorFactory {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code DefaultValidatorFactory}.
	 *
	 */
	public DefaultValidatorFactory() {
		add(new BuiltinValidationFactory());
		addAll(new ClasspathValidatorFactoryProvider());
	}
	
}
