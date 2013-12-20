package org.einnovator.log;

import java.util.Properties;

import org.einnovator.log.simple.SimpleLoggerFactory;

public class LoggerUtil {
	
	public static LoggerFactory loggerFactory;

	public static Properties properties;
	
	public static LoggerFactory getLoggerFactory() {
		if (loggerFactory==null) {
			loggerFactory = new SimpleLoggerFactory();
		}
		return loggerFactory;
	}

	public static void setLoggerFactory(LoggerFactory loggerFactory) {
		LoggerUtil.loggerFactory = loggerFactory;
	}

	public static Logger getLogger(Class<?> type, Object... format) {
		return getLoggerFactory().getLogger(type, format);
	}

	public static Logger getLogger(Class<?> type) {
		return getLoggerFactory().getLogger(type);
	}

	public static Logger getLogger(Class<?> type, Level level) {
		Logger logger = getLoggerFactory().getLogger(type);
		logger.setLevel(level);
		return logger;
	}

	public static Properties getProperties() {
		if (properties==null) {
			properties = new Properties();
		}
		return properties;
	}
	
	public static void setLevel(Class<?> type, Level level) {
		setLevel(type.getName(), level);
	}
	
	public static void setLevel(String name, Level level) {
		getProperties().setProperty(name, level.toString());
	}


	public static void setLevel(Level level, Class<?>... types) {
		for (Class<?> type: types) {
			setLevel(type, level);
		}
	}
	
	public static void setLevel(Level level, String... names) {
		for (String name: names) {
			setLevel(name, level);
		}
	}
	
	public static void setLevel(Level level, Object... objs) {
		for (Object obj: objs) {
			setLevel(obj.getClass(), level);
		}
	}

}
