/**
 * 
 */
package org.einnovator.meta.metaclass.build;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.einnovator.meta.FieldHandler;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.Property;
import org.einnovator.meta.property.FieldProperty;




/**
 * A MetaClassBuilderUtil.
 *
 * @author Jorge Simão
 */
@Deprecated
public class MetaClassBuilderUtil {

	static public Property<?>[] getAllProperties(MetaClass<?> metaClass) {
		List<Property<?>> propertyList = new ArrayList<Property<?>>();
		collectProperties(metaClass, propertyList);
		return propertyList.toArray(new Property<?>[propertyList.size()]);
	}

	static private void collectProperties(MetaClass<?> metaClass, List<Property<?>> propertyList) {
		if (metaClass.getParent()!=null) {
			collectProperties(metaClass.getParent(), propertyList);
		}
		for (Property<?> property: metaClass.getDeclaredProperties()) {
			propertyList.add(property);				
		}	
	}
	

	static public Property<?>[] collectFieldProperties(Class<?> type, boolean recurse) {
		final List<Property<?>> propertyList = new ArrayList<Property<?>>();
		MetaUtil.iterateFields(type, new FieldHandler() {
			@Override
			public void handle(Field field) {
				propertyList.add(new FieldProperty<Object>(field));
			}
		}, recurse, true);
		return propertyList.toArray(new Property<?>[propertyList.size()]);
	}
	
	static public Property<?>[] collectGetterSetterProperties(Class<?> type, boolean recurse) {
		final List<Property<?>> propertyList = new ArrayList<Property<?>>();
		//TODO: implement
		return propertyList.toArray(new Property<?>[propertyList.size()]);
	}

}
