package org.einnovator.meta.criteria;

import java.lang.reflect.Method;

import org.einnovator.meta.MetaUtil;
import org.einnovator.meta.criteria.GetterCriteria;



/**
 * A GetterCriteria.
 *
 * @author Jorge Simão
 */
public class GetterCriteria implements MethodCriteria {
	public static GetterCriteria criteria = new GetterCriteria();
	
	@Override
	public boolean check(Method mt) {
		return MetaUtil.isGetter(mt);
	}

}