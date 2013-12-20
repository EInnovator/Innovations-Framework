package org.einnovator.meta;

/**
 * The default {@code ObjectResolver}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class DefaultObjectResolver extends CompositeObjectResolver {
	
	/**
	 * Create instance of {@code DefaultObjectResolver}.
	 * 
	 * Adds the following {@link ObjectResolver}:
	 * <ul>
	 * <li> All returned by a {@link ClasspathEnvironmentProvider}
	 * <ul>
	 *
	 */
	public DefaultObjectResolver() {
		addAll(new ClasspathObjectResolverProvider());
	}
	
	//
	// ObjectResolver Implementation
	//
	
	/**
	 * @see org.einnovator.meta.CompositeObjectResolver#getObject(org.einnovator.meta.ObjectRef)
	 */
	@Override
	public <T> T getObject(ObjectRef<T> ref) {
		T obj = super.getObject(ref);
		if (obj==null) {
			if (ref.getDefaultType()!=null) {
				obj = MetaUtil.newInstance(ref.getDefaultType());
			}
		}
		return obj;
	}
}
