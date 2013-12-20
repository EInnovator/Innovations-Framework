/**
 * 
 */
package org.einnovator.util.wrapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * A SetWrapper.
 *
 * @author Jorge Simão
 */
public class SetWrapper<T> extends GenericWrapper<Set<T>> implements Set<T> {

	//
	// Constructors
	//
	
	/**
	 * Create instance of SetWrapper.
	 *
	 */
	public SetWrapper() {
		super();
	}

	/**
	 * Create instance of SetWrapper.
	 *
	 * @param delegate
	 */
	public SetWrapper(Set<T> delegate) {
		super(delegate);
	}

	
	//
	// Set<T> implementation
	//
	
	@Override
	public boolean add(T element) {
		return getDelegate().add(element);
	}

	@Override
	public boolean addAll(Collection<? extends T> collection) {
		return getDelegate().addAll(collection);
	}

	@Override
	public void clear() {
		getDelegate().clear();
	}

	@Override
	public boolean contains(Object element) {
		return getDelegate().contains(element);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return getDelegate().containsAll(collection);
	}

	@Override
	public boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return getDelegate().iterator();
	}

	@Override
	public boolean remove(Object element) {
		return getDelegate().remove(element);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return getDelegate().removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return getDelegate().retainAll(collection);
	}

	@Override
	public int size() {
		return getDelegate().size();
	}

	@Override
	public Object[] toArray() {
		return getDelegate().toArray();
	}

	@Override
	public <U> U[] toArray(U[] array) {
		return getDelegate().toArray(array);
	}

}
