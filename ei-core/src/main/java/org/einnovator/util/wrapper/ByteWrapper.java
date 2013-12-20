/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * AA ByteWrapper.
 *
 * @author Jorge Simão
 */
public class ByteWrapper extends ComparableWrapper<Byte> {

	private static final long serialVersionUID = 1L;

	protected Byte value;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of ByteWrapper.
	 *
	 */
	public ByteWrapper() {
	}

	
	/**
	 * Create instance of ByteWrapper.
	 *
	 * @param value
	 */
	public ByteWrapper(Byte value) {
		super(value);
	}

	//
	// Other methods
	//

	public byte byteValue() {
		return value.byteValue();
	}
	
}
