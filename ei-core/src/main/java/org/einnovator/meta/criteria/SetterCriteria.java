package org.einnovator.meta.criteria;

import java.lang.reflect.Method;

import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.criteria.SetterCriteria;






public class SetterCriteria implements MethodCriteria {
	public static SetterCriteria criteria = new SetterCriteria();
	
	@Override
	public boolean check(Method mt) {
		return MetaUtil.isSetter(mt);
	}

}
