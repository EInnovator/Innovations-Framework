/**
 * 
 */
package org.einnovator.meta;

import java.lang.reflect.AnnotatedElement;


/**
 * A framework defined model of a Class with reflection service.
 * 
 * Usually wraps a Java class, i.e. an instance of {@link java.lang.Class}.
 *
 * @see {@link Property}
 * @see {@link MetaClassBuilder}
 * @author Jorge Simão
 */
public interface MetaClass<T>  extends AnnotatedElement {

	/**
	 * Get the full name of this {@code MetaClass}.
	 * 
	 * Usually the name of the wrapped instance of {@link java.lang.Class}.
	 * 
	 * @return the full name of the {@code MetaClass}
	 * @see {@link #getUnqualifiedName()}}
	 */
	String getName();

	/**
	 * Get the unqualified name of this {@code MetaClass}.
	 * 
	 * An unqualified name is the the full name without the package prefix.
	 * 
	 * @return the unqualified name of the {@code MetaClass}
	 * @see {@link #getName()}}
	 */
	String getUnqualifiedName();
	
	
	/**
	 * Get the wrapped {@link java.lang.Class} instance.
	 * 
	 * @return the {@link java.lang.Class}  instance; or {@code null},
	 * if this is a synthetic {@code MetaClass}.
	 */
	Class<T> getTheClass();
	
	/**
	 * Get the parent {@code MetaClass}.
	 * 
	 * @return the parent {@code MetaClass}; or {@code null}, if this is a root {@code MetaClass}.
	 */
	MetaClass<?> getParent();
	
	/**
	 * Get array of declared properties of this {@code MetaClass}.
	 * 
	 * Declared properties are the properties defined explicitly by this {@code MetaClass}.
	 * Propertied inherited from ancestors {@code MetaClass} are not included.
	 * However, if a {@code MetaClass} overrides a property with the same name in an ancestor
	 * class, then the overriding property is returned.
	 * 
	 * The return array of {@code Property} should not be treated as immutable; 
	 * i.e. components should not be replaced or modified.
	 * 
	 * @return an array with the parent {@code MetaClass}.
	 * @see {@link #getAllProperties()}}
	 */
	Property<?>[] getDeclaredProperties();

	/**
	 * Get array of all properties of this {@code MetaClass}.
	 * 
	 * The returned array includes all declared properties, and properties inherited from
	 * the parent {@code MetaClass} and his ancestors, if any.
	 * 
	 * No two properties with the same name will ever be returned. If a property defined
	 * in this class overrides a property defined in an ancestor class with the same name,
	 * only the property defined by this class is included in the returned array.
	 * The same applies to a property defined in an ancestor class, that overrides a
	 * property defined in an ancestor of that ancestor.
	 * 
	 * A {@link org.einnovator.meta.MetaClassBuilder} should guaranty this semantics for the returned array..
	 * 
	 * The return array should not be treated as immutable; 
	 * i.e. components should not be replaced or modified.
	 * 
	 * @return an array with the parent {@code MetaClass}.
	 * @see {@link #getDeclaredProperties()}}
	 */
	Property<?>[] getAllProperties();
	
	/**
	 * Get the property with the specified name,
	 * 
	 * @param name the name of the property to retrieve.
	 * @return the property with the specified name, if found; {@code null}, otherwise.
	 * @see {@link #getProperty(String, Class)}}
	 * @see {@link #getRequiredProperty(String)}}
	 */
	Property<?> getProperty(String name);

	/**
	 * Get the property with the specified name, and specified type,
	 * 
	 * @param name the name of the property to retrieve.
	 * @param type the type of the property.
	 * @return the property with the specified name and type, if found; {@code null}, otherwise.
	 * @see {@link #getRequiredProperty(String)}
	 * @see {@link #getRequiredProperty(String,Class)}
	 */
	<P> Property<P> getProperty(String name, Class<P> type);

	/**
	 * Get the property with the specified name,
	 *
	 * If property with matching name is not found a {@link NoSuchPropertyException} is thrown.
	 * 
	 * @param name the name of the property to retrieve.
	 * @throws {@link NoSuchPropertyException} if property with matching name is not found
	 * @return the property with the specified name, if found; {@code null}, otherwise.
	 * @see {@link #getProperty(String)}}
	 * @see {@link #getProperty(String, Class)}}
	 */
	Property<?> getRequiredProperty(String name);

	/**
	 * Get the property with the specified name, and specified type.
	 * 
	 * If property with matching name is not found a {@link NoSuchPropertyException} is thrown.
	 * 
	 * @param name the name of the property to retrieve.
	 * @param type the type of the property.
	 * @return the property with the specified name and type, if found; {@code null}, otherwise.
	 * @throws {@link NoSuchPropertyException} if property with matching name is not found
	 * @see {@link #getProperty(String, Class)}}
	 */
	<P> Property<P> getRequiredProperty(String name, Class<P> type);

	
	/**
	 * Get the declared property with the specified name,
	 * 
	 * @param name the name of the property to retrieve.
	 * @return the property with the specified name, if found; {@code null}, otherwise.
	 * @see {@link #getDeclaredProperties()}}
	 */
	Property<?> getDeclaredProperty(String name);

	/**
	 * Get the declared property with the specified name, and specified type,
	 * 
	 * @param name the name of the property to retrieve.
	 * @param type the type of the property.
	 * @return the property with the specified name and type, if found; {@code null}, otherwise.
	 * @see {@link #getDeclaredProperties()}}
	 */
	<P> Property<P> getDeclaredProperty(String name, Class<P> type);
	
	/**
	 * Create a new instance of the wrapped {@link java.lang.Class}.
	 * 
	 * @return the new instance
	 * @throws org.einnovator.meta.MetaException wrapping the exception thrown
	 * by the underlying {@link java.lang.Class#getConstructor(Class...)} or
	 * {@link java.lang.reflect.Constructor#newInstance(Object...)}
	 */
	T newInstance();
	
	/**
	 * Create a new instance of the wrapped {@link java.lang.Class}.
	 * 
	 * A constructor with a matching number and type of arguments is looked for.
	 * For creation of multiple instances an alternative approach is
	 * to retrieved a {@link java.lang.reflect.Constructor} instance from the wrapped {@link java.lang.Class}.
	 *
	 * @param args a variable number of arguments to be passed to a constructor.	
	 * @return the new instance
	 * @throws org.einnovator.meta.MetaException wrapping the exception thrown
	 * by the underlying {@link java.lang.Class#getConstructor(Class...)} or
	 * {@link java.lang.reflect.Constructor#newInstance(Object...)}
	 */
	T newInstance(Object... args);

}
