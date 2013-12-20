/**
 * 
 */
package org.einnovator.log;


/**
 * A LoggerConfigUtil.
 *
 * @author Jorge Simão
 */
public class LoggerConfigUtil {

	public static void configLoggersForType(Class<?> type) {		
		Class<?> type2 = type;
		while (type2!=null) {
			LoggerConfig config = type2.getAnnotation(LoggerConfig.class);
			if (config!=null) {
				configLoggers(config);
				if (!config.inherit()) {
					break;
				}
			}
			type2 = type2.getSuperclass();				
		}
		
		type2 = type;
		while (type2!=null) {
			LoggerConfigs configs = type2.getAnnotation(LoggerConfigs.class);
			if (configs!=null) {
				configLoggers(configs);
				if (!configs.inherit()) {
					break;
				}
			}
			type2 = type2.getSuperclass();
		}
	}

	private static void configLoggers(LoggerConfigs configs) {
		for (LoggerConfig config: configs.value()) {
			configLoggers(config);
		}
	}

	private static void configLoggers(LoggerConfig config) {
		Class<?>[] types = config.type();
		for (Class<?> type: types) {
			Logger logger = LoggerUtil.getLogger(type);
			logger.setLevel(config.level());
		}
	}
	
}
