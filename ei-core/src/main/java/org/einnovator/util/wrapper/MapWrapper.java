/**
 * 
 */
package org.einnovator.util.wrapper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A MapWrapper.
 *
 * @author Jorge Simão
 */
public class MapWrapper<K,V> extends GenericWrapper<Map<K,V>> implements Map<K,V> {

	//
	// Constructors
	//
	
	/**
	 * Create instance of MapWrapper.
	 *
	 */
	public MapWrapper() {
	}

	/**
	 * Create instance of MapWrapper.
	 *
	 * @param delegate
	 */
	public MapWrapper(Map<K,V> delegate) {
		super(delegate);
	}

	
	//
	// Map<K,V> implementation
	//
	
	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return delegate.entrySet();
	}

	@Override
	public V get(Object key) {
		return delegate.get(key);
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return delegate.keySet();
	}

	@Override
	public V put(K key, V value) {
		return delegate.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		delegate.putAll(map);
	}

	@Override
	public V remove(Object key) {
		return delegate.remove(key);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Collection<V> values() {
		return delegate.values();
	}

}
