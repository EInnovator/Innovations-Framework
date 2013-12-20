/**
 * 
 */
package org.einnovator.text;


/**
 * A IdentityTextTransformer.
 *
 * @author Jorge Simão
 */
public class IdentityTextTransform implements TextTransform {

	private static IdentityTextTransform instance;
	
	//
	// TextTransform implementation
	//
	
	@Override
	public String transform(String text) {
		return text;
	}

	//
	// Static utility
	//

	public static IdentityTextTransform getInstance() {
		if (instance==null) {
			instance = new IdentityTextTransform();
		}
		return instance;
	}

}
