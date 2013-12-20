/**
 * 
 */
package org.einnovator.text.transforms;


import static org.einnovator.util.CharacterUtil.isUpperCase;

import org.einnovator.text.TextTransform;

/**
 * AA CamelCaseToWordsTextTransform.
 *
 * @author Jorge Simão
 */
public class CamelCaseToWordsTextTransform implements TextTransform {

	private static CamelCaseToWordsTextTransform instance;
	
	protected String wordSeparator = " ";
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of CamelCaseToWordsTextTransform.
	 *
	 */
	public CamelCaseToWordsTextTransform() {
	}
	
	
	/**
	 * Create instance of CamelCaseToWordsTextTransform.
	 *
	 * @param wordSeparator
	 */
	public CamelCaseToWordsTextTransform(String wordSeparator) {
		this.wordSeparator = wordSeparator;
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of wordSeparator.
	 *
	 * @return the wordSeparator
	 */
	public String getWordSeparator() {
		return wordSeparator;
	}

	/**
	 * Set the value of wordSeparator.
	 *
	 * @param wordSeparator the wordSeparator to set
	 */
	public void setWordSeparator(String wordSeparator) {
		this.wordSeparator = wordSeparator;
	}

	
	//
	// TextTransform implementation
	//
	
	@Override
	public String transform(String text) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<text.length(); i++) {
			if (i>0 && isUpperCase(text.charAt(i)) && !isUpperCase(text.charAt(i-1))) {
				sb.append(wordSeparator);
			}
			sb.append(text.charAt(i));
		}
		return sb.toString();
	}

	//
	// Static utility
	//

	public static CamelCaseToWordsTextTransform getInstance() {
		if (instance==null) {
			instance = new CamelCaseToWordsTextTransform();
		}
		return instance;
	}

}
