/**
 * 
 */
package org.einnovator.i18n;


/**
 * A desciptor for a i18n message.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class MessageDescriptor {

	/**
	 * The key for the message.
	 */
	private String key;

	/**
	 * The default value for the message.
	 */
	private String defaultValue;
	
	/**
	 * Extra data.
	 */
	private Object payload;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code MessageDescriptor}.
	 *
	 */
	public MessageDescriptor() {
	}

	
	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code key}.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set the value of property {@code key}.
	 *
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Get the value of property {@code defaultValue}.
	 *
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Set the value of property {@code defaultValue}.
	 *
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	/**
	 * Get the value of property {@code payload}.
	 *
	 * @return the payload
	 */
	public Object getPayload() {
		return payload;
	}

	/**
	 * Set the value of property {@code payload}.
	 *
	 * @param payload the payload to set
	 */
	public void setPayload(Object payload) {
		this.payload = payload;
	}	
	
	//
	// Static Utilities
	//
	
	/**
	 * Assign the property values of a {@code Message} to this {@code MessageDescriptor}.
	 * 
	 * @param message the {@code Message} 
	 * @return this {@code MessageDescriptor}
	 */
	public MessageDescriptor assign(Message message) {
		defaultValue = !message.value().isEmpty() ? message.value() : null;
		key = !message.key().isEmpty() ? message.key() : null;
		return this;
	}
}
