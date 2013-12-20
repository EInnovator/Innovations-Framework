package org.einnovator.meta.criteria;

import java.lang.reflect.Member;
import java.lang.reflect.Method;



public class CriteriaUtil {
	public static boolean gcheck(CriteriaMarker criteria, Object obj) {
		if (criteria instanceof MethodCriteria && (obj==null || obj instanceof Method)) {
			return ((MethodCriteria)criteria).check((Method)obj);
		}
		if (criteria instanceof MemberCriteria && (obj==null || obj instanceof Member)) {
			return ((MemberCriteria)criteria).match((Member)obj);
		}

		return false;
	}
	
	public static NotCriteria NOT(CriteriaMarker criteria) {	
		return new NotCriteria(criteria);
	}
	
	public static MultiAndCriteria AND(CriteriaMarker... criteria) {	
		return new MultiAndCriteria(criteria);
	}

	public static MultiOrCriteria OR(CriteriaMarker... criteria) {	
		return new MultiOrCriteria(criteria);
	}
	
}
