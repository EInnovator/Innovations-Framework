/**
 * 
 */
package org.einnovator.util.wrapper;

/**
 * A GenericWrapper.
 *
 * @author Jorge Simão
 * @param <T>
 */
public class GenericWrapper<T> {

	protected T delegate;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of GenericWrapper.
	 *
	 */
	public GenericWrapper() {
	}

	
	/**
	 * Create instance of GenericWrapper.
	 *
	 * @param delegate
	 */
	public GenericWrapper(T delegate) {
		this.delegate = delegate;
	}

	//
	// Getters and setters
	//

	/**
	 * Get the value of delegate.
	 *
	 * @return the delegate
	 */
	public T getDelegate() {
		return delegate;
	}

	/**
	 * Set the value of value.
	 *
	 * @param value the value to set
	 */
	public void setDelegate(T delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean equals(Object obj) {
		if (delegate!=null) {
			return delegate.equals(obj);
		} else {
			return obj==null;
		}
	}
	
	@Override
	public String toString() {
		return delegate!=null ? delegate.toString() : null;
	}


}
