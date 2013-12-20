/**
 * 
 */
package org.einnovator.validation;

/**
 * A {@code Validator}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface Validator<T> {
			
	/**
	 * Validate the specified object.
	 * 
	 * Method {@link FormatContext#getErrors()} should be called to get the {@code Errors} object
	 * where to add the validation errors.
	 * 
	 * @param obj the object to validate
	 * @param context the {@code FormatContext}
	 * @return <code>true</code>, if the obj is valid; <code>false</code>, otherwise.
	 */
	boolean validate(T obj, ValidationContext context);

}
