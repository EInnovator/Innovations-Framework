/**
 * 
 */
package org.einnovator.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.einnovator.convert.ConversionService;
import org.einnovator.log.Level;
import org.einnovator.meta.Projection;
import org.einnovator.meta.Ref;

/**
 * Annotation to control how an object, field or property should be formatted.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Format {

	/**
	 * Flag specifying if field or property should be ignored in printing/formatting.
	 * Opposite of {@link #ignore()}.
	 */
	boolean value() default true;
	
	/**
	 * Label for field or property.
	 * 
	 */
	String label() default "";

	/**
	 * I18n key for the label (defaults to {@code qualifiedClassName.propertyName}).
	 * 
	 */
	String key() default "";

	/**
	 * Flag specifying if i18n message resolution should be used for labels.
	 */
	boolean i18n() default false;
	
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
	 * Flag specifying if field or property should be ignored if it is default value.
	 */
	boolean ignoreDefault() default false;	

	/**
	 * Flag specifying if field or property should be ignored unless explicitly asked to be included.
	 */
	boolean optional() default false;	

	/**
	 * Flag specifying if value should be quoted (String values only).
	 */
	boolean quote() default true;

	/**
	 * The character to use in quotes (String values only).
	 */
	char quoteChar() default '\0';

	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type.
	 */
	boolean fieldAccess() default true;

	/**
	 * Flag specifying if recursive field/property formatting should be applied.
	 * If <code>false</code> (the default), the {@code toString()} method for the object is called
	 * (unless a {@link ConversionService} is configured that supports printing of the field/property type).
	 * If a projection is specified with {@link #include()}, {@link #exclude()},
	 * {@link #includeAnnotated()()}, {@link #excludeAnnotated()()}, {@link #includeClasses()()}, or
	 * {@link #excludeClasses()()} it implies this is value is considered <code>true</code>.
	 */
	boolean recurse() default false;

	/**
	 * Projection specifying which fields/properties to include/exclude.
	 * 
	 * @return the {@code Projection}
	 */
	Projection projection() default @Projection;
	
	/**
	 * Flag that specified if cycles should be checked.
	 */
	boolean checkCycles() default true;

	/**
	 * The {@code ProjectionOptions} to use when cycles are detected. 
	 */
	Projection cycleProjection() default @Projection;
	
	/**
	 * A reference to a {@code Printer} to use for custom formatting.
	 */
	Ref printer() default @Ref;

	/**
	 * A reference to a {@code TextTransform} to apply to the formatted text.
	 */
	Ref transform() default @Ref;
	
	/**
	 * A reference to a {@code TextTransform} to apply to the label of field or property.
	 */
	Ref labelTransform() default @Ref;
	
	/**
	 * Level of logging required to have a field/property being printed.
	 */
	Level level() default Level.ALL;
}
