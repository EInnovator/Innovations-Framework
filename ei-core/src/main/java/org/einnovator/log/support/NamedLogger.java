package org.einnovator.log.support;

import org.einnovator.log.Level;


/**
 * A NamedLogger.
 *
 * @author Jorge Simão
 */
abstract public class NamedLogger extends LoggerSupport {
	protected Level level = Level.WARNING;

	protected String name;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code NamedLogger}.
	 *
	 */
	public NamedLogger() {
	}
	
	/**
	 * Create instance of {@code NamedLogger}.
	 *
	 * @param type
	 */
	public NamedLogger(Class<?> type) {
		this.name = type.getName();
	}
	
	/**
	 * Create instance of {@code NamedLogger}.
	 *
	 * @param name
	 */
	public NamedLogger(String name) {
		this.name = name;
	}

	/**
	 * Create instance of {@code NamedLogger}.
	 *
	 * @param type
	 * @param level
	 */
	public NamedLogger(Class<?> type, Level level) {
		this.name = type.getName();
		this.level = level;
	}
	
	/**
	 * Create instance of {@code NamedLogger}.
	 *
	 * @param name
	 * @param level
	 */
	public NamedLogger(String name, Level level) {
		this.name = name;
		this.level = level;
	}

	//
	// Logger implementation
	//
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	//
	// Object overrides
	//
	
	public String toString() {
		return name + "[" + level + "]";
	}
	
}
