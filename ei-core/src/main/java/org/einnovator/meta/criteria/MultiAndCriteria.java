package org.einnovator.meta.criteria;

import static org.einnovator.meta.criteria.CriteriaUtil.gcheck;

import org.einnovator.meta.criteria.CriteriaMarker;

public class MultiAndCriteria  implements Criteria {
	CriteriaMarker[] pc;
	
	public MultiAndCriteria(CriteriaMarker... pc) {
		this.pc = pc;
	}
	
	@Override
	public boolean match(Object obj) {
		for (CriteriaMarker pc_: pc) {
			if (!gcheck(pc_, obj)) return false;
		}
		return true;
	}
	
}
