/**
 * 
 */
package org.einnovator.validation.contraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.einnovator.i18n.Message;

/**
 * Annotation that specifies a minimum and/or maximum value or length constraints.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Size {
	/**
	 * Element size/length must be higher or equal to. 
	 */
	int min() default Integer.MIN_VALUE;

	/**
	 * Element size/length must be lower or equal to. 
	 */
	int max() default Integer.MAX_VALUE;
	
	/**
	 * @return the message to produce or render.
	 */
	Message message() default @Message;
}
