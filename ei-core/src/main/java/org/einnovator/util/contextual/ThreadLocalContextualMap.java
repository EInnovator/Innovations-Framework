/**
 * 
 */
package org.einnovator.util.contextual;

/**
 * A ThreadLocalContextualMap.
 *
 * @author Jorge Simão
 */
public class ThreadLocalContextualMap<T> implements ContextualMap<T> {
	
	ThreadLocal<T> map = new ThreadLocal<T>();

	/**
	 * Create instance of ThreadLocalContextualMap.
	 *
	 */
	public ThreadLocalContextualMap() {
	}

	@Override
	public void set(T value) {
		map.set(value);
	}

	@Override
	public T get() {
		return map.get();
	}

	@Override
	public void remove() {
		map.set(null);
		
	}

	@Override
	public Object getCurrentContext() {
		return Thread.currentThread();
	}
}
