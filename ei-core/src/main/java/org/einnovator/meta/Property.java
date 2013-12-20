/**
 * 
 */
package org.einnovator.meta;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.einnovator.meta.property.FieldProperty;
import org.einnovator.meta.property.GetterSetterProperty;

/**
 * A high-level abstraction for a property in a Java type.
 *
 * A {@link MetaClass} is modeled as a collection of {@code Property}.
 * Each {@code Property} can be mapped to a {@code Field} as
 * {@link FieldProperty}, or getter/setter {@link Method}s as a {@link GetterSetterProperty}.
 * 
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see FieldProperty
 * @see GetterSetterProperty
 * @see MetaClass
 */
public interface Property<T> extends AnnotatedElement {

	/**
	 * Get the class declaring this {@code Property}
	 * 
	 * @return the class
	 */
	Class<?> getDeclaringClass();
	
	/**
	 * Get the {@code MetaClass} for the class declaring this {@code Property}
	 * 
	 * @return the {@code MetaClass}
	 */
	MetaClass<?> getDeclaringMetaClass();

	/**
	 * Get the class owning this {@code Property}
	 * 
	 * @return the owner class
	 */
	Class<?> getOwnerClass();
	
	MetaClass<?> getOwnerMetaClass();

	Class<T> getType();
	
	Type getGenericType();
	
	/**
	 * Get the name of this {@code Property}.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Get the {@code Member} to which this {@code Property} is mapped.
	 * 
	 * @return the {@code Member}
	 */
	Member getMember();
	
	/**
	 * Get the modifier flags for this {@code Property}.
	 * 
	 * @return the modifier flags
	 */
	int getModifiers();
	
	/**
	 * Set the value of this {@code Property} in a target object.
	 * 
	 * @param target the target object
	 * @param value the value
	 */
	void setValue(Object target, T value);

	/**
	 * Get the value of this {@code Property} in a target object.
	 * 
	 * @param target the target object
	 * @return the value of this {@code Property}
	 */
	T getValue(Object target);
	
	/**
	 * Get the component (element) type for this property.
	 *
	 * @return the component type
	 */
	Class<?> getComponentType();

	
	//
	// Modifier utilities
	//
	
	boolean isStatic();

	boolean isFinal();

	boolean isPublic();
	
	boolean isProtected();
	
	boolean isPrivate();

	boolean isNative();
	
	boolean isTransient();
}
