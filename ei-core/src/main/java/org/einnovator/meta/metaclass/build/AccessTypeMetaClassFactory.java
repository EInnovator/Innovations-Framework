/**
 * 
 */
package org.einnovator.meta.metaclass.build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * An implementation of {@code DefaultMetaClassFactory} for which
 * the default access type of field or getter/setter pairs can be configured.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see SimpleMetaClassBuilder
 * @see DefaultMetaClassFactory
 */
public class AccessTypeMetaClassFactory extends DefaultMetaClassFactory {

	protected boolean fieldAccess;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code AccessTypeMetaClassFactory}.
	 *
	 * @param fieldAccess flag specifying if field access is the default access type
	 */
	public AccessTypeMetaClassFactory(boolean fieldAccess) {
		this.fieldAccess = fieldAccess;
	}

	//
	// MetaClassFactoryBase overrides
	//
	
	/**
	 * @see org.einnovator.meta.metaclass.build.SimpleMetaClassFactory#includeField(java.lang.reflect.Field)
	 */
	@Override
	protected boolean includeField(Class<?> theClass, Field field) {
		if (fieldAccess) {
			return includeMember(theClass, field);
		} else {
			return false;
		}
	}

	/**
	 * @see org.einnovator.meta.metaclass.build.SimpleMetaClassFactory#includeProperty(java.lang.reflect.Method, java.lang.reflect.Method)
	 */
	@Override
	protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
		if (!fieldAccess) {
			return includeMember(theClass, getter);
		} else {
			return false;
		}
	}

}
