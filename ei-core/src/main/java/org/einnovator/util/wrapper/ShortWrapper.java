/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * A ShortWrapper.
 *
 * @author Jorge Simão
 */
public class ShortWrapper extends ComparableWrapper<Short> {

	private static final long serialVersionUID = 1L;

	protected Short value;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of ShortWrapper.
	 *
	 */
	public ShortWrapper() {
	}

	
	/**
	 * Create instance of ShortWrapper.
	 *
	 * @param value
	 */
	public ShortWrapper(Short value) {
		super(value);
	}

	//
	// Other method
	//

	public short shortValue() {
		return value.shortValue();
	}
	
}
