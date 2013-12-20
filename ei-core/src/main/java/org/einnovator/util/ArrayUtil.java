package org.einnovator.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


/**
 * Miscellaneous array operations.
 *
 * @author Jorge Simão
 */
public class ArrayUtil {
	
	/**
	 * Check if array or string is null or has length zero.
	 * 
	 * @param array the array or string
	 * @return {@code true}, if object is null, is an array of length zero, or a {@code String} of length zero; {@code false}, otherwise.
	 */
	public static boolean isEmpty(Object array) {
		if (array==null) {
			return true;
		}
		if (array.getClass().isArray()) {
			return Array.getLength(array)==0;
		}
		if (array instanceof String){
			return StringUtil.isEmpty((String)array);
		}
		return false;
	}
	
	/**
	 * Check if array is null or has length zero.
	 * 
	 * @param array the array
	 * @return {@code true}, if array is null or has length zero; {@code false}, otherwise.
	 */
	public static boolean isEmpty(Object[] array) {
		return array==null || array.length==0;
	}

	/**
	 * Check if array of strings is null or has length zero.
	 * 
	 * @param array the array
	 * @return {@code true}, if array is null or has length zero; {@code false}, otherwise.
	 */
	public static boolean isEmpty(String[] array) {
		return array==null || array.length==0;
	}

	/**
	 * Check if list is null or has size zero.
	 * 
	 * @param list the list
	 * @return {@code true}, if the list is null or has length zero; {@code false}, otherwise.
	 */
	public static boolean isEmpty(List<?> list) {
		return list==null || list.size()==0;
	}

	/**
	 * Check if array contains an object.
	 * 
	 * @param array the array
	 * @param obj the object to search for
	 * @return {@code true}, if the array contains the object; {@code false}, otherwise.
	 */
	public static boolean contains(Object[] array, Object obj) {
		for (Object obj2: array) {
			if (obj2==obj) {
				return true;
			}
			if (obj!=null && obj.equals(obj2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if array of types contains an a type.
	 * 
	 * @param array the array
	 * @param type the type to search for
	 * @return {@code true}, if the array contains the type; {@code false}, otherwise.
	 */
	public static boolean contains(Class<?>[] array, Class<?> type) {
		for (Class<?> type2: array) {
			if (type2==type) {
				return true;
			}
			if (type!=null && type.equals(type2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if array of types contains a type that can be assigned to the specified type.
	 * 
	 * @param array the array
	 * @param type the type to search for
	 * @return {@code true}, if the array contains an assignable type; {@code false}, otherwise.
	 */
	public static boolean containsAssignable(Class<?>[] array, Class<?> type) {
		for (Class<?> type2: array) {
			if (type.isAssignableFrom(type2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if an array of string contains an equal string.
	 * 
	 * @param array the array for string
	 * @param s the string to search for
	 * @return {@code true}, if the array contains an equal string; {@code false}, otherwise.
	 */
	public static boolean contains(String[] array, String s) {
		for (Object s2: array) {
			if (s==s2) {
				return true;
			}
			if (s!=null && s.equals(s2)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check the first object in a list that has the specified type.
	 * 
	 * @param list the list of objects
	 * @param type the type of the object to search for
	 * @return the object found, or {@code null} if none is found.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T findOneWithMatchingType(List<?> list, Class<T> type) {
		for (Object obj: list) {
			if (obj.getClass().isAssignableFrom(type)) {
				return (T)obj;
			}
		}
		return null;
	}

	/**
	 * Find all objects in an array that has the specified type.
	 * 
	 * @param array the array
	 * @param type the type of the object to search for
	 * @return a new array with the object found
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] select(Object[] array, Class<T> type) {
		int n = 0;
		for (Object obj: array) {
			if (obj!=null && type.isAssignableFrom(obj.getClass())) {
				n++;
			}
		}
		T[] l2= (T[])Array.newInstance(type, n);
		int i = 0;
		for (Object obj: array) {
			if (obj!=null && type.isAssignableFrom(obj.getClass())) {
				l2[i++] = (T)obj;
				n++;
			}
		}
		return l2;
	}

	/**
	 * Find all types in an array that have the specified annotation.
	 * 
	 * @param types the array of types
	 * @param annotationType the type of the annotation to search for
	 * @return a new array with the type that have a matching annotation
	 */
	public static Class<?>[] selectByAnnotation(Class<?>[] types, Class<? extends Annotation> annotationType) {
		int n = 0;
		for (Class<?> type: types) {
			if (type!=null && type.getAnnotation(annotationType)!=null) {
				n++;
			}
		}
		Class<?>[] types2 = new Class[n];
		int i = 0;
		for (Class<?> type: types) {
			if (type!=null && type.getAnnotation(annotationType)!=null) {
				types2[i++] = type;
				n++;
			}
		}
		return types2;
	}

	
	/**
	 * Return a string representation of an object an array.
	 * 
	 * If the array contains arrays as elements, the function is applied recursively.
	 * Some possible outputs include: {@code [1,2,[3,4]]}, and {@code [1,2,[null,4], text]}
	 * 
	 * @param value the object or array
	 * @return the string representation of the object or array
	 */
	public static String toString(Object value) {
		return toString(value, "[", "]", ",");
	}
	
	/**
	 * Return a string representation of an object an array.
	 * 
	 * If the array contains arrays as elements, the function is applied recursively.
	 * Some possible outputs include: {@code [1,2,[3,4]]}, and {@code [1,2,[null,4], text]}
	 * 
	 * @param value the object or array
	 * @param beginMarker
	 * @param endMarker
	 * @param elementSeparator
	 * @return the string representation of the object or array
	 */
	public static String toString(Object value, String beginMarker, String endMarker, String elementSeparator) {
		if (value==null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		if (value.getClass().isArray()) {
			if (beginMarker!=null) {
				sb.append(beginMarker);				
			}
			for (int i=0; i<Array.getLength(value); i++) {
				sb.append(toString(Array.get(value, i), beginMarker, endMarker, elementSeparator));
				if ((beginMarker!=null || i>0) && elementSeparator!=null && i<Array.getLength(value)-1) {
					sb.append(elementSeparator);
				}
			}
			if (endMarker!=null) {
				sb.append(endMarker);				
			}
		} else {
			sb.append(value.toString());
		}
		return sb.toString();
	}

	/**
	 * Return a string representation of an object an array.
	 * 
	 * Unlike {@link #toString(Object)}, no recursion is applied.
	 *  
	 * @param value the object or array
	 * @return the string representation of the object or array
	 */
	public static String format(Object obj) {
		if (obj.getClass().isArray()) {
			return Arrays.toString((Object[])obj);
		}
		return obj.toString();
	}

}
