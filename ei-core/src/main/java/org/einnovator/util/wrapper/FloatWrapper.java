/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * A FloatWrapper.
 *
 * @author Jorge Simão
 */
public class FloatWrapper extends ComparableWrapper<Float> {

	private static final long serialVersionUID = 1L;

	//
	// Constructors
	//
	
	/**
	 * Create instance of FloatWrapper.
	 *
	 */
	public FloatWrapper() {
	}

	
	/**
	 * Create instance of FloatWrapper.
	 *
	 * @param value
	 */
	public FloatWrapper(Float value) {
		super(value);
	}

	//
	// Other method
	//

	public float floatValue() {
		return delegate.floatValue();
	}
	
}
