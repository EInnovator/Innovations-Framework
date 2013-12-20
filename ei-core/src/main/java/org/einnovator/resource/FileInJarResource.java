package org.einnovator.resource;

import java.io.File;

import static org.einnovator.util.StringUtil.substringAfterNonNull;
import static org.einnovator.util.StringUtil.substringBeforeLastNonNull;

/**
 * A {@code FileInJarResource}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FileInJarResource extends FileResource {

	private File jarFile;
	
	//
	// Getters and Setters
	//
	
	/**
	 * Create instance of {@code FileInJarResource}.
	 *
	 * @param file
	 */
	public FileInJarResource(File file) {
		super(file);
		jarFile = new File(substringBeforeLastNonNull(substringAfterNonNull(file.getPath(), "jar:"), "!"));
	}

	//
	// Constructors
	//

	@Override
	public File getOptionalResourceFile() {
		return jarFile;
	}
}
