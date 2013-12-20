package org.einnovator.meta.criteria;

import static org.einnovator.meta.criteria.CriteriaUtil.gcheck;

import org.einnovator.meta.criteria.CriteriaMarker;

public class NotCriteria implements Criteria {
	CriteriaMarker pc;
	
	public NotCriteria(CriteriaMarker pc) {
		this.pc = pc;
	}
	
	@Override
	public boolean match(Object obj) {
		return !gcheck(pc, obj);
	}
	
}