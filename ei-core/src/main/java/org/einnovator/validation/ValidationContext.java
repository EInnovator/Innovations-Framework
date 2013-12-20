package org.einnovator.validation;

import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.Path;
import org.einnovator.meta.PathCriteria;

public class ValidationContext extends ObjectSupport {

	private Path path;
	
	private PathCriteria projection;
	
	private Errors errors;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code FormatContext}.
	 *
	 */
	public ValidationContext() {
	}

	/**
	 * Create instance of {@code FormatContext}.
	 *
	 * @param path
	 * @param projection
	 */
	public ValidationContext(Path path, PathCriteria projection) {
		this(path, projection, new Errors());
	}
	
	/**
	 * Create instance of {@code FormatContext}.
	 *
	 * @param path
	 * @param projection
	 * @param errors
	 */
	public ValidationContext(Path path, PathCriteria projection, Errors errors) {
		this.path = path;
		this.projection = projection;
		this.errors = errors;
	}
	
	/**
	 * Create instance of {@code FormatContext}.
	 *
	 * @param errors
	 */
	public ValidationContext(Errors errors) {
		this.errors = errors;
	}


	//
	// Getters and Setters
	//
	

	/**
	 * Get the value of property {@code path}.
	 *
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Set the value of property {@code path}.
	 *
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Get the value of property {@code projection}.
	 *
	 * @return the projection
	 */
	public PathCriteria getProjection() {
		return projection;
	}

	/**
	 * Set the value of property {@code projection}.
	 *
	 * @param projection the projection to set
	 */
	public void setProjection(PathCriteria projection) {
		this.projection = projection;
	}

	/**
	 * Get the value of property {@code errors}.
	 *
	 * @return the errors
	 */
	public Errors getErrors() {
		return errors;
	}

	/**
	 * Set the value of property {@code errors}.
	 *
	 * @param errors the errors to set
	 */
	public void setErrors(Errors errors) {
		this.errors = errors;
	}
	
	//
	// Utilities
	//
	
	public ValidationContext addError(Error error) {
		errors.addError(error);
		return this;
	}
	
	public ValidationContext addError(Object invalidValue, String key, String message, Object... params) {
		errors.addError(new Error(invalidValue, path!=null ? path.toString() : null, key, message, params));
		return this;
	}
	
	
}
