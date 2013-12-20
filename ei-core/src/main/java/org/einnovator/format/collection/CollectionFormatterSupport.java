package org.einnovator.format.collection;

import org.einnovator.convert.ConversionService;


/**
 * AA ArrayFormatter.
 *
 * @author Jorge Simão
 */
public class CollectionFormatterSupport  {
	static public final String SEPARATOR = ",";

	protected String separator = SEPARATOR;
	
	protected ConversionService conversionService;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of ArrayTextConverter.
	 *
	 * @param conversionService
	 */
	public CollectionFormatterSupport(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	/**
	 * Create instance of ArrayTextConverter.
	 *
	 * @param conversionService
	 * @param separator
	 */
	public CollectionFormatterSupport(ConversionService conversionService, String separator) {
		this.conversionService = conversionService;
		this.separator = separator;
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of separator.
	 *
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * Set the value of separator.
	 *
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * Get the value of conversionService.
	 *
	 * @return the conversionService
	 */
	public ConversionService getConversionService() {
		return conversionService;
	}

	/**
	 * Set the value of conversionService.
	 *
	 * @param conversionService the conversionService to set
	 */
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	
}
