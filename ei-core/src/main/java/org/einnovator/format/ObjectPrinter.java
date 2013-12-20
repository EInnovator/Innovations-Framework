/**
 * 
 */
package org.einnovator.format;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.einnovator.convert.ConversionService;
import org.einnovator.i18n.LocaleResolverHolder;
import org.einnovator.i18n.MessageResolverHolder;
import org.einnovator.log.Level;
import org.einnovator.log.Logger;
import org.einnovator.log.LoggerUtil;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;
import org.einnovator.meta.MetaClassRegistry;
import org.einnovator.meta.ObjectRef;
import org.einnovator.meta.ObjectResolverHolder;
import org.einnovator.meta.Path;
import org.einnovator.meta.PathCriteria;
import org.einnovator.meta.PathParser;
import org.einnovator.meta.ProjectionOptions;
import org.einnovator.meta.Property;
import org.einnovator.meta.TypeProjection;
import org.einnovator.meta.metaclass.DefaultMetaClass;
import org.einnovator.meta.metaclass.DefaultMetaClassRegistry;
import org.einnovator.meta.metaclass.build.DefaultMetaClassBuilder;
import org.einnovator.meta.metaclass.build.MetaClassFactoryBase;
import org.einnovator.meta.property.PropertyWrapper;
import org.einnovator.text.TextTransform;
import org.einnovator.util.ArrayUtil;
import org.einnovator.util.CollectionUtil;
import org.einnovator.util.StringUtil;
import org.einnovator.util.ValueUtil;
import org.einnovator.util.types.TypeUtil;


/**
 * A generic printer for objects that iterates on (field and/or getter/setter) properties.
 *
 * Useful to implement auto-formatting of {@code Objects} as in the implementation of the {@code toString()} method.
 * 
 * Annotation {@link NotNull} can be used at the type-level, field-level and property getter-level to tailor
 * the details how the object and object fields are printed.
 * 
 * A {@code ConversionService} can also be configured to format property values.
 * 
 * Some examples of output include:
 * {@code Account(id=123, username="hal01", dob=1-1-2001, address=Address(city="NY", country="US")) } 
 * {@code org.example.Account@4564978(id=123, username="hal01") } 
 * {@code Account<id=123 username="hal01" dob=1-1-2001 address=Address<city="NY", country="US">> } 
 * {@code Account{id:123, username:"hal01", dob:1-1-2001, address:Address{city:'NY', country:'US'}} } 
 * 
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see NotNull
 */
public class ObjectPrinter implements Printer<Object> {

	final Logger logger = LoggerUtil.getLogger(this.getClass());

	/**
	 * {@code MetaClassRegistry} to use. 
	 */
	private MetaClassRegistry registry;

