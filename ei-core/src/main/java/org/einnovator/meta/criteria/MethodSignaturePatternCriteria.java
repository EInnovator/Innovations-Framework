package org.einnovator.meta.criteria;

import java.lang.reflect.Method;

import org.einnovator.meta.criteria.MethodSignaturePatternCriteria;





public class MethodSignaturePatternCriteria implements MethodCriteria {
	protected NamePatternMemberCriteria criteria;

	protected Class<?>[] types;
	
	protected boolean strict;
	
	//
	// Constructors
	//
	
	public MethodSignaturePatternCriteria(String regex, Class<?>[] types, boolean strict) {
		criteria = new NamePatternMemberCriteria(regex);
		this.types = types;
		this.strict = strict;
	}

	public MethodSignaturePatternCriteria(boolean strict) {
		criteria = new NamePatternMemberCriteria();
		this.strict = strict;
	}

	public MethodSignaturePatternCriteria() {
		criteria = new NamePatternMemberCriteria();
	}

	//
	// Getters and setters
	//

	/**
	 * Get the value of criteria.
	 *
	 * @return the criteria
	 */
	public NamePatternMemberCriteria getCriteria() {
		return criteria;
	}

	/**
	 * Set the value of criteria.
	 *
	 * @param criteria the criteria to set
	 */
	public void setCriteria(NamePatternMemberCriteria criteria) {
		this.criteria = criteria;
	}


	/**
	 * Get the value of types.
	 *
	 * @return the types
	 */
	public Class<?>[] getTypes() {
		return types;
	}


	/**
	 * Set the value of types.
	 *
	 * @param types the types to set
	 */
	public void setTypes(Class<?>[] types) {
		this.types = types;
	}


	/**
	 * Get the value of strict.
	 *
	 * @return the strict
	 */
	public boolean isStrict() {
		return strict;
	}

	/**
	 * Set the value of strict.
	 *
	 * @param strict the strict to set
	 */
	public void setStrict(boolean strict) {
		this.strict = strict;
	}
	

	//
	// MethodCriteria implementation
	//
	@Override
	public boolean check(Method method) {
		if (!criteria.match(method)) {
			return false;
		}
		if (method.getParameterTypes().length!=types.length) {
			return false;
		}
		if (types!=null) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (int i=0; i<types.length; i++) {
				if (!matchParameterType(types[i], parameterTypes[i], i)) {
					return false;
				}
			}
		}
		return true;
	}


	protected boolean matchParameterType(Class<?> type, Class<?> parameterType, int i) {
		return matchParameterType(type, parameterType, strict);
	}
	
	protected boolean matchParameterType(Class<?> type, Class<?> parameterType, boolean strict) {
		if (strict) {
			if (type!=null && !type.equals(parameterType)) {
					return false;
			}
		} else {
			if (type!=null && !parameterType.isAssignableFrom(type)) {
				return false;
			}
		}
		return true;
	}	
}
