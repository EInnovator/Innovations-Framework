/**
 * 
 */
package org.einnovator.meta.metaclass;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassFactory;
import org.einnovator.meta.MetaClassRegistry;


/**
 * A DefaultMetaClassDirectory.
 *
 * @author Jorge Simão
 */
public class DefaultMetaClassRegistry implements MetaClassRegistry {
	
	protected MetaClassFactory factory;
	
	protected Map<Class<?>, MetaClass<?>> metaClassMap = new HashMap<Class<?>, MetaClass<?>>();

	protected Map<String, MetaClass<?>> namesMap = new HashMap<String, MetaClass<?>>();

	//
	// Constructors
	//
	
	public DefaultMetaClassRegistry(MetaClassFactory factory) {
		this.factory = factory;
	}
	
	//
	// MetaClassDirectory implementation
	//
	
	@Override
	public <T> MetaClass<T> getMetaClass(Class<T> theClass) {
		if (theClass==null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		MetaClass<T> metaClass = (MetaClass<T>)metaClassMap.get(theClass);
		if (metaClass==null) {
			return addMetaClassFor(theClass);
		} else {
			return metaClass;			
		}
	}

	@Override
	public Collection<MetaClass<?>> getMetaClasses() {
		return metaClassMap.values();
	}

	@Override
	public MetaClass<?> forName(String name) {
		return namesMap.get(name);
	}

	@Override
	public void addMetaClass(MetaClass<?> metaClass) {
		metaClassMap.put(metaClass.getTheClass(), metaClass);
		namesMap.put(metaClass.getName(), metaClass);
	}

	@Override
	public <T> MetaClass<T> addMetaClassFor(Class<T> theClass) {	
		MetaClass<T> metaClass = factory.createMetaClass(getParentMetaClass(theClass), theClass);
		addMetaClass(metaClass);
		return metaClass;
	}

	@Override
	public <T> MetaClass<T> createMetaClassFor(Class<T> theClass) {	
		return factory.createMetaClass(createParentMetaClass(theClass), theClass);
	}

	protected MetaClass<?> createParentMetaClass(Class<?> theClass) {	
		Class<?> superClass = getSuperClass(theClass);
		return superClass!=null ? createMetaClassFor(superClass) : null;
	}

	protected MetaClass<?> getParentMetaClass(Class<?> theClass) {
		Class<?> superClass = getSuperClass(theClass);
		return superClass!=null ? getMetaClass(superClass) : null;
	}

	protected Class<?> getSuperClass(Class<?> theClass) {
		return theClass.getSuperclass();
	}

	@Override
	public MetaClassFactory getMetaClassFactory() {
		return factory;
	}

	@Override
	public void setMetaClassFactory(MetaClassFactory metaClassFactory) {
		this.factory = metaClassFactory;
	}

	@Override
	public boolean contains(MetaClass<?> metaClass) {
		MetaClass<?> metaClass2 = metaClassMap.get(metaClass.getTheClass());
		return metaClass2!=null && metaClass==metaClass2;
	}

	@Override
	public boolean contains(Class<?> theClass) {
		return metaClassMap.containsKey(theClass);
	}

	@Override
	public boolean contains(String name) {
		return namesMap.containsKey(name);
	}

	/**
	 * @see org.einnovator.meta.MetaClassRegistry#getMetaClassMap()
	 */
	@Override
	public Map<Class<?>, MetaClass<?>> getMetaClassMap() {
		return metaClassMap;
	}


}
