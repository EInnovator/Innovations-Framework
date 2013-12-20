package org.einnovator.meta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.einnovator.format.ObjectSupport;
import org.einnovator.util.InvalidPropertyPathException;

/**
 * A {@code TypeProjection}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class TypeProjection extends ObjectSupport implements PathCriteria {

	private Map<Path, Boolean> pathMap = new LinkedHashMap<Path, Boolean>();

	private ProjectionOptions projection;

	private List<Path> includedPaths;	

	//
	// Constructors
	//
	
	public TypeProjection(Class<?> type, ProjectionOptions projection, PathParser parser) {
		build(type, projection, parser);
	}

	private void build(Class<?> type, ProjectionOptions projection, PathParser parser) {
		this.projection = projection;
		build(type, projection.getInclude(), true, parser);
		build(type, projection.getExclude(), false, parser);
		buildIncludedPaths();
	}

	private void build(Class<?> type, String[] paths, boolean value, PathParser parser) {
		if (paths!=null) {
			for (String pathAsString: paths) {
				try {
					Path path = parser.parse(pathAsString, type);
					pathMap.put(path, value);					
				} catch (InvalidPropertyPathException e) {
					if (projection.isRequired()) {
						throw e;
					}
				}
			}
		}		
	}

	
	private void buildIncludedPaths() {
		if (includedPaths==null) {
			includedPaths = new ArrayList<Path>(pathMap.keySet());
			for (Map.Entry<Path, Boolean> e: pathMap.entrySet()) {
				if (e.getValue()) {
					includedPaths.add(e.getKey());
				}
			}
		}		
	}
	
	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code pathMap}.
	 *
	 * @return the pathMap
	 */
	public Map<Path, Boolean> getPathMap() {
		return pathMap;
	}

	/**
	 * Set the value of property {@code pathMap}.
	 *
	 * @param pathMap the pathMap to set
	 */
	public void setPathMap(Map<Path, Boolean> pathMap) {
		this.pathMap = pathMap;
	}

	//
	// Matching
	//
	
	/**
	 * @see org.einnovator.meta.PathCriteria#checkInclude(org.einnovator.meta.Path, boolean)
	 */
	@Override
	public boolean checkInclude(Path path, boolean optional, boolean prefix) {
		Boolean b = pathMap.get(path);
		if (b!=null) {
			return b;
		}
		if (prefix) {
			if (checkAnyIncluded(path)) {
				return true;
			}
		}
		return projection.checkInclude(path.getTail(), optional, false);
	}

	/**
	 * Delegate to the underlying {@code PropertyOptions}.
	 * 
	 * @see org.einnovator.meta.PathCriteria#checkInclude(org.einnovator.meta.Property, boolean)
	 */
	@Override
	public boolean checkInclude(Property<?> property, boolean optional) {
		return projection.checkInclude(property, optional);
	}
	
	@Override
	public boolean checkAnyIncluded(Path path) {
		for (int i=0; i<includedPaths.size(); i++) {
			if (path.prefix(includedPaths.get(i))) {
				return true;
			}
		}			
		return false;
	}
	
	
}
