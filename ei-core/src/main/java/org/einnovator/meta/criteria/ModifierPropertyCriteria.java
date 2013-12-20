/**
 * 
 */
package org.einnovator.meta.criteria;

import org.einnovator.meta.Property;
import org.einnovator.meta.PropertyCriteria;

/**
 * A ModifierPropertyCriteria.
 *
 * @author Jorge Simão
 */
public class ModifierPropertyCriteria implements PropertyCriteria {

	protected int modifiers;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of ModifierPropertyCriteria.
	 *
	 * @param modifiers
	 */
	public ModifierPropertyCriteria(int modifiers) {
		this.modifiers = modifiers;
	}
	
	//
	// Getters and setters
	//
	
	/**
	 * Get the value of modifiers.
	 *
	 * @return the modifiers
	 */
	public int getModifiers() {
		return modifiers;
	}

	/**
	 * Set the value of modifiers.
	 *
	 * @param modifiers the modifiers to set
	 */
	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	
	//
	// PropertyCriteria implementation
	//
	
	@Override
	public boolean match(Property<?> property) {
		return (property.getModifiers() & modifiers)!=0;
	}
}
