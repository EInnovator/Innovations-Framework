package org.einnovator.meta.criteria;

import java.lang.reflect.Method;




public interface MethodCriteria extends CriteriaMarker {
	boolean check(Method method);
}
