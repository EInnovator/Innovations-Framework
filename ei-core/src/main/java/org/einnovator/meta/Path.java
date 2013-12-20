/**
 * 
 */
package org.einnovator.meta;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.einnovator.convert.ConversionService;

/**
 * A {@code Path}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class Path {

	private List<Property<?>> properties;

	private String pathAsString;

	private int advance;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code Path}.
	 *
	 */
	public Path() {
		properties = new LinkedList<Property<?>>();
	}

	/**
	 * Create instance of {@code Path}.
	 *
	 * @param properties
	 */
	public Path(List<Property<?>> properties) {
		this.properties = properties;
	}

	/**
	 * Create instance of {@code Path}.
	 *
	 * @param properties
	 */
	public Path(Property<?>... properties) {
		this.properties = new LinkedList<Property<?>>(Arrays.asList(properties));		
	}
	
	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code properties}.
	 *
	 * @return the properties
	 */
	public List<Property<?>> getProperties() {
		return properties;
	}

	/**
	 * Set the value of property {@code properties}.
	 *
	 * @param properties the properties to set
	 */
	public void setProperties(List<Property<?>> properties) {
		this.properties = properties;
	}

	
	
	
	/**
	 * Get the value of property {@code advance}.
	 *
	 * @return the advance
	 */
	public int getAdvance() {
		return advance;
	}

	/**
	 * Set the value of property {@code advance}.
	 *
	 * @param advance the advance to set
	 */
	public void setAdvance(int advance) {
		this.advance = advance;
	}

	
	//
	// Other Modifiers
	//

	public void push(Property<?> property) {
		properties.add(property);
		pathAsString = null;
	}

	public Property<?> pop() {
		pathAsString = null;
		return properties.remove(properties.size()-1);
	}

	public Property<?> getHead() {
		return properties.size()>0 ? properties.get(0) : null;
	}

	public Property<?> getTail() {
		return properties.size() > 0 ? properties.get(properties.size()-1) : null;
	}

	public Class<?> getType() {
		return getTail().getType();
	}

	//
	// Other accessors
	//
	
	/**
	 * Get the length (size) of the path.
	 * 
	 * The size of the path is the number of properties in it.
	 * (Same as {@link #length()}).
	 * 
	 * @return the size of the path
	 */
	public int size() {
		return properties.size();
	}

	/**
	 * Get the length (size) of the path.
	 * 
	 * The length of the path is the number of properties in it.
	 * (Same as {@link #size()}).
	 * 
	 * @return the size of the path
	 */
	public int length() {
		return properties.size();
	}

	/**
	 * Check if path is empty (length is 0).
	 * 
	 * @return <code>true</code>, if the path is empty; <code>false</code>, otherwise.
	 * 
	 */
	public boolean isEmpty() {
		return properties.size()==0;
	}

	public Property<?> getProperty(int i) {
		return properties.get(i);
	}

	public void setProperty(int i, Property<?> property) {
		properties.set(i, property);
	}

	public String getPathAsString() {
		if (pathAsString==null) {
			pathAsString = getPathAsString(PathParser.DEFAULT_SEPARATOR);
		}
		return pathAsString;
	}
	
	public String getPathAsString(String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<properties.size(); i++) {
			if (sb.length()>0) {
				sb.append(separator);
			}
			sb.append(properties.get(i).getName());			
		}
		return sb.toString();
	}
	
	/**
	 * @param target
	 * @return
	 */
	public Object getValue(Object target) {
		return getValue(target, properties.size());
	}
	
	/**
	 * @param target
	 * @param pathLength
	 * @return
	 */
	public Object getValue(Object target, int pathLength) {
		if (target==null) {
			return null;
		}
		for (int i=0; i<pathLength; i++) {
			@SuppressWarnings("unchecked")
			Property<Object> property = (Property<Object>)properties.get(i);
			target = property.getValue(target);
			if (target==null) {
				return null;
			}
		}
		return target;
	}

	public Class<?> getType(Object target) {
		Object value = getValue(target);
		return value!=null ? value.getClass() : null;
	}

	
	public void setValue(Object target, Object value) {
		setValue(target, value, null);
	}

	private void setValue(Object target, Object value, ConversionService conversionService) {
		if (properties.size()>1) {
			target = getValue(target, properties.size()-1);			
		}
		
		@SuppressWarnings("unchecked")
		Property<Object> property = (Property<Object>)getTail();
		if (conversionService!=null) {
			value = conversionService.convert(value, property.getType());
		}
		property.setValue(target, value);
	}

	
	//
	// Object overrides
	//
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPathAsString();
	}

	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (int i=0; i<properties.size(); i++) {
			result = prime * result + properties.get(i).getName().hashCode();			
		}
		return result;
	}


	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Path path = (Path) obj;
		if (properties.size()-advance!=path.properties.size()) {
			return false;
		}
		for (int i=advance; i<properties.size(); i++) {
			if (!properties.get(i).getName().equals(path.properties.get(i-advance).getName())) {
				return false;
			}
		}
		return true;
	}


	public boolean prefix(Path path) {
		if (this.properties.size()-advance>path.properties.size()) {
			return false;
		}
		for (int i=advance; i<properties.size(); i++) {
			if (!properties.get(i).getName().equals(path.properties.get(i-advance).getName())) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Increment the advance of this {@code Path}. 
	 */
	public void forward() {
		advance++;
	}

	/**
	 * Decrement the advance of this {@code Path}. 
	 */
	public void backward() {
		advance--;
	}

	//
	// Utilities
	//

	public boolean matchAny(String[] paths) {
		return matchAny(paths, PathParser.DEFAULT_SEPARATOR_PATTERN);
	}

	public boolean matchAny(String[] paths, String separator) {
		for (String properties: paths) {
			if (match(properties, separator)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean match(String properties) {
		return match(properties.split(PathParser.DEFAULT_SEPARATOR_PATTERN));
	}

	public boolean match(String properties, String separator) {
		return match(properties.split(separator));
	}

	public boolean match(String[] properties) {
		if (properties.length-advance!=this.properties.size()) {
			return false;
		}
		for (int i=advance; i<properties.length; i++) {
			if (!this.properties.get(i).getName().equals(properties[i-advance])) {
				return false;
			}
		}
		return true;
	}

	public boolean prefixAny(String[] paths) {
		return prefixAny(paths, PathParser.DEFAULT_SEPARATOR_PATTERN);
	}

	public boolean prefixAny(String[] paths, String separator) {
		for (String properties: paths) {
			if (prefix(properties, separator)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean prefix(String properties) {
		return prefix(properties.split(PathParser.DEFAULT_SEPARATOR_PATTERN));
	}

	public boolean prefix(String properties, String separator) {
		return prefix(properties.split(separator));
	}

	public boolean prefix(String[] properties) {
		if (this.properties.size()-advance>properties.length) {
			return false;
		}
		for (int i=advance; i<this.properties.size(); i++) {
			if (!this.properties.get(i).getName().equals(properties[i-advance])) {
				return false;
			}
		}
		return true;
	}
	
	//
	// Static Utilities
	//

	public static Object[] getValues(Object target, List<Path> paths) {
		Object[] values = new Object[paths.size()];
		int i = 0;
		for (Path path: paths) {
			values[i++] = path.getValue(target);
		}
		return values;
	}

	public static Path newInstance(String path, Class<?> type, boolean fieldAccess) {
		return PathParser.getInstance(fieldAccess).parse(path, type);
	}
	
	public static Path newInstance(String path, Class<?> type) {
		return newInstance(path, type, true);
	}
	
}
