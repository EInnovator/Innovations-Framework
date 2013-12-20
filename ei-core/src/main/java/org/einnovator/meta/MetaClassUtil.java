/**
 * 
 */
package org.einnovator.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.meta.property.PropertyHandler;


/**
 * A MetaClassUtil.
 *
 * @author Jorge Simão
 */
public class MetaClassUtil {
	
	static public void iterateProperties(MetaClass<?> metaClass, PropertyHandler handler) {
		iterateProperties(metaClass, handler, null);
	}

	static public void iterateProperties(MetaClass<?> metaClass, PropertyHandler handler, PropertyCriteria criteria) {
		for (Property<?> property: metaClass.getAllProperties()) {
			if (criteria==null || criteria.match(property)) {
				handler.handle(property);				
			}
		}
	}

	static public String[] getPropertyNames(MetaClass<?> metaClass, PropertyCriteria criteria) {
		final List<String> propertyNameList = new ArrayList<String>();
		for (Property<?> property: metaClass.getAllProperties()) {
			if (criteria==null || criteria.match(property)) {
				propertyNameList.add(property.getName());
			}
		}
		return propertyNameList.toArray(new String[propertyNameList.size()]);
	}

	
	static public boolean hasAncestor(MetaClass<?> metaClass, MetaClass<?> ancestor) {
		if (metaClass.getParent()!=null) {			
			if (metaClass.getParent()==ancestor) {
				return true;
			}
			return hasAncestor(metaClass.getParent(), ancestor);
		} else {
			return false;
		}
	}

	public static MetaClass<?>[] getMetaClassTree(MetaClass<?> root, Collection<MetaClass<?>> allClasses) {
		List<MetaClass<?>> classTree = new ArrayList<MetaClass<?>>();
		for (MetaClass<?> mappedClass: allClasses) {
			if (mappedClass==root || hasAncestor(mappedClass, root)) {
				classTree.add(mappedClass);
			}
		}
		return classTree.toArray(new MetaClass[classTree.size()]);
	}
	
	public static MetaClass<?>[] getClassTree(MetaClass<?> root, MetaClassRegistry registry) {
		List<MetaClass<?>> classTree = new ArrayList<MetaClass<?>>();
		getClassTree(root, registry.getMetaClasses(), classTree);
		return classTree.toArray(new MetaClass[classTree.size()]);
	}

	public static void getClassTree(MetaClass<?> root, Collection<MetaClass<?>> allClasses, List<MetaClass<?>> classTree) {
		for (MetaClass<?> metaClass: allClasses) {
			if (metaClass==root || MetaClassUtil.hasAncestor(metaClass, root)) {
				classTree.add(metaClass);
			}
		}
	}


}
