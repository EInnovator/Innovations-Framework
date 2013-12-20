package org.einnovator.meta;

/**
 * A source for meta data.
 *
 * @see SimpleMetaDataSource
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 */
public interface MetaDataSource {

	/**
	 * Get meta-data of a given type for the specified {@code MetaDescriptor}.
	 * 
	 * @param descriptor the {@code MetaDescriptor}
	 * @param metaDataType the meta-data type
	 * @return the meta-data; or <code>null</code>, if none is found.
	 */
	<T> T getMetaData(MetaDescriptor descriptor, Class<T> metaDataType);
}
