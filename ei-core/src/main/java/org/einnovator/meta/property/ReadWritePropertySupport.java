/**
 * 
 */
package org.einnovator.meta.property;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import org.einnovator.meta.ReadWriteProperty;

/**
 * Abstract class to support implementations of {@code Property}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class ReadWritePropertySupport<T> extends PropertySupport<T> implements ReadWriteProperty<T> {

	//
	// Constructors
	//

	/**
	 * Create instance of {@code ReadWritePropertySupport}.
	 *
	 * @param ownerClass the class owning the this {@code Property}
	 * @param field the {@code Field}
	 */
	public ReadWritePropertySupport(Class<?> ownerClass) {
		super(ownerClass);
	}	
	
	/**
	 * Create instance of ReadWritePropertySupport.
	 *
	 */
	public ReadWritePropertySupport() {
	}
	
	//
	// Property<T> implementation
	//
	
	@Override
	public int getReadModifiers() {
		return getReadMember().getModifiers();
	}

	@Override
	public int getWriteModifiers() {
		return getWriteMember().getModifiers();
	}
	
	@Override
	public Member getMember() {
		return getReadMember();
	}

	//
	// Modifier utility
	//
	
	public boolean isReadStatic() {
		return Modifier.isStatic(getReadModifiers());
	}

	public boolean isWriteStatic() {
		return Modifier.isStatic(getWriteModifiers());
	}

	public boolean isReadFinal() {
		return Modifier.isStatic(getReadModifiers());
	}

	public boolean isReadPublic() {
		return Modifier.isPublic(getReadModifiers());
	}

	public boolean isWritePublic() {
		return Modifier.isPublic(getWriteModifiers());
	}
	
	public boolean isReadProtected() {
		return Modifier.isProtected(getReadModifiers());
	}

	public boolean isWriteProtected() {
		return Modifier.isProtected(getWriteModifiers());
	}
	
	public boolean isReadPrivate() {
		return Modifier.isPrivate(getReadModifiers());
	}

	public boolean isWritePrivate() {
		return Modifier.isPrivate(getWriteModifiers());
	}
	

}
