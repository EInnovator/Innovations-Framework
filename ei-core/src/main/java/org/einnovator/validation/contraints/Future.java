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
 * Annotation that specifies the target value must be a date in the future.
 * 
 * Now is defined as the current time as returned by {@code Date()}.
 * The calendar used if the compared type is of type Calendar is the calendar based
 * on the current timezone and the current locale.
 * 
 * Supported types are:
 * <ul>
 * <li>java.util.Date
 * <li>java.util.Calendar
 * </ul>
 * 
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Future {
	
	/**
	 * @return the message to produce or render.
	 */
	Message message() default @Message;
}
