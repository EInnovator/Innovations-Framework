package org.einnovator.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.einnovator.log.Logger;
import org.einnovator.log.simple.SimpleLogger;
import org.einnovator.util.FileUtil;
import org.einnovator.util.StringUtil;

/**
 * AA {@code ClasspathResourceFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ClasspathResourceFactory extends FileResourceFactory {

	private Logger logger = new SimpleLogger(this.getClass());

	/**
	 * ClassPath system property name, 
	 */
	public static final String CLASSPATH_PROPERTY = "java.class.path";

	/**
	 * Fall-back ClassPath. 
	 */
	public static final String[] FALLBACK_CLASSPATH = {"WEB-INF/classes/"};
	
	/**
	 * Is sOS Windows
	 */
	protected static final boolean WINDOWS = java.io.File.separatorChar == '\\';

	//
	// ResourceFactory Implementation
	//
	
	
	/**
	 * @see org.einnovator.resource.FileResourceFactory#createResource(java.lang.String)
	 */
	@Override
	public ResourceBase createResource(String uri) {
		String name = ResourceUtil.getResourceName(uri);
		File file = getClassPathResourceFile(name);
		ClasspathResource resource = new ClasspathResource(new File(uri), file);
		return file.getPath().startsWith("jar:") ? new FileInJarResource(file) : resource;
	}

	protected File getClassPathResourceFile(String name) {
		String classpath = System.getProperty(CLASSPATH_PROPERTY);
		if (!StringUtil.isEmpty(classpath)) {
			logger.trace(this, "getClassPathResourceFile", classpath);
			String[] bases = classpath.split(WINDOWS ? ";" : ":");
			File file = getResourceFile(bases, name);
			if (file!=null) {
				return file;
			}
			
		}
		File file = searchFromJarBases(name);
		if (file!=null) {
			return file;
		}
		return getFileResourceFile(name);
	}


	private File getResourceFile(String[] bases, String name) {
		for (String base: bases) {
			File file = getResourceFile(base, name);
			if (file!=null) {
				return file;
			}
		}
		return null;
	}

	private File getResourceFile(String base, String name) {
		logger.trace(this, "getResourceFile", base, name);
		File file = new File(FileUtil.pathConcat(base, name));
		if (file.exists()) {
			return file;
		}				
		return null;
	}

	String[] jarBases = {"META-INF/lib", "WEB-INF/lib", "WEB-INF/classes"};

	private File searchFromJarBases(String name) {
		return searchFromJarBases(jarBases, name);
	}
	
	private File searchFromJarBases(String bases[], String name) {
		for (String base: bases) {
			File file = searchFromJarBase(base, name);
			if (file!=null) {
				return file;
			}			
		}
		return null;
	}
	
	private File searchFromJarBase(String base, String name)  {
		Enumeration<URL> resources;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			resources = classLoader.getResources(base);
		} catch (IOException e) {
			return null;
		}
		logger.trace(this, "searchFromJarBase", base);
		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			File file = new File(FileUtil.decodeFileName(url));
			if (file.exists() && file.isDirectory()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					if (file2.getName().endsWith(".jar")) {
						String jar = file2.getPath();
						File file3 = searchInJar(jar, name);
						if (file3!=null) {
							return file3;
						}				
					}
				}
			}
		}
		return null;
	}
	
	public File searchInJar(String jar, String name) {		
		JarInputStream file = null;
		logger.trace(this, "searchInJar", jar, name);
		
		try {
			file = new JarInputStream(new FileInputStream(jar));
		} catch (java.io.FileNotFoundException e) {
		} catch (IOException e) {
		}
		if (file==null) {
			return null;
		}
		try {
			JarEntry entry;
			while((entry = file.getNextJarEntry())!=null) {
				if (name.equals(entry.getName())) {
					logger.trace(this, "searchInJar[ENTRY]", entry.getName());
					return new File("jar:file:" + jar + "!" + name);
				}
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
