/**
 * 
 */
package org.einnovator.util.attribute;

/**
 * AA AttributeImpl.
 *
 * @author Jorge Simão
 */
public class AttributeImpl implements Attribute {
	protected String name;
	
	protected Object value;

	/**
	 * Create instance of AttributeImpl.
	 *
	 * @param name
	 * @param value
	 */
	public AttributeImpl(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Create instance of AttributeImpl.
	 *
	 * @param attribute
	 */
	public AttributeImpl(Attribute attribute) {
		this(attribute.getName(), attribute.getValue());
	}

	/**
	 * Create instance of Attribute.
	 *
	 */
	public AttributeImpl() {
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the value of value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set the value of value.
	 *
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
}
