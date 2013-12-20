/**
 * 
 */
package org.einnovator.i18n;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define a i18n message.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Message {

	/**
	 * The default value for the message.
	 */
	String value() default "";
	
	/**
	 * The key for the message.
	 */
	String key() default "";

	/**
	 * PThe key for the message.
	 */
	Class<?> payload() default Object.class;	
}
