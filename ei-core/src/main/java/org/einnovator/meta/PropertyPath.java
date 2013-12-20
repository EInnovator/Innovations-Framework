/**
 * 
 */
package org.einnovator.meta;

import java.util.ArrayList;
import java.util.List;

import org.einnovator.convert.ConversionService;
import org.einnovator.util.InvalidPropertyPathException;
import org.einnovator.util.StringUtil;


/**
 * A PropertyPath.
 *
 * @author Jorge Simão
 */
public class PropertyPath {

	protected String path;
	
	protected MetaClassResolver resolver;
	
	private String[] apath;
	
	private boolean dynamic = true;
	
	/**
	 * Create instance of PropertyPath.
	 *
	 * @param path
	 * @param resolver
	 */
	public PropertyPath(String path, MetaClassResolver resolver) {
		this.path = path;
		this.resolver = resolver;
		init();
	}

	/**
	 * Create instance of PropertyPath.
	 *
	 * @param path
	 */
	public PropertyPath(String path) {
		this(path, MetaClassResolverHolder.getInstance().getMetaClassResolver());
	}

	public void init() {
		if (path==null) {
			throw new InvalidPropertyPathException(path);
		}
		apath = StringUtil.split(path, "\\.");
		if (apath.length==0) {
			throw new InvalidPropertyPathException("'" + path + "'");
		}		
	}
	//
	// Getters and setters
	//
	
	/**
	 * Get the value of path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set the value of path.
	 *
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
		init();
	}

	/**
	 * Get the value of resolver.
	 *
	 * @return the resolver
	 */
	public MetaClassResolver getResolver() {
		return resolver;
	}

	/**
	 * Set the value of resolver.
	 *
	 * @param resolver the resolver to set
	 */
	public void setResolver(MetaClassResolver resolver) {
		this.resolver = resolver;
	}
	
	/**
	 * Get the value of property {@code dynamic}.
	 *
	 * @return the dynamic
	 */
	public boolean isDynamic() {
		return dynamic;
	}

	/**
	 * Set the value of property {@code dynamic}.
	 *
	 * @param dynamic the dynamic to set
	 */
	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}
	
	/**
	 * Get the length of the path.
	 * 
	 * @return the length
	 */
	public int length() {
		return apath.length;
	}
	
	//
	// Computed properties
	//
	
	public Object getValue(Object target) {
		return getValue(target, target, 0);
	}
	
	private Object getValue(Object root, Object target, int index) {
		if (target==null) {
			return null;
		}
		MetaClass<?> metaClass = resolver.getMetaClass(target.getClass());
		String name = apath[index];
		if (name==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		name = name.trim();
		if (name.isEmpty()) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		Property<?> property = metaClass.getProperty(name);
		if (property==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'" +
					(apath.length>0 ? " while looking up property '" + name + "'" : ""));			
		}
		Object value = property.getValue(target);
		index++;
		if (index==apath.length) {
			return value;
		}
		if (value==null) {
			return null;
		}
		return getValue(root, value, index);
	}

	public Class<?> getType(Object target) {
		return getType(target, target, 0);
	}

	private Class<?> getType(Object root, Object target, int index) {
		if (target==null) {
			return null;
		}
		MetaClass<?> metaClass = resolver.getMetaClass(target.getClass());
		String name = apath[index];
		if (name==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		name = name.trim();
		if (name.isEmpty()) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		Property<?> property = metaClass.getProperty(name);
		if (property==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");			
		}
		Object value = property.getValue(target);
		index++;
		if (index==apath.length) {
			return property.getType();
		}
		if (value==null) {
			return null;
		}
		return getType(root, value, index);
	}
	
	public Property<?> getTailProperty(Object target) {
		Property<?>[] properties = getProperties(target);
		return properties.length>0 ? properties[properties.length-1] : null;
	}
	
	public Property<?>[] getProperties(Object target) {
		Property<?>[] properties = new Property[path.length()];
		getProperties(target, target.getClass(), 0, properties);
		return properties;
	}

	private void getProperties(Object root, Class<?> type, int index, Property<?>[] properties) {
		MetaClass<?> metaClass = resolver.getMetaClass(type);
		String name = apath[index];
		if (name==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		name = name.trim();
		if (name.isEmpty()) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		Property<?> property = metaClass.getProperty(name);
		if (property==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");			
		}
		properties[index] = property;
		index++;
		if (index==apath.length) {
			return;
		}
		getProperties(root, property.getType(), index, properties);
	}

	public void setValue(Object target, Object value) {
		setValue(target, target, value, 0, null);
	}

	public void setValue(Object target, Object value, ConversionService conversionService) {
		setValue(target, target, value, 0, conversionService);
	}

	private void setValue(Object root, Object target, Object value, int index, ConversionService conversionService) {
		MetaClass<?> metaClass = resolver.getMetaClass(target.getClass());
		String name = apath[index];
		if (name==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		name = name.trim();
		if (name.isEmpty()) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'");
		}
		@SuppressWarnings("unchecked")
		Property<Object> property = (Property<Object>) metaClass.getProperty(name);
		if (property==null) {
			throw new InvalidPropertyPathException("'" + path + "' for '" + root + "'" +
					(apath.length>0 ? " while looking up property '" + name + "'" : ""));			
		}
		if (index==apath.length-1) {
			if (conversionService!=null) {
				value = conversionService.convert(value, property.getType());
			}
			property.setValue(target, value);
			return;
		}
		Object value2 = property.getValue(target);
		index++;
		if (value2==null) {
			value2 = MetaUtil.newInstance(property.getType());
			property.setValue(target, value2);
		}
		setValue(root, value2, value, index, conversionService);
	}


	//
	// static utility
	//
	
	public static List<PropertyPath> parseAll(String[] paths) {
		List<PropertyPath> propertyPaths = new ArrayList<PropertyPath>(paths.length);
		for (String path: paths) {
			PropertyPath propertyPath = new PropertyPath(path);
			propertyPaths.add(propertyPath);
		}
		return propertyPaths;
	}
	
	public static Object[] getValues(Object target, List<PropertyPath> propertyPaths) {
		Object[] values = new Object[propertyPaths.size()];
		int i = 0;
		for (PropertyPath propertyPath: propertyPaths) {
			values[i++] = propertyPath.getValue(target);
		}
		return values;
	}

}
