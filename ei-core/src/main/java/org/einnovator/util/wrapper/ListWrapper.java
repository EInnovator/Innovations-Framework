/**
 * 
 */
package org.einnovator.util.wrapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A ListWrapper.
 *
 * @author Jorge Simão
 */
public class ListWrapper<T> extends GenericWrapper<List<T>> implements List<T> {

	//
	// Constructors
	//
	
	/**
	 * Create instance of ListWrapper.
	 *
	 */
	public ListWrapper() {
	}
	
	/**
	 * Create instance of ListWrapper.
	 *
	 * @param delegate
	 */
	public ListWrapper(List<T> delegate) {
		super(delegate);
	}


	//
	// List<T> implementation
	//

	@Override
	public boolean add(T value) {
		return getDelegate().add(value);
	}


	@Override
	public void add(int index, T value) {
		getDelegate().add(index, value);
	}

	@Override
	public boolean addAll(Collection<? extends T> values) {
		return getDelegate().addAll(values);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> values) {
		return getDelegate().addAll(index, values);
	}

	@Override
	public void clear() {
		getDelegate().clear();
	}

	@Override
	public boolean contains(Object value) {
		return getDelegate().contains(value);
	}

	@Override
	public boolean containsAll(Collection<?> values) {
		return getDelegate().containsAll(values);
	}

	@Override
	public T get(int index) {
		return getDelegate().get(index);
	}

	@Override
	public int indexOf(Object value) {
		return getDelegate().indexOf(value);
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
	public int lastIndexOf(Object value) {
		return getDelegate().lastIndexOf(value);
	}

	@Override
	public ListIterator<T> listIterator() {
		return getDelegate().listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return getDelegate().listIterator(index);
	}

	@Override
	public boolean remove(Object value) {
		return getDelegate().remove(value);
	}

	@Override
	public T remove(int index) {
		return getDelegate().remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> values) {
		return getDelegate().removeAll(values);
	}

	@Override
	public boolean retainAll(Collection<?> values) {
		return getDelegate().retainAll(values);
	}

	@Override
	public T set(int index, T value) {
		return getDelegate().set(index, value);
	}

	@Override
	public int size() {
		return getDelegate().size();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return getDelegate().subList(fromIndex, toIndex);
	}
	@Override
	public Object[] toArray() {
		return getDelegate().toArray();
	}

	@Override
	public <U> U[] toArray(U[] array) {
		return getDelegate().toArray(array);
	}

	//
	// Object overrides
	//
	
	@Override
	public boolean equals(Object obj) {
		return getDelegate().equals(obj);
	}	
	
	@Override
	public int hashCode() {
		return getDelegate().hashCode();
	}

	@Override
	public String toString() {
		return getDelegate().toString();
	}
	

}
