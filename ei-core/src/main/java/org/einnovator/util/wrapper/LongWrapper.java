/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * A LongWrapper.
 *
 * @author Jorge Simão
 */
public class LongWrapper extends ComparableWrapper<Long> {

	private static final long serialVersionUID = 1L;

	protected Long value;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of LongWrapper.
	 *
	 */
	public LongWrapper() {
	}

	
	/**
	 * Create instance of LongWrapper.
	 *
	 * @param value
	 */
	public LongWrapper(Long value) {
		super(value);
	}

	//
	// Other method
	//

	public long longValue() {
		return value.longValue();
	}
	
}
