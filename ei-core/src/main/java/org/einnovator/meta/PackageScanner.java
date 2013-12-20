package org.einnovator.meta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.einnovator.meta.criteria.TypeCriteria;
import org.einnovator.util.FileUtil;



/**
 * 
 *  A PackageScanner.
 * 
 *  List<Class<?>> lcl = PackageUtil.getClasses(pckg);
 *  
 *  System.out.printf("class for package: %s: %s %n", pckg, lcl);		
 *  
 *	Class<?>[] lcl2 = PackageUtil.getClassesRecursive(pckg);
 *
 *	System.out.printf("class for package root: %s: %s %n", pckg, StringUtil.toString(lcl2));		
 *
 * @author jsimao
 *
 */
public class PackageScanner {
	
	/**
	 * 
	 */
	public boolean scanJars = true;

	/**
	 * 
	 */
	public boolean innerClasses = false;

	
	//
	// Constructors
	//
	
	
	/**
	 * Create instance of PackageScan.
	 *
	 */
	public PackageScanner() {
	}
	
	//
	// Getters and setters
	//
	
	public boolean isScanJars() {
		return scanJars;
	}

	public void setScanJars(boolean scanJars) {
		this.scanJars = scanJars;
	}

	public boolean isInnerClasses() {
		return innerClasses;
	}

	public void setInnerClasses(boolean innerClasses) {
		this.innerClasses = innerClasses;
	}

	/**
	 * Scans all classes accessible from the context class loader which 
	 * belong to the given package and sub-packages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 */
	public Class<?>[] findClasses(String packageName)  {
		return findClasses(packageName, null);
	}
	
	public Class<?>[] findClasses(String packageName, TypeCriteria criteria)  {
		final List<Class<?>> classes = new ArrayList<Class<?>>();

		findClasses(packageName, criteria, classes);
		
		return classes.toArray(new Class[classes.size()]);
	}

	public Class<?>[] findClasses(String[] packageNames)  {
		return findClasses(packageNames, null);
	}
		
	public Class<?>[] findClasses(String[] packageNames, TypeCriteria criteria)  {
		final List<Class<?>> classes = new ArrayList<Class<?>>();

		for (String packageName: packageNames) {
			findClasses(packageName, criteria, classes);			
		}
		
		return classes.toArray(new Class[classes.size()]);
	}

	public void findClasses(String packageName, final TypeCriteria criteria, final List<Class<?>> classes)  {
		TypeHandler handler  = new TypeHandler() {
			@Override
			public void handle(Class<?> type) {
				if (criteria==null || criteria.check(type)) {
					classes.add(type);					
				}
			}			
		};

		iterateClasses(packageName, handler);
	}

	public void iterateClasses(String packageName, TypeHandler handler)  {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		assert loader != null;

		try {
			iterateClasses(loader, packageName, handler);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void iterateClasses(ClassLoader loader, String packageName, TypeHandler handler) 
		throws ClassNotFoundException, IOException {
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = loader.getResources(path);
		if (resources==null) {
			return;
		}
		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			//System.out.println("url:" + url);
			iterateClasses(FileUtil.decodeFileName(url), packageName, handler);
		}
	}
			

	public void iterateClasses(String filePath, String packageName, TypeHandler handler) throws FileNotFoundException, IOException, ClassNotFoundException {
		String path = packageName.replace('.', '/');
		if (filePath.indexOf("!") > 0 && filePath.indexOf(".jar") > 0) {
			if (!scanJars) {
				return;
			}
			String jarPath = filePath.substring(0, filePath.indexOf("!")).substring(filePath.indexOf(":") + 1);
			//WINDOWS HACK
			if (jarPath.indexOf(":") >= 0) {
				jarPath = jarPath.substring(1);
			}
			iterateClassesInJar(jarPath, path, handler);
		} else {
			iterateClasses(new File(filePath), packageName, handler);
		}
	}
	
	private void iterateClasses(File fileOrDir, String packageName, TypeHandler handler) throws ClassNotFoundException {
		if (!fileOrDir.exists()) {
			return;
		}
		File[] files = fileOrDir.listFiles();
		for (File file : files) {
			//System.out.println(":" + file);
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				iterateClasses(file, packageName + "." + file.getName(), handler);
			} else if (file.getName().endsWith(".class")) {
				//System.out.println("!:" + file);
				String className = file.getName().substring(0, file.getName().length() - 6);
				if (!innerClasses && className.indexOf('$')>0) {
					continue;
				}
				handler.handle(Class.forName(packageName + '.' + className));
			}
		}
	}
		
	public void iterateClassesInJar(String jar, String packageName, TypeHandler handler)  throws FileNotFoundException, IOException, ClassNotFoundException {		
		JarInputStream file = new JarInputStream(new FileInputStream(jar));
		JarEntry entry;
		while((entry = file.getNextJarEntry())!=null) {
			//System.out.println("jar entry:" + entry);	
			String className = entry.getName();
			if (className.endsWith(".class") && className.indexOf('-')<0) {
				if (!innerClasses && className.indexOf('$')>0) {
					continue;
				}
				className = FileUtil.removeExtention(className);
				if (className.startsWith(packageName)) {
					//System.out.println(" ->jar entry:" + entry);	
					try {
						Class<?> cl = Class.forName(className.replace('/', '.'));
						handler.handle(cl);
					} catch (ClassNotFoundException ex) { ex.printStackTrace(); }
					catch (NoClassDefFoundError ex) {  ex.printStackTrace(); }
				}
			}
		}
		file.close();
	}
	
			
}
