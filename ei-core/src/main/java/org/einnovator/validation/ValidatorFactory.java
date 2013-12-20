/**
 * 
 */
package org.einnovator.validation;

import org.einnovator.meta.MetaDescriptor;


/**
 * A ValidatorFactory.
 *
 * @author Jorge Simão
 */
public interface ValidatorFactory {

	/**
	 * Create a Parameter suitable for the {@code MetaDescriptor}.
	 * 
	 * @param descriptor the {@code MetaDescriptor}
	 * @return the created {@code Validator}
	 */
	Validator<?> createValidator(MetaDescriptor descriptor);
}
