package org.einnovator.log.support;

import org.einnovator.log.Level;
import org.einnovator.log.Logger;


abstract public class LoggerSupport implements Logger {
	
	public LoggerSupport() {
	}

	@Override
	abstract public void log(Level level, Object source, String method, Object... objs);

	 
	public void message(Level level, Object source, Object... objs) {
		log(level, source, null, objs);
	}
	 
	public void message(Object source, Object... objs) {
		log(Level.INFO, source, null, objs);
	}
 
	
	public void info(Object source, String method, Object... objs) {
		log(Level.INFO, source, method, objs);
	}

	@Override
	public void warning(Object source, String method, Object... objs) {
		log(Level.WARNING, source, method, objs);
	}
	
	@Override
	public void severe(Object source, String method, Object... objs) {
		log(Level.SEVERE, source, method, objs);
	}
	
	@Override
	public void debug(Object source, String method, Object... objs) {
		log(Level.DEBUG, source, method, objs);
	}

	@Override
	public void trace(Object source, String method, Object... objs) {
		log(Level.TRACE, source, method, objs);
	}

	public void _message(Level level, Object... objs) {
		log(level, null, null, objs);
	}
	 
	public void _message(Object... objs) {
		log(Level.INFO, null, null, objs);
	}
	 
	public void _log(Level level, String method, Object... objs) {
		log(level, null, method, objs);
	}

	public void _info(String method, Object... objs) {	 
		log(Level.INFO, null, method, objs);
	}
	
	public void _warning(String method, Object... objs) {
		log(Level.WARNING, null, method, objs);		 
	}
	
	public void _severe(String method, Object... objs) {
		log(Level.SEVERE, null, method, objs);
	}

	public void _debug(String method, Object... objs) {
		log(Level.DEBUG, null, method, objs);		 
	}
	
}
