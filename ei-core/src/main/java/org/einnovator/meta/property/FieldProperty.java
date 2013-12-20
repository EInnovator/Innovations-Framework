/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/**
 * A {@code Property} mapped to a {@code Field}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FieldProperty<T> extends PropertySupport<T> {

	protected Field field;

	//
	// Constructors
	//
	
	/**
	 * Create instance of FieldProperty.
	 *
	 * @param field
	 */
	public FieldProperty(Field field) {
		this(null, field);
	}

	/**
	 * Create instance of {@code FieldProperty}.
	 *
	 * @param ownerClass the class owning the this {@code Property}
	 * @param field the {@code Field}
	 */
	public FieldProperty(Class<?> ownerClass, Field field) {
		super(ownerClass);
		this.field = field;
	}

	//
	// Property<T> implementation
	//
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		return (Class<T>)field.getType();
	}

	@Override
	public Type getGenericType() {
		return field.getGenericType();
	}

	@Override
	public Member getMember() {
		return field;
	}

	@Override
	public void setValue(Object target, T value) {
		try {
			field.setAccessible(true);
			field.set(target, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue(Object target) {
		try {
			field.setAccessible(true);	
			return (T)field.get(target);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}	

}
