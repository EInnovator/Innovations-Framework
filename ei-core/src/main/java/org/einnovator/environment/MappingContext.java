package org.einnovator.environment;

import java.util.List;

import org.einnovator.format.ObjectSupport;
import org.einnovator.meta.Path;
import org.einnovator.meta.PathCriteria;
import org.einnovator.meta.Property;

public class MappingContext extends ObjectSupport {

	private Path path;
	
	private PathCriteria projection;
	
	private List<Object> visited;
	
	private List<Property<?>> initializedProperties;
	 
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code FormatContext}.
	 *
	 */
	public MappingContext() {
	}

	/**
	 * Create instance of {@code FormatContext}.
	 *
	 * @param path
	 * @param projection
	 */
	public MappingContext(Path path, PathCriteria projection) {
		this.path = path;
		this.projection = projection;
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
	 * Get the value of property {@code visited}.
	 *
	 * @return the visited
	 */
	public List<Object> getVisited() {
		return visited;
	}

	/**
	 * Set the value of property {@code visited}.
	 *
	 * @param visited the visited to set
	 */
	public void setVisited(List<Object> visited) {
		this.visited = visited;
	}
	
	/**
	 * Get the value of property {@code initializedProperties}.
	 *
	 * @return the initializedProperties
	 */
	public List<Property<?>> getInitializedProperties() {
		return initializedProperties;
	}

	/**
	 * Set the value of property {@code initializedProperties}.
	 *
	 * @param initializedProperties the initializedProperties to set
	 */
	public void setInitializedProperties(List<Property<?>> initializedProperties) {
		this.initializedProperties = initializedProperties;
	}	

	//
	// Fluent API
	//

	public MappingContext path(Path path) {
		this.path = path;
		return this;
	}

	public MappingContext projection(PathCriteria projection) {
		this.projection = projection;
		return this;
	}


}
