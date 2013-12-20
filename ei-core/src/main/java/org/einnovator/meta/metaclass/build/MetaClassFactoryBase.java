/**
 * 
 */
package org.einnovator.meta.metaclass.build;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.Transient;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;
import org.einnovator.meta.MetaClassFactory;
import org.einnovator.meta.Property;
import org.einnovator.meta.property.FieldProperty;
import org.einnovator.meta.property.GetterSetterProperty;


/**
 * Abstract base class used to support the implementation of a {@code MetaClassFactory}.
 *
 * It iterates over declared fields and getter/setter to collect properties.
 * The default behavior is to include properties for all fields for which method 
 * {@link #includeMember(Member)} returns <code>true</code>; 
 * and not include properties for any getter/setter pair. This behavior can be overriden
 * by implementing methods {@link #includeField(Field)} and {@link #includeProperty(Method, Method)}.
 * 
 * Sub-class should implemented the abstract method {@link #createMetaClassBuilder(MetaClass, Class)}.
 * 
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class MetaClassFactoryBase implements MetaClassFactory {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code MetaClassFactoryBase}.
	 *
	 */
	public MetaClassFactoryBase() {
	}

	//
	// MetaClassFactory Implementation
	//
	
	/**
	 * @see org.einnovator.meta.MetaClassFactory#createMetaClass(org.einnovator.meta.MetaClass, java.lang.Class)
	 */
	@Override
	public <T> MetaClass<T> createMetaClass(MetaClass<?> parent, Class<T> theClass) {	
		MetaClassBuilder<T> builder = createMetaClassBuilder(parent, theClass);
		List<Property<?>> properties = createProperties(theClass);
		properties = postProcessProperties(theClass, properties);
		builder.addProperties(properties);
		postProcess(builder);
		MetaClass<T> metaClass = builder.build();
		postProcess(metaClass);
		return metaClass;
	}

	protected <T> List<Property<?>> createProperties(Class<T> theClass) {
		List<Property<?>> properties = new ArrayList<Property<?>>();
		createFieldProperties(theClass, properties);
		createGetterSetterProperties(theClass, properties);
		return properties;
	}
	
	/**
	 * Iterate and collect properties for declared fields and getter/setter methods.
	 * 
	 * @param theClass the class to iterate
	 * @param properties a list to collect the {@code Property}s
	 */
	protected <T> void createProperties(Class<T> theClass, List<Property<?>> properties) {	
		createFieldProperties(theClass, properties);
		createGetterSetterProperties(theClass, properties);
	}
		
	/**
	 * Iterate and collect properties for declared fields.
	 * 
	 * @param theClass the class to iterate
	 * @param properties a list to collect the {@code Property}s
	 */
	protected <T> void createFieldProperties(Class<T> theClass, List<Property<?>> properties) {	
		Field[] fields = theClass.getDeclaredFields();
		for (Field field: fields) {
			if (includeField(theClass, field)) {
				Property<?> property = createFieldProperty(theClass, field);
				if (property!=null) {
					properties.add(property);
				}
			}
		}		
	}

	/**
	 * Iterate and collect properties for declared getter (and setter) methods.
	 * 
	 * @param theClass the class for the built {@code MetaClass}
	 * @param properties a list to collect the {@code Property}s
	 */
	protected <T> void createGetterSetterProperties(Class<T> theClass, List<Property<?>> properties) {	
		for (Method method: theClass.getDeclaredMethods()) {
			if (MetaUtil.isGetter(method)) {
				Method setter = MetaUtil.getSetter(theClass, MetaUtil.getPropertyName(method));
				if (includeProperty(theClass, method, setter)) {
					Property<?> property = createGetterSetterProperty(theClass, method, setter);
					if (property!=null) {
						properties.add(property);
					}
				}
			}			
		}
	}


	/**
	 * Create a {@code Property} for a getter and setter methods pair.
	 * 
	 * Default implementation returns a {@link GetterSetterProperty}.
	 * 
	 * If this method return <code>null</code>, the getter and setter methods pair is ignored.
	 * 
	 * @param theClass the class for the built {@code MetaClass}
	 * @param getter the getter method
	 * @param setter the setter method (or <code>null</code>, if there is no matching setter for the getter)
	 * @return the {@code Property}
	 */
	protected Property<?> createGetterSetterProperty(Class<?> theClass, Method getter, Method setter) {
		return new GetterSetterProperty<Object>(theClass, getter, setter);
	}

	/**
	 * Create a {@code Property} for a {@code Field}.
	 * 
	 * Default implementation returns a {@link FieldProperty}.
	 * 
	 * If this method return <code>null</code>, the field is ignored.
	 * 
	 * @param theClass the class for the built {@code MetaClass}
	 * @param field the {@code Field}
	 * @return the {@code Property}
	 */
	protected Property<?> createFieldProperty(Class<?> theClass, Field field) {
		return new FieldProperty<Object>(theClass, field);
	}

	/**
	 * Check if field should be included as property.
	 * 
	 * Default implementation delegates to {@link #includeMember(Member)}.
	 * 
	 * @param theClass the class for the built {@code MetaClass}
	 * @param field the {@code Field}
	 * @return <code>true</code>, if the {@code Field} should be included as property;
	 * <code>false</code>, otherwise.
	 */
	protected boolean includeField(Class<?> theClass, Field field) {
		return includeMember(theClass, field);
	}

	/**
	 * Check if getter and setter should be included as property.
	 * 
	 * Default implementation returns <code>false</code>.
	 * 
	 * @param theClass the class for the built {@code MetaClass}
	 * @param getter the getter method
	 * @param setter the setter method
	 * @return <code>true</code>, if the getter and setter should be included as property;
	 * <code>false</code>, otherwise.
	 */
	protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
		return false;
	}

	/**
	 * Check if member should be included as property.
	 * 
	 * @param theClass the class for the built {@code MetaClass}
	 * @param member the {@code Member}
	 * @return <code>true</code>, if the {@code Member} should be included as property;
	 * <code>false</code>, otherwise.
	 */
	protected boolean includeMember(Class<?> theClass, Member member) {
		int mod = member.getModifiers();
		if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
			return false;
		}
		Transient transientAn = ((AnnotatedElement)member).getAnnotation(Transient.class);
		if (transientAn!=null) {
			return false;
		}
		if (MetaUtil.isInner(member.getName())) {
			return false;
		}
		return true;

	}
	
	//
	// Required Contract
	//

	/**
	 * Create a {@code MetaClassBuilder} to build the {@code MetaClass}.
	 * 
	 * @param parent the parent {@code MetaClass}
	 * @param theClass the Java class for the {@code MetaClass}
	 * @return the {@code MetaClassBuilder}
	 */
	abstract protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass);

	//
	// Post-processing
	//
	

	protected List<Property<?>> postProcessProperties(Class<?> theClass, List<Property<?>> properties) {	
		return properties;
	}

	/**
	 * Invoked before MetaClass is built.
	 * @param property
	 */
	protected void postProcess(MetaClassBuilder<?> builder) {
	}

	/**
	 * Invoked after MetaClass is built.
	 * @param property
	 */
	protected void postProcess(MetaClass<?> metaClass) {
		postProcessProperties(metaClass);
	}
	
	protected void postProcessProperties(MetaClass<?>  metaClass) {	
		for (Property<?> property: metaClass.getDeclaredProperties()) {
			postProcess(property, metaClass);
		}
	}

	/**
	 * Invoked after MetaClass is built.
	 * @param property
	 */
	protected void postProcess(Property<?> property, MetaClass<?> metaClass) {	
	}


}
