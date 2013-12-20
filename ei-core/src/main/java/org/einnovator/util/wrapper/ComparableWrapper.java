/**
 * 
 */
package org.einnovator.util.wrapper;

import java.io.Serializable;

/**
 * AA ComparableWrapper.
 *
 * @author Jorge Simão
 * @param <T>
 */
public class ComparableWrapper<T extends Comparable<T>> extends GenericWrapper<T> implements Serializable, Comparable<T> {

	private static final long serialVersionUID = 1L;

	//
	// Constructors
	//
	
	/**
	 * Create instance of ComparableWrapper.
	 *
	 */
	public ComparableWrapper() {
	}

	
	/**
	 * Create instance of ComparableWrapper.
	 *
	 * @param value
	 */
	public ComparableWrapper(T value) {
		super(value);
	}

	//
	// Comparable<T> implementation
	//
	
	@Override
	public int compareTo(T value) {
		return this.delegate.compareTo(value);
	}
	

}
