package org.einnovator.validation;

import org.einnovator.format.ObjectSupport;
import org.einnovator.util.ArrayUtil;

/**
 * Description of an error.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class Error extends ObjectSupport {

	private Object value;
	
	private String path;
	
	private String key;
	
	private String message;
	
	private Object[] params;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code Error}.
	 *
	 */
	public Error() {
	}

	/**
	 * Create instance of {@code Error}.
	 *
	 * @param path
	 * @param key
	 * @param message
	 * @param params
	 */
	public Error(String path, String key, String message, Object[] params) {
		this(null, path, key, message, params);
	}
	
	/**
	 * Create instance of {@code Error}.
	 *
	 * @param value
	 * @param path
	 * @param key
	 * @param message
	 * @param params
	 */
	public Error(Object value, String path, String key, String message, Object[] params) {
		this.value = value;
		this.path = path;
		this.key= key;
		this.message = message;
		this.params = !ArrayUtil.isEmpty(params) ? params : null;
	}

	/**
	 * Create instance of {@code Error}.
	 *
	 * @param path
	 * @param key
	 * @param message
	 */
	public Error(String path, String key, String message) {
		this(path, key, message, null);
	}

	//
	// Getters and setters
	//

	/**
	 * Get the value of property {@code value}.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set the value of property {@code value}.
	 *
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Set the value of path.
	 *
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}	
	
	/**
	 * Get the value of path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Get the value of key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set the value of key.
	 *
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Get the value of message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the value of message.
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	/**
	 * Get the value of property {@code params}.
	 *
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * Set the value of property {@code params}.
	 *
	 * @param params the params to set
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

	public String toString() {
		return path + ":" + key + "=" + message;
	}
	
	public String getRequiredMessage() {
		if (message!=null) {
			return message;
		}
		return key;
	}

}

