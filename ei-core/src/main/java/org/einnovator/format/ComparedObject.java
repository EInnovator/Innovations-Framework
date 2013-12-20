/**
 * 
 */
package org.einnovator.format;

import org.einnovator.meta.MetaOperations;


/**
 * Abstract support class for object performing auto-deep comparison and auto-formatting.
 * 
 * This class overrides the {@code equals()} to use auto-deep comparison
 * by calling method {@link MetaOperations#equals(Object)}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class ComparedObject extends ObjectSupport {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ComparedObject}.
	 *
	 */
	public ComparedObject() {
	}
	
	//
	// Object overrides
	//

	/**
	 * Implements auto-deep comparison by delegating to {#link {@link MetaOperations#equals(Object)}}.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return MetaOperations.equals(this, obj);
	}
	
}
