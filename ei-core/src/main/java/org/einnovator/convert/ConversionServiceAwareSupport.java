/**
 * 
 */
package org.einnovator.convert;

import javax.annotation.PostConstruct;

import org.einnovator.convert.ConversionService;
import org.einnovator.convert.ConversionServiceAware;
import org.einnovator.convert.ConversionServiceHolder;
import org.einnovator.format.Format;


/**
 * Base abstract class for classes that implement {@code ConversionServiceAware}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ConversionServiceAwareSupport implements ConversionServiceAware {

	@Format(false) 
	protected ConversionService conversionService;


	//
	// Constructors
	//

	
	/**
	 * Create instance of {@code ConversionServiceAwareSupport}.
	 *
	 * @param conversionService the {@code ConversionService}
	 */
	public ConversionServiceAwareSupport(ConversionService conversionService) {
		this.conversionService = conversionService;
	}	
	
	/**
	 * Create instance of {@code ConversionServiceAwareSupport}.
	 *
	 */
	public ConversionServiceAwareSupport() {
	}
	
	@PostConstruct
	public void init() {
		if (conversionService==null) {
			conversionService = ConversionServiceHolder.getConversionService();
		}
	}
	
	//
	// Getters and setters
	//
	
	/**
	 * Get the {@code ConversionService}.
	 *
	 * @return the {@code ConversionService}
	 */
	public ConversionService getConversionService() {
		return conversionService;
	}

	/**
	 * Set the {@code ConversionService}.
	 *
	 * @param conversionService the {@code ConversionService}
	 */
	@Override
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

}
