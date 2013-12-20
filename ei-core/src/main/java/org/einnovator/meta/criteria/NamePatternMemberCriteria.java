package org.einnovator.meta.criteria;

import java.lang.reflect.Member;
import java.util.regex.Pattern;




public class NamePatternMemberCriteria implements MemberCriteria {
	protected Pattern pattern;
	
	public NamePatternMemberCriteria(String regex) {
		setPattern(regex);
	}

	/**
	 * Create instance of NamePatternMemberCriteria.
	 *
	 */
	public NamePatternMemberCriteria() {
	}

	@Override
	public boolean match(Member member) {
		String name = member.getName();
		return pattern.matcher(name).matches();
	}


	//
	// Getters and setters
	//
	
	/**
	 * Get the value of pattern.
	 *
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * Set the value of pattern.
	 *
	 * @param pattern the pattern to set
	 */
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Set the value of pattern.
	 *
	 * @param pattern the pattern to set
	 */
	public void setPattern(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	
}
