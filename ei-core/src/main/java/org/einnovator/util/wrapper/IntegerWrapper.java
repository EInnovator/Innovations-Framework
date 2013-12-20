/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * A IntegerWrapper.
 *
 * @author Jorge Simão
 */
public class IntegerWrapper extends ComparableWrapper<Integer> {

	private static final long serialVersionUID = 1L;

	protected Integer value;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of IntegerWrapper.
	 *
	 */
	public IntegerWrapper() {
	}

	
	/**
	 * Create instance of IntegerWrapper.
	 *
	 * @param value
	 */
	public IntegerWrapper(Integer value) {
		super(value);
	}

	//
	// Other method
	//

	public int intValue() {
		return value.intValue();
	}
	
}
