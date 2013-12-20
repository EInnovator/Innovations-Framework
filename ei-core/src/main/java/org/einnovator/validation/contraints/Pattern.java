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
 * Annotation that specifies a regular expression pattern constraint.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Pattern {

	/**
	 * The regular expression to match.
	 */
	String regexp();
	
	/**
	 * @return array of {@code Flag} considered when resolving the regular expression.
	 */
	Flag[] flags() default {};
	
	/**
	 * @return the message to produce or render.
	 */
	Message message() default @Message;
	
	/**
	 * A {@code Flag}.
	 *
	 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
	 *
	 */
	public enum Flag {
		/**
		 * Enables canonical equivalence.
		 */
		CANON_EQ,

		/**
		 * Enables case-insensitive matching.
		 */
		CASE_INSENSITIVE,

		/**
		 * Permits whitespace and comments in pattern.
		 */
		COMMENTS,
		
		/**
		 * 	Enables dotall mode.
		 */
		DOTALL,

		/**
		 * Enables multiline mode. 
		 */
		MULTILINE,
		
		/**
		 * Enables Unicode-aware case folding. 
		 */
		UNICODE_CASE,
		
		/**
		 * Enables Unix lines mode. 
		 */
		UNIX_LINES;
	}
}
