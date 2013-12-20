/**
 * 
 */
package org.einnovator.text.transforms;


import org.einnovator.text.TextTransform;
import org.einnovator.util.CharacterUtil;

/**
 * A WordsToCamelCaseTextTransform.
 *
 * @author Jorge Simão
 */
public class WordsToCamelCaseTextTransform implements TextTransform {

	private static WordsToCamelCaseTextTransform instance;
	
	private String wordSeparator = " ";
	
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of WordsToCamelCaseTextTransform.
	 *
	 */
	public WordsToCamelCaseTextTransform() {
	}
	
	
	/**
	 * Create instance of WordsToCamelCaseTextTransform.
	 *
	 * @param wordSeparator
	 */
	public WordsToCamelCaseTextTransform(String wordSeparator) {
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
			if (wordSeparator.charAt(0)==text.charAt(i)) {
				while (i<text.length() &&  wordSeparator.charAt(0)==text.charAt(i)) {
					i++;
				}
				if (i<text.length()) {
					sb.append(CharacterUtil.toUpperCase(text.charAt(i)));					
				}
			} else {
				sb.append(text.charAt(i));				
			}
		}
		return sb.toString();
	}

	//
	// Static utility
	//

	public static WordsToCamelCaseTextTransform getInstance() {
		if (instance==null) {
			instance = new WordsToCamelCaseTextTransform();
		}
		return instance;
	}

}
