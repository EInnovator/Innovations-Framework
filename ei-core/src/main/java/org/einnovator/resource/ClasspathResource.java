package org.einnovator.resource;

import java.io.File;

/**
 * AA {@code FileResource}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ClasspathResource extends FileResource {


	protected File userFile;

	
	//
	// Constructors
	//

	/**
	 * Create instance of {@code FileResource}.
	 *
	 * @param file
	 */
	public ClasspathResource(File file, File actualFile) {
		super(actualFile);
		this.userFile = file;
	}

	//
	// ResourceBase Implementation
	//
		

	@Override
	public String toString() {
		return userFile.toString() + " -> " +super.toString();
	}
}
