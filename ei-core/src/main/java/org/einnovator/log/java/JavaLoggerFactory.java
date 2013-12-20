package org.einnovator.log.java;

import org.einnovator.log.Logger;
import org.einnovator.log.support.LoggerFactorySupport;

/**
 * A JavaLoggerFactory.
 *
 * @author Jorge Simão
 */
public class JavaLoggerFactory extends LoggerFactorySupport {

	@Override
	public Logger getLoggerInternal(Class<?> type, Object... format) {
		return new JavaLogger(type);
	}

	@Override
	public Logger getLoggerInternal(String name, Object... format) {
		return new JavaLogger(name);
	}

}
