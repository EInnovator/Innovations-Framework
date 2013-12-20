package org.einnovator.validation;

import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.ProjectionOptions;

/**
 * Validation Options.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ValidationOptions extends ObjectSupport implements Cloneable {
	
	
	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type.
	 */
	private Boolean fieldAccess;

	/**
	 * Recurse validation on complex object.
	 */
	private Boolean recurse;

	/**
	 * Flag that specified if cycles should be checked.
	 */
	private Boolean checkCycles;

	/**
	 * Flag that specifies whether path projections should supported.
	 */
	private Boolean paths;
	
	/**
	 * Flag that specifies whether path projections should be compiled/optimized.
	 */
	private Boolean compilePaths;

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
	public ValidationOptions() {
	}
	
	//
	// Getters and Setters
	//
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
	 * Get the value of property {@code fieldAccess}.
	 *
	 * @return the fieldAccess
	 */
	public Boolean getFieldAccess() {
		return fieldAccess;
	}

	/**
	 * Set the value of property {@code fieldAccess}.
	 *
	 * @param fieldAccess the fieldAccess to set
	 * @return this {@code ValidationOptions}
	 */
	public void setFieldAccess(Boolean fieldAccess) {
		this.fieldAccess = fieldAccess;
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
	
	/**
	 * Get the value of property {@code checkCycles}.
	 *
	 * @return the checkCycles
	 */
	public Boolean getCheckCycles() {
		return checkCycles;
	}

	/**
	 * Set the value of property {@code checkCycles}.
	 *
	 * @param checkCycles the checkCycles to set
	 */
	public void setCheckCycles(Boolean checkCycles) {
		this.checkCycles = checkCycles;
	}
	
	/**
	 * Get the value of property {@code recurse}.
	 *
	 * @return the recurse
	 */
	public Boolean getRecurse() {
		return recurse;
	}

	/**
	 * Set the value of property {@code recurse}.
	 *
	 * @param recurse the recurse to set
	 */
	public void setRecurse(Boolean recurse) {
		this.recurse = recurse;
	}


	/**
	 * Get the value of property {@code paths}.
	 *
	 * @return the paths
	 */
	public Boolean getPaths() {
		return paths;
	}

	/**
	 * Set the value of property {@code paths}.
	 *
	 * @param paths the paths to set
	 */
	public void setPaths(Boolean paths) {
		this.paths = paths;
	}

	/**
	 * Get the value of property {@code compilePaths}.
	 *
	 * @return the compilePaths
	 */
	public Boolean getCompilePaths() {
		return compilePaths;
	}

	/**
	 * Set the value of property {@code compilePaths}.
	 *
	 * @param compilePaths the compilePaths to set
	 */
	public void setCompilePaths(Boolean compilePaths) {
		this.compilePaths = compilePaths;
	}

	
	//
	// Fluent API
	//

	/**
	 * Set the value of property {@code fieldAccess}.
	 *
	 * @param fieldAccess the fieldAccess to 
	 * @return this {@code ValidationOptions}
	 */
	public ValidationOptions fieldAccess(boolean fieldAccess) {
		this.fieldAccess = fieldAccess;
		return this;
	}

	/**
	 * Set the value of property for the {@code ProjectionOptions}.
	 *
	 * @param projection the projection to 
	 * @return this {@code ValidationOptions}
	 */
	public ValidationOptions projection(ProjectionOptions projection) {
		this.projection = projection;
		return this;
	}
	
	/**
	 * Set the value of property for the {@code checkCycles}.
	 *
	 * @param checkCycles the value of checkCycles
	 * @return this {@code ValidationOptions}
	 */
	public ValidationOptions checkCycles(boolean checkCycles) {
		this.checkCycles = checkCycles;
		return this;
	}

	/**
	 * Set the value of property for the {@code recurse}.
	 *
	 * @param recurse the value of recurse
	 * @return this {@code ValidationOptions}
	 */
	public ValidationOptions recurse(boolean recurse) {
		this.recurse = recurse;
		return this;
	}
	
	/**
	 * Set the value of property {@code paths}.
	 *
	 * @param paths the value of paths
	 * @return this {@code FormatOptions}
	 */
	public ValidationOptions paths(boolean paths) {
		this.paths = paths;
		return this;
	}

	/**
	 * Set the value of property {@code compilePaths}.
	 *
	 * @param paths the value of compilePaths
	 * @return this {@code FormatOptions}
	 */
	public ValidationOptions compilePaths(boolean compilePaths) {
		this.compilePaths = compilePaths;
		return this;
	}
	
	//
	// Object Overrides
	//

	/**
	 * Deep clone this {@code ValidationOptions}.
	 * 
	 * Deep cloned properties include {@code projection}.
	 * 
	 * @return the {@code ValidationOptions} clone
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		ValidationOptions options = (ValidationOptions) super.clone();
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
	public static ValidationOptions clone(ValidationOptions options) {
		try {
			return (ValidationOptions) options.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Return a new {@code ValidationOptions} instance with default settings.
	 * 
	 * @return the {@code ValidationOptions}
	 */
	public static ValidationOptions newInstance() {
		ValidationOptions options = new ValidationOptions();		
		options.fieldAccess = true;
		options.checkCycles = true;
		options.recurse = true;
		options.paths = true;
		options.compilePaths = true;
		return options;
	}
	
	//
	// Assignment
	//
	
	ValidationOptions assign(Valid valid) {
		recurse = valid.recurse();
		checkCycles = valid.checkCycles();
		if (this.projection!=null) {
			this.projection.assign(valid.projection());					
		} else if (!ProjectionOptions.isEmpty(valid.projection())) {			
			this.projection = new ProjectionOptions().assign(valid.projection());					
		}
		return this;
	}
	
	
}
