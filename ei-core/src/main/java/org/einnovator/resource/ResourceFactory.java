package org.einnovator.resource;

/**
 * A factory for {@code ResourceBase}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface ResourceFactory {

	/**
	 * Create a {@code ResourceBase} from a url.
	 * 
	 * @param url the URL
	 * @return the {@code ResourceBase}
	 */
	ResourceBase createResource(String url);
}
