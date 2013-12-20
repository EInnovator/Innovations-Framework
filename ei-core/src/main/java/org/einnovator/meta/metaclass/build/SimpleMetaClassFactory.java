/**
 * 
 */
package org.einnovator.meta.metaclass.build;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;

/**
 * A {@code MetaClassFactory} that uses a {@code SimpleMetaClassBuilder} to create
 *  instances of {@code SimpleMetaClass}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see SimpleMetaClassBuilder
 * @see DefaultMetaClassFactory
 */
public class SimpleMetaClassFactory extends MetaClassFactoryBase {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code SimpleMetaClassFactory}.
	 *
	 */
	public SimpleMetaClassFactory() {
	}

	//
	// MetaClassFactory Implementation
	//

	/**
	 * Create a {@code SimpleMetaClassBuilder} to build the {@code MetaClass}.
	 * 
	 * @param parent the parent {@code MetaClass}
	 * @param theClass the Java class for the {@code MetaClass}
	 * @return the {@code SimpleMetaClassBuilder}
	 */
	@Override
	protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
		return new SimpleMetaClassBuilder<T>(parent, theClass);
	}

}
