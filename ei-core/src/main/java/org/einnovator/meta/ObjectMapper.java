/**
 * 
 */
package org.einnovator.meta;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.einnovator.convert.ConversionException;
import org.einnovator.convert.ConversionService;
import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.metaclass.DefaultMetaClass;
import org.einnovator.meta.metaclass.DefaultMetaClassRegistry;
import org.einnovator.meta.metaclass.build.DefaultMetaClassBuilder;
import org.einnovator.meta.metaclass.build.MetaClassFactoryBase;
import org.einnovator.meta.property.PropertyWrapper;
import org.einnovator.util.CollectionUtil;
import org.einnovator.util.types.TypeUtil;

import static org.einnovator.util.ValueUtil.coalesce;

/**
 * A mapper between inter/intra type objects.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ObjectMapper {

	private AssignOptions options;

	/**
	 * {@code MetaClassRegistry} to use. 
	 */
	private MetaClassRegistry registry;

	//
	// Constructors
	//

	/**
	 * Create instance of {@code ObjectMapper}.
	 *
	 * @param options
	 */
	public ObjectMapper(AssignOptions options) {
		this.options = options;
		this.registry = new DefaultMetaClassRegistry(new AssignMetaClassFactory());
	}

	/**
	 * Create instance of {@code ObjectMapper}.
	 *
	 */
	public ObjectMapper() {
		this(AssignOptions.newInstance());
	}
	
	//
	// Getters and Setters
	//


	/**
	 * Get the value of property {@code options}.
	 *
	 * @return the options
	 */
	public AssignOptions getOptions() {
		return options;
	}

	/**
	 * Set the value of property {@code options}.
	 *
	 * @param options the options to set
	 */
	public void setOptions(AssignOptions options) {
		this.options = options;
	}

	/**
	 * Get the value of property {@code registry}.
	 *
	 * @return the registry
	 */
	public MetaClassRegistry getRegistry() {
		return registry;
	}

	/**
	 * Set the value of property {@code registry}.
	 *
	 * @param registry the registry to set
	 */
	public void setRegistry(MetaClassRegistry registry) {
		this.registry = registry;
	}
	
	//
	// Inner classes
	//
	
	private class AssignMetaClassFactory extends MetaClassFactoryBase {
		
		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#createMetaClassBuilder(org.einnovator.meta.MetaClass, java.lang.Class)
		 */
		@Override
		protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			return new AssignedClassBuilder<T>(parent, theClass);
		}

		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#includeField(java.lang.Class, java.lang.reflect.Field)
		 */
		@Override
		protected boolean includeField(Class<?> theClass, Field field) {
			Assign assign = field.getAnnotation(Assign.class);
			if (assign!=null) {
				return !assign.ignore() && assign.value();
			}
			if (!super.includeField(theClass, field)) {
				return false;
			}
			Assign assign0 = field.getDeclaringClass().getAnnotation(Assign.class);
			if (assign0!=null) {
				return assign0.fieldAccess();
			}
			return true;
		}
		
		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#includeProperty(java.lang.Class, java.lang.reflect.Method, java.lang.reflect.Method)
		 */
		@Override
		protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
			Assign assign = getter.getAnnotation(Assign.class);
			if (assign!=null) {
				return !assign.ignore() && assign.value();
			}
			Assign assign0 = getter.getDeclaringClass().getAnnotation(Assign.class);
			if (assign0!=null) {
				return !assign0.fieldAccess();
			}
			return false;
		}
		
		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#createFieldProperty(java.lang.Class, java.lang.reflect.Field)
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createFieldProperty(Class<?> theClass, Field field) {
			return config(new AssignedProperty<Object>((Property<Object>)super.createFieldProperty(theClass, field)));
		}

		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#createGetterSetterProperty(java.lang.Class, java.lang.reflect.Method, java.lang.reflect.Method)
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createGetterSetterProperty(Class<?> theClass, Method getter, Method setter) {
			return config(new AssignedProperty<Object>((Property<Object>)super.createGetterSetterProperty(theClass, getter, setter)));
		}

		private <T> AssignedProperty<?> config(AssignedProperty<?> property) {
			property.options = AssignOptions.clone(options);
			Class<?> owner = property.getDeclaringClass();
			Assign assign0 = owner.getAnnotation(Assign.class);
			if (assign0!=null) {
				property.options.assign(assign0);
			}
			Assign assign = property.getAnnotation(Assign.class);
			if (assign!=null) {
				property.options.assign(assign);
			}
			property.simple = isSimple(property);
			return property;
		}
		

	}
	
	private static class AssignedClass<T> extends DefaultMetaClass<T> {
		public AssignedClass(MetaClass<?> parent, Class<T> theClass,
				Property<?>[] declaredProperties, Property<?>[] allProperties) {
			super(parent, theClass, declaredProperties, allProperties);
		}		
	}

	private static class AssignedProperty<T> extends PropertyWrapper<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private AssignOptions options;

		/**
		 * Flag specifying if property is simple. 
		 */
		private boolean simple;
		
		public AssignedProperty(Property<T> property) {
			super(property);
		}
		
	}
	private class AssignedClassBuilder<T> extends DefaultMetaClassBuilder<T> {
		
		public AssignedClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			super(parent, theClass);
		}	
		
		@Override
		protected MetaClass<T> createMetaClass(Property<?>[] declaredProperties, Property<?>[] allProperties) {
			AssignedClass<T> metaClass = new AssignedClass<T>(getParent(), getTheClass(), declaredProperties, allProperties);		
			config(metaClass);
			return metaClass;
		}
		
		private void config(AssignedClass<T> metaClass) {
		}
	}

	private boolean isSimple(AssignedProperty<?> property) {
		if (!property.options.isProjectionEmpty()) {				
			return false;
		}
		return isSimple(property.getType(), property.options);
	}

	private static boolean isSimpleOrImmutable(Class<?> type, AssignOptions options) {
		return isSimple(type, options) || isImmutable(type);
	}
	
	private static boolean isImmutable(Class<?> type) {
		return Class.class.equals(type);
	}
	
	private static boolean isSimple(Class<?> type, AssignOptions options) {
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
	// Clone
	//

	public <T> T clone(T obj) {
		return clone(obj, AssignOptions.emptyInstance());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T clone(T obj, AssignOptions options) {
		if (obj==null) {
			return null;
		}
		
		MetaClass<T> metaClass = registry.getMetaClass((Class<T>)obj.getClass());
		if (obj.getClass().isArray()) {
			return (T) cloneArray(obj, true, options);
		}
		if (Collection.class.isAssignableFrom(obj.getClass())) {
			return (T) cloneCollection((Collection<?>) obj, true, options);
		}
		if (isSimpleOrImmutable(obj.getClass(), options)) {
			return obj;
		}
		T copy = metaClass.newInstance();
		assign(copy, obj, options);
		return copy;
	}

	//
	// Assign (intra-type hierarchy)
	//

	public <T, U extends T> T assign(U left, T obj) {
		return assign(left, obj, AssignOptions.emptyInstance());
	}

	public <T, U extends T> T assign(U left, T obj, boolean deep) {
		return assign(left, obj, AssignOptions.emptyInstance().deep(deep));
	}
	
	public <T, U extends T>  T assign(U left, T right, AssignOptions options) {
		if (right==null) {
			return left;
		}
		MetaClass<?> metaClass = registry.getMetaClass(right.getClass());
		Property<?>[] properties = metaClass.getAllProperties();
		for (Property<?> property: properties) {
			@SuppressWarnings("unchecked")
			AssignedProperty<Object> property2 = (AssignedProperty<Object>)property;
			boolean overwrite = coalesce(options.getOverwrite(), property2.options.getOverwrite());
			Object oldValue = property.getValue(left);
			if (oldValue!=null && !overwrite) {
				continue;
			}
			Object value = property.getValue(right);
			if (!checkAssign(property2, value, options)) {
				continue;
			}
			//System.out.println(property + " " + oldValue + " " + value);
			if (isDeep(property2, value, options)) {
				value = clone(value, options);
			}
			property2.setValue(left, value);
			
		}
		return left;
	}

	private boolean checkAssign(AssignedProperty<?> property, Object value, AssignOptions options) {
		boolean overwriteWithNull = coalesce(options.getOverwriteWithNull(), property.options.getOverwriteWithNull());
		if (value==null && !overwriteWithNull) {
			return false;
		}
		if (value!=null) {
			if (property.getType().isArray()  || Collection.class.isAssignableFrom(property.getType())) {
				boolean overwriteWithEmpty = coalesce(options.getOverwriteWithEmpty(), property.options.getOverwriteWithEmpty());
				if (!overwriteWithEmpty) {
					if (property.getType().isArray() && Array.getLength(value)==0) {
						return false;
					}
					if (Collection.class.isAssignableFrom(property.getType()) && ((Collection<?>)value).isEmpty()) {
						return false;
					}					
				}					
			}
		}
		return true;
	}
	private boolean isDeep(AssignedProperty<?> property, Object value, AssignOptions options) {
		if (value==null) {
			return false;
		}
		boolean deep = coalesce(options.getDeep(), property.options.getDeep());
		if (!deep) {
			return false;
		}
		if (TypeUtil.isSimple(property.getType()) && property.simple &&
				(options.getSimpleTypeCriteria()==null || options.getSimpleTypeCriteria().check(property.getType()))) {
			return false;
		}
		return true;
	}


	public Object cloneArray(Object a, boolean deep, AssignOptions options) {
		int n = Array.getLength(a);
		Object a2 = Array.newInstance(a.getClass().getComponentType(), n);
		if (!deep) {
			System.arraycopy(a, 0, a2, 0, n);			
		} else {
			for (int i=0; i<n; i++) {
				Array.set(a2, i, clone(Array.get(a, i)));
			}
		}
		return a2;
	}

	@SuppressWarnings("unchecked")
	public Collection<?> cloneCollection(Collection<?> collection, boolean deep, AssignOptions options) {
		int n = collection.size();
		Collection<Object> collection2 = (Collection<Object>) CollectionUtil.createCollection(collection.getClass(), n);
		if (deep) {
			Iterator<Object> it = (Iterator<Object>) collection.iterator();
			while (it.hasNext()) {
				collection2.add(clone(it.next(), options));
			}			
		} else {
			collection2.addAll(collection);
		}
		return collection2;	
	}


	//
	// Assign (inter-type hierarchy)
	//

	public <T> T newInstance(Class<T> type, Object right) {
		return newInstance(type, right, options);
	}

	public <T> T newInstance(Class<T> type, Object right, AssignOptions options) {
		T left = MetaUtil.newInstance(type);
		assignFrom(left, right, options);
		return left;
	}

	public <T> T assignFrom(T left, Object right) {
		return assignFrom(left, right, options);
	}

	public <T> T assignFrom(T left, Object right, AssignOptions options) {
		MetaClass<?> leftMetaClass = registry.getMetaClass(left.getClass());
		MetaClass<?> rightMetaClass = registry.getMetaClass(right.getClass());
		for (Property<?> property: leftMetaClass.getAllProperties()) {
			@SuppressWarnings("unchecked")
			AssignedProperty<Object> property1 = (AssignedProperty<Object>)property;
			AssignedProperty<?> property2 = (AssignedProperty<?>)rightMetaClass.getProperty(property.getName());
			if (property2==null) {
				if (options.getRequired()) {
					throw new AssignException("Missing property " + property.getName() + " on type " + leftMetaClass.getName());
				}
				continue;
			}
			boolean overwrite = coalesce(options.getOverwrite(), property2.options.getOverwrite());
			Object oldValue = property.getValue(left);
			if (oldValue!=null && !overwrite) {
				continue;
			}
			Object value = property2.getValue(right);
			if (!checkAssign(property1, value, options)) {
				continue;
			}
			if (isDeep(property2, value, options)) {
				value = clone(value, options);
			}
			property1.setValue(left, value);	

			if (value!=null && !property.getType().isAssignableFrom(value.getClass())) {
				ConversionService conversionService = options.getConversionService();
				if (conversionService!=null && conversionService.supportsFormat(property.getType())) {
					value = conversionService.convert(value, property.getType());
					try {
					} catch (ConversionException e) {					
					}
				}
			}
			try {
				property1.setValue(left, value);					
			} catch (ClassCastException e) {
				if (property1.options.getRequired()) {
					throw e;					
				}
			}

		}
		return left;
	}

}
