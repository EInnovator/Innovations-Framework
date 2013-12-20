package org.einnovator.meta.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.Property;
import org.einnovator.util.types.TypeUtil;

/**
 * Abstract class to support implementations of {@code Property}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class PropertySupport<T> implements Property<T>, AnnotatedElement, Comparable<Property<?>> {
	
	private MetaClass<?> declaringMetaClass;
	
	private Class<?> ownerClass;
	
	private MetaClass<?> ownerMetaClass;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code PropertySupport}.
	 *
	 */
	public PropertySupport() {
	}

	/**
	 * Create instance of {@code PropertySupport}.
	 *
	 * @param ownerClass the class owning the this {@code Property}
	 */
	public PropertySupport(Class<?> ownerClass) {
		this.ownerClass = ownerClass;
	}

	
	//
	// Getters and setters
	//

	/**
	 * Get the value of declaringMetaClass.
	 *
	 * @return the declaringMetaClass
	 */
	public MetaClass<?> getDeclaringMetaClass() {
		return declaringMetaClass;
	}

	/**
	 * Set the value of declaringMetaClass.
	 *
	 * @param declaringMetaClass the declaringMetaClass to set
	 */
	public void setDeclaringMetaClass(MetaClass<?> declaringMetaClass) {
		this.declaringMetaClass = declaringMetaClass;
	}

	//
	// Property<T> implementation
	//
	
	@Override
	public Class<?> getDeclaringClass() {
		return getMember().getDeclaringClass();
	}
	
	@Override
	public String getName() {
		return getMember().getName();
	}

	@Override
	public int getModifiers() {
		return getMember().getModifiers();
	}

	@Override
	public Class<?> getOwnerClass() {
		return ownerClass;
	}
	
	@Override
	public MetaClass<?> getOwnerMetaClass() {
		return ownerMetaClass;
	}
	//
	// AnnotatedElement implementation
	//
	
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return ((AnnotatedElement)getMember()).getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getAnnotations() {
		return ((AnnotatedElement)getMember()).getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return ((AnnotatedElement)getMember()).getDeclaredAnnotations();
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return ((AnnotatedElement)getMember()).isAnnotationPresent(annotationClass);
	}
	
	/**
	 * @see org.einnovator.meta.Property#getComponentType()
	 */
	@Override
	public Class<?> getComponentType() {
		return TypeUtil.getComponentType(getMember());
	}

	//
	// Modifier utility
	//
	
	public boolean isStatic() {
		return Modifier.isStatic(getModifiers());
	}

	public boolean isFinal() {
		return Modifier.isFinal(getModifiers());
	}

	public boolean isPublic() {
		return Modifier.isPublic(getModifiers());
	}
	
	public boolean isProtected() {
		return Modifier.isProtected(getModifiers());
	}
	
	public boolean isPrivate() {
		return Modifier.isPrivate(getModifiers());
	}

	public boolean isNative() {
		return Modifier.isNative(getModifiers());
	}


	@Override
	public boolean isTransient() {
		return Modifier.isTransient(getModifiers());
	}



	//
	// Object overrides
	//
	
	/* 
	 * Compares this object with the specified object.
	 * 
	 * Comparison is based on the name of the property.
	 * 
	 * @param obj the object to compare
	 * @return {@code true}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj==null || !(obj instanceof Property)) {
			return false;
		}
		if (getName()!=null) {
			return getName().equals(((Property<?>)obj).getName());
		}
		return super.equals(obj);
	}
	
	/** 
	 * Get hash code.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (getName()!=null) {
			return getName().hashCode();			
		} else {
			return super.hashCode();
		}
		
	}
	
	/* 
	 * Get a string representation of this.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getType()!=null) {
			typeToString(sb);
			sb.append(" ");
		}
		if (declaringMetaClass!=null) {
			sb.append(declaringMetaClass.getUnqualifiedName());
			sb.append(".");
		} else if (getDeclaringClass()!=null) {
			sb.append(MetaUtil.getUnqualifiedName(getDeclaringClass()));
			sb.append(".");			
		}
		sb.append(getName());
		return sb.toString();
	}

	protected void typeToString(StringBuilder sb) {
		if (getType().isArray()) {
			sb.append(MetaUtil.getUnqualifiedName(getType()));
		} else {
			sb.append(MetaUtil.getUnqualifiedName(getType().toString()));
			Class<?> componentType = getComponentType();
			if (componentType!=null && componentType!=getType()) {
				sb.append("<");
				sb.append(MetaUtil.getUnqualifiedName(componentType));
				sb.append(">");				
			}
		}
	}


	//
	// Comparable implementation
	//
	
	@Override
	public int compareTo(Property<?> property) {
		return getName().compareTo(property.getName());
	}



}
