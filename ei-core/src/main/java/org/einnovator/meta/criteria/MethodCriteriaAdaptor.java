package org.einnovator.meta.criteria;

import java.lang.reflect.Method;




public class MethodCriteriaAdaptor implements MethodCriteria {
	protected CriteriaMarker criteria;

	
	public MethodCriteriaAdaptor(CriteriaMarker criteria) {
		this.criteria = criteria;
	}


	@Override
	public boolean check(Method meth) {
		if (criteria instanceof Criteria) {
			return ((Criteria)criteria).match(meth);
		}
		if (criteria instanceof MemberCriteria) {
			return ((MemberCriteria)criteria).match(meth);
		}
		if (criteria instanceof MethodCriteria) {
			return ((MethodCriteria)criteria).check(meth);
		}
		return false;
	}

}
