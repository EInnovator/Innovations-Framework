package org.einnovator.meta.criteria;


/**
 * AA {@code Criteria}.
 *
 * @param <T>
 * @author Jorge Simão
 */
public interface Criteria extends CriteriaMarker {
	boolean match(Object obj);
}
