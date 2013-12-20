/**
 * 
 */
package org.einnovator.meta.criteria;

import java.lang.reflect.Member;




/**
 * A DeclarationCriteria.
 *
 * @author Jorge Simão
 */
public class DeclarationCriteria implements MemberCriteria {
	
	protected Class<?>[] typeList;

	protected Op op;
	
	public enum Op {
		DECLARED,
		NOT_DECLARED,
	}

	//
	// Constructors
	//
	
	public DeclarationCriteria(Op op) {	
		this.op = op;
	}
	
	public DeclarationCriteria(Op op, Class<?>... cl) {
		this.op = op;
		this.typeList = cl;
	}
	/* 
	 * @see meta.criteria.PropertyCriteria#check(meta.Property)
	 */
	@Override
	public boolean match(Member member) {
		Class<?> cl = member.getDeclaringClass();
		if (cl==null) {
			return true;
		}
		switch (op) {
			case DECLARED:
				for (Class<?> type: this.typeList) {
					if (type.equals(cl)) {
						return true;
					}
				}
				return false;
			case NOT_DECLARED:
				for (Class<?> type: this.typeList) {
					if (type.equals(cl)) {
						return false;
					}
				}
				return true;
		}
		return true;
	}

}
