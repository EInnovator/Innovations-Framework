package org.einnovator.i18n;

import java.util.Locale;

public interface MessageResolver {

	String getMessage(String key, Locale locale, String defaultValue);
}
