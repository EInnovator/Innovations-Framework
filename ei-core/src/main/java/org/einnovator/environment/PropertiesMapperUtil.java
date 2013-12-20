/**
 * 
 */
package org.einnovator.environment;

import java.util.Map;
import java.util.Properties;

/**
 * Utilities to read and write POJO propeties to environments.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class PropertiesMapperUtil {
	
	public static PropertiesMapper mapper;
	
	//
	// Getters and Setters
	//

	/**
	 * Set the value of property {@code mapper}.
	 *
	 * @param mapper the mapper to set
	 */
	public static void setMapper(PropertiesMapper mapper) {
		PropertiesMapperUtil.mapper = mapper;
	}

	/**
	 * Get the value of property {@code mapper}.
	 *
	 * @return the mapper
	 */
	public static PropertiesMapper getMapper() {
		return mapper;
	}

	/**
	 * Get the value of property {@code mapper}.
	 *
	 * @return the mapper
	 */
	public static PropertiesMapper getRequiredMapper() {
		if (mapper==null) {
			mapper = new PropertiesMapper();
		}
		return mapper;
	}

	//
	// POJO To Map
	//

	public static Map<String, Object> toMap(Object obj) {
		return toMap(null, obj);
	}

	public static Map<String, Object> toMap(String prefix, Object obj) {
		if (obj==null) {
			return null;
		}
		MapEnvironment environment = new MapEnvironment();
		getRequiredMapper().write(prefix, obj, environment);
		return environment.getMap();
	}

	//
	// POJO to Properties
	//

	public static Properties toProperties(Object obj) {
		return toProperties(null, obj);
	}

	public static Properties toProperties(String prefix, Object obj) {
		if (obj==null) {
			return null;
		}
		PropertiesEnvironment environment = new PropertiesEnvironment();
		getRequiredMapper().write(prefix, obj, environment);
		return environment.getProperties();
	}

	//
	// POJO from Map
	//

	public static <T> T fromMap(Class<T> type, Map<String, Object> map) {
		return fromMap(null, type, map);
	}

	public static <T> T fromMap(String prefix, Class<T> type, Map<String, Object> map) {
		return getRequiredMapper().read(prefix, type, new MapEnvironment(map));
	}

	//
	// POJO from Properties
	//

	public static <T> T fromProperties(Class<T> type, Properties properties) {
		return fromMap(null, type, null);
	}

	public static <T> T fromProperties(String prefix, Class<T> type, Properties properties) {
		return getRequiredMapper().read(prefix, type, new PropertiesEnvironment(properties));
	}

}
