/**
 * 
 */
package org.einnovator.meta.metaclass.build;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassBuilder;
import org.einnovator.meta.Property;
import org.einnovator.meta.metaclass.SimpleMetaClass;
import org.einnovator.meta.property.FieldProperty;
import org.einnovator.meta.property.GenericFieldProperty;
import org.einnovator.meta.property.PropertySupport;
import org.einnovator.meta.property.PropertyWrapper;
import org.einnovator.util.types.TypeUtil;



/**
 * A SimpleMetaClassBuilder.
 *
 * @author Jorge Simão
 * @param <T>
 */
public class SimpleMetaClassBuilder<T> implements MetaClassBuilder<T> {
	
	private MetaClass<?> parent;
	
	private Class<T> theClass;

	protected List<Property<?>> declaredPropertyList = new ArrayList<Property<?>>();

	//
	// Constructors
	//
	
	/**
	 * Create instance of SimpleMetaClassBuilder.
	 *
	 * @param parent
	 * @param theClass
	 */
	public SimpleMetaClassBuilder(MetaClass<?> parent, Class<T> theClass) {
		this.parent = parent;
		this.theClass = theClass;
	}

	//
	// MetaClassBuilder<T> implementation
	//

	public MetaClass<?> getParent() {
		return parent;
	}

	public void setParent(MetaClass<?> parent) {
		this.parent = parent;
	}

	public Class<T> getTheClass() {
		return theClass;
	}

	public void setTheClass(Class<T> theClass) {
		this.theClass = theClass;
	}

	public List<Property<?>> getDeclaredPropertyList() {
		return declaredPropertyList;
	}

	public void setDeclaredPropertyList(List<Property<?>> declaredPropertyList) {
		this.declaredPropertyList = declaredPropertyList;
	}

	@Override
	public MetaClassBuilder<T> addProperty(Property<?> property) {
		declaredPropertyList.add(property);
		return this;
	}

	@Override
	public MetaClassBuilder<T> addProperties(Collection<Property<?>> properties) {
		declaredPropertyList.addAll(properties);
		return this;
	}

	@Override
	public Property<?> getProperty(String name) {
		for (Property<?> property: declaredPropertyList) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	/**
	 * Clears the internal properties.
	 */
	public void clear() {
		declaredPropertyList.clear();
	}


	@Override
	public MetaClass<T> build() {
		Property<?>[] declaredProperties = declaredPropertyList.toArray(new Property<?>[declaredPropertyList.size()]);
		Property<?>[] allProperties = getAllProperties(declaredProperties);
		MetaClass<T> metaClass = createMetaClass(declaredProperties, allProperties);
		for (Property<?> property: metaClass.getDeclaredProperties()) {
			if (property instanceof PropertyWrapper) {
				property = ((PropertyWrapper<?>)property).getProperty();
			}
			if (property instanceof PropertySupport) {
				((PropertySupport<?>)property).setDeclaringMetaClass(metaClass);
			}
		}
		return metaClass;
	}
	
	protected MetaClass<T> createMetaClass(Property<?>[] declaredProperties, Property<?>[] allProperties) {
		return new SimpleMetaClass<T>(parent, theClass, declaredProperties, allProperties);		
	}
	
	protected Property<?>[] getAllProperties(Property<?>[] declaredProperties) {
		if (parent==null) {
			return declaredProperties;
		}
		Set<Property<?>> allProperties = new LinkedHashSet<Property<?>>();
		allProperties.addAll(Arrays.asList(parent.getAllProperties()));
		allProperties.addAll(declaredPropertyList);
		Property<?>[] properties = allProperties.toArray(new Property[allProperties.size()]);
		//System.out.println("!>" + MetaUtil.getUnqualifiedName(theClass.getName()) + " " + Arrays.toString(properties));
		resolveGenericProperties(properties);
		//System.out.println("!<" + MetaUtil.getUnqualifiedName(theClass.getName()) + " " + Arrays.toString(properties));
		return properties;
	}

	
	@SuppressWarnings("unchecked")
	private void resolveGenericProperties(Property<?>[] properties) {
		int i = 0;
		for (Property<?> property: properties) {
			Property<?> property2 = property;
			if (property instanceof PropertyWrapper) {
				property2 = ((PropertyWrapper<?>)property).getProperty();
			}
			if (property2 instanceof GenericFieldProperty) {
				i++;
				continue;
			}
			boolean replace = false;
			if (property2 instanceof FieldProperty) {
				Class<?> type = TypeUtil.getType(theClass, (Field)property2.getMember());
				if (!property2.getType().equals(type)) {
					property2 = new GenericFieldProperty<T>(property.getOwnerClass(),
							(Field)property.getMember(), type);
					replace = true;
				}
			}
			//System.out.println(property + " " + property2 + " " + MetaUtil.getUnqualifiedName(property2.getClass()));
			if (replace) {
				if (property instanceof PropertyWrapper) {
					try {
						property = properties[i] = (Property<?>) ((PropertyWrapper<?>)property).clone();
					} catch (CloneNotSupportedException e) {
						throw new RuntimeException(e);
					}
					((PropertyWrapper<Object>)property).setProperty((Property<Object>)property2);
				} else {
					properties[i] = property2;
				}
			}
			i++;
		}		
	}
	
	
}
