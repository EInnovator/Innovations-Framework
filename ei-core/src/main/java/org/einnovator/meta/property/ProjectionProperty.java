/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.reflect.Type;

import org.einnovator.meta.Property;
import org.einnovator.meta.property.PropertyWrapper;


/**
 * A ProjectionProperty.
 *
 * @author Jorge Simão
 */
public class ProjectionProperty extends PropertyWrapper<Object> {

	protected Property<?> projectionProperty;
	
	//
	// Constructors
	//


	/**
	 * Create instance of ProjectionProperty.
	 *
	 * @param property
	 * @param projectionProperty
	 */
	@SuppressWarnings("unchecked")
	public ProjectionProperty(Property<?> property, Property<?> projectionProperty) {
		super((Property<Object>)property);
		this.projectionProperty = projectionProperty;
	}

	//
	// Property<T> implementation
	//
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<Object> getType() {
		return (Class<Object>)projectionProperty.getType();
	}

	@Override
	public Type getGenericType() {
		return projectionProperty.getGenericType();
	}


	
	@Override
	public void setValue(Object target, Object value) {
		Object target0 = super.getValue(target);
		if (target0!=null) {
			getProperty().setValue(target0, value);			
		}
	}

	@Override
	public Object getValue(Object target) {
		Object target0 = super.getValue(target);
		return target0!=null ? projectionProperty.getValue(target0) : null;
	}
	
}
