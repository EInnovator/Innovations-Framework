package org.einnovator.meta.criteria;

import static org.einnovator.meta.criteria.CriteriaUtil.gcheck;

import org.einnovator.meta.criteria.CriteriaMarker;

public class OrCriteria implements Criteria {
	CriteriaMarker pc0, pc1;
	
	public OrCriteria(CriteriaMarker pc0, CriteriaMarker pc1) {
		this.pc0 = pc0;
		this.pc1 = pc1;
	}
	
	@Override
	public boolean match(Object obj) {
		return gcheck(pc0, obj) || gcheck(pc1, obj);
	}
	
}