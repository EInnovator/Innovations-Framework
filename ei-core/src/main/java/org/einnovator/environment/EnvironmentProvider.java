package org.einnovator.environment;

import java.util.List;

/**
 * A {@code Environment}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface EnvironmentProvider {

	List<Environment> getEnvironments();
}
