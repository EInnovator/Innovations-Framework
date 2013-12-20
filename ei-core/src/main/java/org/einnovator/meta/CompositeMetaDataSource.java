/**
 * 
 */
package org.einnovator.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.util.Ordered;


/**
 * AA {@code CompositeMetaDataSource}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class CompositeMetaDataSource implements MetaDataSource {

	private List<MetaDataSource> sources;

	private List<SimpleMetaDataSource<?>> simpleSources;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of CompositeMetaDataSource.
	 *
	 */
	public CompositeMetaDataSource() {
		this.sources = new ArrayList<MetaDataSource>();
		this.simpleSources = new ArrayList<SimpleMetaDataSource<?>>();
	}
	
	/**
	 * Create instance of CompositeMetaDataSource.
	 *
	 * @param sources
	 */
	public CompositeMetaDataSource(List<MetaDataSource> sources) {
		this.sources = sources;
	}	

	//
	// Getters and setters
	//


	/**
	 * Get the value of property {@code sources}.
	 *
	 * @return the sources
	 */
	public List<MetaDataSource> getSources() {
		return sources;
	}

	/**
	 * Set the value of property {@code sources}.
	 *
	 * @param sources the sources to set
	 */
	public void setSources(List<MetaDataSource> sources) {
		this.sources = sources;
	}

	/**
	 * Get the value of property {@code simpleSources}.
	 *
	 * @return the simpleSources
	 */
	public List<SimpleMetaDataSource<?>> getSimpleSources() {
		return simpleSources;
	}

	/**
	 * Set the value of property {@code simpleSources}.
	 *
	 * @param simpleSources the simpleSources to set
	 */
	public void setSimpleSources(List<SimpleMetaDataSource<?>> simpleSources) {
		this.simpleSources = simpleSources;
	}

	
	
	
	//
	// Builders
	//

	/**
	 * Add a source to this chain
	 * 
	 * @param source the source
	 */
	public void addSource(MetaDataSource source) {
		int order = 0;
		if (source instanceof Ordered) {
			order = ((Ordered)source).getOrder();
		}
		for (int i=0; i<sources.size(); i++) {
			MetaDataSource source2 = sources.get(i);
			int order2 = 0;
			if (source2 instanceof Ordered) {
				order2 = ((Ordered)source2).getOrder();
			} 
			if (order<order2) {
				sources.add(i, source);
				return;
			}
		}
		this.sources.add(source);
	}
	/**
	 * Add a variable number of {@code MetaDataSource}
	 * 
	 * @param sources the sources
	 */
	public void addSources(MetaDataSource... sources) {
		for (MetaDataSource source: sources) {
			addSource(source);
		}
	}

	/**
	 * Add a collection of {@code MetaDataSource}
	 * 
	 * @param sources the sources
	 */
	public void addSources(Collection<MetaDataSource> sources) {
		for (MetaDataSource source: sources) {
			addSource(source);
		}
	}

	/**
	 * Add a source to this chain
	 * 
	 * @param source the source
	 */
	public void addSimpleSource(SimpleMetaDataSource<?> source) {
		int order = 0;
		if (source instanceof Ordered) {
			order = ((Ordered)source).getOrder();
		}
		for (int i=0; i<simpleSources.size(); i++) {
			SimpleMetaDataSource<?> source2 = simpleSources.get(i);
			int order2 = 0;
			if (source2 instanceof Ordered) {
				order2 = ((Ordered)source2).getOrder();
			} 
			if (order<order2) {
				simpleSources.add(i, source);
				return;
			}
		}
		this.simpleSources.add(source);
	}
	/**
	 * Add a variable number of {@code SimpleMetaDataSource}
	 * 
	 * @param sources the sources
	 */
	public void addSimpleSources(SimpleMetaDataSource<?>... sources) {
		for (SimpleMetaDataSource<?> source: sources) {
			addSimpleSource(source);
		}
	}

	/**
	 * Add a collection of {@code SimpleMetaDataSource}
	 * 
	 * @param sources the sources
	 */
	public void addSimpleSources(Collection<SimpleMetaDataSource<?>> sources) {
		for (SimpleMetaDataSource<?> source: sources) {
			addSimpleSource(source);
		}
	}

	//
	// Fluent API
	//

	/**
	 * Add a source to this chain
	 * 
	 * @param source the source
	 */
	public CompositeMetaDataSource add(MetaDataSource source) {
		addSource(source);
		return this;
	}

	/**
	 * Add a variable number of {@code MetaDataSource}
	 * 
	 * @param sources the sources
	 */
	public CompositeMetaDataSource addAll(MetaDataSource... sources) {
		addSources(sources);
		return this;
	}

	/**
	 * Add a variable number of {@code MetaDataSource}
	 * 
	 * @param sources the sources
	 */
	public CompositeMetaDataSource addAll(Collection<MetaDataSource> sources) {
		addSources(sources);
		return this;
	}

	//
	//
	// MetaDataSource implementation
	//

	/**
	 * @see org.einnovator.meta.MetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor, java.lang.Class)
	 */
	@Override
	public <T> T getMetaData(MetaDescriptor descriptor, Class<T> metaDataType) {
		T metaData = null;
		for (MetaDataSource source: sources) {
			T metaData2 = source.getMetaData(descriptor, metaDataType);
			metaData = metaData==null ? metaData2 : (metaData2==null ? metaData : mergeMetaData(metaData, metaData2));
		}
		for (SimpleMetaDataSource<?> source: simpleSources) {
			Class<?> type = getMetaDataType(source);
			if (metaDataType.equals(type)) {
				@SuppressWarnings("unchecked")
				T metaData2 = (T)source.getMetaData(descriptor);
				metaData = metaData==null ? metaData2 : (metaData2==null ? metaData : mergeMetaData(metaData, metaData2));
			}
		}
		return metaData;
	}

	protected <T> T mergeMetaData(T metaData, T metaData2) {
		return metaData2;
	}
	
	protected Class<?> getMetaDataType(SimpleMetaDataSource<?> source) {
		return GenericUtil.getGenericInterfaceTypeParameter(source.getClass(), SimpleMetaDataSource.class);
	}
}
