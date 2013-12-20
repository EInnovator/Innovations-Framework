package org.einnovator.meta;

import java.util.List;

/**
 * A {@code EnvironmentProvider}.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
public interface ObjectResolverProvider {

	List<ObjectResolver> getObjectResolvers();
}
