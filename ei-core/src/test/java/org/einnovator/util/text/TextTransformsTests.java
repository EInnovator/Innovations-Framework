/**
 * 
 */
package org.einnovator.util.text;

import static org.junit.Assert.*;

import org.einnovator.text.TextTransform;
import org.einnovator.text.TextTransforms;
import org.einnovator.text.transforms.XmlAttributeToCamelCaseTextTransform;
import org.junit.Test;

/**
 * A TextTransformsTests.
 *
 * @author Jorge Simão
 */
public class TextTransformsTests {


	@Test
	public void xmlToCamelCaseTests() {
		TextTransform transform = TextTransforms.getTransform(TextTransforms.Transform.XML_ATTRIBUTE_TO_CAMEL_CASE);
		transform = XmlAttributeToCamelCaseTextTransform.getInstance();
		assertEquals("abcDef", transform.transform("abc-def"));
	}
}
