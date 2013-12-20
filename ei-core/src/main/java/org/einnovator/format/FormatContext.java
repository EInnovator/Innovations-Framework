package org.einnovator.format;

import java.util.List;
import java.util.Locale;

import org.einnovator.format.ObjectSupport;
import org.einnovator.log.Level;
import org.einnovator.meta.Path;
import org.einnovator.meta.PathCriteria;

/**
 * Context of formatting.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FormatContext extends ObjectSupport {

	private Path path;
	
	private PathCriteria projection;
	
	private Locale locale;
	
	private Level level;
	
	private List<Object> visited;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code FormatContext}.
	 *
	 */
	public FormatContext() {
	}

	/**
	 * Create instance of {@code FormatContext}.
	 *
	 * @param path
	 * @param projection
	 */
	public FormatContext(Path path, PathCriteria projection) {
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
	 * Get the value of property {@code level}.
	 *
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Set the value of property {@code level}.
	 *
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
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
	
	//
	// Fluent API
	//

	public FormatContext locale(Locale locale) {
		this.locale = locale;
		return this;
	}

	public FormatContext projection(PathCriteria projection) {
		this.projection = projection;
		return this;
	}

	public FormatContext level(Level level) {
		this.level = level;
		return this;
	}
	
}
