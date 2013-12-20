package org.einnovator.i18n;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class ResourceBundleMessageResolverTests {

	@Test
	public void test() {//org.einnovator.i18n.
		MessageResolver resolver = new ResourceBundleMessageResolver("org/einnovator/i18n/messages");
		assertEquals("ABC", resolver.getMessage("abc", Locale.getDefault(), "ABC"));
		assertEquals("Test Message", resolver.getMessage("test.message", Locale.getDefault(), null));		
		assertEquals("Mensagem de Teste", resolver.getMessage("test.message", new Locale("pt"), null));
	}
}
