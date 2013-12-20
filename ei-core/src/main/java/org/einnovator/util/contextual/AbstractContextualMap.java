/**
 * 
 */
package org.einnovator.util.contextual;

import java.util.HashMap;
import java.util.Map;

/**
 * AA ContextualMap.
 *
 * @author Jorge Simão
 */
abstract public class AbstractContextualMap<T> implements ContextualMap<T> {

	protected Map<Object, T> map = new HashMap<Object, T>();
	
	/**
	 * Create instance of ContextualMap.
	 *
	 */
	public AbstractContextualMap() {
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
