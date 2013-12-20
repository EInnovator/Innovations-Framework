package org.einnovator.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * AA composite {@code Environment}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class CompositeEnvironment implements Environment {

	private List<Environment> environments;
	
	//
	// Constructors
	//
	
	public CompositeEnvironment() {
		environments = new ArrayList<Environment>();
	}
	
	/**
	 * Create instance of {@code CompositeEnvironment}.
	 *
	 * @param environments
	 */
	public CompositeEnvironment(List<Environment> environments) {
		this.environments = environments;
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param environments the environments
	 */
	public void addResolver(Environment environment) {
		this.environments.add(environment);
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param environments the environments
	 */
	public void addResolvers(Environment... environments) {
		for (Environment environment: environments) {
			addResolver(environment);
		}
	}

	/**
	 * Add a collection of {@code Environment}
	 * 
	 * @param environments the environments
	 */
	public void addResolvers(Collection<Environment> environments) {
		for (Environment environment: environments) {
			addResolver(environment);
		}
	}

	//
	// Fluent API
	//

	/**
	 * Add a environment to this chain
	 * 
	 * @param environment the environment
	 */
	public CompositeEnvironment add(Environment environment) {
		addResolver(environment);
		return this;
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param environments the environments
	 */
	public CompositeEnvironment addAll(Environment... environments) {
		addResolvers(environments);
		return this;
	}

	/**
	 * Add a variable number of {@code Environment}
	 * 
	 * @param environments the environments
	 */
	public CompositeEnvironment addAll(Collection<Environment> environments) {
		addResolvers(environments);
		return this;
	}

	/**
	 * Add all {@code Environment} returned by the specified {@code EnvironmentProvider}.
	 * 
	 * @param provider the {@code EnvironmentProvider}
	 */
	public CompositeEnvironment addAll(EnvironmentProvider provider) {
		List<Environment> environments = provider.getEnvironments();
		if (environments!=null) {
			environments.addAll(environments);
		}
		return this;
	}

	//
	// Environment Implementation
	//

	/**
	 * @see org.einnovator.environment.Environment#getValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getValue(String key, Object defaultValue) {
		for (Environment environment: environments) {
			Object value= environment.getValue(key, null);
			if (value!=null) {
				return value;
			}
		}
		return defaultValue;
	}

	/**
	 * @see org.einnovator.environment.Environment#getValue(java.lang.String, java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T getValue(String key, Class<T> type, T defaultValue) {
		for (Environment environment: environments) {
			T value= environment.getValue(key, type, null);
			if (value!=null) {
				return value;
			}
		}
		return defaultValue;
	}

}
