package org.einnovator.resource;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.einnovator.util.FileNotFoundException;
import org.einnovator.util.IOUtil;

/**
 * A {@code ResourceBase}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public abstract class ResourceBase {

	private int bufferSize = IOUtil.DEFAULT_BUFFER_SIZE;
	
	//
	// Constructors
	//
	
	protected ResourceBase() {
	}
	
	//
	// Getters and Setters
	//	

	/**
	 * Get the value of property {@code bufferSize}.
	 *
	 * @return the bufferSize
	 */
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * Set the value of property {@code bufferSize}.
	 *
	 * @param bufferSize the bufferSize to set
	 */
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	
	/**
	 * Get file for this resource.
	 * 
	 * @return the {@code File}
	 * @throws FileNotFoundException if file not found
	 */
	public File getFile() {
		File file = getOptionalResourceFile();
		if (!file.exists()) {
			throw new FileNotFoundException(file.getPath());
		}
		return file;
	}

	/**
	 * Get the {@code URI} for this resource.
	 * 
	 * @return the {@code URI}
	 */
	abstract public URI getURI();

	/**
	 * Get the {@code URI} for this resource.
	 * 
	 * @return the {@code URI}
	 */
	public URL getURL() {
		try {
			return getURI().toURL();
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * Get {@code File} for this resource.
	 * 
	 * No check is done to see if {@code File} exists.
	 * 
	 * @return the {@code File}
	 */
	abstract public File getOptionalResourceFile();

	public boolean exists() {
		File file = getOptionalResourceFile();
		return file!=null ? file.exists() : false;
	}

	/**
	 * Get file name for this resource.
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		File file = getOptionalResourceFile();
		return file.getName();
	}

	/**
	 * Get path for this resource.
	 * 
	 * @return the path
	 */
	public String getPath() {
		File file = getOptionalResourceFile();
		return file.getPath();
	}

	/**
	 * Get prefix for this resource.
	 * 
	 * @return the prefix
	 */
	public String getPrefix() {
		return ResourceUtil.getResourcePrefix(getURI().toString());
	}

	/**
	 * Get the {@code InputStream} for this resource.
	 * 
	 * @return the {@code InputStream}
	 */
	abstract public InputStream getInputStream();

	/**
	 * Get the {@code OutputStream} for this resource.
	 * 
	 * @return the {@code OutputStream}
	 */
	abstract public OutputStream getOutputStream();

	/**
	 * Get the content of this resource as a byte array.
	 * 
	 * @return the byte array
	 */
	public byte[] readBytes() {
		return IOUtil.readBytes(getInputStream(), bufferSize);
	}

	/**
	 * Get the content of this resource as a byte array.
	 * 
	 * @param the size of the buffer to use
	 * @return the byte array
	 */
	public byte[] readBytes(int bufferSize) {
		return IOUtil.readBytes(getInputStream(), bufferSize);
	}
	
	/**
	 * Write the content of this resource to an {@code OutputStream}.
	 * 
	 * @param out the {@code OutputStream}
	 */
	public void writeBytes(OutputStream out) {
		IOUtil.writeBytes(getInputStream(), out, bufferSize);
	}

	/**
	 * Write the content of this resource to an {@code OutputStream}.
	 * 	
	 * @param out the {@code OutputStream}
	 * @param the size of the buffer to use
	 */
	public void writeBytes(OutputStream out, int bufferSize) {
		IOUtil.writeBytes(getInputStream(), out, bufferSize);
	}

	/**
	 * Write the content of this resource to an {@code Writer}.
	 * 
	 * @param out the {@code Writer}
	 */
	public void writeBytes(Writer out) {
		IOUtil.writeBytes(getInputStream(), out, bufferSize);
	}

	/**
	 * Write the content of this resource to an {@code Writer}.
	 * 	
	 * @param out the {@code Writer}
	 * @param the size of the buffer to use
	 */
	public void writeBytes(Writer out, int bufferSize) {
		IOUtil.writeBytes(getInputStream(), out, bufferSize);
	}
	
	//
	// Object overrides
	//
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		File file = getOptionalResourceFile();
		return file.toString();
	}
}
