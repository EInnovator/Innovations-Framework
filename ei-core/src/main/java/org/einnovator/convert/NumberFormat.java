/**
 * 
 */
package org.einnovator.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

/**
 * A NumberFormat.
 *
 * @author Jorge Simão
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberFormat {
	NumberType type() default NumberType.NUMBER;

	String pattern() default "";
	
	String currency() default "";
	
	boolean grouping() default false;

	int maxFractionDigits() default -1;
	
	int minFractionDigits() default -1;

	int maxIntegerDigits() default -1;
	
	int minIntegerDigits() default -1;
	
	RoundingMode roundingMode() default RoundingMode.UNNECESSARY;

	boolean integerOnly() default false;
	

}
