/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.reflect.Member;
import java.lang.reflect.Type;

import org.einnovator.meta.MetaUtil;


/**
 * A SynteticProperty.
 *
 * @author Jorge Simão
 */
public class TypeModifiedProperty<T> extends PropertySupport<T> {

	protected Class<T> type;

	protected Member member;
	
	//
	// Constructors
	//

	/**
	 * Create instance of TypeModifiedProperty.
	 *
	 * @param type
	 * @param member
	 */
	public TypeModifiedProperty(Class<T> type, Member member) {
		this.type = type;
		this.member = member;
	}
	
	//
	// Property<T> interface
	//
	
	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public Type getGenericType() {
		return MetaUtil.getGenericType(member);
	}

	@Override
	public Member getMember() {
		return member;
	}
	
	@Override
	public void setValue(Object target, T value) {
		MetaUtil.setPropertyValue(target, member, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue(Object target) {
		return (T)MetaUtil.getPropertyValue(target, member);
	}
	
}
