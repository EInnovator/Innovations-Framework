package org.einnovator.text;

import java.util.HashMap;
import java.util.Map;

import org.einnovator.meta.ObjectRef;
import org.einnovator.meta.ObjectResolver;

/**
 * A registry for {@code TextTransform} indexed by some key.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class TextTransformRegistry implements ObjectResolver {

	private Map<Object, TextTransform> tranformMap;

	private TextTransform defaultTranform;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code TextTransformRegistry}.
	 *
	 */
	public TextTransformRegistry() {
		this(new HashMap<Object, TextTransform>());
	}
	
	/**
	 * Create instance of {@code TextTransformRegistry}.
	 *
	 * @param tranformMap
	 */
	public TextTransformRegistry(Map<Object, TextTransform> tranformMap) {
		this.tranformMap = tranformMap;
	}
	
	
	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code tranformMap}.
	 *
	 * @return the tranformMap
	 */
	public Map<Object, TextTransform> getTranformMap() {
		return tranformMap;
	}

	/**
	 * Set the value of property {@code tranformMap}.
	 *
	 * @param tranformMap the tranformMap to set
	 */
	public void setTranformMap(Map<Object, TextTransform> tranformMap) {
		this.tranformMap = tranformMap;
	}

	/**
	 * Get the value of property {@code defaultTranform}.
	 *
	 * @return the defaultTranform
	 */
	public TextTransform getDefaultTranform() {
		return defaultTranform;
	}

	/**
	 * Set the value of property {@code defaultTranform}.
	 *
	 * @param defaultTranform the defaultTranform to set
	 */
	public void setDefaultTranform(TextTransform defaultTranform) {
		this.defaultTranform = defaultTranform;
	}

	//
	// Collections
	//

	/**
	 * Add a transform.
	 * 
	 * @param key the key for the {@code TextTransform}
	 * @param transform the {@code TextTransform}
	 * @return this {@code TextTransformRegistry}
	 */
	public void put(Object key, TextTransform transform) {
		tranformMap.put(key, transform);
	}
	

	//
	// Fluent API
	//
	
	/**
	 * Add a transform (fluent API).
	 * 
	 * @param key the key for the {@code TextTransform}
	 * @param transform the {@code TextTransform}
	 * @return this {@code TextTransformRegistry}
	 */
	public TextTransformRegistry add(Object key, TextTransform transform) {
		put(key, transform);
		return this;
	}
	
	/**
	 * Add a {@code TextTransform} mapped to a variable number of keys (fluent API).
	 * 
	 * @param transform the {@code TextTransform}
	 * @param keys the variable number of keys
	 * @return this {@code TextTransformRegistry}
	 */
	public TextTransformRegistry add(TextTransform transform, Object... keys) {
		for (Object key: keys) {
			put(key, transform);			
		}
		return this;
	}
	

	
	//
	// Lookup and transform
	//
	
	/**
	 * Get the {@code TextTransform} for the specified key.
	 * 
	 * @param key the key for the {@code TextTransform}
	 * @return the found {@code TextTransform}, or the default one if none is found
	 */
	public TextTransform getTransform(Object key) {
		TextTransform transform = tranformMap.get(key);
		if (transform==null) {
			transform = defaultTranform;
		}
		return transform;
	}
	

	/**
	 * Ref input text using a transform index by the specified key.
	 * 
	 * @param key the transform key
	 * @param text the input text
	 * @return the tranformed text; or the input text if no transform is found
	 */
	public String transform(Object key, String text) {
		TextTransform transform = getTransform(key);
		if (transform!=null) {
			return transform.transform(text);
		} else {
			return text;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getObject(ObjectRef<T> ref) {
		if (!TextTransform.class.isAssignableFrom(ref.getType()) ||
				!TextTransform.class.isAssignableFrom(ref.getDefaultType())) {
			return null;
		}
		if (ref.getName()!=null) {
			TextTransform transform = getTransform(ref.getName()); 
			if (transform!=null) {
				return (T)transform;
			}
		}
		return null;
	}
	
	//
	// Static utilities
	//
	
	public static TextTransformRegistry instance;
	
	/**
	 * Get singleton instance of a {@code TextTransformRegistry}.
	 * 
	 * This method does not create the singleton. 
	 * Method {@link #getRequiredInstance()} or {@link #setDefaultTranform(TextTransform)} 
	 * should be called before hand.
	 * 
	 * To return a non-<code>null</code> value required
	 * @return the {@code TextTransformRegistry} instance; or <code>null</code>, if none was set.
	 * @see #getRequiredInstance()
	 */
	public static TextTransformRegistry getInstance() {
		return instance;
	}
	
	
	/**
	 * Get singleton instance of a {@code TextTransformRegistry}.
	 * 
	 * If none was set or created before a {@code TextTransformRegistry} is created.

	 * @return the {@code TextTransformRegistry} instance
	 * @see #setInstance(TextTransformRegistry)
	 */
	public static TextTransformRegistry getRequiredInstance() {
		if (instance==null) {
			instance = new TextTransformRegistry();
		}
		return instance;
	}

	/**
	 * Get the singleton instance of a {@code TextTransformRegistry}.
	 * 
	 * @param instance the {@code TextTransformRegistry} instance
	 * @see #getInstance()
	 * @see #getRequiredInstance()
	 */
	public static void setInstance(TextTransformRegistry instance) {
		TextTransformRegistry.instance = instance;
	}


}
