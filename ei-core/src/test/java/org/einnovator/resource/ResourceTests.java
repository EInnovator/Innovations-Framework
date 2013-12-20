/**
 * 
 */
package org.einnovator.resource;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.einnovator.resource.Resource;
import org.einnovator.util.FileNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



/**
 * A ResourceTests.
 *
 * @author Jorge Simão
 */
public class ResourceTests {

	@Before
	public void setup() {
		//Resource.logger.setLevel(Level.ALL);
	}
	
	@Test
	public void fileResourceTest() throws IOException {
		checkResource("file:src/test/resources/org/einnovator/resource/test-resource.txt");
	}

	@Test
	public void noPrefixResourceTest() throws IOException {
		checkResource("src/test/resources/org/einnovator/resource/test-resource.txt");
	}

	void checkResource(String path) throws IOException {
		Resource resource = new Resource(path);
		assertTrue(resource.getOptionalResourceFile().exists());
		assertTrue(resource.getFile().exists());
		System.out.println("FILE:" + resource.getFile());
		System.out.println("URI:" + resource.getURI());
		System.out.println("PATH:" + resource.getPath());
		read(resource);		
	}
	@Test
	public void classpathResourceTest() throws IOException, URISyntaxException {

		checkResource("classpath:org/einnovator/resource/test-resource.txt");
		assertTrue(new Resource("classpath:META-INF/lib").getOptionalResourceFile().exists());
		checkResource("classpath:META-INF/test-resource3.txt");
		checkResource("classpath:META-INF/test-resource2.txt");
	}

	@Test
	public void readResourceTest() throws IOException, URISyntaxException {
		read(new Resource("classpath:org/einnovator/resource/test-resource.txt"));
	}

	@Test
	public void httpResourceTest() throws IOException, URISyntaxException {
		Resource httpResouce = new Resource("http://www.jpalace.org");
		assertEquals(HttpResource.class, httpResouce.getDelegate().getClass());
		assertNotNull(httpResouce.getInputStream());
		read(httpResouce);
	}
	
	void read(Resource resource) throws IOException {
		System.out.println("Loading " + resource + " ...");
		resource.writeBytes(System.out);
		System.out.println();
	}
	
	
	@Test
	@Ignore
	public void test() {
		testLoad("file:/META-INF/persistence.xml");
		testLoad("file:/org/einnovator/samples/transfer/dataset1.xml");		
		testLoad("file:web.xml");
		testLoad("classpath:/META-INF/persistence.xml");
		testLoad("classpath:/org/einnovator/samples/transfer/dataset1.xml");
		testLoad("classpath:/web.xml");
		testLoad("classpath:META-INF/persistence.xml");
		testLoad("classpath:org.einnovator/samples/transfer/dataset1.xml");
		testLoad("classpath:web.xml");
	}

	static void testLoad(String name) {
		try {
			File file = new Resource(name).getFile();
			System.out.println("FOUND:" + name + " " + file);
		} catch (FileNotFoundException e) {
			System.out.println("NOT FOUND:" + name);
		}
		
	}

}
