/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AA type, field or property that should be used in comparing two objects.
 *
 * @author Jorge Simão
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface Compare {
	
	/**
	 * Specifies if field or property should be compared.
	 */
	boolean value() default true;

	/**
	 * Specifies if field or property should be ignore for comparison (the opposite of {@link #value()}.
	 */
	boolean ignore() default false;
	
	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type 
	 *  (only applicable to type level annotations; ignored for field and property level annotations).
	 */
	boolean fieldAccess() default true;

}
