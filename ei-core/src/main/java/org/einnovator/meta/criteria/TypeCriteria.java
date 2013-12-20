package org.einnovator.meta.criteria;



public interface TypeCriteria extends CriteriaMarker {
	boolean check(Class<?> type);
}
