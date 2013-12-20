/**
 * 
 */
package org.einnovator.meta.criteria;

import org.einnovator.meta.Property;
import org.einnovator.meta.PropertyCriteria;

/**
 * AA ComplexPropertyCriteria.
 *
 * @author Jorge Simão
 */
public class ComplexPropertyCriteria implements PropertyCriteria {

	//
	// PropertyCriteria implementation
	//

	@Override
	public boolean match(Property<?> property) {
		return !SimpleTypeCriteria.isSimple(property.getType());
	}

	//
	// Static utilities
	//
	
	private static ComplexPropertyCriteria instance;
	
	public static ComplexPropertyCriteria getInstance() {
		if (instance==null) {
			instance = new ComplexPropertyCriteria();
		}
		return instance;
	}


}
