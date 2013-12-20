package org.einnovator.validation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.einnovator.log.Logger;
import org.einnovator.log.LoggerUtil;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;
import org.einnovator.meta.MetaClassRegistry;
import org.einnovator.meta.MetaDescriptor;
import org.einnovator.meta.ObjectRef;
import org.einnovator.meta.ObjectResolverHolder;
import org.einnovator.meta.Path;
import org.einnovator.meta.PathCriteria;
import org.einnovator.meta.Property;
import org.einnovator.meta.metaclass.DefaultMetaClass;
import org.einnovator.meta.metaclass.DefaultMetaClassRegistry;
import org.einnovator.meta.metaclass.build.DefaultMetaClassBuilder;
import org.einnovator.meta.metaclass.build.MetaClassFactoryBase;
import org.einnovator.meta.property.PropertyWrapper;
import org.einnovator.util.CollectionUtil;
import org.einnovator.util.types.TypeUtil;

public class ObjectValidator implements Validator<Object> {

	private final Logger logger = LoggerUtil.getLogger(this.getClass());

	/**
	 * {@code MetaClassRegistry} to use. 
	 */
	private MetaClassRegistry registry;

	private ValidationOptions options;
	
	private ValidatorFactory validatorFactory;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code ObjectValidator}.
	 *
	 * @param options
	 * @param validatorFactory
	 */
	public ObjectValidator(ValidationOptions options, ValidatorFactory validatorFactory) {
		this.options = options;
		this.validatorFactory = validatorFactory;
		this.registry = new DefaultMetaClassRegistry(new ValidatedClassFactory());
	}

	/**
	 * Create instance of {@code ObjectValidator}.
	 *
	 * @param options
	 * @param validatorFactory
	 */
	public ObjectValidator(ValidationOptions options) {
		this(options, ValidatorFactoryHolder.getRequiredInstance());
		this.registry = new DefaultMetaClassRegistry(new ValidatedClassFactory());
	}

	/**
	 * Create instance of {@code PropertiesMapper} with default {@code PropertiesOptions}.
	 *
	 */
	public ObjectValidator() {
		this(ValidationOptions.newInstance());
	}

	//
	// Getters and Setters
	//

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
	protected void setRegistry(MetaClassRegistry registry) {
		this.registry = registry;
	}

	/**
	 * Get the value of property {@code options}.
	 *
	 * @return the options
	 */
	public ValidationOptions getOptions() {
		return options;
	}

	/**
	 * Set the value of property {@code options}.
	 *
	 * @param options the options to set
	 */
	protected void setOptions(ValidationOptions options) {
		this.options = options;
	}
	
	//
	// Inner classes
	//
	
	private class ValidatedClassFactory extends MetaClassFactoryBase {
		@Override
		protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			return new FormattedClassBuilder<T>(parent, theClass);
		}

		private Validator<? extends Object> validator; //non-thread safe
		
		@Override
		protected boolean includeField(Class<?> theClass, Field field) {
			if (!super.includeField(theClass, field)) {
				return false;
			}
			validator = validatorFactory.createValidator(new MetaDescriptor(field));
			if (validator!=null) {
				return true;
			}
			Valid valid = field.getAnnotation(Valid.class);
			if (valid!=null) {
				return valid.value() && !valid.ignore();
			}
			Valid valid0 = field.getDeclaringClass().getAnnotation(Valid.class);
			if (valid0!=null) {
				return valid0.value() && !valid0.ignore() && valid0.fieldAccess();
			}
			return false;
		}
		
		@Override
		protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
			Valid valid = getter.getAnnotation(Valid.class);
			if (valid!=null) {
				return valid.value() && !valid.ignore();
			}
			Valid valid0 = getter.getDeclaringClass().getAnnotation(Valid.class);
			if (valid0!=null) {
				return valid0.value() && !valid0.ignore() && !valid0.fieldAccess();
			}
			return false;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createFieldProperty(Class<?> theClass, Field field) {
			return config(new ValidatedProperty<Object>((Property<Object>)super.createFieldProperty(theClass, field)));
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createGetterSetterProperty(Class<?> theClass, Method getter, Method setter) {
			return config(new ValidatedProperty<Object>((Property<Object>)super.createGetterSetterProperty(theClass, getter, setter)));
		}

		@SuppressWarnings("unchecked")
		private <T> ValidatedProperty<T> config(ValidatedProperty<T> property) {
			property.options = ValidationOptions.clone(options);
			Class<?> owner = property.getDeclaringClass();
			Valid valid0 = owner.getAnnotation(Valid.class);
			if (valid0!=null) {
				property.options.assign(valid0);
			}
			Valid valid = property.getAnnotation(Valid.class);
			if (valid!=null) {
				property.options.assign(valid);
				property.recurse = valid.recurse();
			}
			property.simple = isSimple(property);
			if (property.simple) {
				if (!property.options.isProjectionEmpty()) {				
					logger.warning(this, "config", "ignoring projection on simple property: ", property);
				}			
				property.recurse = false;
			} else {
				property.recurse = true;
			}
			if (valid!=null && !ObjectRef.isEmpty(valid.validator())) {
				property.validator = getObject(ObjectRef.newInstance(Validator.class).assign(valid.validator()));
			}
			property.validator =  (Validator<T>) CompositeValidatorFactory.mergeValidators(validator, property.validator);
			if (property.simple && property.validator==null && valid!=null) {
				logger.warning(this, "config", "missing validator for " + property);
			}
			logger.debug(this, "config", property, property.validator);
			
			return property;
		}
		
		private boolean isSimple(ValidatedProperty<?> property) {
			return isSimple(property.getType());
		}
		
		private boolean isSimple(Class<?> type) {
			if (type.isArray() || Collection.class.isAssignableFrom(type)) {
				return false;
			}
			if (TypeUtil.isSimple(type)) {
				return true;
			}
			return false;
		}
	
	}
	
	private <T> T getObject(ObjectRef<T> ref) {
		return ObjectResolverHolder.getRequiredInstance().getObject(ref.defaultType());
	}

	private static class ValidatedClass<T> extends DefaultMetaClass<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private ValidationOptions options;		

		/**
		 * The {@code Validator}.
		 */
		private Validator<T> validator;

		public ValidatedClass(MetaClass<?> parent, Class<T> theClass,
				Property<?>[] declaredProperties, Property<?>[] allProperties) {
			super(parent, theClass, declaredProperties, allProperties);
		}
		
	}

	private static class ValidatedProperty<T> extends PropertyWrapper<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private ValidationOptions options;

		/**
		 * Flag specifying if property is simple. 
		 */
		private boolean simple;
		
		/**
		 * The {@code Validator}.
		 */
		private Validator<T> validator;
		
		/**
		 * Flag specifying if recursive field/property formatting should be applied.
		 */
		private boolean recurse;

		public ValidatedProperty(Property<T> property) {
			super(property);
		}
		
		
	}
	private class FormattedClassBuilder<T> extends DefaultMetaClassBuilder<T> {
		
		public FormattedClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			super(parent, theClass);
		}	
		
		@Override
		protected MetaClass<T> createMetaClass(Property<?>[] declaredProperties, Property<?>[] allProperties) {
			ValidatedClass<T> metaClass = new ValidatedClass<T>(getParent(), getTheClass(), declaredProperties, allProperties);		
			config(metaClass);
			return metaClass;
		}
		
		@SuppressWarnings("unchecked")
		private void config(ValidatedClass<T> metaClass) {
			metaClass.options = ValidationOptions.clone(options);
			Valid valid = getTheClass().getAnnotation(Valid.class);
			if (valid!=null) {
				metaClass.options.assign(valid);
			} else {
			}
			metaClass.validator = (Validator<T>) validatorFactory.createValidator(new MetaDescriptor(metaClass.getTheClass()));
	
		}
	}

	//
	// Validator implementation
	//
	
	/**
	 * @see org.einnovator.valid.Validator#validate(java.lang.Object)
	 */
	@Override
	public boolean validate(Object obj, ValidationContext context) {
		if (obj==null) {
			return true;
		}
		if (context==null) {
			context = new ValidationContext();
		}
		List<Object> visited = null;
		if (options.getCheckCycles()) {
			visited = new ArrayList<Object>();
			visited.add(obj);
		}
		if (context.getPath()==null && options.getPaths()) {
			context.setPath(new Path());
		}
		if (context.getErrors()==null) {
			context.setErrors(new Errors());
		}
		return validate(obj, context, visited);
	}


	//
	// Validation
	//
	
	@SuppressWarnings("unchecked")
	private boolean validate(Object obj, ValidationContext context, List<Object> visited) {
		if (obj==null) {
			return true;
		}
		ValidatedClass<?> metaClass = (ValidatedClass<?>) registry.getMetaClass(obj.getClass());
		if (metaClass.validator!=null) {
			return ((Validator<Object>)metaClass.validator).validate(obj, context);
		}
		return validateProperties(obj, metaClass, context, visited);
	}

	private boolean validateProperties(Object obj, ValidatedClass<?> metaClass, 
			ValidationContext context, List<Object> visited) {		
		boolean valid = true;
		PathCriteria projection = context.getProjection();
		Path path = context.getPath();
		for (Property<?> property: metaClass.getAllProperties()) {
			if (path!=null) {
				path.push(property);
			}
			if (!includeProperty((ValidatedProperty<?>)property, path, projection)) {
				if (path!=null) {
					path.pop();
				}
				continue;
			}
			Object value = property.getValue(obj);
			if (!validate(value, (ValidatedProperty<?>)property, metaClass, context, visited)) {
				valid = false;
				logger.debug(this, ".validate[FALSE]:", value, property, path, projection);
			}
			if (path!=null) {
				path.pop();
			}
		}
		return valid;
	}
	
	protected boolean includeProperty(ValidatedProperty<?> property, Path path, PathCriteria projection) {
		if (projection!=null) {
			if (path!=null) {
				return projection.checkInclude(path, false, true);
			}  else {
				return projection.checkInclude(property, false);
			}
		} else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean validate(Object value, ValidatedProperty<?> property, ValidatedClass<?> metaClass, ValidationContext context, List<Object> visited) {
		if (property.validator!=null) {
			return ((Validator<Object>)property.validator).validate(value, context);
		} else if (value!=null) {
			if (value instanceof Collection) {
				return validateCollection(property, (Collection<?>)value, context, visited);	
			} else if (property.getType().isArray()) {
				return validateArray(property, value, context, visited);				
			} else if (!property.simple && property.recurse) {
				if (visited!=null) {
					if (CollectionUtil.containsSame(visited, value)) {
						return true;
					}
					visited.add(value);
				}
				PathCriteria projection = context.getProjection();
				PathCriteria projection2 = property.options.getProjection();
				boolean advance = false;
				Path path = context.getPath();
				if (projection!=null) {
					if (path!=null) {
						if (projection.checkAnyIncluded(path)) {
							if (!projection.checkInclude(path, false, false)) {
								projection2 = projection;						
							} else {
								path.forward();
								advance = true;
							}
						}
					} else {					
						projection2 = projection;
					}
				} else {
					path.forward();
					advance = true;
				}
				context.setProjection(projection2);
				boolean valid = validate(value, context, visited);
				context.setProjection(projection);
				if (advance) {
					path.backward();
				}
				return valid;
			}			
		}
		return true;
	}
	

	@SuppressWarnings("unchecked")
	protected boolean validateValue(ValidatedProperty<?> property, Object value, ValidationContext context) {
		return ((ValidatedProperty<Object>)property).validator.validate(value, context);
	}
	
	private boolean validateCollection(ValidatedProperty<?> property, Collection<?> collection, 
			ValidationContext context, List<Object> visited) {
		boolean valid = true;
		Iterator<?> it = collection.iterator();
		while (it.hasNext()) {
			Object value = it.next();
			if (!validate(value, context, visited)) {
				valid = false;
			};
		}
		return valid;
	}

	private boolean validateArray(ValidatedProperty<?> property, Object array, ValidationContext context, List<Object> visited) {
		boolean valid = true;
		for (int i=0; i<Array.getLength(array); i++) {
			if (!validate(Array.get(array, i), context, visited)) {
				valid = false;
			}
		}
		return valid;
	}

	
}
