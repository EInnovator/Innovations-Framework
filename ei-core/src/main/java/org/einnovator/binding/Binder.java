package org.einnovator.binding;

import java.util.List;

import org.einnovator.meta.Property;
import org.einnovator.validation.Errors;


/**
 * AA binder from parameter in an external and properties in a target object.
 * 
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface Binder {

	Errors bind(Object target, Object context);

	Errors bind(String prefix, Object target, List<Property<?>> initializedProperties, Object context);

}