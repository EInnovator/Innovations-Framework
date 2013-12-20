package org.einnovator.i18n;

import org.einnovator.meta.ObjectRef;
import org.einnovator.meta.ObjectResolverHolder;


/**
 * A holder for a singleton {@code Environment}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 */
public class MessageResolverHolder {

	//
	// Static utilities
	//
	
	public static MessageResolver instance;
	
	/**
	 * Get singleton instance of a {@code Environment}.
	 * 
	 * This method does not create the singleton. 
	 * Method {@link #getRequiredInstance()} or {@link #setInstance(Environment)} 
	 * should be called before hand.
	 * 
	 * To return a non-<code>null</code> value required
	 * @return the {@code Environment} instance; or <code>null</code>, if none was set.
	 * @see #getRequiredInstance()
	 */
	public static MessageResolver getInstance() {
		return instance;
	}
	
	
	/**
	 * Get singleton instance of a {@code Environment}.
	 * 
	 * If none was set or created before a {@code DefaultObjectResolver} is created and returned.

	 * @return the {@code Environment} instance
	 * @see #setInstance(Environment)
	 */
	public static MessageResolver getRequiredInstance() {
		if (instance==null) {
			instance = ObjectResolverHolder.getRequiredInstance().getObject(
					new ObjectRef<MessageResolver>(MessageResolver.class, DefaultMessageResolver.class));
		}
		return instance;
	}

	/**
	 * Get the singleton instance of a {@code Environment}.
	 * 
	 * @param instance the {@code Environment} instance
	 * @see #getInstance()
	 * @see #getRequiredInstance()
	 */
	public static void setInstance(MessageResolver instance) {
		MessageResolverHolder.instance = instance;
	}

}
