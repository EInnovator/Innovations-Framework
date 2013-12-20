/**
 * 
 */
package org.einnovator.environment;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.convert.ConversionService;
import org.einnovator.format.Printer;
import org.einnovator.format.ObjectPrinter;
import org.einnovator.log.Logger;
import org.einnovator.log.LoggerUtil;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;
import org.einnovator.meta.MetaClassRegistry;
import org.einnovator.meta.MetaUtil;
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


/**
 * A generic properties from POJO importer/exporter.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see ObjectPrinter
 */
public class PropertiesMapper {

	final Logger logger = LoggerUtil.getLogger(this.getClass());

	/**
	 * {@code MetaClassRegistry} to use. 
	 */
	private MetaClassRegistry registry;

	private PropertiesOptions options;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code PropertiesMapper}.
	 *
	 * @param metaClass
	 */
	
	/**
	 * Create instance of {@code PropertiesMapper}.
	 *
	 * @param options the {@code PropertiesOptions}
	 */
	public PropertiesMapper(PropertiesOptions options) {
		this.options = options;
		this.registry = new DefaultMetaClassRegistry(new PropMetaClassFactory());
	}

	/**
	 * Create instance of {@code PropertiesMapper} with default {@code PropertiesOptions}.
	 *
	 */
	public PropertiesMapper() {
		this(PropertiesOptions.newInstance());
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
	public void setRegistry(MetaClassRegistry registry) {
		this.registry = registry;
	}

	/**
	 * Get the value of property {@code options}.
	 *
	 * @return the options
	 */
	public PropertiesOptions getOptions() {
		return options;
	}

	/**
	 * Set the value of property {@code options}.
	 *
	 * @param options the options to set
	 */
	public void setOptions(PropertiesOptions options) {
		this.options = options;
	}
	
	//
	// Inner classes
	//
	
	private class PropMetaClassFactory extends MetaClassFactoryBase {
		@Override
		protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			return new MappedClassBuilder<T>(parent, theClass);
		}

		/**
		 * @see org.einnovator.meta.metaclass.build.MetaClassFactoryBase#includeField(java.lang.Class, java.lang.reflect.Field)
		 */
		@Override
		protected boolean includeField(Class<?> theClass, Field field) {
			Prop prop = field.getAnnotation(Prop.class);
			if (prop!=null) {
				return !prop.ignore() && prop.value();
			}
			if (!super.includeField(theClass, field)) {
				return false;
			}
			Prop prop0 = field.getDeclaringClass().getAnnotation(Prop.class);
			if (prop0!=null) {
				return prop0.fieldAccess();
			}
			return true;
		}
		
		@Override
		protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
			Prop prop = getter.getAnnotation(Prop.class);
			if (prop!=null) {
				return !prop.ignore() && prop.value();
			}
			Prop prop0 = getter.getDeclaringClass().getAnnotation(Prop.class);
			if (prop0!=null) {
				return !prop0.fieldAccess();
			}
			return false;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createFieldProperty(Class<?> theClass, Field field) {
			return config(new MappedProperty<Object>((Property<Object>)super.createFieldProperty(theClass, field)));
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createGetterSetterProperty(Class<?> theClass, Method getter, Method setter) {
			return config(new MappedProperty<Object>((Property<Object>)super.createGetterSetterProperty(theClass, getter, setter)));
		}

		private <T> MappedProperty<?> config(MappedProperty<?> property) {
			Class<?> owner = property.getDeclaringClass();
			property.options = PropertiesOptions.clone(options);
			Prop prop0 = owner.getAnnotation(Prop.class);
			if (prop0!=null) {
				property.options.assign(prop0);
			}
			Prop prop = property.getAnnotation(Prop.class);
			String key;
			if (prop!=null) {
				key = !prop.key().isEmpty() ? prop.key() : property.getName();
				property.options.assign(prop);
				property.optional = prop.optional();
				property.simple = isSimple(property);
			} else {
				key = property.getName();
				property.simple = isSimple(property);
			}
			if (property.recurse && property.simple) {
				logger.warning(this, "config", "recurse or projection ignored for simple property: ", property);
			}
			property.key = (prop0!=null && !prop0.key().isEmpty()) ? (prop0.key() + "." + key) : key;
			return property;
		}
		
		
		private boolean isSimple(MappedProperty<?> property) {
			if (property.getType().equals(String.class) || 
				TypeUtil.isNumeric(property.getType()) ||
				TypeUtil.isBool(property.getType())) {
				return true;
			}
			ConversionService conversionService = options.getConversionService();
			if (conversionService!=null) {
				return conversionService.supportsFormat(property.getType());
			}
			return false;
		}
	
	}

	@SuppressWarnings("unchecked")
	private Printer<?> createPrinter(Class<?> printerClass) {
		return MetaUtil.newInstance((Class<Printer<?>>)printerClass);
	}
	
	private static class MappedClass<T> extends DefaultMetaClass<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private PropertiesOptions options;
		
		/**
		 * Label for field or property.
		 * 
		 */
		private String key;		

		/**
		 * Class of custom printer for this field or property.
		 */
		private Printer<?> printer;


		public MappedClass(MetaClass<?> parent, Class<T> theClass,
				Property<?>[] declaredProperties, Property<?>[] allProperties) {
			super(parent, theClass, declaredProperties, allProperties);
		}
		
	}

	private static class MappedProperty<T> extends PropertyWrapper<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private PropertiesOptions options;

		/**
		 * Flag specifying if property is simple. 
		 */
		private boolean simple;
		
		/**
		 * Label for field or property.
		 * 
		 */
		private String key;

		/**
		 * Flag specifying if field or property should be ignored unless explicitly asked to be included.
		 */
		boolean optional = false;	

		/**
		 * Flag specifying if recursive field/property propting should be applied.
		 */
		boolean recurse = false;
		

		public MappedProperty(Property<T> property) {
			super(property);
		}
		
	}
	private class MappedClassBuilder<T> extends DefaultMetaClassBuilder<T> {
		
		public MappedClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			super(parent, theClass);
		}	
		
		@Override
		protected MetaClass<T> createMetaClass(Property<?>[] declaredProperties, Property<?>[] allProperties) {
			MappedClass<T> metaClass = new MappedClass<T>(getParent(), getTheClass(), declaredProperties, allProperties);		
			config(metaClass);
			return metaClass;
		}
		
		private void config(MappedClass<T> metaClass) {
			metaClass.options = PropertiesOptions.clone(options);
			Prop prop = getTheClass().getAnnotation(Prop.class);
			if (prop!=null) {
				if (!prop.key().isEmpty()) {
					metaClass.key = prop.key();
				}
				if (!prop.printer().equals(Printer.class)) {
					metaClass.printer = createPrinter(prop.printer());
				}
				metaClass.options.assign(prop);
			} else {			
			}
		
			if (metaClass.key==null) {
				metaClass.key =metaClass.getUnqualifiedName();
			}
		}
	}
	
	
	//
	// Environment Writing
	//

	public void write(Object obj, WritableEnvironment environment) {
		write(null, obj, environment);
	}

	public void write(String prefix, Object obj, WritableEnvironment environment) {
		write(prefix, obj, environment, new MappingContext());
	}

	public void write(Object obj, WritableEnvironment environment, MappingContext context) {
		write(null, obj, environment, context);
	}
	

	private void write(String prefix, Object obj, WritableEnvironment environment, MappingContext context) {
		if (obj==null) {
			return;
		}
		if (context.getVisited()==null) {
			context.setVisited(new ArrayList<Object>());
		}
		writeInternal(prefix, obj, environment, context);
	}

		
	@SuppressWarnings("unchecked")
	private void writeInternal(String prefix, Object obj, WritableEnvironment environment, MappingContext context) {
		if (obj==null) {
			return;
		}
		MappedClass<?> metaClass = (MappedClass<?>) registry.getMetaClass(obj.getClass());
		if (metaClass.printer!=null) {
			environment.setValue(metaClass.key, ((Printer<Object>)metaClass.printer).print(obj, null));
			return;
		}
		writeProperties(prefix, obj, environment, metaClass, context);
	}

	private void writeProperties(String prefix, Object obj, WritableEnvironment environment,
			MappedClass<?> metaClass, MappingContext context) {		
		PathCriteria projection = context.getProjection();
		for (Property<?> property: metaClass.getAllProperties()) {

			if (projection!=null && !projection.checkInclude(property, ((MappedProperty<?>)property).optional)) {
				continue;
			}
			if (projection==null &&  ((MappedProperty<?>)property).optional) {
				continue;
			}
			Object value = property.getValue(obj);
			if (!includeProperty((MappedProperty<?>)property, value)) {
				continue;
			}
			write(prefix, value, environment, (MappedProperty<?>)property, metaClass, context);
		}
	}
	
	
	private void write(String prefix, Object value, WritableEnvironment environment, MappedProperty<?> property, MappedClass<?> metaClass, MappingContext context) {
		prefix = (prefix!=null) ? (prefix + "." + property.key) : property.key;
		if (value==null || property.simple) {
			environment.setValue(prefix, value);		
			//System.out.println(prefix + " " + value);
			//logger.finest(this, "write", prefix, value);
		} else {
			List<Object> visited = context.getVisited();
			if (visited!=null) {
				if (CollectionUtil.containsSame(visited, property)) {
					return;
				}
				visited.add(property);
			}
			PathCriteria projection = context.getProjection();
			PathCriteria projection2 = property.options.getProjection();
			context.setProjection(projection2);
			writeInternal(prefix, value, environment, context);
			context.setProjection(projection);
		}
	}

	protected boolean includeProperty(MappedProperty<?> property, Path path, PathCriteria projection) {
		//Path path = context.getPath();
		//PathCriteria projection = context.getProjection();
		boolean optional = property.optional;
		if (projection!=null) {
			if (path!=null) {
				return projection.checkInclude(path, optional, true);
			}  else {
				return projection.checkInclude(property, optional);
			}
		} else {
			return !optional;
		}		
	}

	protected boolean includeProperty(MappedProperty<?> property, Object value) {
		if (value==null) {
			if (property.options.getIgnoreNull()) {
				return false;
			}
		} else if (isEmpty(value)) {
				return !property.options.getIgnoreEmpty();
		} else if (isZero(value)) {
			return !property.options.getIgnoreZero();
		}
		return true;
	}
	
	protected boolean isEmpty(Object value) {
		if (value instanceof String) {
			return ((String)value).isEmpty();
		}
		if (value.getClass().isArray()) {
			return Array.getLength(value)==0;
		}
		if (value instanceof Collection<?>) {
			return ((Collection<?>)value).isEmpty();
		}
		return false;
	}

	protected boolean isZero(Object value) {
		return TypeUtil.isZero(value);
	}
	

	//
	// Environment Reading
	//

	public <T> T read(Class<T> type, Environment environment) {
		return read(null, type, environment);
	}

	public void read(Object obj, Environment environment) {
		read(null, obj, environment, new MappingContext());
	}

	public <T> T read(String prefix, Class<T> type, Environment environment) {
		return read(prefix, type, environment, new MappingContext());
	}

	public <T> T read(String prefix, Class<T> type, Environment environment, MappingContext context) {
		T obj = MetaUtil.newInstance(type);
		if (context.getInitializedProperties()==null) {
			context.setInitializedProperties(new ArrayList<Property<?>>());
		}
		read(prefix, obj, environment, context);
		if (context.getInitializedProperties()!=null && context.getInitializedProperties().isEmpty()) {
			return null;
		}
		return obj;
	}
		
	public void read(String prefix, Object obj, Environment environment, MappingContext context) {
		if (obj==null) {
			return;
		}
		MappedClass<?> metaClass = (MappedClass<?>) registry.getMetaClass(obj.getClass());
		readProperties(prefix, obj, environment, metaClass, context);
	}

	private void readProperties(String prefix, Object obj, Environment environment,
			MappedClass<?> metaClass, MappingContext context) {
		PathCriteria projection = context.getProjection();
		for (Property<?> property: metaClass.getAllProperties()) {

			if (projection!=null && !projection.checkInclude(property, ((MappedProperty<?>)property).optional)) {
				continue;
			}
			if (projection==null &&  ((MappedProperty<?>)property).optional) {
				continue;
			}
			//if (!includeProperty((MappedProperty<?>)property, value)) {
			//	continue;
			//}
			read(prefix, obj, environment, (MappedProperty<?>)property, metaClass, context);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void read(String prefix, Object target, Environment environment, MappedProperty<?> property, MappedClass<?> metaClass, MappingContext context) {
		prefix = (prefix!=null) ? (prefix + "." + property.key) : property.key;
		if (property.simple) {
			Object value = environment.getValue(prefix, null);
			ConversionService conversionService = options.getConversionService();
			
			if (value!=null && conversionService!=null && conversionService.supports(value.getClass(), property.getType())) {
				//value = conversionService.convert(value, property.getType());
			}
			if (value!=null) {
				((Property<Object>)property).setValue(target, value);
				if (context.getInitializedProperties()!=null) {
					context.getInitializedProperties().add(property);
				}
			}
		} else {
			if (property.getType().isArray() || Collection.class.isAssignableFrom(property.getType())) {
				return;
			}
			Object target2 = MetaUtil.newInstance(property.getType());
			PathCriteria projection = context.getProjection();
			PathCriteria projection2 = property.options.getProjection();
			context.setProjection(projection2);
			read(prefix, target2, environment, context);
			context.setProjection(projection);
		}
	}
	
}
