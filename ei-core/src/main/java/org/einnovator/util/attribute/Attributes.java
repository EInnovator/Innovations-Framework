/**
 * 
 */
package org.einnovator.util.attribute;

import java.util.Iterator;

/**
 * AA AttributesOwner.
 *
 * @author Jorge Simão
 */
public interface Attributes extends Iterable<Attribute> {

	@Override
	Iterator<Attribute> iterator();
	
	void setValue(String name, Object value);

	void addAttribute(Attribute attribute);
	
	void addAttributes(Attributes attributes, boolean clone);
	
	Object getValue(String name);	
	
	Object removeAttribute(String name);
	
	boolean contains(String name);
	
	int size();
}
