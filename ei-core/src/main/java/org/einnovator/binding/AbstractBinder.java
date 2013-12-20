package org.einnovator.binding;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.einnovator.convert.ConversionException;
import org.einnovator.convert.ConversionService;
import org.einnovator.convert.ConversionServiceHolder;
import org.einnovator.log.Logger;
import org.einnovator.log.LoggerUtil;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;
import org.einnovator.meta.MetaClassResolver;
import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.ProjectionOptions;
import org.einnovator.meta.Property;
import org.einnovator.meta.metaclass.DefaultMetaClassRegistry;
import org.einnovator.meta.metaclass.build.DefaultMetaClassBuilder;
import org.einnovator.meta.metaclass.build.MetaClassFactoryBase;
import org.einnovator.meta.property.PropertyWrapper;
import org.einnovator.util.CollectionUtil;
import org.einnovator.util.StringUtil;
import org.einnovator.util.types.TypeUtil;
import org.einnovator.validation.Errors;

//import org.einnovator.context.cdi.CDIUtil;


/**
 * AA binder from parameter in an external and properties in a target object.
 * 
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class AbstractBinder implements Binder {
	
	static public Logger log = LoggerUtil.getLogger(AbstractBinder.class);
	
	static public final String TYPE_PREFIX = "Type.";
	
	static public final String ERROR_MSG = "Type conversion error";

	private MetaClassResolver metaClassResolver;

	private ConversionService conversionService;

	private boolean checkVisited = true;
	
	private boolean rootPrefix;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code Binder}.
	 *
	 * @param metaClassResolver
	 * @param conversionService
	 */
	protected AbstractBinder(MetaClassResolver metaClassResolver, ConversionService conversionService) {
		this.metaClassResolver = metaClassResolver;
		this.conversionService = conversionService;
	}

	/**
	 * Create instance of Binder.
	 *
	 * @param metaClassResolver
	 */
	protected AbstractBinder(MetaClassResolver metaClassResolver) {
		this.metaClassResolver = metaClassResolver;
	}
	
	/**
	 * Create instance of {@code Binder}.
	 *
	 * @param conversionService
	 */
	public AbstractBinder(ConversionService conversionService) {
		this.metaClassResolver = new DefaultMetaClassRegistry(new BindedMetaClassFactory());
		this.conversionService = conversionService;
	}
	
	/**
	 * Create instance of {@code Binder}.
	 *
	 * @param conversionService
	 */
	public AbstractBinder() {
		this(ConversionServiceHolder.getRequiredConversionService());
	}
	
	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code checkVisited}.
	 *
	 * @return the checkVisited
	 */
	public boolean isCheckVisited() {
		return checkVisited;
	}

	/**
	 * Set the value of property {@code checkVisited}.
	 *
	 * @param checkVisited the checkVisited to set
	 */
	public void setCheckVisited(boolean checkVisited) {
		this.checkVisited = checkVisited;
	}
	
	/**
	 * Get the value of property {@code metaClassResolver}.
	 *
	 * @return the metaClassResolver
	 */
	public MetaClassResolver getMetaClassResolver() {
		return metaClassResolver;
	}

	/**
	 * Set the value of property {@code metaClassResolver}.
	 *
	 * @param metaClassResolver the metaClassResolver to set
	 */
	public void setMetaClassResolver(MetaClassResolver metaClassResolver) {
		this.metaClassResolver = metaClassResolver;
	}

	/**
	 * Get the value of property {@code conversionService}.
	 *
	 * @return the conversionService
	 */
	public ConversionService getConversionService() {
		return conversionService;
	}	
	
	/**
	 * Set the value of property {@code conversionService}.
	 *
	 * @param conversionService the conversionService to set
	 */
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	//
	// Inner classes
	//
	
	private class BindedMetaClassFactory extends MetaClassFactoryBase {
		@Override
		protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			return new BindedClassBuilder<T>(parent, theClass);
		}
		
		@Override
		protected void postProcess(MetaClass<?> metaClass) {
			super.postProcess(metaClass);
			Property<?>[] properties = metaClass.getAllProperties();
			for (Property<?> property: properties) {
				BindedProperty<?> property2 = (BindedProperty<?>)property;
				property2.simple = isSimple(property2);
				Class<?> componentType = property.getComponentType();
				if (componentType!=null) {
					property2.simpleComponent = isSimple(componentType);
				}
			}
		}

		@Override
		protected boolean includeField(Class<?> theClass, Field field) {
			if (!AbstractBinder.this.includeMember(theClass, field)) {
				return false;
			}
			Bind bind = field.getAnnotation(Bind.class);
			if (bind!=null) {
				return bind.value();
			}
			if (!super.includeField(theClass, field)) {
				return false;
			}
			Bind bind0 = field.getDeclaringClass().getAnnotation(Bind.class);
			if (bind0!=null) {
				return bind0.fieldAccess();
			}
			return true;
		}
		
		@Override
		protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
			if (setter==null) {
				return false;
			}
			if (!AbstractBinder.this.includeMember(theClass, setter)) {
				return false;
			}
			Class<?> type = MetaUtil.getPropertyType(setter);
			if (!(TypeUtil.isPrimitive(type) || type.isArray()) && Modifier.isAbstract(type.getModifiers())) {
					return false;
			}
			Bind bind = getter.getAnnotation(Bind.class);
			if (bind!=null) {
				return bind.value();
			}
			Bind bind0 = getter.getDeclaringClass().getAnnotation(Bind.class);
			if (bind0!=null) {
				return !bind0.fieldAccess();
			}
			return false;
		}		
		

		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#includeMember(java.lang.Class, java.lang.reflect.Member)
		 */
		protected boolean includeMember(Class<?> theClass, Member member) {
			return AbstractBinder.this.includeMember(theClass, member);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createFieldProperty(Class<?> theClass, Field field) {
			return config(new BindedProperty<Object>((Property<Object>)super.createFieldProperty(theClass, field)));
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createGetterSetterProperty(Class<?> theClass, Method getter, Method setter) {
			return config(new BindedProperty<Object>((Property<Object>)super.createGetterSetterProperty(theClass, getter, setter)));
		}

		private <T> BindedProperty<?> config(BindedProperty<?> property) {
			Class<?> owner = property.getDeclaringClass();
			Bind bind0 = owner.getAnnotation(Bind.class);
			if (bind0!=null) {
			}
			Bind bind = property.getAnnotation(Bind.class);
			if (bind!=null) {
				property.parameter = !bind.name().isEmpty() ? bind.name() : property.getName();
				if (!bind.binder().equals(AbstractBinder.class)) {
					property.binder = createBinder(bind.binder());
				}
			} else {
				property.parameter = property.getName();
			}
			return property;
		}
		
		
		private boolean isSimple(BindedProperty<?> property) {
			if (property.projection!=null) {				
				return false;
			}
			return isSimple(property.getType());
		}
		
		private boolean isSimple(Class<?> type) {
			if (TypeUtil.isPrimitiveOrSimple(type)) {
				return true;
			}
			if (type.isArray() || Collection.class.isAssignableFrom(type)) {
				return false;
			}
			if (conversionService!=null && conversionService.supportsFormat(type)) {
				return true;
			}
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private Binder createBinder(Class<?> binderClass) {
		return MetaUtil.newInstance((Class<? extends Binder>)binderClass);
	}
	
	private class BindedClassBuilder<T> extends DefaultMetaClassBuilder<T> {
		
		public BindedClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			super(parent, theClass);
		}	
	}
	
	private static class BindedProperty<T> extends PropertyWrapper<T> {

		/**
		 * Label for field or property.
		 * 
		 */
		private String parameter;

		/**
		 * Flag specifying if property type is simple. 
		 */
		private boolean simple;

		/**
		 * Flag specifying if property component type is simple. 
		 */
		private boolean simpleComponent;
		
		/**
		 * Class of custom binder for this field or property.
		 */
		private Binder binder;
		
		private ProjectionOptions projection;

		public BindedProperty(Property<T> property) {
			super(property);
		}
		
	}

	/**
	 * Hook method to allow injected properties to be excluded from the binding.
	 * 
	 * @param theClass the class for the built {@code MetaClass}
	 * @param member the {@code Member}
	 * @return <code>true</code>, if the {@code Member} should be included as property;
	 * <code>false</code>, otherwise.
	 */
	protected boolean includeMember(Class<?> theClass, Member member) {
		return true;
	}
	
	//
	// Public methods
	//

	public Errors bind(Object target, Object context) {
		//System.out.println(this + ".bind: OBJ:" +  target + " " + context.getParameterMap());
		String prefix = rootPrefix ? MetaUtil.getVariableName(target.getClass()) : null;
		return bind(prefix, target, null, context);
	}

	public Errors bind(String prefix, final Object target, List<Property<?>> initializedProperties, Object context) {
		return bind(prefix, target, initializedProperties, context, checkVisited ? new ArrayList<Object>() : null);
	}

	private Errors bind(String prefix, final Object target, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		//final Object target = CDIUtil.unwrapProxy(target_);
		MetaClass<?> metaClass = metaClassResolver.getMetaClass(target.getClass());
		return bind(prefix, target, metaClass, initializedProperties, context, visited);
	}
	
	private Errors bind(final String prefix, final Object target, MetaClass<?> metaClass, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		final Errors errors = new Errors();
		log.trace(this, "bind", prefix, target);

		preProcessTarget(target);
		Property<?>[] properties = metaClass.getAllProperties();
		for (Property<?> property: properties) {
			BindedProperty<?> property2 = (BindedProperty<?>)property;
			log.trace(this, "bind", prefix, property);
			
			String key = key(prefix, property2.parameter);
			if (property2.binder!=null) {
				Errors errors2 = property2.binder.bind(key, target, initializedProperties, context);
				if (errors2!=null && !errors2.ok()) {
					errors.addAll(errors2);					
				}
				continue;
			}			
			Object value;
			if (property2.simple) {
				value = setProperty(target, property, key, errors, context);
			} else if (property.getType().isArray()){
				value = setArrayProperty(target, property2, key, errors, initializedProperties, context, visited);
			} else if (Collection.class.isAssignableFrom(property.getType())) {
				value = setCollectionProperty(target, property2, key, errors, initializedProperties, context, visited);
			} else {
				if (visited!=null) {
					if (CollectionUtil.containsSame(visited, property)) {
						break;
					}
					visited.add(property);
				}
				value = setComplexProperty(target, property, key, errors, context, visited);
			}
			if (initializedProperties!=null && value!=null) {
				initializedProperties.add(property);
			}
		}
		
		return errors;
	}

	@SuppressWarnings("unchecked")
	protected Object setArrayProperty(Object target, BindedProperty<?> property, String key, Errors errors, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		Object value = getArrayParameterValue(key, property, errors, initializedProperties, context, visited);
		if (value!=null) {
			((Property<Object>)property).setValue(target, value);			
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	protected Object setCollectionProperty(Object target, BindedProperty<?> property, String key, Errors errors, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		Object value = getCollectionParameterValue(key, property, errors, initializedProperties, context, visited);
		if (value!=null) {
			((Property<Object>)property).setValue(target, value);			
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	protected Object setProperty(Object target, Property<?> property, String key, Errors errors, Object context) {
		Object value = getParameterValue(key, property.getType(), errors, context);
		if (value!=null) {
			((Property<Object>)property).setValue(target, value);			
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	protected Object setComplexProperty(Object target, Property<?> property, String key, Errors errors, Object context, List<Object> visited) {
		Object value = property.getValue(target);
		List<Property<?>> initializedProperties = new ArrayList<Property<?>>();
		value = getComplexValue(value, key, property.getType(), errors, initializedProperties, context, visited);
		if (value!=null && initializedProperties.size()>0) {
			((Property<Object>)property).setValue(target, value);	
			log.trace(this, "bind", key, "=", value);
			return value;
		}
		return null;
	}

	protected Object getComplexValue(Object value, String key, Class<?> type, Errors errors, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		if (value==null) {
			value = MetaUtil.newInstance(type);
			log.trace(this, "bind", key, "=" , value);
		}
		if (value!=null) {
			Errors errors2 = bind(key, value, initializedProperties, context, visited);
			errors.addAll(errors2);
		}
		return value;
	}

	protected <T> T getParameterValue(String key, Class<T> type, Errors errors, Object context) {
		//if (context.getParameterMap().get(key)==null) {
		//	return null;
		//}
		String paramValue = getParameter(key, context);
		if (paramValue==null || paramValue.isEmpty()) {
			return null;
		}		
		try {
			T value = conversionService.parse(paramValue, type, getLocale(context));
			log.trace(this, "bind", key, "=", paramValue, value);
			return value;
		} catch (ConversionException e) {
			log.info(this, "bind: ERROR", key, "=", paramValue, type);
			errors.addError(paramValue, TYPE_PREFIX + key, ERROR_MSG, paramValue);
			return null;
		}
	}

	protected Object getArrayParameterValue(String key, BindedProperty<?> property, Errors errors, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		Object avalues = getParameterValue(key, property.getType(), errors, context);
		if (avalues!=null) {
			return avalues;
		}
		Object values = getListParameterValue(key, property, errors, initializedProperties, context, visited);
		if (values==null || !List.class.isAssignableFrom(values.getClass())) {
			return values;
		}
		List<?> list = (List<?>)values;
		if (list.size()==0) {
			return null;
		}
		avalues = Array.newInstance(property.getComponentType(), list.size());
		for (int i=0; i<TypeUtil.size(values); i++) {
			Array.set(avalues, i, list.get(i));
			log.trace(this, "bind", key, "[", i , "]=", list.get(i));
		}
		return avalues;
	}

	protected Object getCollectionParameterValue(String key, BindedProperty<?> property, Errors errors, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		Object values = getListParameterValue(key, property, errors, initializedProperties, context, visited);
		if (values==null) {
			return null;
		}
		return CollectionUtil.convert((List<?>)values, property.getType(), null);			
	}

		
	protected Object getListParameterValue(String key, BindedProperty<?> property, Errors errors, List<Property<?>> initializedProperties, Object context, List<Object> visited) {
		Object avalues = getParameterValue(key, property.getType(), errors, context);
		if (avalues!=null) {
			return avalues;
		}
		List<Object> values = null;
		if (property.simpleComponent) {
			for (int i = 0;; i++) {
				String keyi = key + "[" + i + "]";
				String paramValuei = getParameter(keyi, context);
				if (StringUtil.isEmpty(paramValuei)) {
					break;
				}
				//if (context.getParameterMap().get(keyi)==null) {
				//	break;
				//}
				try {
					Object valuei = conversionService.parse(paramValuei, property.getComponentType(), getLocale(context));					
					if (values==null) {
						values = new ArrayList<Object>();
					}
					values.add(valuei);										
				} catch (ConversionException e) {
					errors.addError(key, TYPE_PREFIX + keyi, ERROR_MSG, paramValuei);
				}
			}			
		} else {
			for (int i = 0;; i++) {
				String prefix = key + "[" + i + "]";
				//Object value = property.getValue(target);
				initializedProperties = new ArrayList<Property<?>>();
				Object valuei = getComplexValue(null, prefix, property.getComponentType(), errors, initializedProperties, context, visited);
				if (valuei==null || initializedProperties.isEmpty()) {
					break;
				}
				if (values==null) {
					values = new ArrayList<Object>();
				}
				values.add(valuei);										
			}		
		}
		if (values==null || values.size()==0) {
			return null;
		}
		return values;
	}

	protected String key(String prefix, String name) {
		if (prefix!=null && !prefix.isEmpty()) {
			return prefix + "." + name;
		} else {
			return name;
		}
	}
	
	protected abstract String getParameter(String name, Object context);

	protected abstract Locale getLocale(Object context);

	protected void preProcessTarget(Object obj) {
	}
}