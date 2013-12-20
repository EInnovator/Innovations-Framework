package org.einnovator.meta.criteria;

import java.lang.reflect.Member;


/**
 * A {@code MemberCriteria}.
 *
 * @author Jorge Simão
 */
public interface MemberCriteria extends CriteriaMarker {
	boolean match(Member member);
}
