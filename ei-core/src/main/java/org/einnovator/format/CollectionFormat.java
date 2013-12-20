/**
 * 
 */
package org.einnovator.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation with options for formating a collection or array.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface CollectionFormat {
	
	/**
	 * The marker/separator before first element of a collection or array.
	 */
	String beginMarker() default "";

	/**
	 * The marker/separator after last element of a collection or array.
	 */
	String endMarker() default "";

	/**
	 * The separator between elements in the collection or array.
	 */
	String elementSeparator() default "";
	
	/**
	 * The separator between keys and values (Maps only).
	 */
	String keyValueSeparator() default "";

}
