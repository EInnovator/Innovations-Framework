package org.einnovator.meta.criteria;

import static org.einnovator.meta.criteria.CriteriaUtil.gcheck;

import org.einnovator.meta.criteria.CriteriaMarker;

/**
 * AA {@code AndCriteria}.
 *
 * @author Jorge Simão
 */
public class AndCriteria implements Criteria {
	CriteriaMarker left, right;
	
	public AndCriteria(CriteriaMarker left, CriteriaMarker right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public boolean match(Object obj) {
		return gcheck(left, obj) && gcheck(right, obj);
	}

}
