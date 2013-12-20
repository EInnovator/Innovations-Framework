package org.einnovator.log.simple;

import java.util.Properties;

import org.einnovator.log.Level;
import org.einnovator.log.Logger;
import org.einnovator.log.LoggerUtil;
import org.einnovator.log.simple.SimpleLogger;
import org.einnovator.log.support.LoggerFactorySupport;
import org.einnovator.util.PropertiesUtil;
import org.einnovator.util.StringUtil;


/**
 * A SimpleLoggerFactory.
 *
 * @author Jorge Simão
 */
public class SimpleLoggerFactory extends LoggerFactorySupport {

	public static final String[] LOG_PROPERTIES = { "log.properties", "META-INF/log.properties"};
	
	private Properties properties;

	//
	// Constructors
	//
	
	/**
	 * Create instance of SimpleLoggerFactory.
	 *
	 */
	public SimpleLoggerFactory() {
		this(LOG_PROPERTIES);
	}
	
	/**
	 * Create instance of {@code SimpleLoggerFactory}.
	 *
	 * @param filename
	 */
	public SimpleLoggerFactory(String[] filenames) {
		properties = new Properties(LoggerUtil.getProperties());
		PropertiesUtil.mergedLoadFromClasspath(properties, filenames);
	}

	/**
	 * Create instance of {@code SimpleLoggerFactory}.
	 *
	 * @param filename
	 */
	public SimpleLoggerFactory(String filename) {
		properties = new Properties(LoggerUtil.getProperties());
		PropertiesUtil.mergedLoadFromClasspath(properties, filename);
	}

	//
	// LoggerFactorySupport overrides
	//
	
	@Override
	public Logger getLoggerInternal(Class<?> type, Object... format) {
		SimpleLogger log = new SimpleLogger(type, format);
		configLogger(type.getName(), log);
		return log;
	}

	@Override
	public Logger getLoggerInternal(String name, Object... format) {
		SimpleLogger log = new SimpleLogger(name, format);
		configLogger(name, log);
		return log;
	}


	// 
	// Configuration from property file
	//
	
	protected void configLogger(String key, SimpleLogger log) {
		if (properties==null) {
			return;
		}
		for (;;) {
			String value = properties.getProperty(key);
			if (!StringUtil.isEmpty(value)) {
				parseAndConfigureLogger(value, log);
				break;
			}
			int i = key.lastIndexOf('.');
			if (i>0) {
				key = key.substring(0, i);
			} else {
				break;
			}
		}
	}
	
	protected void parseAndConfigureLogger(String value, SimpleLogger log) {
		Level level = Level.valueOf(value.toUpperCase());
		log.setLevel(level);
	}
	

}
