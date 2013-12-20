/**
 * 
 */
package org.einnovator.meta;

import java.lang.reflect.Type;

import org.einnovator.util.types.TypeUtil;

/**
 * A {@code GenericUtil}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class GenericUtil {

	/**
	 * Get first type parameter for a generic interface implemented by a class.
	 * 
	 * @param klass the class type
	 * @param interfaceType the interface type
	 * @return the actual type parameter of the interface
	 */
	public static Class<?> getGenericInterfaceTypeParameter(Class<?> klass, Class<?> interfaceType) {
		return getGenericInterfaceTypeParameter(klass, interfaceType, 0);
	}

	/**
	 * Get type parameter for a generic interface implemented by a class.
	 * 
	 * @param klass the class type
	 * @param interfaceType the interface type
	 * @param index the index of the type parameter
	 * @return the actual type parameter of the interface
	 */
	public static Class<?> getGenericInterfaceTypeParameter(Class<?> klass, Class<?> interfaceType, int index) {
		while (klass!=null) {
			Type gtype = TypeUtil.getGenericInterface(klass, interfaceType);
			if (gtype!=null) {
				return TypeUtil.getTypeArgument(gtype, index);			
			}
			klass = klass.getSuperclass();
		}
		return null;
	}
	
}
