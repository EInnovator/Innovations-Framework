/**
 * 
 */
package org.einnovator.text.transforms;


/**
 * AA CamelCaseToUnderscoreTextTransform.
 *
 * @author Jorge Simão
 */
public class CamelCaseToUnderscoreTextTransform extends CamelCaseToWordsTextTransform {

	private static CamelCaseToUnderscoreTextTransform instance;

	protected int capitalize;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of CamelCaseToWordsTextTransform.
	 *
	 */
	public CamelCaseToUnderscoreTextTransform() {
		super("_");
	}
	
	
	/**
	 * Create instance of CamelCaseToWordsTextTransform.
	 *
	 * @param wordSeparator
	 */
	public CamelCaseToUnderscoreTextTransform(String wordSeparator) {
		super(wordSeparator);
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of capitalize.
	 *
	 * @return the capitalize
	 */
	public int getCapitalize() {
		return capitalize;
	}


	/**
	 * Set the value of capitalize.
	 *
	 * @param capitalize the capitalize to set
	 */
	public void setCapitalize(int capitalize) {
		this.capitalize = capitalize;
	}

	
	//
	// TextTransform implementation
	//
	
	@Override
	public String transform(String text) {
		text = super.transform(text);
		if (capitalize>0) {
			text = text.toUpperCase();
		} else if (capitalize<0) {
			text = text.toLowerCase();
		}
		return text;
	}

	//
	// Static utility
	//


	public static CamelCaseToUnderscoreTextTransform getInstance() {
		if (instance==null) {
			instance = new CamelCaseToUnderscoreTextTransform();
		}
		return instance;
	}

}
