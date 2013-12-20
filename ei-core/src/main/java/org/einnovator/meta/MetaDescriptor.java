package org.einnovator.meta;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.einnovator.format.ObjectSupport;

/**
 * A {@code MetaDescriptor}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class MetaDescriptor extends ObjectSupport {

	private ElementType elementType;
	
	private Member member;
	
	private Class<?> type;
	
	private Annotation[] annotations;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code MetaDescriptor}.
	 *
	 */
	public MetaDescriptor() {
	}
	
	/**
	 * Create instance of {@code MetaDescriptor}.
	 *
	 * @param elementType
	 * @param member
	 * @param type
	 * @param annotations
	 */
	public MetaDescriptor(ElementType elementType, Member member, Class<?> type, Annotation[] annotations) {
		super();
		this.elementType = elementType;
		this.member = member;
		this.type = type;
		this.annotations = annotations;
	}
	
	/**
	 * Create instance of {@code MetaDescriptor}.
	 *
	 * @param elementType
	 * @param member
	 * @param type
	 * @param annotations
	 */
	public MetaDescriptor(Member member, Class<?> type, Annotation[] annotations) {
		this(toElementType(member), member, type, annotations);
	}

	public static ElementType toElementType(Member member) {
		if (member instanceof Field) {
			return ElementType.FIELD;
		}
		if (member instanceof Method) {
			return ElementType.CONSTRUCTOR;
		}
		if (member instanceof Method) {
			return ElementType.METHOD;
		}
		return null;
	}
	
	/**
	 * Create instance of {@code MetaDescriptor} for a {@code Field}.
	 *
	 * @param field the {@code Field}
	 */
	public MetaDescriptor(Field field) {
		this(ElementType.FIELD, field, field.getType(), field.getAnnotations());
	}

	/**
	 * Create instance of {@code MetaDescriptor} for a getter {@code Method}.
	 *
	 * @param method the getter {@code Method}
	 */
	public MetaDescriptor(Method method) {
		this(ElementType.METHOD, method, method.getReturnType(), method.getAnnotations());
	}
	
	/**
	 * Create instance of {@code MetaDescriptor} for a type.
	 *
	 * @param type the type
	 */
	public MetaDescriptor(Class<?> type) {
		this(ElementType.TYPE, null, type, type.getAnnotations());
	}

	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code elementType}.
	 *
	 * @return the elementType
	 */
	public ElementType getElementType() {
		return elementType;
	}


	/**
	 * Set the value of property {@code elementType}.
	 *
	 * @param elementType the elementType to set
	 */
	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}
	
	/**
	 * Get the value of property {@code member}.
	 *
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * Set the value of property {@code member}.
	 *
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * Get the value of property {@code type}.
	 *
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Set the value of property {@code type}.
	 *
	 * @param type the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * Get the value of property {@code annotations}.
	 *
	 * @return the annotations
	 */
	public Annotation[] getAnnotations() {
		return annotations;
	}

	/**
	 * Set the value of property {@code annotations}.
	 *
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}
	
	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		return annotations!=null ? MetaUtil.getAnnotation(annotationType, annotations) : null;
	}

	
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return annotations!=null ? MetaUtil.getAnnotation(annotationType, annotations)!=null : false;
	}

}
