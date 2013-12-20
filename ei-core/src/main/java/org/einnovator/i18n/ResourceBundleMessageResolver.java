package org.einnovator.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceBundleMessageResolver implements MessageResolver {

	private String basename;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ResourceBundleMessageResolverTests}.
	 *
	 */
	public ResourceBundleMessageResolver() {
	}

	/**
	 * Create instance of {@code ResourceBundleMessageResolverTests}.
	 *
	 * @param basename
	 */
	public ResourceBundleMessageResolver(String basename) {
		this.basename = basename;
	}

	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code basename}.
	 *
	 * @return the basename
	 */
	public String getBasename() {
		return basename;
	}

	/**
	 * Set the value of property {@code basename}.
	 *
	 * @param basename the basename to set
	 */
	public void setBasename(String basename) {
		this.basename = basename;
	}	
	
	//
	// Environment Implementation
	//
	
	@Override
	public String getMessage(String key, Locale locale, String defaultValue) {
		ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
		if (bundle==null) {
			return defaultValue;
		}
		try {
			String value = bundle.getString(key);
			return value!=null ? value : defaultValue;			
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}
}
