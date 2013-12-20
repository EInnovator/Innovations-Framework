/**
 * 
 */
package org.einnovator.util;

/**
 * AA Criteria.
 *
 * @author Jorge Simão
 */
public interface Criteria<T> {
	boolean match(T obj);
}
