/**
 * 
 */
package org.einnovator.util.attribute;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A AttributesOwnerSupport.
 *
 * @author Jorge Simão
 */
public class LinkedListAttributes extends AttributesSupport {

	protected List<Attribute> attributeList;
	

	@Override
	public void setValue(String name, Object value) {
		if (attributeList==null) {
			attributeList = new LinkedList<Attribute>();
		}
		attributeList.add(new AttributeImpl(name, value));
	}

	@Override
	public void addAttribute(Attribute attribute) {
		if (attributeList==null) {
			attributeList = new LinkedList<Attribute>();
		}
		attributeList.add(attribute);
	}

	@Override
	public Object getValue(String name) {
		if (attributeList!=null) {
			for (Attribute attribute: attributeList) {
				if (attribute.getName().equals(name)) {
					return attribute.getValue();
				}
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	public Object removeAttribute(String name) {
		if (attributeList!=null) {
			for (int i=0; i<attributeList.size(); i++) {
				if (attributeList.get(i).getName().equals(name)) {
					return attributeList.remove(i);
				}
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	public Iterator<Attribute> iterator() {
		return attributeList!=null ? attributeList.iterator() : null;
	}

	@Override
	public int size() {
		return attributeList!=null ? attributeList.size() : 0;
	}

	@Override
	public boolean contains(String name) {
		if (attributeList!=null) {
			for (int i=0; i<attributeList.size(); i++) {
				if (attributeList.get(i).getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
}
