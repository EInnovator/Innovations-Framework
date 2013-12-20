/**
 * 
 */
package org.einnovator.util.contextual;

import java.util.HashMap;
import java.util.Map;

/**
 * AA {@code AbstractNestedContextualMap}.
 *
 * @param <T>
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
abstract public class AbstractNestedContextualMap<T> implements ContextualMap<T> {

	protected Map<Object, T> map = new HashMap<Object, T>();
	
	/**
	 * Create instance of ContextualMap.
	 *
	 */
	public AbstractNestedContextualMap() {
	}
	
	//
	// Getters and setters
	//
	
	synchronized public void set(T value) {
		map.put(getCurrentContext(), value);
	}

	public T get() {
		return map.get(getCurrentContext());
	}

	public void remove() {
		map.remove(getCurrentContext());
	}

}
