/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * AA BooleanWrapper.
 *
 * @author Jorge Simão
 */
public class BooleanWrapper extends ComparableWrapper<Boolean> {

	private static final long serialVersionUID = 1L;

	//
	// Constructors
	//
	
	/**
	 * Create instance of BooleanWrapper.
	 *
	 */
	public BooleanWrapper() {
	}

	
	/**
	 * Create instance of BooleanWrapper.
	 *
	 * @param value
	 */
	public BooleanWrapper(Boolean value) {
		super(value);
	}

	//
	// Other method
	//

	public boolean booleanValue() {
		return delegate!=null ? delegate.booleanValue() : null;
	}
	
}
