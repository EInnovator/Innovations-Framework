/**
 * 
 */
package org.einnovator.util;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.einnovator.meta.MetaUtil;
import org.einnovator.util.Criteria;
import org.einnovator.util.types.TypeUtil;


/**
 * Miscellaneous collection operations.
 *
 * @author Jorge Simão
 */
public class CollectionUtil {
	static public <T> List<T> filterAsList(Collection<T> in, Criteria<T> criteria) {
		List<T> out = new ArrayList<T>();
		filter(in, out, criteria);
		return out;		
	}

	static public <T> Object[] filter(Collection<T> in, Criteria<T> criteria) {
		return filterAsList(in, criteria).toArray();
	}

	static public <T> void filter(Collection<T> in, Collection<T> out, Criteria<T> criteria) {
		Iterator<T> it = in.iterator();
		while (it.hasNext()) {
			T t = it.next();
			if (criteria==null || criteria.match(t)) {
				out.add(t);
			}
		}
	}

	static public <T> List<T> filterAsList(Collection<T> in, Long initial, Long max) {
		return filterAsList(in, null, initial, max);
	}

	static public <T> List<T> filterAsList(Collection<T> in, int initial, int max) {
		return filterAsList(in, null, initial, max);
	}
	
	static public <T> List<T> filterAsList(Collection<T> in, Criteria<T> criteria, Long initial, Long max) {
		return filterAsList(in, criteria, initial!=null ? initial.intValue() : 0, max!=null ? max.intValue() : 0);
	}

	static public <T> List<T> filterAsList(Collection<T> in, Criteria<T> criteria, int initial, int max) {
		if (in==null) {
			return null;
		}
		List<T> out = max>0 ? new ArrayList<T>(max) : new ArrayList<T>();
		int i = 0;
		Iterator<T> it = in.iterator();
		while (it.hasNext()) {
			T t = it.next();
			i++;
			if (i<initial) {
				continue;
			}
			if (max>0 && out.size()>=max) {
				break;
			}
			if (criteria==null || criteria.match(t)) {
				out.add(t);
			}
		}
		return out;		
	}

	static public <T> int count(Collection<T> in, Criteria<T> criteria) {
		if (in==null) {
			return 0;
		}
		Iterator<T> it = in.iterator();
		int n = 0;
		while (it.hasNext()) {
			T t = it.next();
			if (criteria!=null && !criteria.match(t)) {
				continue;
			}
			n++;
		}
		return n;		
	}

	
	public static Object convert(List<?> list, Class<?> type) {
		return convert(list, type, TypeUtil.getComponentType(type));
	}
	
	public static Object convert(List<?> list, Class<?> type, Class<?> elementType) {
		if (List.class.isAssignableFrom(type)) {
			return list;
		}
		if (type.isArray()) {
			return convertToArray(list, elementType);
		}
		if (Set.class.isAssignableFrom(type)) {
			return new TreeSet<Object>(list);
		}
		return null;
	}

	public static <T> T[] convertToArray(List<?> list, Class<T> elementType) {
		@SuppressWarnings("unchecked")
		T[] array = (T[])Array.newInstance(elementType, list.size());
		for (int index=0; index<list.size(); index++) {
			Array.set(array, index, list.get(index));
		}
		return array;			
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> asList(Object obj, Class<T> type) {
		return (List<T>)asList(obj);
	}
	
	public static List<?> asList(Object obj) {
		if (obj instanceof List) {
			return (List<?>)obj;
		}
		if (obj.getClass().isArray()) {
			return Arrays.asList((Object[])obj);
		}
		if (obj instanceof Collection) {
			return new ArrayList<Object>((Collection<?>)obj);
		}
		return null;
	}

	public static void print(Collection<?> collection) {
		print(collection, false);
	}
	
	public static void print(PrintStream out, Collection<?> collection, boolean inline) {
		out.println(toString(collection, inline));
	}

	public static void print(Collection<?> collection, boolean inline) {
		print(System.out, collection, inline);
	}
	
	public static String toString(Collection<?> collection, boolean inline) {
		return toString(collection, inline ? "," : "\n");
	}
	
	public static String toString(Collection<?> collection, String separator) {
		return toString(collection, "{", "}", separator);
	}
	
	public static String toString(Collection<?> collection, String beginMark, String endMark, String separator) {
		Iterator<?> it = collection.iterator();
		StringBuilder sb = new StringBuilder();
		if (beginMark!=null) {
			sb.append(beginMark);
		}
		while (it.hasNext()) {
			Object value = it.next();
			sb.append(ArrayUtil.toString(value));
			if (separator!=null && it.hasNext()) {
				sb.append(separator);
			}
		}
		if (endMark!=null) {
			sb.append(endMark);
		}
		return sb.toString();
	}

	public static String toString(Map<?, ?> map) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<?, ?> e: map.entrySet()) {
			sb.append(e.getKey());
			sb.append("=");
			sb.append(e.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Check if collection contains same element.
	 * 
	 * @param collection the collection
	 * @param obj the element
	 * @return <code>true</code>, if the collection contains the element; <code>false</code>, otherwise.
	 */
	public static boolean containsSame(Collection<?> collection, Object obj) {
		for (Object obj2: collection) {
			if (obj==obj2) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isEmpty(Collection<?> collection) {
		return collection==null || collection.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<?> createCollection(Class<?> type, int n) {
		if (!type.isInterface()) {

			Constructor<Collection<?>> constructor;
			if (n>0) {
				constructor = (Constructor<Collection<?>>) MetaUtil.getConstructorVArgs(type, Integer.TYPE);
				if (constructor!=null) {
					return MetaUtil.newInstance(constructor, new Object[]{n});
				}
			}
			constructor = (Constructor<Collection<?>>) MetaUtil.getNoArgsConstructor(type);
			if (constructor!=null) {
				return MetaUtil.newInstanceVArgs(constructor);
			}			
		} 
		if (List.class.isAssignableFrom(type)) {
			return n>0 ? new ArrayList<Object>(n) : new ArrayList<Object>();
		}
		if (Set.class.isAssignableFrom(type)) {
			return n>0 ? new HashSet<Object>(n) : new HashSet<Object>();
		}
		return (Collection<?>) MetaUtil.newInstance(type);
	}
}
