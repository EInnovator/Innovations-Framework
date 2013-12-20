package org.einnovator.resource;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public abstract class ResourceWrapper extends ResourceBase {

	protected ResourceBase delegate;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ResourceWrapper}.
	 *
	 */
	protected ResourceWrapper() {
	}

	/**
	 * Create instance of {@code ResourceWrapper}.
	 *
	 * @param delegate
	 */
	protected ResourceWrapper(ResourceBase delegate) {
		this.delegate = delegate;
	}

	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code delegate}.
	 *
	 * @return the delegate
	 */
	public ResourceBase getDelegate() {
		return delegate;
	}

	/**
	 * Set the value of property {@code delegate}.
	 *
	 * @param delegate the delegate to set
	 */
	public void setDelegate(ResourceBase delegate) {
		this.delegate = delegate;
	}	
	
	//
	// ResouceBase Implementation
	//
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		return delegate.getInputStream();
	}
	
	/**
	 * @return
	 */
	@Override
	public OutputStream getOutputStream() {
		return delegate.getOutputStream();
	}
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getOptionalResourceFile()
	 */
	@Override
	public File getOptionalResourceFile() {
		return delegate.getOptionalResourceFile();
	}
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getURI()
	 */
	@Override
	public URI getURI() {
		return delegate.getURI();
	}
	
}
