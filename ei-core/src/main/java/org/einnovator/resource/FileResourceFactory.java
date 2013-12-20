package org.einnovator.resource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class FileResourceFactory implements ResourceFactory {

	//
	// ResourceFactory Implementation
	//
	
	@Override
	public ResourceBase createResource(String uri) {
		return new FileResource(getFileResourceFile(uri));
	}

	
	protected File getFileResourceFile(String uri) {
		uri = ResourceUtil.getResourceName(uri);
		File file = new File(uri);
		//System.out.println(this + ".getFileResourceFile:" + uri + " " + file.getPath() + " " + file.exists());
		if (file.exists()) {
			return file;
		}
		URL url = getURL(uri, this.getClass());
		if (url!=null) {
			file = new File(url.getPath());
			//System.out.println(this + ".getFileResourceFile[3]:" + uri + " " + file.getPath() + " " + file.exists());
			if (file.exists()) {
				return file;
			}			
		}
		return file;
	}
	
	public static URL getURL(String path, Class<?> klass) {
		URL url = klass.getResource(path);
		if (url!=null) {
			return url;
		}
		return getURL(path);
	}
	
	public static URL getURL(String path) {
		try {
			return new URL(path);
		} catch(MalformedURLException e) {
			return getURLAsResource(path);
		 }
	}

	public static URL getURLAsResource(String path) {
		URL url = null;
 		//Locate with current context ClassLoader.
 		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
 		if (contextClassLoader!=null) {
 			url = contextClassLoader.getResource(path);
 		}
 		if (url != null) {
 			return url; 			
 		}
 	 	//Locate with current this class's ClassLoader
 		url = FileResourceFactory.class.getClassLoader().getResource(path);
 		if (url != null) {
 			return url; 			
 		}
 		//Locate with system ClassLoader
 		url = ClassLoader.getSystemClassLoader().getResource(path);
 		return url;
 	}
}
