/**
 * 
 */
package org.einnovator.meta.criteria;

import org.einnovator.meta.Property;
import org.einnovator.meta.PropertyCriteria;

/**
 * A SimplePropertyCriteria.
 *
 * @author Jorge Simão
 */
public class SimplePropertyCriteria implements PropertyCriteria {

	//
	// PropertyCriteria implementation
	//
	
	@Override
	public boolean match(Property<?> property) {
		return SimpleTypeCriteria.isSimple(property.getType());
	}

	//
	// Static utilities
	//
	
	private static SimplePropertyCriteria instance;
	
	public static SimplePropertyCriteria getInstance() {
		if (instance==null) {
			instance = new SimplePropertyCriteria();
		}
		return instance;
	}
}
