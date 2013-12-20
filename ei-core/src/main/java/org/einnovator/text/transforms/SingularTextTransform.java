/**
 * 
 */
package org.einnovator.text.transforms;

import org.einnovator.text.TextTransform;


/**
 * A SingularTextTransform.
 *
 * @author Jorge Simão
 */
public class SingularTextTransform implements TextTransform {

	private static SingularTextTransform instance;
	
	//
	// TextTransform implementation
	//
	
	@Override
	public String transform(String text) {
		text = text.trim();
		if (text.endsWith("s")) {
			return text.substring(0, text.length()-1);
		}
		return text;
	}

	//
	// Static utility
	//

	public static SingularTextTransform getInstance() {
		if (instance==null) {
			instance = new SingularTextTransform();
		}
		return instance;
	}
	
}
