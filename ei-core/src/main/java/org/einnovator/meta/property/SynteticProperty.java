/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

import org.einnovator.meta.MetaUtil;

/**
 * A {@code Property} that is not mapped to any {@code Member}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class SynteticProperty<T> extends PropertySupport<T> {

	protected String name;
	
	protected Class<T> type;

	protected int modifiers;
	
	protected Annotation[] annotations;
	
	//
	// Constructors
	//

	/**
	 * Create instance of SynteticProperty.
	 *
	 * @param metaClass
	 * @param name
	 * @param type
	 * @param annotations
	 */
	@SuppressWarnings("unchecked")
	public SynteticProperty(Class<?> ownerClass, String name, Class<?> type, Annotation[] annotations) {
		super(ownerClass);
		this.name = name;
		this.type = (Class<T>)type;
		this.annotations = annotations;
	}


	/**
	 * Create instance of SynteticProperty.
	 *
	 * @param name
	 * @param type
	 * @param annotations
	 */
	public SynteticProperty(String name, Class<?> type, Annotation[] annotations) {
		this(null, name, type, annotations);
	}

	/**
	 * Create instance of SynteticProperty.
	 *
	 * @param name
	 * @param type
	 */
	public SynteticProperty(String name, Class<?> type) {
		this(null, name, type, null);
	}

	/**
	 * Create instance of SynteticProperty.
	 *
	 * @param type
	 */
	public SynteticProperty(Class<T> type) {
		this.type = type;
	}
	
	//
	// Property<T> interface
	//
	
	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public Type getGenericType() {
		return null;
	}

	@Override
	public Member getMember() {
		return null;
	}

	@Override
	public Class<?> getDeclaringClass() {
		if (getDeclaringMetaClass()!=null) {
			return getDeclaringMetaClass().getTheClass();
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public int getModifiers() {
		return modifiers;
	}

	
	public Annotation[] getAnnotations() {
		return annotations;
	}

	public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
		return MetaUtil.getAnnotation(annotationType, annotations);
	}
	
	@Override
	public void setValue(Object target, T value) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue(Object target) {
		return (T)target;
	}

}
