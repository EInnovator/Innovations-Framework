package org.einnovator.meta.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.Property;


/**
 * A {@code PropertyWrapper}.
 *
 * @param <T>
 * @author Jorge Simão
 */
public class PropertyWrapper<T> implements Property<T>, AnnotatedElement, Comparable<Property<T>>, Cloneable  {
	
	private Property<T> property;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of PropertyWrapper.
	 *
	 */
	public PropertyWrapper() {
	}

	/**
	 * Create instance of PropertyWrapper.
	 *
	 * @param property
	 */
	public PropertyWrapper(Property<T> property) {
		this.property = property;
	}

	
	//
	// Getters and setters
	//

	/**
	 * Get the value of property.
	 *
	 * @return the property
	 */
	public Property<T> getProperty() {
		return property;
	}
	
	/**
	 * Set the value of property.
	 *
	 * @param property the property to set
	 */
	public void setProperty(Property<T> property) {
		this.property = property;
	}

	//
	// Property<T> implementation
	//

	/**
	 * Get the value of declaringMetaClass.
	 *
	 * @return the declaringMetaClass
	 */
	@Override
	public MetaClass<?> getDeclaringMetaClass() {
		return property.getDeclaringMetaClass();
	}

	@Override
	public Class<?> getDeclaringClass() {
		return property.getDeclaringClass();
	}
	
	@Override
	public String getName() {
		return property.getName();
	}
	
	@Override
	public int getModifiers() {
		return property.getModifiers();
	}

	
	//
	// AnnotatedElement implementation
	//
	
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return ((AnnotatedElement)property).getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getAnnotations() {
		return ((AnnotatedElement)property).getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return ((AnnotatedElement)property).getDeclaredAnnotations();
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return ((AnnotatedElement)property).isAnnotationPresent(annotationClass);
	}
	

	/**
	 * @see org.einnovator.meta.Property#getComponentType()
	 */
	@Override
	public Class<?> getComponentType() {
		return property.getComponentType();
	}


	//
	// Modifier utility
	//
	
	@Override
	public boolean isStatic() {
		return property.isStatic();
	}

	@Override	
	public boolean isFinal() {
		return property.isFinal();
	}

	@Override	
	public boolean isPublic() {
		return property.isPublic();
	}
	
	@Override	
	public boolean isProtected() {
		return property.isProtected();
	}
	
	@Override	
	public boolean isPrivate() {
		return property.isPrivate();
	}

	@Override	
	public boolean isNative() {
		return property.isNative();
	}

	@Override
	public boolean isTransient() {
		return property.isTransient();
	}

	@Override
	public Class<T> getType() {
		return property.getType();
	}

	@Override
	public Type getGenericType() {
		return property.getGenericType();
	}

	@Override
	public Member getMember() {
		return property.getMember();
	}

	@Override
	public void setValue(Object target, T value) {
		property.setValue(target, value);
	}

	@Override
	public T getValue(Object target) {
		return property.getValue(target);
	}

	@Override
	public Class<?> getOwnerClass() {
		return property.getOwnerClass();
	}
	
	@Override
	public MetaClass<?> getOwnerMetaClass() {
		return property.getOwnerMetaClass();
	}

	//
	// Object overrides
	//

	//
	// Object overrides
	//
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getType()!=null) {
			typeToString(sb);
			sb.append(" ");		
		}
		if (property!=null && getDeclaringClass()!=null) {
			sb.append(MetaUtil.getUnqualifiedName(getDeclaringClass()));
			sb.append(".");			
		}
		sb.append(getName());
		return sb.toString();
	}

	protected void typeToString(StringBuilder sb) {
		sb.append(MetaUtil.getUnqualifiedName(getType()));
	}

	@Override
	public boolean equals(Object obj) {
		return property.equals(obj);
	}

	/** 
	 * Get hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return property.hashCode();
	}

	//
	// Comparable implementation
	//
	
	@Override
	public int compareTo(Property<T> property) {
		return this.getName().compareTo(property.getName());
	}
	
	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


}
