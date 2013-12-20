/**
 * 
 */
package org.einnovator.meta.property;

import org.einnovator.meta.Property;

/**
 * A PropertyHandle.
 *
 * @author Jorge Simão
 */
public interface PropertyHandler {

	void handle(Property<?> property);
}
