/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A grouping of properties.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Group {

	String name() default "";
	
	Class<?> type() default Object.class;
}
