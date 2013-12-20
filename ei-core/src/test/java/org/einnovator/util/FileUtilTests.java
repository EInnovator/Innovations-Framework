package org.einnovator.util;

import org.junit.Test;

import static org.einnovator.util.FileUtil.*;
import static org.junit.Assert.*;

public class FileUtilTests {

	@Test
	public void replaceFileNameTest() {
		assertEquals("\\path\\to\\dir\\filename.TXT", replaceExtention("\\path\\to\\dir\\filename.txt", ".TXT"));
		assertEquals("/path/to/dir/filename.TXT", replaceExtention("/path/to/dir/filename.txt", ".TXT"));
		assertEquals("\\path\\to\\dir\\FILENAME.TXT", replaceFilename("\\path\\to\\dir\\filename.txt", "FILENAME.TXT"));
		assertEquals("/path/to/dir/FILENAME.TXT", replaceFilename("/path/to/dir/filename.txt", "FILENAME.TXT", "/"));

	}
}
