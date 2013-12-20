package org.einnovator.binding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.einnovator.meta.Projection;
import org.einnovator.text.TextTransform;

/**
 * AA {@code Bind}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Bind {

	/**
	 * Specifies if binding should be performed.
	 */
	boolean value() default true;
	
	/**
	 * Prop of parameter to get value for binding.
	 */
	String name() default "";


	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type.
	 */
	boolean fieldAccess() default true;

	/**
	 * Specifies if validation should be performed.
	 */
	boolean validate() default true;

	/**
	 * Projection with field or properties to bind.
	 */
	Projection projection() default @Projection;

	/**
	 * Projection with field or properties to validate.
	 */
	Projection validation() default @Projection;
	
	/**
	 * Class of custom binder for the field or property, or class.
	 */
	Class<? extends Binder> binder() default Binder.class;

	/**
	 * Class of a {@code TextTransform} to apply to parameter value.
	 */
	Class<? extends TextTransform> transform() default TextTransform.class;
}
