/**
 * 
 */
package org.einnovator.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.einnovator.meta.Projection;
import org.einnovator.meta.Ref;

/**
 * Annotation that specifies a type that validates other type.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER})
@Inherited
public @interface Valid {
	
	/**
	 * Specifies if validation should be applied.
	 */
	boolean value() default true;

	/**
	 * Specified if validation should not be applied.
	 * (opposite of {@link #value()}).
	 */
	boolean ignore() default false;

	/**
	 * Specified if validation should be done recursively on complex objects.
	 */
	boolean recurse() default true;

	/**
	 * Specified cycles in the object should be checked when recursing on complex object.
	 */
	boolean checkCycles() default true;

	/**
	 * Projection specifying which fields/properties should be validated.
	 * 
	 * @return the {@code Projection}
	 */
	Projection projection() default @Projection;

	/**
	 * Reference to a validator.
	 */
	Ref validator() default @Ref;
	
	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type.
	 */
	boolean fieldAccess() default true;

}
