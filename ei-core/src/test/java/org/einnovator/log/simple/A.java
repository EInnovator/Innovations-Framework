/**
 * 
 */
package org.einnovator.log.simple;

import org.einnovator.log.Logger;
import org.einnovator.log.LoggerUtil;

/**
 * AA {@code AA}.
 *
 * @author Jorge Simão
 */
public class A {
	
	public static Logger logger = LoggerUtil.getLogger(A.class);

	/**
	 * Create instance of {@code AA}.
	 *
	 */
	public A() {
		logger.info(this, "(constructor)");
	}

}
