package org.einnovator.util.contextual;

/**
 * AA ContextualMap.
 *
 * @author Jorge Simão
 */
public interface ContextualMap<T> {

	void set(T value);

	T get();

	void remove();

	Object getCurrentContext();
	
}
