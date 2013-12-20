package org.einnovator.meta;

import org.einnovator.convert.ConversionService;
import org.einnovator.convert.ConversionServiceHolder;
import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.criteria.TypeCriteria;

/**
 * Options for (intra/inter-type) deep auto-assignments.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class AssignOptions extends ObjectSupport implements Cloneable {

	/**
	 * Specifies if assignment created copies of values.
	 */
	private Boolean deep;

	/**
	 * Specifies if assignment should overwrite non-<code>null</code> value.
	 */
	private Boolean overwrite;

	/**
	 * Specifies if assignment should overwrite with <code>null</code> value.
	 */
	private Boolean overwriteWithNull;

	/**
	 * Specifies if assignment should overwrite with <code>null</code> value.
	 */
	private Boolean overwriteWithEmpty;

	/**
	 * Specifies if assignment is required (intra-type assignment only).
	 */
	private Boolean required;

	/**
	 * Specifies if existing value should be merged with assigned value.
	 */
	private Boolean merge;

	private TypeCriteria simpleTypeCriteria;
	 
	private ConversionService conversionService;
	
	private ProjectionOptions projection;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code AssignOptions}.
	 *
	 */
	public AssignOptions() {
	}

	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code overwrite}.
	 *
	 * @return the overwrite
	 */
	public Boolean getOverwrite() {
		return overwrite;
	}

	/**
	 * Set the value of property {@code overwrite}.
	 *
	 * @param overwrite the overwrite to set
	 */
	public void setOverwrite(Boolean overwrite) {
		this.overwrite = overwrite;
	}

	/**
	 * Get the value of property {@code overwriteWithNull}.
	 *
	 * @return the overwriteWithNull
	 */
	public Boolean getOverwriteWithNull() {
		return overwriteWithNull;
	}

	/**
	 * Set the value of property {@code overwriteWithNull}.
	 *
	 * @param overwriteWithNull the overwriteWithNull to set
	 */
	public void setOverwriteWithNull(Boolean overwriteWithNull) {
		this.overwriteWithNull = overwriteWithNull;
	}
	
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
	 * Get the value of property {@code required}.
	 *
	 * @return the required
	 */
	public Boolean getRequired() {
		return required;
	}

	/**
	 * Set the value of property {@code required}.
	 *
	 * @param required the required to set
	 */
	public void setRequired(Boolean required) {
		this.required = required;
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
	 * Get the value of property {@code overwriteWithEmpty}.
	 *
	 * @return the overwriteWithEmpty
	 */
	public Boolean getOverwriteWithEmpty() {
		return overwriteWithEmpty;
	}

	/**
	 * Set the value of property {@code overwriteWithEmpty}.
	 *
	 * @param overwriteWithEmpty the overwriteWithEmpty to set
	 */
	public void setOverwriteWithEmpty(Boolean overwriteWithEmpty) {
		this.overwriteWithEmpty = overwriteWithEmpty;
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
	 * Get the value of property {@code merge}.
	 *
	 * @return the merge
	 */
	public Boolean getMerge() {
		return merge;
	}

	/**
	 * Set the value of property {@code merge}.
	 *
	 * @param merge the merge to set
	 */
	public void setMerge(Boolean merge) {
		this.merge = merge;
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
	
	
	public AssignOptions deep(boolean deep) {
		this.deep = deep;
		return this;
	}

	public AssignOptions overwrite(boolean overwrite) {
		this.overwrite = overwrite;
		return this;
	}

	public AssignOptions overwriteWithNull(boolean overwriteWithNull) {
		this.overwriteWithNull = overwriteWithNull;
		return this;
	}

	public AssignOptions overwriteWithEmpty(boolean overwriteWithEmpty) {
		this.overwriteWithEmpty = overwriteWithEmpty;
		return this;
	}

	public AssignOptions required(boolean required) {
		this.required = required;
		return this;
	}

	public AssignOptions simpleTypeCriteria(TypeCriteria typeCriteria) {
		this.simpleTypeCriteria = typeCriteria;
		return this;
	}

	public AssignOptions merge(boolean merge) {
		this.merge = merge;
		return this;
	}

	public AssignOptions conversionService(ConversionService conversionService) {
		this.conversionService  = conversionService;
		return this;
	}

	public AssignOptions projection(ProjectionOptions projection) {
		this.projection = projection;
		return this;
	}
	
	//
	// Object overrides
	//
	
	/**
	 * Deep clone this {@code AssignOptions}.
	 * 
	 * Deep cloned properties include {@code projection}.
	 * 
	 * @return the {@code AssignOptions} clone
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		AssignOptions options = (AssignOptions) super.clone();
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
	public static AssignOptions clone(AssignOptions options) {
		try {
			return (AssignOptions) options.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Return a new {@code AssignOptions} instance with default settings.
	 * 
	 * @return the {@code AssignOptions}
	 */
	public static AssignOptions newInstance() {
		AssignOptions options = new AssignOptions();
		options.deep = true;
		options.overwrite = true;
		options.overwriteWithNull = true;
		options.overwriteWithEmpty = true;
		options.required =false;
		options.simpleTypeCriteria = null;
		options.conversionService = ConversionServiceHolder.getConversionService();
		return options;
	}

	private static AssignOptions instance;
	
	public static AssignOptions emptyInstance() {
		if (instance==null) {
			instance = new AssignOptions();
		}
		return instance;
	}

	AssignOptions assign(Assign assign) {
		deep = assign.deep();
		overwrite = assign.overwrite();
		overwriteWithNull = assign.overwriteWithNull();
		overwriteWithEmpty = assign.overwriteWithEmpty();
		merge = assign.merge();
		required = assign.required();
		return this;
	}
	
}
