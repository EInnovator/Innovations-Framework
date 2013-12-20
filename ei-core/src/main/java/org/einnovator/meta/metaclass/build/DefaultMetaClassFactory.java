/**
 * 
 */
package org.einnovator.meta.metaclass.build;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;

/**
 * A {@code MetaClassFactory} that uses a {@code DefaultMetaClassFactory} to create
 *  instances of {@code DefaultMetaClass}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see DefaultMetaClassBuilder
 * @see SimpleMetaClassFactory
 */
public class DefaultMetaClassFactory extends MetaClassFactoryBase {

	//
	// Constructors
	//
	
	public DefaultMetaClassFactory() {
	}
	
	//
	// MetaClassFactoryBase Implementation
	//
	
	/**
	 * @see org.einnovator.meta.metaclass.build.SimpleMetaClassFactory#createMetaClassBuilder(org.einnovator.meta.MetaClass, java.lang.Class)
	 */
	@Override
	protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
		return new DefaultMetaClassBuilder<T>(parent, theClass);
	}

}
