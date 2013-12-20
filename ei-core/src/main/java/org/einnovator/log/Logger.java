package org.einnovator.log;


public interface Logger {
	
	void setLevel(Level level);
	
	Level getLevel();
	
	void message(Level level, Object source, Object... objs);
	 
	void message(Object source, Object... objs);
	 
	void log(Level level, Object source, String method, Object... objs);
	 	 
	void info(Object source, String method, Object... objs);
	
	void warning(Object source, String method, Object... objs);
	
	void severe(Object source, String method, Object... objs);

	void debug(Object source, String method, Object... objs);

	void trace(Object source, String method, Object... objs);
	
	void _message(Level level, Object... objs);
	 
	void _message(Object... objs);
 
	void _log(Level level, String method, Object... objs);
 	 
	void _info(String method, Object... objs);
	
	void _warning(String method, Object... objs);
	
	void _severe(String method, Object... objs);

	void _debug(String method, Object... objs);
 
}
