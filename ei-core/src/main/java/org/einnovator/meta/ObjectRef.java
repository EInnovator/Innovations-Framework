package org.einnovator.meta;

import java.lang.annotation.Annotation;

import org.einnovator.format.ObjectSupport;

/**
 * A reference to an object or managed bean.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ObjectRef<T> extends ObjectSupport {

	/**
	 * Name of the bean. 
	 */
	private String name;

	/**
	 * Type of the object or bean.
	 * 
	 * (same as {@link #type})
	 */
	private Class<? extends T> type;

	/**
	 * The default type of a prototype object to use when bean lookup fails.
	 * 
	 * Assumed to have a no-arguments constructor.
	 */
	private Class<? extends T> defaultType;


	/**
	 * Flag specifying if the bean should be looked up in some context if available.
	 */
	private boolean lookup = true;

	/**
	 * Types of qualifier annotations for the bean.
	 */
	private Class<? extends Annotation>[] qualifierTypes;

	/**
	 * Annotations qualifying the object or bean.
	 */
	private Annotation[] qualifiers;

	//
	// Constructors
	//

	/**
	 * Create instance of {@code ObjectRef}.
	 *
	 * @param name
	 * @param type
	 * @param qualifiers
	 * @param defaultType
	 */
	public ObjectRef(String name, Class<? extends T> type, Annotation[] qualifiers, Class<? extends T> defaultType) {
		this.name = name;
		this.type = type;
		this.qualifiers = qualifiers;
		this.defaultType = defaultType;
	}

	/**
	 * Create instance of {@code ObjectRef}.
	 *
	 * @param name
	 * @param type
	 * @param qualifiers
	 */
	public ObjectRef(String name, Class<? extends T> type, Annotation[] qualifiers) {
		this(name, type, qualifiers, null);
	}

	/**
	 * Create instance of {@code ObjectRef}.
	 *
	 * @param name name of object or managed bean
	 * @param type type of object or managed bean
	 */
	public ObjectRef(String name, Class<? extends T> type) {
		this(name, type, null, null);
	}

	/**
	 * Create instance of {@code ObjectRef}.
	 *
	 * @param type type of object or managed bean
	 */
	public ObjectRef(Class<? extends T> type) {
		this(null, type, null, null);
	}

	/**
	 * Create instance of {@code ObjectRef}.
	 *
	 * @param type type of object or managed bean
	 * @param defaultType the default type of the object or managed bean 
	 */
	public ObjectRef(Class<? extends T> type, Class<? extends T> defaultType) {
		this(null, type, null, defaultType);
	}

	/**
	 * Create instance of {@code ObjectRef}.
	 *
	 * @param name the name of {@code ObjectRef}
	 */
	public ObjectRef(String name) {
		this(name, null, null, null);
	}

	/**
	 * Create instance of {@code ObjectRef}.
	 *
	 */
	public ObjectRef() {
	}
	
	//
	// Getters and setters (and BeanShell implementation)
	//
	
	/**
	 * Get the value of name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Get the value of type.
	 *
	 * @return the type
	 */
	public Class<? extends T> getType() {
		return type;
	}

	/**
	 * Set the value of type.
	 *
	 * @param type the type to set
	 */
	public void setType(Class<? extends T> type) {
		this.type = type;
	}


	/**
	 * Get the value of qualifiers.
	 *
	 * @return the qualifiers
	 */
	public Annotation[] getQualifiers() {
		return qualifiers;
	}

	/**
	 * Set the value of qualifiers.
	 *
	 * @param qualifiers the qualifiers to set
	 */
	public void setQualifiers(Annotation[] qualifiers) {
		this.qualifiers = qualifiers;
	}
	
	/**
	 * Get the value of property {@code defaultType}.
	 *
	 * @return the defaultType
	 */
	public Class<? extends T> getDefaultType() {
		return defaultType;
	}

	/**
	 * Set the value of property {@code defaultType}.
	 *
	 * @param defaultType the defaultType to set
	 */
	public void setDefaultType(Class<? extends T> defaultType) {
		this.defaultType = defaultType;
	}

	/**
	 * Get the value of property {@code lookup}.
	 *
	 * @return the lookup
	 */
	public boolean isLookup() {
		return lookup;
	}

	/**
	 * Set the value of property {@code lookup}.
	 *
	 * @param lookup the lookup to set
	 */
	public void setLookup(boolean lookup) {
		this.lookup = lookup;
	}

	/**
	 * Get the value of property {@code qualifierTypes}.
	 *
	 * @return the qualifierTypes
	 */
	public Class<? extends Annotation>[] getQualifierTypes() {
		return qualifierTypes;
	}

	/**
	 * Set the value of property {@code qualifierTypes}.
	 *
	 * @param qualifierTypes the qualifierTypes to set
	 */
	public void setQualifierTypes(Class<? extends Annotation>[] qualifierTypes) {
		this.qualifierTypes = qualifierTypes;
	}
	
	
	//
	// Fluent API
	//

	/**
	 * Set the value of property {@code type}
	 *
	 * @param type the type
	 * @return returns this {@code ObjectRef}
	 */
	public ObjectRef<T> type(Class<? extends T> type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the value of property {@code lookup}
	 *
	 * @param lookup the lookup value
	 * @return returns this {@code ObjectRef}
	 */
	public ObjectRef<T> lookup(boolean lookup) {
		this.lookup = lookup;
		return this;
	}


	/**
	 * Set the value of property {@code name}
	 *
	 * @param name the name
	 * @return returns this {@code ObjectRef}
	 */
	public ObjectRef<T> name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set the value of property {@code defaultType}
	 *
	 * @param defaultType the default type
	 * @return returns this {@code ObjectRef}
	 */
	public ObjectRef<T> defaultType(Class<? extends T> defaultType) {
		this.defaultType = defaultType;
		return this;
	}

	/**
	 * Make the type the default type if none was set.
	 *
	 * @return returns this {@code ObjectRef}
	 */
	public ObjectRef<T> defaultType() {
		return this.defaultType==null ? defaultType(this.type) : this;
	}

	/**
	 * Set the value of property {@code qualifiers}
	 *
	 * @param qualifiers an array of qualifying annotations
	 * @return returns this {@code ObjectRef}
	 */
	public ObjectRef<T> qualifiers(Annotation[] qualifiers) {
		this.qualifiers = qualifiers;
		return this;
	}
	
	/**
	 * Set the value of property {@code qualifierTypes}
	 *
	 * @param qualifierTypes an array of qualifying annotation types
	 * @return returns this {@code ObjectRef}
	 */
	public ObjectRef<T> qualifiers(Class<? extends Annotation>[] qualifierTypes) {
		this.qualifierTypes = qualifierTypes;
		return this;
	}
	
	//
	// Object Overrides
	//

	/**
	 * Deep clone this {@code ObjectRef}.
	 * 
	 * @return the {@code ObjectRef} clone
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		@SuppressWarnings("unchecked")
		ObjectRef<T> ref = (ObjectRef<T>) super.clone();
		return ref;
	}
	

	//
	// Static Utilities
	//

	/**
	 * Deep clone a {@code ObjectRef}.
	 * 
	 * @param ref the {@code ObjectRef} to clone
	 * @return the {@code ObjectRef} clone
	 */
	@SuppressWarnings("unchecked")
	public static <U> ObjectRef<U> clone(ObjectRef<U> ref) {
		try {
			return (ObjectRef<U>) ref.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Return a new {@code ObjectRef} instance with default settings.
	 * 
	 * @return the {@code ObjectRef}
	 */
	public static <T> ObjectRef<T> newInstance(Class<T> type) {
		ObjectRef<T> ref = new ObjectRef<T>();
		return ref;
	}

	//
	// Assignment
	//
	
	/**
	 * Assign the value defines in a {@code Ref} annotation to this {@code ObjectRef}.
	 * 
	 * @param ref the {@code Ref}
	 * @return this {@code ObjectRef}
	 */
	@SuppressWarnings("unchecked")
	public ObjectRef<T> assign(Ref ref) {
		this.type = (Class<? extends T>) (!Object.class.equals(ref.value()) ? ref.value() : null);
		if (!Object.class.equals(ref.type())) {
			this.type =  (Class<? extends T>) ref.type();			
		}
		this.name = !ref.name().isEmpty() ? ref.name() : null;
		this.lookup = ref.lookup();
		this.defaultType =  (Class<? extends T>) (!Object.class.equals(ref.defaultType()) ? ref.value() : null);
		if (!ref.name().isEmpty()) {
			this.name = ref.name();			
		}
		return this;
	}

	//
	// Static Utilities
	//
	
	/**
	 * Check if a {@code Ref} annotation defines or references an object or bean.
	 * 
	 * @return <code>true</code>, if the {@code Ref} annotation defines or references a {@code Ref};
	 *  <code>false</code>, otherwise.
	 */
	public static boolean isEmpty(Ref ref) {
		return Object.class.equals(ref.value()) &&
				Object.class.equals(ref.type()) && 
				ref.name().isEmpty() &&
				Object.class.equals(ref.defaultType());
	}

}
