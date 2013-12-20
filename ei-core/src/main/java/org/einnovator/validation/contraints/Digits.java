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
 * Annotation that specifies that a number must have a up to maximum of integer and fractional digits.
 * 
 * Accepted typed: {@code BigDecimal}, {@code BigInteger}, {@code CharSequence}, {@code byte}, 
 * {@code short}, {@code int}, {@code long}, and its wrapper types.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Digits {
	
	/**
	 *  @return maximum number of integral digits accepted for this numberThe String representation of the max value according to the {@code BigDecimal} string representation. 
	 */
	int integer();
		
	/**
	 * @return maximum number of fractional digits accepted for this number
	 */
	int fraction();
	    
	/**
	 * @return the message to produce or render.
	 */
	Message message() default @Message;
}
