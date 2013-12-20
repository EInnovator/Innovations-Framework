/**
 * 
 */
package org.einnovator.text.transforms;

import org.einnovator.text.TextTransform;
import org.einnovator.util.StringUtil;

/**
 * AA {@code CapsTextTransform}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class CapsTextTransform implements TextTransform {

	private boolean capitalize;

	//
	// Constructors
	//
	
	/**
	 * Create instance of CapsTextTransform.
	 *
	 * @param capitalize
	 */
	public CapsTextTransform(boolean capitalize) {
		this.capitalize = capitalize;
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of capitalize.
	 *
	 * @return the capitalize
	 */
	public boolean isCapitalize() {
		return capitalize;
	}

	/**
	 * Set the value of capitalize.
	 *
	 * @param capitalize the capitalize to set
	 */
	public void setCapitalize(boolean capitalize) {
		this.capitalize = capitalize;
	}
		
	
	//
	// TextTransform implementation
	//
	

	@Override
	public String transform(String text) {
		if (capitalize) {
			return StringUtil.capitalize(text);			
		} else {
			return StringUtil.uncapitalize(text);
		}
	}

	
	//
	// Static utility
	//

	private static CapsTextTransform capitalizeInstance;

	private static CapsTextTransform uncapitalizeInstance;
	
	public static CapsTextTransform getCapitalizeInstance() {
		if (capitalizeInstance==null) {
			capitalizeInstance = new CapsTextTransform(true);
		}
		return capitalizeInstance;
	}

	public static CapsTextTransform getUncapitalizeInstance() {
		if (uncapitalizeInstance==null) {
			uncapitalizeInstance = new CapsTextTransform(false);
		}
		return uncapitalizeInstance;
	}

}
