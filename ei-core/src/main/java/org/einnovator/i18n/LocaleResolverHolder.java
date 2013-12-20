package org.einnovator.i18n;

import java.util.Locale;

import org.einnovator.meta.ObjectRef;
import org.einnovator.meta.ObjectResolverHolder;


/**
 * A holder for a singleton {@code LocaleResolver}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 */
public class LocaleResolverHolder {

	//
	// Static utilities
	//
	
	public static LocaleResolver instance;
	
	/**
	 * Get singleton instance of a {@code LocaleResolver}.
	 * 
	 * This method does not create the singleton. 
	 * Method {@link #getRequiredInstance()} or {@link #setInstance(LocaleResolver)} 
	 * should be called before hand.
	 * 
	 * To return a non-<code>null</code> value required
	 * @return the {@code LocaleResolver} instance; or <code>null</code>, if none was set.
	 * @see #getRequiredInstance()
	 */
	public static LocaleResolver getInstance() {
		return instance;
	}
	
	
	/**
	 * Get singleton instance of a {@code LocaleResolver}.
	 * 
	 * If none was set or created before, a {@code DefaultLocaleResolver} is created and returned.

	 * @return the {@code LocaleResolver} instance
	 * @see #setInstance(LocaleResolver)
	 */
	public static LocaleResolver getRequiredInstance() {
		if (instance==null) {
			instance =  ObjectResolverHolder.getRequiredInstance().getObject(
					new ObjectRef<LocaleResolver>(LocaleResolver.class, DefaultLocaleResolver.class));
		}
		return instance;
	}

	/**
	 * A {@code LocaleResolver} that returns the default {@code Locale}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class DefaultLocaleResolver implements LocaleResolver {

		/**
		 * @see org.einnovator.i18n.LocaleResolver#getLocale()
		 */
		@Override
		public Locale getLocale() {
			return Locale.getDefault();
		}
	}
	
	/**
	 * Get the singleton instance of a {@code LocaleResolver}.
	 * 
	 * @param instance the {@code LocaleResolver} instance
	 * @see #getInstance()
	 * @see #getRequiredInstance()
	 */
	public static void setInstance(LocaleResolver instance) {
		LocaleResolverHolder.instance = instance;
	}

}
