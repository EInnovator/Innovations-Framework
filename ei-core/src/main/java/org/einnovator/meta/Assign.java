/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AA type, field or property that should be assigned when assigning one object to another.
 *
 * @author Jorge Simão
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface Assign {

	/**
	 * Specifies if field or property should be assigned.
	 */
	boolean value() default true;

	/**
	 * Specifies if field or property should be ignore for assignment (the opposite of {@link #value()}.
	 */
	boolean ignore() default false;
	
	/**
	 * Specifies if assignment created copies of values.
	 */
	boolean deep() default true;

	/**
	 * Specifies if assignment should overwrite non-<code>null</code> value.
	 */
	boolean overwrite() default true;

	/**
	 * Specifies if assignment should overwrite with <code>null</code> value.
	 */
	boolean overwriteWithNull() default true;

	/**
	 * Specifies if assignment should overwrite with empty array or collection.
	 */
	boolean overwriteWithEmpty() default true;

	/**
	 * Specifies if existing value should be merged with assigned value.
	 */
	boolean merge() default false;

	/**
	 * Specifies if assignment is required (only intra-type hierarchy assignment).
	 */
	boolean required() default false;
	
	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type 
	 *  (only applicable to type level annotations; ignored for field and property level annotations).
	 */
	boolean fieldAccess() default true;

}
