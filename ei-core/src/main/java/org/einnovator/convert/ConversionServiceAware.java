/**
 * 
 */
package org.einnovator.convert;


/**
 * An object that defines a setter for a {@code ConversionService}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface ConversionServiceAware {

	/**
	 * Set the {@code ConversionService}.
	 * 
	 * @param conversionService the {@code ConversionService}
	 */
	void setConversionService(ConversionService conversionService);
}
