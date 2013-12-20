/**
 * 
 */
package org.einnovator.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Ignore.
 *
 * @author Jorge Simão
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
	String style() default "";

	TemporalStyle dateStyle() default TemporalStyle.NONE;
	
	TemporalStyle timeStyle() default TemporalStyle.NONE;
	
	String pattern() default "";
	
	String name() default "";
}
