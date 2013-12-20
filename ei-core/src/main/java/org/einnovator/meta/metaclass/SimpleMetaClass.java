/**
 * 
 */
package org.einnovator.meta.metaclass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaException;
import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.NoSuchPropertyException;
import org.einnovator.meta.Property;
import org.einnovator.meta.metaclass.DefaultMetaClass;


/**
 * Simple implementation of {@link org.einnovator.meta.MetaClass}.
 * 
 *
 * @see {@link DefaultMetaClass} for an implementation {@link org.einnovator.meta.MetaClass} that uses
 *  a {@link java.util.Map} to resolve properties names.
 *  
 * @author Jorge Simão
 */
public class SimpleMetaClass<T> implements MetaClass<T>, AnnotatedElement {

	protected Class<T> theClass;

	protected MetaClass<?> parent;
	
	protected Property<?>[] declaredProperties;
	
	protected String unqualifiedName;
	
	protected Property<?>[] allProperties;
	
	//
	// Constructors
	//

	/**
	 * Create instance of SimpleMetaClass.
	 *
	 * @param parent
	 * @param theClass
	 * @param declaredProperties
	 * @param allProperties
	 */
	public SimpleMetaClass(MetaClass<?> parent, Class<T> theClass, Property<?>[] declaredProperties, Property<?>[] allProperties) {
		this.theClass = theClass;
		this.declaredProperties = declaredProperties;
		this.allProperties = allProperties;
		this.unqualifiedName = MetaUtil.getUnqualifiedName(theClass);
		this.parent = parent;
	}

	/**
	 * Create instance of SimpleMetaClass.
	 *
	 * @param theClass
	 * @param declaredProperties
	 * @param allProperties
	 */
	public SimpleMetaClass(Class<T> theClass, Property<?>[] declaredProperties, Property<?>[] allProperties) {
		this(null, theClass, declaredProperties, allProperties);
	}

	/**
	 * Create instance of SimpleMetaClass.
	 *
	 * @param theClass
	 * @param declaredProperties
	 * @param allProperties
	 */
	public SimpleMetaClass(Class<T> theClass, Property<?>[] declaredProperties) {
		this(theClass, declaredProperties, declaredProperties);
	}


	/**
	 * Create instance of SimpleMetaClass.
	 *
	 * @param parent
	 * @param theClass
	 * @param declaredProperties
	 */
	public SimpleMetaClass(MetaClass<?> parent, Class<T> theClass, Property<?>[] declaredProperties) {
		this(parent, theClass, declaredProperties, declaredProperties);
	}

	//
	// MetaClass<T> implementation
	//

	@Override
	public String getName() {
		return theClass.getName();
	}

	@Override
	public String getUnqualifiedName() {
		return unqualifiedName;
	}

	@Override
	public Class<T> getTheClass() {
		return theClass;
	}

	@Override
	public MetaClass<?> getParent() {
		return parent;
	}
	
	@Override
	public Property<?>[] getDeclaredProperties() {
		return declaredProperties;
	}

	@Override
	public Property<?>[] getAllProperties() {
		return allProperties;
	}

	@Override
	public Property<?> getProperty(String name) {
		for (Property<?> property: allProperties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P> Property<P> getProperty(String name, Class<P> type) {
		return (Property<P>)getProperty(name);
	}
	
	@Override
	public Property<?> getDeclaredProperty(String name) {
		for (Property<?> property: declaredProperties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P> Property<P> getDeclaredProperty(String name, Class<P> type) {
		return (Property<P>)getDeclaredProperty(name);
	}

	@Override
	public T newInstance() {
		try {
			Constructor<T> constructor = theClass.getConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (NoSuchMethodException e) {
			throw new MetaException(e);
		} catch (InstantiationException e) {
			throw new MetaException(e);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			throw new MetaException(e);
		}
	}

	@Override
	public T newInstance(Object... args) {
		try {
			Class<?>[] parameterTypes = MetaUtil.getTypes(args);
			Constructor<T> constructor = theClass.getConstructor(parameterTypes);
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (NoSuchMethodException e) {
			throw new MetaException(e);
		} catch (InstantiationException e) {
			throw new MetaException(e);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			throw new MetaException(e);
		}
	}

	//
	// Object overrides
	//
	
	@Override
	public String toString() {
		return unqualifiedName + "(" + getTheClass().getName().toString() + ")";
	}

	//
	// AnnotatedElement implementation
	//
	
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return theClass.getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getAnnotations() {
		return theClass.getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return theClass.getDeclaredAnnotations();
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return theClass.isAnnotationPresent(annotationClass);
	}

	@Override
	public Property<?> getRequiredProperty(String name) {
		Property<?> property = getProperty(name);
		if (property==null) {
			throw new NoSuchPropertyException(this.getName() + "." + name);
		}
		return property;
	}

	@Override
	public <P> Property<P> getRequiredProperty(String name, Class<P> type) {
		Property<P> property = getProperty(name, type);
		if (property==null) {
			throw new NoSuchPropertyException(this.getName() + "." + name);
		}
		return property;
	}
	
}
