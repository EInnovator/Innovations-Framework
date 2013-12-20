package org.einnovator.binding;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * AA descriptor to be used as context by {@code SimpleBinder}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class BindingContext {
	
	private Map<String, String> paramaterMap;
	
	private Locale locale;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code SimpleBinder}.
	 *
	 * @param paramaterMap
	 */
	public BindingContext(Map<String, String> paramaterMap, Locale locale) {
		this.paramaterMap = paramaterMap;
		this.locale = locale;
	}

	/**
	 * Create instance of {@code SimpleBinder}.
	 *
	 */
	public BindingContext() {
		this(new HashMap<String, String>(), null);
	}
	
	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code paramaterMap}.
	 *
	 * @return the paramaterMap
	 */
	public Map<String, String> getParamaterMap() {
		return paramaterMap;
	}

	/**
	 * Set the value of property {@code paramaterMap}.
	 *
	 * @param paramaterMap the paramaterMap to set
	 */
	public void setParamaterMap(Map<String, String> paramaterMap) {
		this.paramaterMap = paramaterMap;
	}

	/**
	 * Get the value of property {@code locale}.
	 *
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Set the value of property {@code locale}.
	 *
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * Get value of specified parameter.
	 * 
	 * @param name the name of the parameter
	 * @return the value
	 */
	public String getParameter(String name) {
		return paramaterMap.get(name);
	}
	
	//
	// Fluent API
	//
	
	/**
	 * Add a parameter value to the parameter map.
	 * 
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 * @return
	 */
	public BindingContext add(String name, String value) {
		paramaterMap.put(name, value);
		return this;
	}
	
}
