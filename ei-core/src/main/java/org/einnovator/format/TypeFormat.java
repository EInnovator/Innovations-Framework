/**
 * 
 */
package org.einnovator.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.einnovator.meta.Ref;

/**
 * Annotation with options to format an object fields or properties and type signature.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface TypeFormat {

	/**
	 * Flag specifying if field or property should be ignored in printing/formatting.
	 * Opposite of {@link #ignore()}.
	 */
	boolean value() default true;
	
	/**
	 * Label for field or property.
	 * 
	 */
	String label() default "";
	
	/**
	 * Flag specifying if field or property should be ignored in printing/formatting.
	 * Opposite of {@link #value()}.
	 */
	boolean ignore() default false;

	/**
	 * Specifies if value type name should be printed in a qualified.
	 */
	boolean qualified() default false;
	
	/**
	 * Specified if hash value should be printed in formatting non-simple objects.
	 */
	boolean hash() default false;
	
	/**
	 * The marker/separator between object type (and hash) and first field.
	 */
	String beginMarker() default "";

	/**
	 * The marker/separator after last field or property.
	 */
	String endMarker() default "";

	/**
	 * The separator between label and value.
	 */
	String valueSeparator() default "";
	
	/**
	 * The separator between field in formatting non-simple objects.
	 */
	String fieldSeparator() default "";

	/**
	 * The transform to apply to the formatted text.
	 */
	Ref transform() default @Ref;
}
