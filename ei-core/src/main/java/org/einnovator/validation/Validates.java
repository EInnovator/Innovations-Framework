/**
 * 
 */
package org.einnovator.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that specifies a type that validates other type.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Validates {
	
	/**
	 * The types that are validated (same as {@link #type()}).
	 */
	Class<?>[] value() default Validates.class;

	/**
	 * The types that are validated (same as {@link #value()}).
	 */
	Class<?>[] type() default Validates.class;
}
