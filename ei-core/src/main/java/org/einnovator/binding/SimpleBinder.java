package org.einnovator.binding;

import java.util.Locale;

import org.einnovator.convert.ConversionService;
import org.einnovator.meta.MetaClassResolver;

/**
 * A {@code Binder} that extract parameters values from a {@code BindingContext}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class SimpleBinder extends AbstractBinder {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code SimpleBinder}.
	 *
	 */
	public SimpleBinder() {
	}
		
	/**
	 * Create instance of {@code SimpleBinder}.
	 *
	 * @param conversionService
	 */
	public SimpleBinder(ConversionService conversionService) {
		super(conversionService);
	}

	/**
	 * Create instance of {@code SimpleBinder}.
	 *
	 * @param metaClassResolver
	 * @param conversionService
	 */
	public SimpleBinder(MetaClassResolver metaClassResolver,
			ConversionService conversionService) {
		super(metaClassResolver, conversionService);
	}

	/**
	 * Create instance of {@code SimpleBinder}.
	 *
	 * @param metaClassResolver
	 */
	public SimpleBinder(MetaClassResolver metaClassResolver) {
		super(metaClassResolver);
	}
	
	//
	// AbstractBinder Implementation
	//


	/**
	 * @see org.einnovator.binding.AbstractBinder#getParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	protected String getParameter(String name, Object context) {
		return ((BindingContext)context).getParameter(name);
	}

	/**
	 * @see org.einnovator.binding.AbstractBinder#getLocale(java.lang.Object)
	 */
	@Override
	protected Locale getLocale(Object context) {
		return ((BindingContext)context).getLocale();
	}

}
