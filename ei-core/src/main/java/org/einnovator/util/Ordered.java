/**
 * 
 */
package org.einnovator.util;

/**
 * A component that defines its ordering position within the context of other components.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public interface Ordered {

	public static final int DEFAULT_ORDER = 0;
	
	public static final int MAX_ORDER = Integer.MAX_VALUE;
	
	public static final int MIN_ORDER = Integer.MIN_VALUE;
	
	/**
	 * Get ordering position of a component within the context of other components.
	 * 
	 * @return the order value
	 */
	int getOrder();
}
