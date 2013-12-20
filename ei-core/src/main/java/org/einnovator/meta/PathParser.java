/**
 * 
 */
package org.einnovator.meta;

import java.util.ArrayList;
import java.util.List;

import org.einnovator.util.InvalidPropertyPathException;
import org.einnovator.util.StringUtil;

/**
 * A {@code PathParser}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class PathParser {

	public static final String DEFAULT_SEPARATOR_PATTERN = "\\.";
	
	public static final String DEFAULT_SEPARATOR = ".";

	protected MetaClassResolver resolver;

	private String separator = DEFAULT_SEPARATOR;
	
	private String separatorPattern = DEFAULT_SEPARATOR_PATTERN;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code PathParser}.
	 *
	 * @param path
	 * @param resolver
	 */
	public PathParser(MetaClassResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * Create instance of {@code PathParser}.
	 *
	 * @param path
	 * @param resolver
	 */
	public PathParser() {
		this(MetaOperations.getMetaClassResolver());
	}

	//
	// Getters and setters
	//

	/**
	 * Get the value of resolver.
	 *
	 * @return the resolver
	 */
	public MetaClassResolver getResolver() {
		return resolver;
	}

	/**
	 * Set the value of resolver.
	 *
	 * @param resolver the resolver to set
	 */
	public void setResolver(MetaClassResolver resolver) {
		this.resolver = resolver;
	}

	//
	// Parsing
	//
	
	/**
	 * @param path
	 * @param separator
	 * @return
	 */
	public Path parse(String path, Class<?> type) {
		return parse(path, type, separatorPattern);
	}

	public Path parse(String path, Class<?> type, String separator) {
		return new Path(parseAsProperties(path, type, separator));
	}

	
	public Path parse(String path[], Class<?> type) {
		return new Path(parseAsProperties(path, type));
		
	}

	public Property<?>[] parseAsProperties(String path, Class<?> type, String separator) {
		if (path==null) {
			throw new InvalidPropertyPathException(path);
		}
		String[] apath = StringUtil.split(path, separator);
		if (apath.length==0) {
			throw new InvalidPropertyPathException("'" + path + "'");
		}
		return parseAsProperties(path, apath, type);
	}

	public Property<?>[] parseAsProperties(String path[], Class<?> type) {
		return parseAsProperties(null, path, type);
	}
	
	private Property<?>[] parseAsProperties(String path, String apath[], Class<?> type) {
		Property<?>[] properties = new Property[apath.length];
		parseAsProperties(path, apath, type, properties);
		return properties;		
	}

	private void parseAsProperties(String path, String[] apath, Class<?> type, Property<?>[] properties) {
		for (int i=0; i<apath.length; i++) {
			MetaClass<?> metaClass = resolver.getMetaClass(type);
			String name = apath[i];
			if (name==null) {
				throw new InvalidPropertyPathException("'" + (path!=null ? path : StringUtil.sconcat(apath, separator)) + "' for " + type);
			}
			name = name.trim();
			if (name.isEmpty()) {
				throw new InvalidPropertyPathException("'" + (path!=null ? path : StringUtil.sconcat(apath, separator))+ "' for " + type);
			}
			Property<?> property = metaClass.getProperty(name);
			if (property==null) {
				throw new InvalidPropertyPathException("'" + (path!=null ? path : StringUtil.sconcat(apath, separator)) + "' for " + type);			
			}
			properties[i] = property;
			type = property.getType();
		}
	}

	//
	// static utility
	//
	
	private static PathParser instanceFieldAccess, instancePropertyAccess;
	
	public static PathParser getInstance() {
		return getInstance(true);
	}
	
	public static PathParser getInstance(boolean fieldAccess) {
		if (fieldAccess) {
			if (instanceFieldAccess==null) {
				instanceFieldAccess = new PathParser(MetaOperations.getMetaClassResolver());
			}
			return instanceFieldAccess;
		} else {
			if (instancePropertyAccess==null) {
				instancePropertyAccess = new PathParser(MetaOperations.getMetaClassResolver());
			}
			return instancePropertyAccess;
			
		}
	}

	public static List<Path> parseAll(String[] paths, Class<?> type) {
		return parseAll(paths, type, true);
	}
	
	public static List<Path> parseAll(String[] paths, Class<?> type, boolean fieldAccess) {
		List<Path> paths2 = new ArrayList<Path>(paths.length);
		for (String path: paths) {
			paths2.add(getInstance(fieldAccess).parse(path, type));
		}
		return paths2;
	}

}
