/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Definition of a projection on the set of fields/properties of a type.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Projection {

	/**
	 * Fields or properties to include strictly.
	 * Same as using {@link #includeOnly()}.
	 */
	String[] value() default {};

	/**
	 * Fields or properties to include.
	 */
	String[] include() default {};

	/**
	 * Fields or properties to include strictly.
	 * Same as using {@link #include()} and setting {{@link #complete()}={@code true}. 
	 */
	String[] includeOnly() default {};

	/**
	 * Fields or properties to exclude.
	 */
	String[] exclude() default {};	

	/**
	 * Annotations types for fields to include.
	 */
	Class<? extends Annotation>[] includeAnnotated() default {};

	/**
	 * Annotations types for only fields to include.
	 * Same as using {@link #includeAnnotated()} and setting {{@link #complete()}={@code true}. 
	 */
	Class<? extends Annotation>[] includeOnlyAnnotated() default {};

	/**
	 * Annotations types for fields or properties to exclude.
	 */
	Class<? extends Annotation>[] excludeAnnotated() default {};	

	/**
	 * Classes (types) of fields or properties to include..
	 */
	Class<?>[] includeClasses() default {};

	/**
	 * Classes (types) of fields or properties to include.
	 * Same as using {@link #includeClasses()} and setting {{@link #complete()}={@code true}. 
	 */
	Class<?>[] includeOnlyClasses() default {};
	
	/**
	 * Classes (type) of fields or properties to exclude.
	 */
	Class<?>[] excludeClasses() default {};	

	/**
	 * Classes (types) to match the properties to include.
	 */
	Class<?>[] includeMatching() default {};

	/**
	 * Classes (types) to match the properties to include strictly.
	 */
	Class<?>[] includeOnlyMatching() default {};
	
	/**
	 * Classes (types) to match the properties to exclude.
	 */
	Class<?>[] excludeMatching() default {};	

	/**
	 * Flag specifying if only explicitly included field should be included.
	 * If <code>false</code>, inclusion refers to the optional field to include.
	 */
	boolean complete() default false;

}
