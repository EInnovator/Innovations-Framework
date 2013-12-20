package org.einnovator.log.java;

import org.einnovator.log.Level;
import org.einnovator.log.support.LoggerSupport;


/**
 * A JavaLogger.
 *
 * @author Jorge Simão
 */
public class JavaLogger extends LoggerSupport {
	java.util.logging.Logger log;
	
	//
	// Constructors
	//
	
	public JavaLogger(Class<?> type) {
		this(type.getName());
	}
	
	public JavaLogger(String name) {
		log = java.util.logging.Logger.getLogger(name);
	}

	public JavaLogger() {
	}
	
	//
	// Logger implementation
	//

	@Override
	public void log(Level level, Object source, String meth, Object... objs) {
		log.logp(mapLevel(level), source.getClass().getName(), meth, "", objs);
	}

	@Override
	public void setLevel(Level level) {
		log.setLevel(mapLevel(level));
	}

	@Override
	public Level getLevel() {
		return mapLevel(log.getLevel());
	}

	//
	// Level mapping
	//

	private java.util.logging.Level mapLevel(Level level) {
		return java.util.logging.Level.parse(level.toString());
	}

	private Level mapLevel(java.util.logging.Level level) {
		return Level.valueOf(level.toString());
	}


}
