/**
 * 
 */
package org.einnovator.util.attribute;

import java.util.Iterator;


/**
 * AA AttributesSupport.
 *
 * @author Jorge Simão
 */
abstract public class AttributesSupport implements Attributes {


	@Override
	public void addAttributes(Attributes attributes, boolean clone) {
		if (attributes.size()>0) {
			for (Attribute attribute: attributes) {
				if (clone) attribute = new AttributeImpl(attribute);
				addAttribute(attribute);
			}			
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName() + "@" + hashCode() + "(");
		if (size()>0) {
			Iterator<Attribute> it = iterator();
			while(it.hasNext()) {
				Attribute attr = it.next();
				sb.append(attr.getName() + "=" + attr.getValue());
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
