package org.einnovator.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;

/**
 * A {@code HttpResource}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class HttpResource extends ResourceBase {

	private URI uri;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code HttpResource}.
	 *
	 * @param uri
	 */
	public HttpResource(URI uri) {
		this.uri = uri;
	}

	//
	// Implementation
	//
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getURI()
	 */
	@Override
	public URI getURI() {
		return uri;
	}
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {		
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) uri.toURL().openConnection();
		} catch (MalformedURLException e) {
			throw new ResourceException(e);
		} catch (IOException e) {
			throw new ResourceException(e);
		}

		//connection.setDoOutput(true);				
		connection.setDoInput(true);
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e3) {
			throw new ResourceException(e3);
		}		

		try {
			return connection.getInputStream();
		} catch (IOException e1) {
			throw new ResourceException(e1);
		}			
	}
	
	/**
	 * @see org.einnovator.resource.ResourceBase#getOptionalResourceFile()
	 */
	public File getOptionalResourceFile() {
		return new File(uri.toString());
	}
	
	@Override
	public OutputStream getOutputStream() {
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) uri.toURL().openConnection();
		} catch (MalformedURLException e) {
			throw new ResourceException(e);
		} catch (IOException e) {
			throw new ResourceException(e);
		}

		connection.setDoOutput(true);				
		try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e3) {
			throw new ResourceException(e3);
		}			
	
		try {
			return connection.getOutputStream();
		} catch (IOException e1) {
			throw new ResourceException(e1);
		}	
	}

	/**
	 * @return always <code>true</code>
	 * @see org.einnovator.resource.ResourceBase#exists()
	 */
	@Override
	public boolean exists() {
		return true;
	}

}
