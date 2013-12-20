/**
 * 
 */
package org.einnovator.meta;


/**
 * A PropertyCriteria.
 *
 * @author Jorge Simão
 */
public interface PropertyCriteria {

	boolean match(Property<?> property);
}
