/**
 * 
 */
package org.einnovator.format.simple;

import java.util.Locale;

import org.einnovator.format.Formatter;
import org.einnovator.meta.MetaUtil;

/**
 * AA {@code ClassFormatter}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class ClassFormatter implements Formatter<Class<?>>{

	/**
	 * Create instance of {@code ClassFormatter}.
	 *
	 */
	public ClassFormatter() {
	}

	//
	// Formatter implementation
	//
	

	/**
	 * @see org.einnovator.format.Formatter#print(java.lang.Object, java.util.Locale)
	 */
	@Override
	public String print(Class<?> value, Locale locale) {
		return value.getName();
	}

	/**
	 * @see org.einnovator.format.Formatter#parse(java.lang.String, java.util.Locale)
	 */
	@Override
	public Class<?> parse(String text, Locale locale) {
		Class<?> type = MetaUtil.forNamePrimitive(text);
		if (type!=null) {
			return type;
		}
		return MetaUtil.forNameRequired(text);
	}

}
