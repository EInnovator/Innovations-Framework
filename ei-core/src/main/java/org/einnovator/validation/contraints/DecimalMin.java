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
 * Annotation that specifies a minimum decimal value constraint.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface DecimalMin {
	
	/**
	 * 	The String representation of the min value according to the {@code BigDecimal} string representation. 
	 */
	String value();
		
	/**
	 * Specifies whether the specified minimum is inclusive or 	exclusive.  By default, it is inclusive.
	 * @return <code>true</code> if the value must be lower or equal to the specified minimum, <code>false</code> if the value must be lower.
	 */
	boolean inclusive() default true;

	/**
	 * @return the message to produce or render.
	 */
	Message message() default @Message;
}
