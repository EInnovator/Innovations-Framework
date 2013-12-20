package org.einnovator.meta;

/**
 * A source for meta data.
 *
 * @param <T> the type of the meta-data
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 * @see MetaDataSource
 */
public interface SimpleMetaDataSource<T> {

	/**
	 * Get meta-data of a fixed type for the specified {@code MetaDescriptor}.
	 * 
	 * @param descriptor the {@code MetaDescriptor}
	 * @return the meta-data; or <code>null</code>, if none is found.
	 */
	T getMetaData(MetaDescriptor descriptor);
}
