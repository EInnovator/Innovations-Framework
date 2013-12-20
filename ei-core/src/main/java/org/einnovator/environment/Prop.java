/**
 * 
 */
package org.einnovator.environment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.einnovator.format.Printer;
import org.einnovator.meta.Projection;

/**
 * Prop for a property.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface Prop {
	
	/**
	 * Flag specifying if field or property should be ignored in printing/formatting.
	 * Opposite of {@link #ignore()}.
	 */
	boolean value() default true;
	
	/**
	 * The key for field or property.
	 * 
	 */
	String key() default "";
		
	/**
	 * Flag specifying if field or property should be ignored in printing/formatting.
	 * Opposite of {@link #value()}.
	 */
	boolean ignore() default false;
	
	/**
	 * Flag specifying if field or property should be ignored if it is <code>null</code> valued.
	 */
	boolean ignoreNull() default true;

	/**
	 * Flag specifying if field or property should be ignored if it is empty (Strings, arrays, and collections).
	 */
	boolean ignoreEmpty() default false;	

	/**
	 * Flag specifying if field or property should be ignored if it is zero (Number sub-types only).
	 */
	boolean ignoreZero() default false;	

	/**
	 * Flag specifying if field or property should be ignored unless explicitly asked to be included.
	 */
	boolean optional() default false;
	
	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type.
	 */
	boolean fieldAccess() default true;

	/**
	 * Projection specifying which fields/properties to include/exclude.
	 * @return
	 */
	Projection projection() default @Projection;
	
	/**
	 * Class of custom printer for the field or property, or class.
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends Printer> printer() default Printer.class;
}