	private FormatOptions options;
	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code PropertiesMapper}.
	 *
	 * @param metaClass
	 */
	
	/**
	 * Create instance of {@code ObjectPrinter}.
	 *
	 * @param options the {@code FormatOptions}
	 */
	public ObjectPrinter(FormatOptions options) {
		this.options = options;
		this.registry = new DefaultMetaClassRegistry(new FormatMetaClassFactory());
	}

	/**
	 * Create instance of {@code ObjectPrinter} with default {@code FormatOptions}.
	 *
	 */
	public ObjectPrinter() {
		this(FormatOptions.newInstance());
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
	public FormatOptions getOptions() {
		return options;
	}

	/**
	 * Set the value of property {@code options}.
	 *
	 * @param options the options to set
	 */
	protected void setOptions(FormatOptions options) {
		this.options = options;
	}
	
	//
	// Inner classes
	//
	
	private class FormatMetaClassFactory extends MetaClassFactoryBase {
		@Override
		protected <T> MetaClassBuilder<T> createMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			return new FormattedClassBuilder<T>(parent, theClass);
		}

		@Override
		protected boolean includeField(Class<?> theClass, Field field) {
			Format format = field.getAnnotation(Format.class);
			if (format!=null) {
				return !format.ignore() && format.value();
			}
			if (!super.includeField(theClass, field)) {
				return false;
			}
			Format format0 = field.getDeclaringClass().getAnnotation(Format.class);
			if (format0!=null) {
				return format0.fieldAccess();
			}
			return true;
		}
		
		@Override
		protected boolean includeProperty(Class<?> theClass, Method getter, Method setter) {
			Format format = getter.getAnnotation(Format.class);
			if (format!=null) {
				return !format.ignore() && format.value();
			}
			Format format0 = getter.getDeclaringClass().getAnnotation(Format.class);
			if (format0!=null) {
				return !format0.fieldAccess();
			}
			return false;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createFieldProperty(Class<?> theClass, Field field) {
			return config(new FormattedProperty<Object>((Property<Object>)super.createFieldProperty(theClass, field)));
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Property<?> createGetterSetterProperty(Class<?> theClass, Method getter, Method setter) {
			return config(new FormattedProperty<Object>((Property<Object>)super.createGetterSetterProperty(theClass, getter, setter)));
		}

		private <T> FormattedProperty<?> config(FormattedProperty<?> property) {
			property.options = FormatOptions.clone(options);
			TypeFormat typeFormat = property.getAnnotation(TypeFormat.class);
			if (typeFormat!=null) {
				property.options.assign(typeFormat);
			}
			Class<?> owner = property.getDeclaringClass();
			boolean i18n = true;
			Format format0 = owner.getAnnotation(Format.class);
			if (format0!=null) {
				property.options.assign(format0);
				i18n = format0.i18n();
			}
			Format format = property.getAnnotation(Format.class);
			if (format!=null) {			
				i18n = format.i18n();
				if (!format.key().isEmpty()) {
					property.label = getMessage(format.key(), null);
				}
				if (StringUtil.isEmpty(property.label) && i18n) {
					property.label = getMessage(property.getDeclaringClass().getName() + "." + property.getName(), null);
				}
				if (StringUtil.isEmpty(property.label)) {
					property.label = format.label();
				}
				property.options.assign(format);
				property.recurse = format.recurse();
				if (!ObjectRef.isEmpty(format.printer())) {
					property.printer = getObject(ObjectRef.newInstance(Printer.class).assign(format.printer()));
				}
				property.optional = format.optional();
				property.simple = isSimple(property);
				if (!ObjectRef.isEmpty(format.transform())) {
					property.options.transform(ObjectRef.newInstance(TextTransform.class).assign(format.transform()));
				}
				if (!ObjectRef.isEmpty(format.labelTransform())) {
					property.options.labelTransform(ObjectRef.newInstance(TextTransform.class).assign(format.labelTransform()));
				}
			} else {
				property.simple = isSimple(property);
				if (StringUtil.isEmpty(property.label) && i18n) {
					property.label = getMessage(property.getDeclaringClass().getName() + "." + property.getName(), null);
				}
			}
			if ((property.options.getLevel()==null || property.options.getLevel()==Level.ALL) 
					&& format0!=null && format0.level()!=Level.ALL) {
				property.options.setLevel(format0.level());
			}
			if (StringUtil.isEmpty(property.label)) {
				property.label = property.getName();
			}			
			if (property.simple) {
				if (!property.options.isProjectionEmpty()) {				
					logger.warning(this, "config", "ignoring projection on simple property: ", property);
				}
				if (property.recurse) {
					logger.warning(this, "config", "ignoring recurse on simple property: ", property);
				}
			} else {
				if (!property.options.isProjectionEmpty() || !property.options.isCycleProjectionEmpty()) {
					property.recurse = true;
				}				
			}
			if (property.options.getTransform()!=null) {
				property.transform = getObject(property.options.getTransform());
			}
			if (property.options.getLabelTransform()!=null) {
				property.labelTransform = getObject(property.options.getLabelTransform());
			}
			return property;
		}
		
		
		private boolean isSimple(FormattedProperty<?> property) {
			if (property.getType().isArray() || Collection.class.isAssignableFrom(property.getType())) {
				return false;
			}
			if (property.getType().equals(String.class)) {
				return true;
			}
			if (Number.class.isAssignableFrom(TypeUtil.box(property.getType()))) {
				return true;
			}
			if (options.getConversionService()!=null) {
				return options.getConversionService().supportsFormat(property.getType());
			}
			return false;
		}
	
	}

	private String getMessage(String key, String defaultValue) {
		return MessageResolverHolder.getRequiredInstance().getMessage(key, 
				LocaleResolverHolder.getRequiredInstance().getLocale(), defaultValue);
	}
	
	private <T> T getObject(ObjectRef<T> ref) {
		return ObjectResolverHolder.getRequiredInstance().getObject(ref.defaultType());
	}
	
	private static class FormattedClass<T> extends DefaultMetaClass<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private FormatOptions options;
		
		/**
		 * Label for field or property.
		 * 
		 */
		private String label;		

		/**
		 * Class of custom printer for this field or property.
		 */
		private Printer<?> printer;


		/**
		 * The transform to apply to the property formatted values..
		 */
		private TextTransform transform;
		
		public FormattedClass(MetaClass<?> parent, Class<T> theClass,
				Property<?>[] declaredProperties, Property<?>[] allProperties) {
			super(parent, theClass, declaredProperties, allProperties);
		}
		
	}

	private static class FormattedProperty<T> extends PropertyWrapper<T> {

		/**
		 * The {@code PropertiesOptions}. 
		 */
		private FormatOptions options;

		/**
		 * Flag specifying if property is simple. 
		 */
		private boolean simple;
		
		/**
		 * Label for field or property.
		 * 
		 */
		private String label;

		/**
		 * Flag specifying if field or property should be ignored unless explicitly asked to be included.
		 */
		private boolean optional = false;	

		/**
		 * Flag specifying if recursive field/property formatting should be applied.
		 */
		private boolean recurse = false;
		
		/**
		 * Class of custom printer for this field or property.
		 */
		private Printer<?> printer;

		/**
		 * The transform to apply to the property formatted values.
		 */
		private TextTransform transform;

		/**
		 * The label transform to apply to the property label.
		 */
		private TextTransform labelTransform;

		public FormattedProperty(Property<T> property) {
			super(property);
		}
		
	}
	
	private class FormattedClassBuilder<T> extends DefaultMetaClassBuilder<T> {
		
		public FormattedClassBuilder(MetaClass<?> parent, Class<T> theClass) {
			super(parent, theClass);
		}	
		
		@Override
		protected MetaClass<T> createMetaClass(Property<?>[] declaredProperties, Property<?>[] allProperties) {
			FormattedClass<T> metaClass = new FormattedClass<T>(getParent(), getTheClass(), declaredProperties, allProperties);		
			config(metaClass);
			return metaClass;
		}
		
		private void config(FormattedClass<T> metaClass) {
			metaClass.options = FormatOptions.clone(options);
			Format format = getTheClass().getAnnotation(Format.class);
			if (format!=null) {
				if (!format.label().isEmpty()) {
					metaClass.label = format.label();
				}
				if (!ObjectRef.isEmpty(format.printer())) {
					metaClass.printer = getObject(ObjectRef.newInstance(Printer.class).assign(format.printer()));
				}
				metaClass.options.assign(format);
			} else {	
			}
			
			TypeFormat typeFormat = getTheClass().getAnnotation(TypeFormat.class);
			if (typeFormat!=null) {
				if (!typeFormat.label().isEmpty()) { //overrides @NotNull(label="..")
					if (metaClass.label!=null && !typeFormat.label().equals(metaClass.label)) {
						logger.warning(this, "createMetaClass", "ambiguos label specified with @TypeFormat and @NotNull, for:" + metaClass.getName());
					}
					metaClass.label = typeFormat.label();
				}
				metaClass.options.assign(typeFormat);
				if (!ObjectRef.isEmpty(typeFormat.transform())) {
					metaClass.options.transform(ObjectRef.newInstance(TextTransform.class).assign(typeFormat.transform()));
				}
			}
			if (metaClass.label==null) {
				metaClass.label = metaClass.options.getQualified() ? metaClass.getName() : metaClass.getUnqualifiedName();
			}
			if (metaClass.options.getTransform()!=null) {
				metaClass.transform = getObject(metaClass.options.getTransform());
			}
		}
	}

	//
	// Printer implementation
	//
	
	/**
	 * @see org.einnovator.format.Printer#print(java.lang.Object)
	 */
	@Override
	public String print(Object obj, Locale locale) {
		return print(obj, locale, (ProjectionOptions)null);
	}
	
	public String print(Object obj, Locale locale, String... include) {
		return print(obj, locale, false, include.length==0? null : include, null);
	}

	public String print(Object obj, Locale locale, boolean complete, String... include) {
		return print(obj, locale, complete, include.length==0? null : include, null);
	}

	private PathParser pathParser;
	
	private PathParser getRequiredPathParser() {
		if (pathParser==null) {
			pathParser = new PathParser(registry);
		}
		return pathParser;
	}
	
	public String print(Object obj, Locale locale, boolean complete, String[] include, String[] exclude) {
		if (obj==null) {
			return options.getNullValue();
		}
		return print(obj, locale, new ProjectionOptions(complete, include, exclude));
	}

	public String print(Object obj, Locale locale, PathCriteria projection) {
		return print(obj, new FormatContext().locale(locale).projection(projection));
	}
	
	public String print(Object obj, FormatContext context) {
		if (obj==null) {
			return options.getNullValue();
		}
		StringBuilder sb = new StringBuilder();
		List<Object> visited = context.getVisited();
		if (visited==null && options.getCheckCycles()) {
			visited = new ArrayList<Object>();
			context.setVisited(visited);
		}
		if (visited!=null) {
			visited.add(obj);			
		}
		Path path = context.getPath();
		if (path==null && options.getPaths()) {
			path = new Path();
			context.setPath(path);
		}
		PathCriteria projection = context.getProjection();
		if (projection instanceof ProjectionOptions && options.getCompilePaths() && 
			!(projection instanceof TypeProjection)) {
			projection = new TypeProjection(obj.getClass(), (ProjectionOptions)projection, getRequiredPathParser());
		}

		print(obj, context, sb);
		return sb.toString();		
	}

		
	@SuppressWarnings("unchecked")
	private void print(Object obj, FormatContext context, StringBuilder sb) {
		if (obj==null) {
			sb.append(options.getNullValue());
		}
		FormattedClass<?> metaClass = (FormattedClass<?>) registry.getMetaClass(obj.getClass());
		Locale locale = ValueUtil.coalesce(context.getLocale(), options.getLocale());
		if (metaClass.printer!=null) {
			sb.append(((Printer<Object>)metaClass.printer).print(obj, locale));
			return;
		}
		StringBuilder sb2 = sb;
		if (metaClass.transform!=null) {
			sb2 = new StringBuilder();
		}
		begin(obj, metaClass, sb2);
		printProperties(obj, metaClass, context, sb2);
		end(metaClass, sb2);
		if (sb2!=sb) {
			sb.append(metaClass.transform.transform(sb2.toString()));
		}
	}

	//
	// Support
	//
	
	protected void begin(Object obj, FormattedClass<?> metaClass, StringBuilder sb) {
		sb.append(metaClass.label);
		if (metaClass.options.getHash()) {
			sb.append("@" + obj.hashCode());
		}
		if (metaClass.options.getBeginMarker()!=null) {
			sb.append(metaClass.options.getBeginMarker());
		}
	}

	protected void end(FormattedClass<?> metaClass, StringBuilder sb) {
		if (metaClass.options.getEndMarker()!=null) {
			sb.append(metaClass.options.getEndMarker());
		}
	}

	private void printProperties(Object obj, FormattedClass<?> metaClass, FormatContext context, StringBuilder sb) {		
		boolean first = true;
		for (Property<?> property: metaClass.getAllProperties()) {
			Path path = context.getPath();
			if (path!=null) {
				path.push(property);
			}
			if (!includeProperty((FormattedProperty<?>)property, context)) {
				//System.out.println("ignored:" + property + " " + path  + " " + projection);
				if (path!=null) {
					path.pop();
				}
				continue;
			}
			Object value = property.getValue(obj);
			if (!includeProperty((FormattedProperty<?>)property, value)) {
				if (path!=null) {
					path.pop();
				}
				continue;
			}
			if (!first) {
				if (metaClass.options.getFieldSeparator()!=null) {
					sb.append(metaClass.options.getFieldSeparator());
				}
			}
			first = false;
			printProperty(value, (FormattedProperty<?>)property, metaClass, context, sb);
			if (path!=null) {
				path.pop();
			}
		}
	}
	
	
	public void printProperty(Object value, FormattedProperty<?> property, FormattedClass<?> metaClass, FormatContext context, StringBuilder sb) {
		sb.append(getLabel(property));
		sb.append(metaClass.options.getValueSeparator());
		printValue(property, value, context, sb);
	}

	protected String getLabel(FormattedProperty<?> property) {
		String label = property.label;
		if (property.labelTransform!=null) {
			label = property.labelTransform.transform(label);
		}
		return label;
	}

	protected boolean includeProperty(FormattedProperty<?> property, FormatContext context) {
		if (context.getLevel()!=null) {
			if (!property.options.getLevel().enabled(context.getLevel())) {
				return false;
			}
		}
		Path path = context.getPath();
		PathCriteria projection = context.getProjection();
		boolean optional = ((FormattedProperty<?>)property).optional;
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
	
	protected boolean includeProperty(FormattedProperty<?> property, Object value) {
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
	
	@SuppressWarnings("unchecked")
	private void printValue(FormattedProperty<?> property, Object value, FormatContext context, StringBuilder sb) {
		Locale locale = ValueUtil.coalesce(context.getLocale(), options.getLocale());
		
		if (property.printer!=null) {
			sb.append(((Printer<Object>)property.printer).print(value, locale));
		} else if (value instanceof Collection) {
			printCollection(property, (Collection<?>)value, context, sb);	
		} else if (value.getClass().isArray()) {
			printArray(property, value, context, sb);
		} else if (!property.simple && property.recurse) {
			List<Object> visited = context.getVisited();
			Path path = context.getPath();
			PathCriteria projection = context.getProjection();
			if (visited!=null) {
				if (CollectionUtil.containsSame(visited, value)) {
					if (!ProjectionOptions.isEmpty(property.options.getCycleProjection())) {
						path.forward();
						context.setProjection(property.options.getCycleProjection());
						print(value, context, sb);
						context.setProjection(projection);
						path.backward();
					} else {
						printCycle(property, value, sb, visited);						
					}
					return;	
				}
				visited.add(value);
			}
			PathCriteria projection2 = property.options.getProjection();
			boolean advance = false;
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
			print(value, context, sb);
			context.setProjection(projection);
			if (advance) {
				path.backward();
			}
		} else {
			sb.append(formatValue(property, value, locale));
		}
	}
	
	private void printCycle(FormattedProperty<?> property, Object value, StringBuilder sb, List<Object> visited) {
		sb.append("...");
	}
	

	protected String formatValue(FormattedProperty<?> property, Object value, Locale locale) {
		if (value==null) {
			return options.getNullValue();
		}
		String text = formatNonNullValue(value, locale);
		if (property.transform!=null) {
			text = property.transform.transform(text);
		}
		if (isText(value) && property.options.getQuote()) {
			String quoteChar = Character.toString(property.options.getQuoteChar());
			text = quoteChar + text + quoteChar;
		}

		return text;
	}
	
	protected boolean isText(Object value) {
		return TypeUtil.isText(value.getClass());
	}
	
	protected String formatNonNullValue(Object value, Locale locale) {
		if (value instanceof Collection) {
			return CollectionUtil.toString((Collection<?>)value, true);	
		}
		if (value.getClass().isArray()) {
			return ArrayUtil.toString(value);
		}
		ConversionService conversionService = options.getConversionService();
		if (conversionService!=null) {
			if (conversionService.supportsFormat(value.getClass())) {
				return conversionService.format(value, locale!=null ? locale : options.getLocale());
			}
		}
		return value.toString();
	}
	
	private void printCollection(FormattedProperty<?> property, Collection<?> collection,
			FormatContext context, StringBuilder sb) {
		Iterator<?> it = collection.iterator();
		if (property.options.getCollectionBeginMarker()!=null) {
			sb.append(property.options.getCollectionBeginMarker());
		}
		String elementSeparator = null;
		if (property.options.getElementSeparator()!=null) {
			elementSeparator = property.options.getElementSeparator();
		}

		while (it.hasNext()) {
			Object value = it.next();
			printValue(property, value, context, sb);
			if (it.hasNext()) {
				sb.append(elementSeparator);
			}
		}
		if (property.options.getCollectionEndMarker()!=null) {
			sb.append(property.options.getCollectionEndMarker());
		}
	}

	private void printArray(FormattedProperty<?> property, Object array, FormatContext context, StringBuilder sb) {
		if (property.options.getArrayBeginMarker()!=null) {
			sb.append(property.options.getArrayBeginMarker());
		}
		String elementSeparator = null;
		if (property.options.getElementSeparator()!=null) {
			elementSeparator = property.options.getElementSeparator();
		}
		for (int i=0; i<Array.getLength(array); i++) {
			printValue(property, Array.get(array, i), context, sb);
			if (i<Array.getLength(array)-1) 
				if (elementSeparator!=null) {
					sb.append(elementSeparator);{					
				}
			}
		}
		if (property.options.getArrayEndMarker()!=null) {
			sb.append(property.options.getArrayEndMarker());			
		}
	}
	
}
