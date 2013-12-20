/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.reflect.Field;

/**
 * A {@code FieldProperty} suitable to model {@code Field} with a generic type.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class GenericFieldProperty<T> extends FieldProperty<T> {

	private Class<?> type;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code GenericFieldProperty}.
	 *
	 * @param ownerClass the class owning the this {@code Property}
	 * @param field the {@code Field}
	 * @param type the type of this {@code Property}
	 */
	public GenericFieldProperty(Class<?> ownerClass, Field field, Class<?> type) {
		super(ownerClass, field);
		this.type = type;
	}

	/**
	 * Create instance of {@code GenericFieldProperty}.
	 *
	 * @param field the {@code Field}
	 * @param type the type of this {@code Property}
	 */
	public GenericFieldProperty(Field field, Class<?> type) {
		this(null, field, type);
	}

	//
	// Property<T> implementation
	//
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		return (Class<T>)type;
	}

}
