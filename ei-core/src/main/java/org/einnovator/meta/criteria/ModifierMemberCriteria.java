/**
 * 
 */
package org.einnovator.meta.criteria;

import java.lang.reflect.Member;




/**
 * @author Jorge P.F. Simao {jsimao@jpalace.org,jsimao@glintt.com,jsimao71@gmail.com}
 *
 */
public class ModifierMemberCriteria implements MemberCriteria {
	protected int mod;
	
	protected Op op = Op.AND;
	
	protected enum Op {
		AND,
		OR,
		NOT
	}
	
	public ModifierMemberCriteria(int mod, Op op) {
		this.mod = mod;
		this.op = op;
	}

	public ModifierMemberCriteria(int mod) {
		this(mod, Op.AND);
	}
	
	/* 
	 *
	 */
	@Override
	public boolean match(Member member) {
		int mod = member.getModifiers();
		switch (op) {
			case AND: return (this.mod&mod)==this.mod;
			case OR: return (this.mod&mod)!=0;
			case NOT: return (this.mod&mod)==0;
		}
		return false;
	}

}
