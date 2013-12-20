package org.einnovator.resource;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * A {@code HttpResourceFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class HttpResourceFactory implements ResourceFactory {

	//
	// ResourceFactory Implementation
	//
	
	@Override
	public ResourceBase createResource(String uri) {
		try {
			return new HttpResource(new URI(uri));
		} catch (URISyntaxException e) {
			throw new ResourceException(e);
		}
	}

}
