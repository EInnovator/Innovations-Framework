/**
 * 
 */
package org.einnovator.resource;

/**
 * A {@code Resource}.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
public class Resource extends ResourceWrapper {

	private ResourceFactory resourceFactory;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of Resource.
	 *
	 */
	protected Resource(ResourceFactory resourceFactory) {
		this.resourceFactory = resourceFactory;
	}

	/**
	 * Create instance of Resource.
	 *
	 */
	protected Resource() {
		this(DefaultResouceFactory.getInstance());
	}

	/**
	 * Create instance of Resource.
	 *
	 * @param uri
	 */
	public Resource(String uri) {
		this();
		delegate = resourceFactory.createResource(uri);
	}


}
