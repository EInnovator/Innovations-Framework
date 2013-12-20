package org.einnovator.resource;

/**
 * A {@code DefaultResouceFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class DefaultResouceFactory extends CompositeResourceFactory {

	//
	// Constructors
	//
	
	public DefaultResouceFactory() {
		ResourceFactory fileResourceFactory = new FileResourceFactory();
		add("file", fileResourceFactory);
		add("classpath", new ClasspathResourceFactory());
		addResourceFactories(new HttpResourceFactory(), "http", "https");		
		setDefaultResourceFactory(fileResourceFactory);
	}
	
	//
	// Static Utilities
	//
	
	private static DefaultResouceFactory instance;
	
	public static DefaultResouceFactory getInstance() {
		if (instance==null) {
			instance = new DefaultResouceFactory();
		}
		return instance;
	}
}
