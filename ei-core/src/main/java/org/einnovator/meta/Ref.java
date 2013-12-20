/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A references to a bean or prototype class instance.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Ref {

	/**
	 * Name of the bean.
	 */
	String name() default "";
	
	/**
	 * Type of the bean.
	 * 
	 * (same as {@link #type})
	 */
	Class<?> value() default Object.class;

	/**
	 * Type of the bean.
	 * 
	 * (same as {@link #value})
	 */
	Class<?> type() default Object.class;

	/**
	 * The default type of a prototype object if bean lookup fails (no-args constructor is called).
	 * 
	 * (same as {@link #value})
	 */
	Class<?> defaultType() default Object.class;

	/**
	 * Flag specifying if the bean should be looked up in some context if available
	 * (defaults to {@code true} if {@link #name()} is specified).
	 */
	boolean lookup() default false;

	/**
	 * Qualifier annotations for the bean.
	 */
	Class<? extends Annotation>[] qualifiers() default {};
}
