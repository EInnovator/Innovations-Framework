package org.einnovator.meta.criteria;

import org.einnovator.meta.criteria.GetterCriteria;
import org.einnovator.meta.criteria.MethodCriteriaAdaptor;
import org.einnovator.meta.criteria.SetterCriteria;


public class MethodCriteriaUtil {
	public static GetterCriteria getterCriteria;
	public static SetterCriteria setterCriteria;

	public static GetterCriteria GETTER() {	
		if (getterCriteria==null) getterCriteria = new GetterCriteria();
		return getterCriteria;
	}

	public static SetterCriteria SETTER() {	
		if (setterCriteria==null) setterCriteria = new SetterCriteria();
		return setterCriteria;
	}

	
	public static MethodCriteriaAdaptor ADAPT(CriteriaMarker criteria) {	
		return new MethodCriteriaAdaptor(criteria);
	}
	
	
}
