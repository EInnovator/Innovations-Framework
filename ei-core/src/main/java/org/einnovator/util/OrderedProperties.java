/**
 * 
 */
package org.einnovator.util;

import java.util.LinkedHashMap;

/**
 * A OrderedProperties.
 *
 * @author Jorge Simão
 */
public class OrderedProperties extends Properties {

	//
	// Constructors
	//
	
	/**
	 * Create instance of OrderedProperties.
	 *
	 */
	public OrderedProperties() {
		super(new LinkedHashMap<String, String>());
	}
	
	/**
	 * Create instance of Properties.
	 *
	 * @param parent
	 */
	public OrderedProperties(Properties parent) {
		this();
		this.parent = parent;
	}

}
