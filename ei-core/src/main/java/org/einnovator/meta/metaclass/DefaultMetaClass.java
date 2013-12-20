/**
 * 
 */
package org.einnovator.meta.metaclass;

import java.util.LinkedHashMap;
import java.util.Map;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.Property;
import org.einnovator.meta.metaclass.SimpleMetaClass;



/**
 * A DefaultMetaClass<T>.
 *
 * @author Jorge Simão
 */
public class DefaultMetaClass<T> extends SimpleMetaClass<T> {

	private Map<String,Property<?>> propertyMap;
	
	private Map<String,Property<?>> declaredPropertyMap;
	
	//
	// Constructors
	//

	/**
	 * Create instance of DefaultMetaClass.
	 *
	 * @param parent
	 * @param theClass
	 * @param declaredProperties
	 * @param allProperties
	 */
	public DefaultMetaClass(MetaClass<?> parent, Class<T> theClass, Property<?>[] declaredProperties, Property<?>[] allProperties) {
		super(parent, theClass, declaredProperties, allProperties);
		buildPropertyMaps();
	}

	/**
	 * Create instance of DefaultMetaClass.
	 *
	 * @param parent
	 * @param theClass
	 * @param declaredProperties
	 */
	public DefaultMetaClass(MetaClass<?> parent, Class<T> theClass, Property<?>[] declaredProperties) {
		this(parent, theClass, declaredProperties, null);
	}

	/**
	 * Create instance of DefaultMetaClass.
	 *
	 * @param theClass
	 * @param declaredProperties
	 * @param allProperties
	 */
	public DefaultMetaClass(Class<T> theClass, Property<?>[] declaredProperties, Property<?>[] allProperties) {
		this(null, theClass, declaredProperties, allProperties);
	}

	/**
	 * Create instance of DefaultMetaClass.
	 *
	 * @param theClass
	 * @param declaredProperties
	 */
	public DefaultMetaClass(Class<T> theClass, Property<?>[] declaredProperties) {
		this(null, theClass, declaredProperties, null);
	}

	private void buildPropertyMaps() {
		if (declaredProperties.length>0) {
			declaredPropertyMap = new LinkedHashMap<String, Property<?>>(declaredProperties.length);
			for (Property<?> property: declaredProperties) {
				declaredPropertyMap.put(property.getName(), property);
			}			
		}
		if (allProperties.length>0) {
			propertyMap = new LinkedHashMap<String, Property<?>>(allProperties.length);
			for (Property<?> property: allProperties) {
				propertyMap.put(property.getName(), property);
			}			
		}

	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of declaredPropertyMap.
	 *
	 * @return the declaredPropertyMap
	 */
	public Map<String, Property<?>> getDeclaredPropertyMap() {
		return declaredPropertyMap;
	}

	/**
	 * Set the value of declaredPropertyMap.
	 *
	 * @param declaredPropertyMap the declaredPropertyMap to set
	 */
	public void setDeclaredPropertyMap(Map<String, Property<?>> declaredPropertyMap) {
		this.declaredPropertyMap = declaredPropertyMap;
	}

	/**
	 * Get the value of {@code propertyMap}.
	 *
	 * @return the value of {@code propertyMap}
	 */
	public Map<String, Property<?>> getPropertyMap() {
		return propertyMap;
	}

	/**
	 * Set the value of {@code propertyMap}.
	 *
	 * @param propertyMap the {@code propertyMap}
	 */
	public void setPropertyMap(Map<String, Property<?>> propertyMap) {
		this.propertyMap = propertyMap;
	}	
	
	//
	// MetaClass<T> implementation
	//
	
	@Override
	public Property<?> getDeclaredProperty(String name) {
		return declaredPropertyMap!=null ? declaredPropertyMap.get(name) : null;
	}

	@Override
	public Property<?> getProperty(String name) {
		return propertyMap!=null ? propertyMap.get(name) : null;
	}

}
