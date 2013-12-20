package org.einnovator.environment;

import java.util.Locale;

import org.einnovator.convert.ConversionService;
import org.einnovator.convert.ConversionServiceHolder;
import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.MetaClassRegistry;
import org.einnovator.meta.ProjectionOptions;

/**
 * A {@code PropertiesOptions}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class PropertiesOptions extends ObjectSupport implements Cloneable {
	
	/**
	 * {@code ConversionService} to use.
	 */
	private ConversionService conversionService;
	
	/**
	 * {@code MetaClassRegistry} to use. 
	 */
	private MetaClassRegistry registry;

	/**
	 * {@code Locale} to pass to the {@code ConversionService}.
	 */
	private Locale locale;

	/**
	 * Flag specifying if field or property should be ignored if it is <code>null</code> valued.
	 */
	private Boolean ignoreNull;

	/**
	 * Flag specifying if field or property should be ignored if it is empty (Strings, arrays, and collections).
	 */
	private Boolean ignoreEmpty;

	/**
	 * Flag specifying if field or property should be ignored if it is zero (Number sub-types only).
	 */
	private Boolean ignoreZero;	

	/**
	 * Text representation of <code>null</code> value.
	 */
	private String nullValue;

	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type.
	 */
	private Boolean fieldAccess;

	/**
	 * The {@code ProjectionOptions} to use. 
	 */
	private ProjectionOptions projection;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code PropertiesOptions}.
	 *
	 */
	public PropertiesOptions() {
	}
	
	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code conversionService}.
	 *
	 * @return the conversionService
	 */
	public ConversionService getConversionService() {
		return conversionService;
	}

	/**
	 * Set the value of property {@code conversionService}.
	 *
	 * @param conversionService the conversionService to set
	 */
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	/**
	 * Get the value of property {@code registry}.
	 *
	 * @return the registry
	 */
	public MetaClassRegistry getRegistry() {
		return registry;
	}
	
	/**
	 * Set the value of property {@code registry}.
	 *
	 * @param registry the registry to set
	 */
	public void setRegistry(MetaClassRegistry registry) {
		this.registry = registry;
	}

	/**
	 * Get the value of property {@code locale}.
	 *
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Set the value of property {@code locale}.
	 *
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Get the value of property {@code ignoreNull}.
	 *
	 * @return the ignoreNull
	 */
	public Boolean getIgnoreNull() {
		return ignoreNull;
	}

	/**
	 * Set the value of property {@code ignoreNull}.
	 *
	 * @param ignoreNull the ignoreNull to set
	 */
	public void setIgnoreNull(Boolean ignoreNull) {
		this.ignoreNull = ignoreNull;
	}

	/**
	 * Get the value of property {@code ignoreEmpty}.
	 *
	 * @return the ignoreEmpty
	 */
	public Boolean getIgnoreEmpty() {
		return ignoreEmpty;
	}

	/**
	 * Set the value of property {@code ignoreEmpty}.
	 *
	 * @param ignoreEmpty the ignoreEmpty to set
	 */
	public void setIgnoreEmpty(Boolean ignoreEmpty) {
		this.ignoreEmpty = ignoreEmpty;
	}

	/**
	 * Get the value of property {@code ignoreZero}.
	 *
	 * @return the ignoreZero
	 */
	public Boolean getIgnoreZero() {
		return ignoreZero;
	}

	/**
	 * Set the value of property {@code ignoreZero}.
	 *
	 * @param ignoreZero the ignoreZero to set
	 */
	public void setIgnoreZero(Boolean ignoreZero) {
		this.ignoreZero = ignoreZero;
	}

	/**
	 * Get the value of property {@code nullValue}.
	 *
	 * @return the nullValue
	 */
	public String getNullValue() {
		return nullValue;
	}

	/**
	 * Set the value of property {@code nullValue}.
	 *
	 * @param nullValue the nullValue to set
	 */
	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}
	/**
	 * Get the value of property {@code fieldAccess}.
	 *
	 * @return the fieldAccess
	 */
	public boolean isFieldAccess() {
		return fieldAccess;
	}

	/**
	 * Set the value of property {@code fieldAccess}.
	 *
	 * @param fieldAccess the fieldAccess to set
	 */
	public void setFieldAccess(boolean fieldAccess) {
		this.fieldAccess = fieldAccess;
	}

	/**
	 * Get the value of property {@code projection}.
	 *
	 * @return the projection
	 */
	public ProjectionOptions getProjection() {
		return projection;
	}

	/**
	 * Set the value of property {@code projection}.
	 *
	 * @param projection the projection to set
	 */
	public void setProjection(ProjectionOptions projection) {
		this.projection = projection;
	}
	
	
	/**
	 * Check if {@code ProjectionOptions} is <code>null</code> or empty.
	 * 
	 * @return <code>true</code>, if the if {@code ProjectionOptions} is <code>null</code> or empty; 
	 * <code>false</code>, otherwise.
	 */
	public boolean isProjectionEmpty() {
		return projection==null || projection.isEmpty();
	}

	//
	// Object Overrides
	//

	/**
	 * Deep clone this {@code PropertiesOptions} including a clone of the {@code ProjectionOptions} (if any).
	 * 
	 * @return the {@code PropertiesOptions} clone
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PropertiesOptions options = (PropertiesOptions) super.clone();
		if (projection!=null) {
			options.projection = ProjectionOptions.clone(projection);
		}
		return options;
	}

	//
	// Static Utilities
	//

	/**
	 * Deep clone a {@code PropertiesOptions} including a clone of the {@code ProjectionOptions} (if any).
	 * 
	 * @param options the {@code PropertiesOptions} to clone
	 * @return the {@code PropertiesOptions} clone
	 */
	public static PropertiesOptions clone(PropertiesOptions options) {
		try {
			return (PropertiesOptions) options.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Return a new {@code PropertiesOptions} instance with default settings.
	 * 
	 * @return the {@code PropertiesOptions}
	 */
	public static PropertiesOptions newInstance() {
		PropertiesOptions options = new PropertiesOptions();
		options.ignoreNull = true;
		options.ignoreEmpty = false;
		options.ignoreZero = false;
		options.conversionService = ConversionServiceHolder.getConversionService();
		options.fieldAccess = true;
		return options;
	}
	
	//
	// Assignment
	//

	PropertiesOptions assign(Prop prop) {
		ignoreNull = prop.ignoreNull();
		ignoreEmpty = prop.ignoreEmpty();
		ignoreZero = prop.ignoreZero();
		if (this.projection!=null) {
			this.projection.assign(prop.projection());					
		} else if (!ProjectionOptions.isEmpty(prop.projection())) {			
			this.projection = new ProjectionOptions().assign(prop.projection());					
		}
		return this;
	}
	
}
