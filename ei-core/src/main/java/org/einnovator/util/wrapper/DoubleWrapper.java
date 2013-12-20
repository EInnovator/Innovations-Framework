/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * A DoubleWrapper.
 *
 * @author Jorge Simão
 */
public class DoubleWrapper extends ComparableWrapper<Double> {

	private static final long serialVersionUID = 1L;

	//
	// Constructors
	//
	
	/**
	 * Create instance of DoubleWrapper.
	 *
	 */
	public DoubleWrapper() {
	}

	
	/**
	 * Create instance of DoubleWrapper.
	 *
	 * @param value
	 */
	public DoubleWrapper(Double value) {
		super(value);
	}

	//
	// Other method
	//

	public double doubleValue() {
		return delegate.doubleValue();
	}
	
}
