package org.einnovator.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.einnovator.util.FileNotFoundException;

/**
 * A {@code FileResource}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FileResource extends ResourceBase {


	protected File file;

	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code FileResource}.
	 *
	 * @param file
	 */
	public FileResource(File file) {
		this.file = file;
	}

	//
	// ResourceBase Implementation
	//
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getOptionalResourceFile()
	 */
	@Override
	public File getOptionalResourceFile() {
		return file;
	}
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getURI()
	 */
	@Override
	public URI getURI() {
		return file.toURI();
	}
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getInputStream()
	 */
	public InputStream getInputStream() {
		return getInputStream(file);
	}

	protected InputStream getInputStream(File file) {
		return getInputStream(this.getClass().getClassLoader(), file);
	}
	
	protected InputStream getInputStream(ClassLoader loader, File file) {
		URI uri =  file.toURI();
		try {
			URL url = uri.toURL();
			return url.openStream();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return loader.getResourceAsStream(file.toURI().getPath());

	}
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() {
		return getOuputStream(file);
	}
	
	protected OutputStream getOuputStream(File file) {
		try {
			return new FileOutputStream(file.toURI().getPath());
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException(e);
		}

	}
	
}
