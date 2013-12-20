package org.einnovator.environment;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * An {@code WritableEnvironment} that reads and writes from/to a Java {@code Map}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class MapEnvironment extends EnvironmentSupport implements WritableEnvironment {

	private Map<String, Object> map;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code MapEnvironment}.
	 *
	 * @param map
	 */
	public MapEnvironment(Map<String, Object> map) {
		this.map = map;
	}
	
	/**
	 * Create instance of {@code MapEnvironment}.
	 *
	 * @param map
	 */
	public MapEnvironment() {
		this.map = new LinkedHashMap<String, Object>();
	}
	
	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code map}.
	 *
	 * @return the map
	 */
	public Map<String, Object> getMap() {
		return map;
	}

	/**
	 * Set the value of property {@code map}.
	 *
	 * @param map the map to set
	 */
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	//
	// WritableEnvironment Implementation
	//
	
	
	/**
	 * @see org.einnovator.environment.Environment#getValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getValue(String key, Object defaultValue) {
		Object value = map.get(key);
		return value!=null ? value : defaultValue;
	}

	@Override
	public void setValue(String key, Object value) {
		map.put(key, value);
	}

}
