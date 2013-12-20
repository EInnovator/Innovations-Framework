/**
 * 
 */
package org.einnovator.util.attribute;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A SimpleMapAttributes.
 *
 * @author Jorge Simão
 */
public class SimpleMapAttributes  extends AttributesSupport {
	protected Map<String, Object> attributeMap;
	
	public Map<String, Object> getAttributes() {
		return attributeMap;
	}

	@Override
	public void setValue(String name, Object value) {
		if (attributeMap==null) {
			attributeMap = new LinkedHashMap<String, Object>();
		}
		attributeMap.put(name, value);
	}

	
	@Override
	public Object getValue(String name) {
		if (attributeMap!=null) {
			return attributeMap.get(name);
		} else {
			return null;
		}
	}

	@Override
	public void addAttribute(Attribute attribute) {
		setValue(attribute.getName(), attribute);
	}

	@Override
	public Object removeAttribute(String name) {
		if (attributeMap!=null) {
			return attributeMap.remove(name);
		} else {
			return null;
		}
	}

	@Override
	public Iterator<Attribute> iterator() {
		throw new RuntimeException("Unsupported operation");
	}

	@Override
	public int size() {
		return attributeMap!=null ? attributeMap.size() : 0;
	}
	
	@Override
	public boolean contains(String name) {
		if (attributeMap!=null) {
			return attributeMap.containsKey(name);
		}
		return false;
	}	
	
}
