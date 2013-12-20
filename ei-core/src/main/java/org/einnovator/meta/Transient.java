/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ignore field, property, or type in reflective recursion.
 *
 * @author Jorge Simão
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface Transient {
	String value() default "";
	boolean ignore() default false;
	boolean ignoreNull() default true;
	boolean ignoreEmpty() default true;	
	String[] fieldInclude() default {};
	String[] fieldExclude() default {};	
}
