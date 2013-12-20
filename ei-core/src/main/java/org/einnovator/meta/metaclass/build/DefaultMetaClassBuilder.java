/**
 * 
 */
package org.einnovator.meta.metaclass.build;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.Property;
import org.einnovator.meta.metaclass.DefaultMetaClass;

/**
 * A {@code DefaultMetaClassBuilder}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class DefaultMetaClassBuilder<T> extends SimpleMetaClassBuilder<T> {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code DefaultMetaClassBuilder}.
	 *
	 * @param parent
	 * @param theClass
	 */
	public DefaultMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
		super(parent, theClass);
	}
	
	//
	// MetaClassBuilder Implementation
	//
	
	@Override
	protected MetaClass<T> createMetaClass(Property<?>[] declaredProperties, Property<?>[] allProperties) {
		return new DefaultMetaClass<T>(getParent(), getTheClass(), declaredProperties, allProperties);		
	}

}
