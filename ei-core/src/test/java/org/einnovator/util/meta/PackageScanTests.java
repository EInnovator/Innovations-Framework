package org.einnovator.util.meta;



import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import org.einnovator.meta.PackageScanner;
import org.junit.Ignore;
import org.junit.Test;


public class PackageScanTests {


	@Test
	@Ignore
	public void scanPackages() {
		Class<?>[] classes = new PackageScanner().findClasses("util.meta");
		print(classes);
	}

	@Test
	@Ignore
	public void scanSwingPackages() throws IOException {
		Class<?>[] classes = new PackageScanner().findClasses("javax.swing");
		print(classes);
	}
	
	@Test
	//@Ignore
	public void scanTests() throws IOException {
		for (Map.Entry<Object, Object> e: System.getProperties().entrySet()) {
			System.out.println(e.getKey() + "=" + e.getValue());
		}
		System.out.println(System.getProperties());
		String packageName =  "javax.swing";
		String path = packageName.replace('.', '/');
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> resources = loader.getResources(path);
		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			System.out.println("url:" + url);
		}
	
	}
	
	@Test
	@Ignore
	public void scanPackagesJar() {
		Class<?>[] classes = new PackageScanner().findClasses("org.springframework.web.servlet.view.tiles2");
		print(classes);
	}


	public void print(Class<?>[] classes) {
		for (Class<?> type : classes) {
			System.out.println(type);
		}
	}

}
