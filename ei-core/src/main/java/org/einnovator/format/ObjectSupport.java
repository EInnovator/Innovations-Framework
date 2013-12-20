/**
 * 
 */
package org.einnovator.format;


/**
 * Abstract support class for object printed with auto-formatting.
 * 
 * This class overrides the {@code toString()} to use auto-formatting
 * by calling method {@link PropertiesMapperUtil#toString()}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class ObjectSupport implements FormattedObject {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ObjectSupport}.
	 *
	 */
	public ObjectSupport() {
	}
	
	//
	// Object overrides
	//
	
	/**
	 * Returns the text representation of the object by delegating to {@link PropertiesMapperUtil#toString()};
	 * 
	 * @return the text representation of the object
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return FormatUtil.toString(this);
	}

	/**
	 * Get the string representation of this object.
	 * 
	 * @param context the {@code FormatContext}
	 * @return the string representation of this object
	 */
	@Override
	public String toString(FormatContext context) {
		return FormatUtil.toString(this, context);
	}
}
