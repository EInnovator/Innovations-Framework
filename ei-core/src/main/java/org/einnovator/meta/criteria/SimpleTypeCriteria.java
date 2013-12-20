/**
 * 
 */
package org.einnovator.meta.criteria;

import org.einnovator.meta.criteria.TypeCriteria;
import org.einnovator.util.types.TypeUtil;

/**
 * A SimpleTypeCriteria.
 *
 * @author Jorge Simão
 */
public class SimpleTypeCriteria implements TypeCriteria {

	//
	// TypeCriteria implementation
	//
	
	@Override
	public boolean check(Class<?> type) {
		return isSimple(type);
	}

	//
	// Static utilities
	//
	
	public static boolean isSimple(Class<?> type) {
		return TypeUtil.isSimple(type);
	}
	
	private static SimpleTypeCriteria instance;
	
	public static SimpleTypeCriteria getInstance() {
		if (instance==null) {
			instance = new SimpleTypeCriteria();
		}
		return instance;
	}
}
