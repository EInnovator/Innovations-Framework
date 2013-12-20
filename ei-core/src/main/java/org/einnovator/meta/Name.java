/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Prop for a property.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface Name {
	String value();
}
