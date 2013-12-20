/**
 * 
 */
package org.einnovator.text.transforms;

import org.einnovator.text.TextTransform;

/**
 * A {@code SubStringTransform}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class EllipsisTransform implements TextTransform {

	private int maxLength = -1;
	
	private String ellipsis = "...";
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code SubStringTransform}.
	 *
	 */
	public EllipsisTransform() {
	}
	
	
	/**
	 * Create instance of {@code EllipsisTransform}.
	 *
	 * @param maxLength
	 * @param ellipsis
	 */
	public EllipsisTransform(int maxLength, String ellipsis) {
		this.maxLength = maxLength;
		this.ellipsis = ellipsis;
	}
	
	/**
	 * Create instance of {@code EllipsisTransform}.
	 *
	 * @param maxLength
	 */
	public EllipsisTransform(int maxLength) {
		this.maxLength = maxLength;
	}



	//
	// Getters and setters
	//
	

	/**
	 * Get the value of {@code maxLength}.
	 *
	 * @return the value of {@code maxLength}
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * Set the value of {@code maxLength}.
	 *
	 * @param maxLength the {@code maxLength}
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Get the value of {@code ellipsis}.
	 *
	 * @return the value of {@code ellipsis}
	 */
	public String getEllipsis() {
		return ellipsis;
	}

	/**
	 * Set the value of {@code ellipsis}.
	 *
	 * @param ellipsis the {@code ellipsis}
	 */
	public void setEllipsis(String ellipsis) {
		this.ellipsis = ellipsis;
	}

	//
	// TextTransform implementation
	//

	
	/**
	 * @see org.einnovator.text.TextTransform#transform(java.lang.String)
	 */
	@Override
	public String transform(String text) {
		if (text==null) {
			return null;
		}
		if (maxLength<=0) {
			return text;
		}
		if (text.length()>maxLength) {
			int n = ellipsis!=null ? ellipsis.length() : 0;
			text = text.substring(0, maxLength - n);			
			if (ellipsis!=null) {
				text = text + ellipsis;
			}
		}
		return text;
	}
}
