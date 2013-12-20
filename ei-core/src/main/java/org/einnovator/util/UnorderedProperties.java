/**
 * 
 */
package org.einnovator.util;

import java.util.HashMap;

/**
 * A UnorderedProperties.
 *
 * @author Jorge Simão
 */
public class UnorderedProperties extends Properties {

	//
	// Constructors
	//
	
	/**
	 * Create instance of UnorderedProperties.
	 *
	 */
	public UnorderedProperties() {
		super(new HashMap<String, String>());
	}
	
	/**
	 * Create instance of UnorderedProperties.
	 *
	 * @param parent
	 */
	public UnorderedProperties(Properties parent) {
		this();
		this.parent = parent;
	}

}
