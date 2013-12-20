/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.einnovator.meta.MetaUtil;

/**
 * A {@code Property} mapped to a getter and setter {@code Method}s.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class GetterSetterProperty<T> extends ReadWritePropertySupport<T> {

	protected String name;
	
	protected Method getter;

	protected Method setter;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code GetterSetterProperty}.
	 *
	 * @param ownerClass the class owning the this {@code Property}
	 * @param getter the getter method
	 * @param setter the setter method
	 */
	public GetterSetterProperty(Class<?> ownerClass, Method getter, Method setter) {
		super(ownerClass);
		this.getter = getter;
		this.setter = setter;
		name = MetaUtil.getPropertyName(getter, setter);
	}

	/**
	 * Create instance of {@code GetterSetterProperty}.
	 *
	 * @param getter the getter method
	 * @param setter the setter method
	 */
	public GetterSetterProperty(Method getter, Method setter) {
		this(null, getter, setter);
	}
	
	//
	// Property<T> implementation
	//

	/**
	 * @see org.einnovator.meta.Property#getType()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		return (Class<T>)getter.getReturnType();
	}

	/**
	 * @see org.einnovator.meta.Property#getGenericType()
	 */
	@Override
	public Type getGenericType() {
		return getter.getGenericReturnType();
	}

	public String getName() {
		if (name!=null) {
			return name;
		}
		return super.getName();
	}
	//
	// ReadWriteProperty<T> implementation
	//
	
	@Override
	public Member getReadMember() {
		return getter;
	}
	
	@Override
	public Member getWriteMember() {
		return setter;
	}

	@Override
	public void setValue(Object target, T value) {
		setter.setAccessible(true);
		try {
			setter.invoke(target, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue(Object target) {
		getter.setAccessible(true);
		try {
			return (T)getter.invoke(target);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
