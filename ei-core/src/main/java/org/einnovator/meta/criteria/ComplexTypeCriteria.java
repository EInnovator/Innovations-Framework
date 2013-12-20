/**
 * 
 */
package org.einnovator.meta.criteria;

import org.einnovator.meta.criteria.TypeCriteria;

/**
 * AA ComplexTypeCriteria.
 *
 * @author Jorge Simão
 */
public class ComplexTypeCriteria implements TypeCriteria {

	//
	// TypeCriteria implementation
	//
	
	@Override
	public boolean check(Class<?> type) {
		String name = type.getName();
		if (SimpleTypeCriteria.isSimple(type)) {
			return false;
		}
		if (name.startsWith("java.") || name.startsWith("javax.")) {
			return false;
		}
		return true;
	}

	//
	// Static utilities
	//
	
	private static ComplexTypeCriteria instance;
	
	public static ComplexTypeCriteria getInstance() {
		if (instance==null) {
			instance = new ComplexTypeCriteria();
		}
		return instance;
	}
}
