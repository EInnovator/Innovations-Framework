/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.NoSuchPropertyException;
import org.einnovator.meta.Property;


/**
 * Miscellaneous static utilities related to {@code Property}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class PropertyUtil {

	public static <T> Property<T> createProperty(Class<?> classType, String name) {
		Field field = MetaUtil.getField(classType, name);
		if (field!=null) {
			return new FieldProperty<T>(field);
		}
		return createGetterSetterProperty(classType, name);
	}

	public static <T> Property<T> createProperty(Class<?> classType, String name, Class<T> propertyType) {
		Field field = MetaUtil.getField(classType, name);
		if (field!=null) {
			return new FieldProperty<T>(field);
		}
		return createGetterSetterProperty(classType, name, propertyType);
	}

	public static <T> Property<T> createProperty(Class<?> classType, String name, Class<T> propertyType, boolean fieldAccess) {
		if (fieldAccess) {
			return createFieldProperty(classType, name, propertyType);
		} else {
			return createGetterSetterProperty(classType, name, propertyType);
		}
	}
	
	public static <T> Property<T> createFieldProperty(Class<?> classType, String name, Class<T> propertyType) {
		Field field = MetaUtil.getField(classType, name);
		checkMember(field, classType, name, propertyType);
		return new FieldProperty<T>(field);		
	}

	public static <T> Property<T> createGetterSetterProperty(Class<?> classType, String name, Class<T> propertyType) {
		Method getter = MetaUtil.getGetter(classType, name);
		Method setter = MetaUtil.getSetter(classType, name);
		if (getter==null && setter==null) {
			checkMember(getter, classType, name, propertyType);
		}
		return new GetterSetterProperty<T>(getter, setter);		
	}

	public static <T> Property<T> createGetterSetterProperty(Class<?> classType, String name) {
		Method getter = MetaUtil.getGetter(classType, name);
		Method setter = MetaUtil.getSetter(classType, name);
		if (getter==null || setter==null) {
			throw new NoSuchPropertyException(formatMember(classType, name, null));
		}
		return new GetterSetterProperty<T>(getter, setter);		
	}

	public static <T> Property<T> createReadOnlyProperty(Class<?> classType, String name, Class<T> propertyType) {
		Method getter = MetaUtil.getGetter(classType, name);
		checkMember(getter, classType, name, propertyType);
		return new GetterSetterProperty<T>(getter, null);
	}
	
	public static <T> Property<T> getWriteOnlyProperty(Class<?> classType, String name, Class<T> propertyType) {
		Method setter = MetaUtil.getSetter(classType, name);
		checkMember(setter, classType, name, propertyType);
		return new GetterSetterProperty<T>(null, setter);
	}


	//
	// Support
	//
	
	public static String formatMember(Class<?> classType, String name, Class<?> memberType) {
		StringBuilder sb = new StringBuilder();
		if (memberType!=null) {
			sb.append(memberType.getName());
			sb.append(" ");
		}
		sb.append(classType.getName());
		sb.append(".");
		sb.append(name);
		return sb.toString();
	}

	
	private static void checkMember(Member member, Class<?> classType, String name, Class<?> propertyType) {
		if (member==null) {
			throw new NoSuchPropertyException(formatMember(classType, name, propertyType));
		}
		if (propertyType!=null && !MetaUtil.getPropertyType(member).equals(propertyType)) {
			throw new NoSuchPropertyException("Type mismatch for: " + formatMember(classType, name, propertyType));
		}	
	}

	/**
	 * Get the names of an array of properties.
	 * 
	 * @param properties the array of {@link Property} 
	 * @return the array with the names of the properties
	 */
	public static String[] getPropertyNames(Property<?>[] properties) {
		String[] names = new String[properties.length];
		for (int i=0; i<properties.length; i++) {
			names[i] = properties[i].getName();
		}
		return names;
	}
	
	/**
	 * Get the names of a list of properties.
	 * 
	 * @param properties the list of {@link Property} 
	 * @return the array with the names of the properties
	 */
	public static List<String> getPropertyNames(List<Property<?>> properties) {
		List<String> names = new ArrayList<String>(properties.size());
		for (Property<?> property: properties) {
			names.add(property.getName());
		}
		return names;
	}

	public static boolean isAnyAnnotationPresent(Property<?> property, Class<? extends Annotation>[] annotationTypes) {
		for (Class<? extends Annotation> annotationType: annotationTypes) {
			if (property.isAnnotationPresent(annotationType)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAnyType(Property<?> property, Class<?>[] types) {
		Class<?> propertyType = property.getType();
		for (Class<?> type: types) {
			if (type.isAssignableFrom(propertyType)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if property name has corresponding method name is a type.
	 * 
	 * @param property the {@code Property}
	 * @param type the type
	 * @return <code>true</code>, if a matching method is found; <code>false</code>, otherwise.
	 */
	public static boolean matchedBy(Property<?> property, Class<?> type) {
		return MetaUtil.getMethod(type, property.getName())!=null;
	}
	
	/**
	 * Check if property name has corresponding method name is any of the specified types.
	 * 
	 * @param property the {@code Property}
	 * @param types an array of types
	 * @return <code>true</code>, if a matching method is found; <code>false</code>, otherwise.
	 */
	public static boolean matchedByAny(Property<?> property, Class<?>[] types) {
		for (Class<?> type: types) {
			if (matchedBy(property, type)) {
				return true;
			}
		}
		return false;
	}
}
