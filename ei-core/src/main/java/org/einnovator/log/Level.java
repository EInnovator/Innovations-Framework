/**
 * 
 */
package org.einnovator.log;

/**
 * A logging level.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public enum Level {
	ALL,
	 
	CONFIG,
	 
	TRACE,
	 
	DEBUG,
	
	INFO,
	
	WARNING,
	
	SEVERE,
	
	OFF;
	

	/**
	 * Check if the specified {@code Level} is lower that this {@code Level}.
	 * 
	 * @param level the {@code Level}
	 * @return <code>true</code>, if the specified level is is lower that this {@code Level};
	 * <code>false</code>, otherwise.
	 */
	public boolean enabled(Level level) {
		 return this!=Level.OFF && level.compareTo(this)<=0;
	 }
	
     
}
