package org.einnovator.environment;

import java.util.Properties;

/**
 * An {@code WritableEnvironment} that reads and writes from/to an instance of {@code Properties}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class PropertiesEnvironment extends EnvironmentSupport implements WritableEnvironment {

	private Properties properties;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code PropertiesEnvironment}.
	 *
	 * @param properties
	 */
	public PropertiesEnvironment(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Create instance of {@code PropertiesEnvironment}.
	 *
	 */
	public PropertiesEnvironment() {
		this(new Properties());
	}

	//
	// Getters and Setters
	//
	

	/**
	 * Get the value of property {@code properties}.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Set the value of property {@code properties}.
	 *
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}	
	
	
	//
	// WritableEnvironment Implementation
	//
	
	
	/**
	 * @see org.einnovator.environment.Environment#getValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getValue(String key, Object defaultValue) {
		Object value = properties.getProperty(key, null);
		return value!=null ? value : defaultValue;
	}

	@Override
	public void setValue(String key, Object value) {
		String s;
		if (value!=null) {
			if (conversionService!=null && conversionService.supportsFormat(value.getClass())) {
				s = conversionService.format(value, null);
			} else {
				s = value.toString();
			}			
		} else {
			s = null;
		}
		properties.setProperty(key, s);
	}

}
