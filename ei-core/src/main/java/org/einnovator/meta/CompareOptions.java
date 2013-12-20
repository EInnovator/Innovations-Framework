package org.einnovator.meta;

import org.einnovator.convert.ConversionService;
import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.criteria.TypeCriteria;

/**
 * Options for (intra/inter-type) deep comparison.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class CompareOptions extends ObjectSupport implements Cloneable {

	/**
	 * Specifies if comparison should recurse of non-simple object..
	 */
	private Boolean deep;

	private TypeCriteria simpleTypeCriteria;
	 
	private ConversionService conversionService;
	
	private ProjectionOptions projection;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code CompareOptions}.
	 *
	 */
	public CompareOptions() {
	}

	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code deep}.
	 *
	 * @return the deep
	 */
	public Boolean getDeep() {
		return deep;
	}

	/**
	 * Set the value of property {@code deep}.
	 *
	 * @param deep the deep to set
	 */
	public void setDeep(Boolean deep) {
		this.deep = deep;
	}
	
	/**
	 * Get the value of property {@code simpleTypeCriteria}.
	 *
	 * @return the simpleTypeCriteria
	 */
	public TypeCriteria getSimpleTypeCriteria() {
		return simpleTypeCriteria;
	}

	/**
	 * Set the value of property {@code simpleTypeCriteria}.
	 *
	 * @param simpleTypeCriteria the simpleTypeCriteria to set
	 */
	public void setSimpleTypeCriteria(TypeCriteria simpleTypeCriteria) {
		this.simpleTypeCriteria = simpleTypeCriteria;
	}

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
		return ProjectionOptions.isEmpty(projection);
	}

	//
	// Fluent API
	//
	
	public CompareOptions deep(boolean deep) {
		this.deep = deep;
		return this;
	}

	public CompareOptions simpleTypeCriteria(TypeCriteria typeCriteria) {
		this.simpleTypeCriteria = typeCriteria;
		return this;
	}

	public CompareOptions conversionService(ConversionService conversionService) {
		this.conversionService  = conversionService;
		return this;
	}

	public CompareOptions projection(ProjectionOptions projection) {
		this.projection = projection;
		return this;
	}
	

	//
	// Object overrides
	//
	
	/**
	 * Deep clone this {@code CompareOptions}.
	 * 
	 * Deep cloned properties include {@code projection}.
	 * 
	 * @return the {@code CompareOptions} clone
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		CompareOptions options = (CompareOptions) super.clone();
		if (projection!=null) {
			options.projection = ProjectionOptions.clone(projection);
		}
		return options;
	}
	

	//
	// Static Utilities
	//

	/**
	 * Deep clone a {@code PropertiesOptions}.
	 * 
	 * Delegates to {@link #clone()}.
	 * 
	 * @param options the {@code PropertiesOptions} to clone
	 * @return the {@code PropertiesOptions} clone
	 */
	public static CompareOptions clone(CompareOptions options) {
		try {
			return (CompareOptions) options.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	//
	// Static Utilities
	//
	
	/**
	 * Return a new {@code CompareOptions} instance with default settings.
	 * 
	 * @return the {@code CompareOptions}
	 */
	public static CompareOptions newInstance() {
		return new CompareOptions().deep(true);
	}
	
		
	CompareOptions compare(Compare compare) {
		return this;
	}
	

}
