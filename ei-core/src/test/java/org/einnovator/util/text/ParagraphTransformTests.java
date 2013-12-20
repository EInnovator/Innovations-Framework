/**
 * 
 */
package org.einnovator.util.text;

import static org.junit.Assert.*;

import org.einnovator.text.ParagraphTransform;
import org.junit.Before;
import org.junit.Test;

/**
 * A TextTransformsTests.
 *
 * @author Jorge Simão
 */
public class ParagraphTransformTests {

	ParagraphTransform transform;
	
	@Before
	public void setup() {
		transform = new ParagraphTransform();
	}

	@Test
	public void tests() {
		String in = "I belive that.   Is cool.\n";
		String out = "I belive that. Is cool.";
		System.out.println(in.replaceAll("\\s+", " "));
		assertEquals(out, transform.transform(in));
	}
}
