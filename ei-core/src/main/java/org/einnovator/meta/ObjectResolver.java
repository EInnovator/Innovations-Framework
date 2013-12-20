package org.einnovator.meta;


/**
 * A resolver for objects pointed by a {@code ObjectRef}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface ObjectResolver {

	/**
	 * Get a an object referenced by {@code ObjectRef}.
	 * 
	 * @param ref the {@code ObjectRef}
	 * @return the {@code ObjectRef}; or <code>null</code>, if not found
	 */
	<T> T getObject(ObjectRef<T> ref);
}
