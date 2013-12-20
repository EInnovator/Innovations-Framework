/**
 * 
 */
package org.einnovator.meta;

import java.lang.reflect.Member;

/**
 * A Property.
 *
 * @author Jorge Simão
 */
public interface ReadWriteProperty<T> extends Property<T>{
	
	Member getReadMember();

	Member getWriteMember();

	int getReadModifiers();
	
	int getWriteModifiers();	
	
}
