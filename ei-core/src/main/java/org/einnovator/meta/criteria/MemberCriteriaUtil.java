package org.einnovator.meta.criteria;

import java.lang.reflect.Modifier;

import org.einnovator.meta.criteria.ModifierMemberCriteria;
import org.einnovator.meta.criteria.NamePatternMemberCriteria;


import static org.einnovator.meta.criteria.CriteriaUtil.*;

public class MemberCriteriaUtil {
	private static ModifierMemberCriteria criteriaFinal;
	private static ModifierMemberCriteria criteriaStatic;
	private static ModifierMemberCriteria criteriaPublic;
	private static ModifierMemberCriteria criteriaProtected;
	private static ModifierMemberCriteria criteriaPrivate;
	private static ModifierMemberCriteria criteriaNative;

	private static Criteria notFinal;	
	private static Criteria notStatic;
	private static Criteria notPublic;
	private static Criteria notProtected;
	private static Criteria notPrivate;
	private static Criteria notNative;	

	public static ModifierMemberCriteria FINAL() {
		if (criteriaFinal==null) criteriaFinal = new ModifierMemberCriteria(Modifier.FINAL);
		return criteriaFinal;
	}
	
	public static ModifierMemberCriteria STATIC() {
		if (criteriaStatic==null) criteriaStatic = new ModifierMemberCriteria(Modifier.STATIC);
		return criteriaStatic;
	}
	
	public static ModifierMemberCriteria PUBLIC() {
		if (criteriaPublic==null) criteriaPublic = new ModifierMemberCriteria(Modifier.PUBLIC);
		return criteriaPublic;
	}
	
	public static ModifierMemberCriteria PROTECTED() {
		if (criteriaProtected==null) criteriaProtected = new ModifierMemberCriteria(Modifier.PROTECTED);
		return criteriaProtected;
	}
	
	public static ModifierMemberCriteria PRIVATE() {
		if (criteriaPrivate==null) criteriaPrivate = new ModifierMemberCriteria(Modifier.PRIVATE);
		return criteriaPrivate;
	}

	public static ModifierMemberCriteria NATIVE() {
		if (criteriaNative==null) criteriaNative = new ModifierMemberCriteria(Modifier.NATIVE);
		return criteriaNative;
	}
	
	public static NamePatternMemberCriteria NAME(String regex) {
		return new NamePatternMemberCriteria(regex);
	}

	public static Criteria NOT_FINAL() {	
		if (notFinal==null) notFinal = NOT(FINAL());
		return notFinal;
	}
	
	public static Criteria NOT_STATIC() {	
		if (notStatic==null) notStatic = NOT(STATIC());
		return notStatic;
	}

	public static Criteria NOT_PUBLIC() {	
		if (notPublic==null) notPublic = NOT(PUBLIC());
		return notPublic;
	}

	public static Criteria NOT_PROTECTED() {	
		if (notProtected==null) notProtected = NOT(PROTECTED());
		return notProtected;
	}

	public static Criteria NOT_PRIVATE() {	
		if (notPrivate==null) notPrivate = NOT(PRIVATE());
		return notPrivate;
	}

	public static Criteria NOT_NATIVE() {	
		if (notNative==null) notNative = NOT(NATIVE());
		return notNative;
	}

}
