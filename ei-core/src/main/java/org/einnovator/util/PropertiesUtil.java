/**
 * 
 */
package org.einnovator.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A {@code PropertiesUtil}.
 *
 * @author Jorge Simão
 */
public class PropertiesUtil {

	public static void mergedLoadFromClasspath(Properties properties, String[] filenames) {
		mergedLoadFromClasspath(Thread.currentThread().getContextClassLoader(), properties, filenames);
	}

	public static void mergedLoadFromClasspath(Properties properties, String filename) {
		mergedLoadFromClasspath(Thread.currentThread().getContextClassLoader(), properties, filename);
	}

	public static void mergedLoadFromClasspath(ClassLoader loader, Properties properties, String[] filenames) {
		for (String filename: filenames) {
			mergedLoadFromClasspath(loader, properties, filename);
		}
		
	}

	public static void mergedLoadFromClasspath(ClassLoader loader, Properties properties, String filename) {
		InputStream in = loader.getResourceAsStream(filename);
		if (in!=null) {
			try {
				properties.load(in);
				System.err.println("[LOADED]:" +  filename + " " + loader.getResource(filename));
			} catch (IOException e) {
			}
		}
	}

}
