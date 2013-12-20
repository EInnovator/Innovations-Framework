/**
 * 
 */
package org.einnovator.meta;

import org.einnovator.meta.criteria.TypeCriteria;
import org.einnovator.meta.metaclass.DefaultMetaClassRegistry;
import org.einnovator.meta.metaclass.build.DefaultMetaClassFactory;

/**
 * Meta-Operations between objects.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class MetaOperations {

	//
	// Default MetaClassResolver
	//

	private static MetaClassResolver resolverFieldAccess, resolverPropertyAccess;

	public static MetaClassResolver getMetaClassResolver(boolean fieldAccess) {
		if (fieldAccess) {
			if (resolverFieldAccess==null) {
				resolverFieldAccess = createMetaClassResolver(fieldAccess);
			}
			return resolverFieldAccess;
		} else {
			if (resolverPropertyAccess==null) {
				resolverPropertyAccess = createMetaClassResolver(fieldAccess);
			}
			return resolverPropertyAccess;
		}
	}
	

	public static MetaClassResolver createMetaClassResolver(boolean fieldAccess) {
		return new DefaultMetaClassRegistry(new DefaultMetaClassFactory(/*fieldAccess*/));
	}


	public static MetaClassResolver getMetaClassResolver() {
		return getMetaClassResolver(true);
	}


	public static <T> MetaClass<T> getMetaClass(Class<T> type) {
		return getMetaClass(type, true);
	}

	public static <T> MetaClass<T> getMetaClass(Class<T> type, boolean fieldAccess) {
		return getMetaClassResolver(fieldAccess).getMetaClass(type);
	}

	//
	// ObjectMapper and ObjectMapper holders/lookup
	//
	
	private static ObjectMapper mapper;
	
	private static ObjectComparator comparator;
	
	public static void setObjectMapper(ObjectMapper mapper) {
		MetaOperations.mapper = mapper;
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}

	public static ObjectMapper getRequiredObjectMapper() {
		if (mapper==null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	public static void setObjectComparator(ObjectComparator comparator) {
		MetaOperations.comparator = comparator;
	}

	public static ObjectComparator getObjectComparator() {
		return comparator;
	}

	public static ObjectComparator getRequiredObjectComparator() {
		if (comparator==null) {
			comparator = new ObjectComparator();
		}
		return comparator;
	}

	//
	// Comparison
	//

	public static boolean equals(Object a, Object b, TypeCriteria simpleTypeCriteria) {
		return equals(a, b, new CompareOptions().simpleTypeCriteria(simpleTypeCriteria));
	}

	public static boolean equals(Object a, Object b) {
		return getRequiredObjectComparator().equals(a, b);
	}

	public static boolean equals(Object a, Object b, CompareOptions options) {
		return getRequiredObjectComparator().equals(a, b, options);
	}

	public static Property<?> getMismatchProperty(Object a, Object b) {
		return getRequiredObjectComparator().getMismatchProperty(a, b);
	}

	public static Property<?> getMismatchProperty(Object a, Object b, CompareOptions options) {
		return getRequiredObjectComparator().getMismatchProperty(a, b, options);
	}

	public static Property<?> getMismatchProperty(Object a, Object b, TypeCriteria simpleTypeCriteria) {
		return getMismatchProperty(a, b, new CompareOptions().simpleTypeCriteria(simpleTypeCriteria));
	}

	//
	// Assign
	//
	
	public static <T> T clone(T obj) {
		return getRequiredObjectMapper().clone(obj);
	}

	
	public static <T> T clone(T obj, AssignOptions options) {
		return getRequiredObjectMapper().clone(obj, options);
	}

	public static <T, U extends T> T assign(U left, T right) {
		return getRequiredObjectMapper().assign(left, right);
	}

	public static <T, U extends T> T assign(U left, T right, boolean deep) {
		return  getRequiredObjectMapper().assign(left, right, deep);
	}
	
	public static <T, U extends T>  T assign(U left, T right, AssignOptions options) {
		return getRequiredObjectMapper().assign(left, right, options);
	}


	public static <T> T newInstance(Class<T> type, Object right) {
		return getRequiredObjectMapper().newInstance(type, right);
	}

	public static <T> T newInstance(Class<T> type, Object right, AssignOptions options) {
		return getRequiredObjectMapper().newInstance(type, right, options);
	}
	
	public static <T> T assignFrom(T left, Object right) {
		return getRequiredObjectMapper().assignFrom(left, right);
	}

	public static <T> T assignFrom(T left, Object right, AssignOptions options) {
		return getRequiredObjectMapper().assignFrom(left, right, options);
	}

}
