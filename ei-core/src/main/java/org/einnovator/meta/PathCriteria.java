/**
 * 
 */
package org.einnovator.meta;


/**
 * A criteria for selection of {@code Path}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public interface PathCriteria {

	/**
	 * Check if the specified path should be selected (included).
	 * 
	 * @param path the {@code Path}
	 * @param optional flag specifying if the path should be included (if <code>false</code>), or excluded (if <code>true</code>)
	 * if the criteria does not explicitly defines that the path should be included.
	 * @param prefix flag specifying is path as a prefix to an include path should be considered
	 * @return <code>true</code>, if the {@code Path} is selected (included); <code>false</code>, otherwise.
	 */
	boolean checkInclude(Path path, boolean optional, boolean prefix);

	/**
	 * Check if specified path is prefix for any selected (included) property.
	 * 
	 * @param path the {@code Path}
	 * @return <code>true</code>, if the {@code Path} is prefix for selected (included) property; <code>false</code>, otherwise.
	 */
	boolean checkAnyIncluded(Path path);
	
	/**
	 * Check if a tail {@code Property} in a path should be selected (included).
	 * 
	 * @param path the {@code Path}
	 * @param optional flag specifying if the {@code Path} should be included (if <code>false</code>), or excluded (if <code>true</code>)
	 * if the criteria does not explicitly defines that the path should be included.
	 * @return <code>true</code>, if the {@code Property} is selected (included); <code>false</code>, otherwise.
	 */
	boolean checkInclude(Property<?> property, boolean optional);
}
