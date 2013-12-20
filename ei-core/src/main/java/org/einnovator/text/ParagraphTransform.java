/**
 * 
 */
package org.einnovator.text;

/**
 * A {@code ParagraphTransform}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class ParagraphTransform implements TextTransform {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ParagraphTransform}.
	 *
	 */
	public ParagraphTransform() {
	}
	
	//
	// Getters and setters
	//
	
	
	//
	// TextTransform implementation
	//
	

	@Override
	public String transform(String text) {
		text = text.replaceAll("\\s+", " ");
		text = text.replaceAll("\n", " ");
		text = text.trim();
		return text;
	}

	
	//
	// Static utility
	//

	private static ParagraphTransform instance;

	public static ParagraphTransform getInstance() {
		if (instance==null) {
			instance = new ParagraphTransform();
		}
		return instance;
	}

}
