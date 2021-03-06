/**
 * 
 */
package org.einnovator.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * A LoggerConfig.
 *
 * @author Jorge Simão
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoggerConfig {
	Class<?>[] type() default {};
	Level level();
	boolean inherit() default true;
}
