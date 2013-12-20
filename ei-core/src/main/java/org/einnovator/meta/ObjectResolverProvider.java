package org.einnovator.meta;

import java.util.List;

/**
 * A {@code EnvironmentProvider}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface ObjectResolverProvider {

	List<ObjectResolver> getObjectResolvers();
}
