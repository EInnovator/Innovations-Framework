package org.einnovator;

import static org.junit.Assert.*;

import org.einnovator.text.TextTransform;
import org.einnovator.text.transforms.CamelCaseToWordsTextTransform;
import org.junit.Test;

public class TransformTests {

	@Test
	public void camelTest() {
		TextTransform transform = new CamelCaseToWordsTextTransform();
		assertEquals("full Name", transform.transform("fullName"));
	}
}
