/**
 * 
 */
package org.einnovator.util.contextual;

/**
 * A ThreadContextualMap.
 *
 * @author Jorge Simão
 */
public class ThreadContextualMap<T> extends AbstractContextualMap<T> {

	@Override
	public Object getCurrentContext() {
		return Thread.currentThread();
	}

}
