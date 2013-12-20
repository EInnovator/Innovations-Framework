/**
 * 
 */
package org.einnovator.log.support;

import java.util.HashMap;
import java.util.Map;

import org.einnovator.log.Logger;
import org.einnovator.log.LoggerFactory;


/**
 * A LoggerFactorySupport.
 *
 * @author Jorge Simão
 */
abstract public class LoggerFactorySupport implements LoggerFactory {

	protected Map<String, Logger> loggerMap = new HashMap<String, Logger>();

	@Override
	public Logger getLogger(Class<?> type, Object... format) {
		Logger log = loggerMap.get(type.getName());
		if (log!=null) {
			return log;
		}
		log = getLoggerInternal(type, format);
		loggerMap.put(type.getName(), log);
		return log;
	}

	abstract protected Logger getLoggerInternal(Class<?> type, Object... format);

	@Override
	public Logger getLogger(String name, Object... format) {
		Logger log = loggerMap.get(name);
		if (log!=null) {
			return log;
		}
		log = getLoggerInternal(name, format);
		loggerMap.put(name, log);
		return log;
	}
	
	abstract protected Logger getLoggerInternal(String name, Object... format);

}
