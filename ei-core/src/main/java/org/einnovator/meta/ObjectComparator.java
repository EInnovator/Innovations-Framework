/**
 * 
 */
package org.einnovator.meta;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.einnovator.meta.criteria.TypeCriteria;
import org.einnovator.meta.metaclass.DefaultMetaClass;
import org.einnovator.meta.metaclass.DefaultMetaClassRegistry;
import org.einnovator.meta.metaclass.build.DefaultMetaClassBuilder;
import org.einnovator.meta.metaclass.build.MetaClassFactoryBase;
import org.einnovator.meta.property.PropertyWrapper;
import org.einnovator.util.types.TypeUtil;


/**
 * An object compartor.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ObjectComparator {


	private CompareOptions options;

	/**
	 * {@code MetaClassRegistry} to use. 
	 */
	private MetaClassRegistry registry;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code ObjectComparator}.
	 *
	 * @param options
	 */
	public ObjectComparator(CompareOptions options) {
		this.options = options;
		this.registry = new DefaultMetaClassRegistry(new ComparedMetaClassFactory());
	}

	/**
	 * Create instance of {@code ObjectComparator}.
	 *
	 */
	public ObjectComparator() {
		this(CompareOptions.newInstance());
	}
	
	//
	// Inner classes
	//
	
	private class ComparedMetaClassFactory extends MetaClassFactoryBase {
		@Override
		protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			return new ComparedClassBuilder<T>(parent, theClass);
		}

		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#includeField(java.lang.Class, java.lang.reflect.Field)
		 */
		@Override
		protected boolean includeField(Class<?> theClass, Field field) {
			Compare compare = field.getAnnotation(Compare.class);
			if (compare!=null) {
				return !compare.ignore() && compare.value();
			}
			if (!super.includeField(theClass, field)) {
				return false;
			}
			Compare compare0 = field.getDeclaringClass().getAnnotation(Compare.class);
			if (compare0!=null) {
				return compare0.fieldAccess();
			}
			return true;
		}
		
		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#includeProperty(java.lang.Class, java.lang.reflect.Method, java.lang.reflect.Method)
		 */
		@Override
		protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
			Compare compare = getter.getAnnotation(Compare.class);
			if (compare!=null) {
				return !compare.ignore() && compare.value();
			}
			Compare compare0 = getter.getDeclaringClass().getAnnotation(Compare.class);
			if (compare0!=null) {
				return !compare0.fieldAccess();
			}
			return false;
		}
		
		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#createFieldProperty(java.lang.Class, java.lang.reflect.Field)
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createFieldProperty(Class<?> theClass, Field field) {
			return config(new ComparedProperty<Object>((Property<Object>)super.createFieldProperty(theClass, field)));
		}

		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#createGetterSetterProperty(java.lang.Class, java.lang.reflect.Method, java.lang.reflect.Method)
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createGetterSetterProperty(Class<?> theClass, Method getter, Method setter) {
			return config(new ComparedProperty<Object>((Property<Object>)super.createGetterSetterProperty(theClass, getter, setter)));
		}

		private <T> ComparedProperty<?> config(ComparedProperty<?> property) {
			property.options = CompareOptions.clone(options);
			Class<?> owner = property.getDeclaringClass();
			Compare compare0 = owner.getAnnotation(Compare.class);
			if (compare0!=null) {
				property.options.compare(compare0);
			}
			Compare compare = property.getAnnotation(Compare.class);
			if (compare!=null) {
				property.options.compare(compare);
				property.simple = isSimple(property);
			}
			return property;
		}
		
		
		private boolean isSimple(ComparedProperty<?> property) {
			if (!property.options.isProjectionEmpty()) {				
				return false;
			}
			if (property.getType().isArray() || Collection.class.isAssignableFrom(property.getType())) {
				return false;
			}
			if (TypeUtil.isSimple(property.getType())) {
				return true;
			}
			if (options.getSimpleTypeCriteria()!=null && options.getSimpleTypeCriteria().check(property.getType())) {
				return true;
			}
			if (options.getConversionService()!=null) {
				return options.getConversionService().supportsFormat(property.getType());
			}
			return false;
		}
	
	}
	
	private static class ComparedClass<T> extends DefaultMetaClass<T> {
		public ComparedClass(MetaClass<?> parent, Class<T> theClass,
				Property<?>[] declaredProperties, Property<?>[] allProperties) {
			super(parent, theClass, declaredProperties, allProperties);
		}		
	}

	private static class ComparedProperty<T> extends PropertyWrapper<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private CompareOptions options;

		/**
		 * Flag specifying if property is simple. 
		 */
		private boolean simple;
		
		public ComparedProperty(Property<T> property) {
			super(property);
		}
		
	}
	private class ComparedClassBuilder<T> extends DefaultMetaClassBuilder<T> {
		
		public ComparedClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			super(parent, theClass);
		}	
		
		@Override
		protected MetaClass<T> createMetaClass(Property<?>[] declaredProperties, Property<?>[] allProperties) {
			ComparedClass<T> metaClass = new ComparedClass<T>(getParent(), getTheClass(), declaredProperties, allProperties);		
			config(metaClass);
			return metaClass;
		}
		
		private void config(ComparedClass<T> metaClass) {
		}
	}
	
	private boolean isSimple(ComparedProperty<?> property) {
		if (!property.options.isProjectionEmpty()) {				
			return false;
		}
		return isSimple(property.getType(), property.options);
	}

	private static boolean isSimpleOrImmutable(Class<?> type, CompareOptions options) {
		return isSimple(type, options) || isImmutable(type);
	}
	
	private static boolean isImmutable(Class<?> type) {
		return Class.class.equals(type);
	}
	
	private static boolean isSimple(Class<?> type, CompareOptions options) {
		if (type.isArray() || Collection.class.isAssignableFrom(type)) {
			return false;
		}
		if (TypeUtil.isSimple(type)) {
			return true;
		}
		if (options.getSimpleTypeCriteria()!=null && options.getSimpleTypeCriteria().check(type)) {
			return true;
		}
		if (options.getConversionService()!=null) {
			return options.getConversionService().supportsFormat(type);
		}
		return false;
	}
	//
	// equals
	//
	
	public boolean equals(Object a, Object b) {
		return equals(a, b, options);
	}

	public boolean equals(Object a, Object b, TypeCriteria simpleTypeCriteria) {
		return equals(a, b, options);
	}
	
	public boolean equals(Object a, Object b, CompareOptions options) {
		//System.out.println(a + "=?" + b);
		if (a==null && b==null) {
			return true;
		}
		if (a==null || b==null) {
			return false;
		}
		if (a instanceof Collection) {
			return equalsCollection((Collection<?>)a, (Collection<?>)b, options);
		}
		if (a.getClass().isArray()) {
			return equalsArray(a, b, options);
		}
		if (isSimple(a.getClass(), options)) {
			return a.equals(b);
		}
		if (!a.getClass().equals(b.getClass())) {
			return false;
		}
		return getMismatchProperty(a, b, options)==null;
	}

	private boolean equalsArray(Object a0, Object a1, CompareOptions options) {
		int n0 = Array.getLength(a0);
		int n1 = Array.getLength(a1);
		if (n0!=n1) {
			return false;
		}
		for (int i=0; i<n0; i++) {
			Object value0 = Array.get(a0, i);
			Object value1 = Array.get(a1, i);
			if (!equals(value0, value1, options)) {
				return false;
			}
		}
		return true;
	}

	private boolean equalsCollection(Collection<?> c0, Collection<?> c1, CompareOptions options) {
		if (c0.size()!=c1.size()) {
			return false;
		}
		Iterator<?> it0 = c0.iterator();
		Iterator<?> it1 = c0.iterator();
		while (it0.hasNext()) {
			Object value0 = it0.next();
			Object value1 = it1.next();
			if (!equals(value0, value1, options)) {
				return false;
			}
		}
		return true;	
	}

	public Property<?> getMismatchProperty(Object a, Object b, CompareOptions options) {
		MetaClass<?> metaClass = registry.getMetaClass(a.getClass());
		for (Property<?> property: metaClass.getAllProperties()) {
			Object avalue = property.getValue(a);
			Object bvalue = property.getValue(b);
			if (!equals(avalue, bvalue, options)) {
				return property;
			}
		}
		return null;
	}

	public Property<?> getMismatchProperty(Object a, Object b) {
		return getMismatchProperty(a, b, options);
	}


	//
	// compare
	//


}
