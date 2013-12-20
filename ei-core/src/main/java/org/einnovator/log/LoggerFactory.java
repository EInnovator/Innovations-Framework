package org.einnovator.log;

/**
 * A LoggerFactory.
 *
 * @author Jorge Simão
 */
public interface LoggerFactory {
	Logger getLogger(Class<?> type, Object... format);

	Logger getLogger(String name, Object... format);
}
