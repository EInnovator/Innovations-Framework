package org.einnovator.meta;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

import org.einnovator.format.ObjectSupport;
import org.einnovator.util.ArrayUtil;

import static org.einnovator.meta.property.PropertyUtil.isAnyAnnotationPresent;
import static org.einnovator.meta.property.PropertyUtil.isAnyType;
import static org.einnovator.meta.property.PropertyUtil.matchedByAny;

/**
 * Options defining a projection on an object fields or properties.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class ProjectionOptions extends ObjectSupport implements Cloneable, PathCriteria {

	/**
	 * Fields to include in formatting non-simple objects.
	 */
	private String[] include;

	/**
	 * Fields or properties to exclude in formatting non-simple objects.
	 */
	private String[] exclude;

	/**
	 * Annotations types for fields to include in formatting non-simple objects.
	 */
	private Class<? extends Annotation>[] includeAnnotated;

	/**
	 * Annotations types for fields or properties to exclude in formatting non-simple objects.
	 */
	private Class<? extends Annotation>[] excludeAnnotated;
	
	/**
	 * Classes (type) of optional fields to include in formatting non-simple objects.
	 */
	private Class<?>[] includeClasses;

	/**
	 * Classes (type) of fields or properties to exclude in formatting non-simple objects.
	 */
	private Class<?>[] excludeClasses;

	/**
	 * Classes (types) to match the properties to include.
	 */
	private Class<?>[] includeMatching;

	/**
	 * Classes (types) to match the properties to exclude.
	 */
	private Class<?>[] excludeMatching;	
	
	/**
	 * Flag specifying if only explicitly included field should be included.
	 * If <code>false</code>, inclusion refers to the optional field to include.
	 */
	private Boolean complete;
	
	
	/**
	 * Flag specifying if invalid property names or paths should throw an exception (if <code>true</code>), or ignore (if <code>false</code>).
	 */
	private boolean required;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ProjectionOptions}.
	 *
	 */
	public ProjectionOptions() {
	}

	/**
	 * Create instance of {@code ProjectionOptions}.
	 *
	 * @param complete
	 * @param include
	 * @param exclude
	 */
	public ProjectionOptions(boolean complete, String[] include, String[] exclude) {
		this.complete = complete;
		this.include = include;
		this.exclude = exclude;
	}

	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code include}.
	 *
	 * @return the include
	 */
	public String[] getInclude() {
		return include;
	}

	/**
	 * Set the value of property {@code include}.
	 *
	 * @param include the include to set
	 */
	public void setInclude(String[] include) {
		this.include = include;
	}

	/**
	 * Get the value of property {@code exclude}.
	 *
	 * @return the exclude
	 */
	public String[] getExclude() {
		return exclude;
	}

	/**
	 * Set the value of property {@code exclude}.
	 *
	 * @param exclude the exclude to set
	 */
	public void setExclude(String[] exclude) {
		this.exclude = exclude;
	}

	/**
	 * Get the value of property {@code includeAnnotated}.
	 *
	 * @return the includeAnnotated
	 */
	public Class<? extends Annotation>[] getIncludeAnnotated() {
		return includeAnnotated;
	}

	/**
	 * Set the value of property {@code includeAnnotated}.
	 *
	 * @param includeAnnotated the includeAnnotated to set
	 */
	public void setIncludeAnnotated(Class<? extends Annotation>[] includeAnnotated) {
		this.includeAnnotated = includeAnnotated;
	}

	/**
	 * Get the value of property {@code excludeAnnotated}.
	 *
	 * @return the excludeAnnotated
	 */
	public Class<? extends Annotation>[] getExcludeAnnotated() {
		return excludeAnnotated;
	}

	/**
	 * Set the value of property {@code excludeAnnotated}.
	 *
	 * @param excludeAnnotated the excludeAnnotated to set
	 */
	public void setExcludeAnnotated(Class<? extends Annotation>[] excludeAnnotated) {
		this.excludeAnnotated = excludeAnnotated;
	}

	/**
	 * Get the value of property {@code includeClasses}.
	 *
	 * @return the includeClasses
	 */
	public Class<?>[] getIncludeClasses() {
		return includeClasses;
	}

	/**
	 * Set the value of property {@code includeClasses}.
	 *
	 * @param includeClasses the includeClasses to set
	 */
	public void setIncludeClasses(Class<?>[] includeClasses) {
		this.includeClasses = includeClasses;
	}

	/**
	 * Get the value of property {@code excludeClasses}.
	 *
	 * @return the excludeClasses
	 */
	public Class<?>[] getExcludeClasses() {
		return excludeClasses;
	}

	/**
	 * Set the value of property {@code excludeClasses}.
	 *
	 * @param excludeClasses the excludeClasses to set
	 */
	public void setExcludeClasses(Class<?>[] excludeClasses) {
		this.excludeClasses = excludeClasses;
	}
	
	/**
	 * Get the value of property {@code includeMatching}.
	 *
	 * @return the includeMatching
	 */
	public Class<?>[] getIncludeMatching() {
		return includeMatching;
	}

	/**
	 * Set the value of property {@code includeMatching}.
	 *
	 * @param includeMatching the includeMatching to set
	 */
	public void setIncludeMatching(Class<?>[] includeMatching) {
		this.includeMatching = includeMatching;
	}

	/**
	 * Get the value of property {@code excludeMatching}.
	 *
	 * @return the excludeMatching
	 */
	public Class<?>[] getExcludeMatching() {
		return excludeMatching;
	}

	/**
	 * Set the value of property {@code excludeMatching}.
	 *
	 * @param excludeMatching the excludeMatching to set
	 */
	public void setExcludeMatching(Class<?>[] excludeMatching) {
		this.excludeMatching = excludeMatching;
	}

	/**
	 * Get the value of property {@code complete}.
	 *
	 * @return the value of the flag specifying if inclusion is strict (if <code>true</code>) or non-strict (if <code>false</code>)
	 */
	public Boolean getComplete() {
		return complete;
	}

	/**
	 * Set the value of property {@code complete}.
	 *
	 * @param complete flag specifying if inclusion is strict (if <code>true</code>) or non-strict (if <code>false</code>)
	 */
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	/**
	 * Get the value of property {@code required}.
	 *
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Set the value of property {@code required}.
	 *
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Get the value of property {@code projectionMap}.
	 *
	 * @return the projectionMap
	 */
	public Map<String, Boolean> getProjectionMap() {
		return projectionMap;
	}

	/**
	 * Set the value of property {@code projectionMap}.
	 *
	 * @param projectionMap the projectionMap to set
	 */
	public void setProjectionMap(Map<String, Boolean> projectionMap) {
		this.projectionMap = projectionMap;
	}

	/**
	 * Check if this projection defines any constraints on fields or properties to include or exclude.
	 * 
	 * @return <code>true</code>, if this projection does not define any constraints; <code>false</code>, otherwise.
	 */
	public boolean isEmpty() {
		return ArrayUtil.isEmpty(include) && ArrayUtil.isEmpty(exclude) 
			&& ArrayUtil.isEmpty(includeAnnotated) && ArrayUtil.isEmpty(excludeAnnotated)
			&& ArrayUtil.isEmpty(includeClasses) && ArrayUtil.isEmpty(excludeClasses)
			&& ArrayUtil.isEmpty(includeMatching) && ArrayUtil.isEmpty(excludeMatching) ;
	}
	
	private Map<String, Boolean> projectionMap;
	
	public void build() {
		if (projectionMap==null) {
			projectionMap = new LinkedHashMap<String, Boolean>();
		} else if (!projectionMap.isEmpty()) {
			projectionMap.clear();
		}
		if (include!=null) {
			for (String name: include) {
				projectionMap.put(name, Boolean.TRUE);
			}			
		}
		if (exclude!=null) {
			for (String name: exclude) {
				projectionMap.put(name, Boolean.FALSE);
			}			
		}
	}
	
	/**
	 * Check if a {@code Property} is selected by this projection.
	 * 
	 * @param property the {@code Property}
	 * @param optional <code>true</code>, if the {@code Property} should be considered optional
	 * @return <code>true</code>, if the {@code Property} is selected; <code>false</code>, otherwise.
	 * @see org.einnovator.meta.PathCriteria#checkInclude(org.einnovator.meta.Property, boolean)
	 */
	@Override
	public boolean checkInclude(Property<?> property, boolean optional) {
		return checkInclude(property, optional, true);
	}

	/**
	 * Check if a {@code Property} is selected by this projection.
	 * 
	 * @param property the {@code Property}
	 * @param optional <code>true</code>, if the {@code Property} should be considered optional
	 * @path naming flag specifying if property name based criteria should be use in making the check
	 * @return <code>true</code>, if the {@code Property} is selected; <code>false</code>, otherwise.
	 */
	public boolean checkInclude(Property<?> property, boolean optional, boolean naming) {		
		if ((naming && include!=null && ArrayUtil.contains(include, property.getName())) ||
					(includeAnnotated!=null && isAnyAnnotationPresent(property, includeAnnotated)) ||
					(includeClasses!=null && isAnyType(property, includeClasses)) ||
					(includeMatching!=null && matchedByAny(property, includeMatching))) {
			return true;
		}
		if (checkExclude(property, optional, naming)) {
			return false;
		}
		return complete ? false : !optional;
	}

	public boolean checkExclude(Property<?> property, boolean optional, boolean naming) {		
		if (((naming && exclude!=null && ArrayUtil.contains(exclude, property.getName()))) ||
				(excludeAnnotated!=null && isAnyAnnotationPresent(property, excludeAnnotated)) ||
				(excludeClasses!=null && isAnyType(property, excludeClasses)) ||
				(excludeMatching!=null && isAnyType(property, excludeMatching))) {
			return true;
		}	
		return false;
	}
	/**
	 * @see org.einnovator.meta.PathCriteria#checkInclude(org.einnovator.meta.Path, boolean, boolean)
	 */
	@Override
	public boolean checkInclude(Path path, boolean optional, boolean prefix) {
		if (include!=null) {
			if (prefix) {
				if (path.prefixAny(include)) {
					return true;
				}				
			} else {
				if (path.matchAny(include)) {
					return true;
				}
			}
		}
		if (exclude!=null) {
			if (path.prefixAny(exclude)) {
				return false;
			}
		}
		return checkInclude(path.getTail(), optional, false);
	}

	/**
	 * @see org.einnovator.meta.PathCriteria#checkAnyIncluded(org.einnovator.meta.Path)
	 */
	@Override
	public boolean checkAnyIncluded(Path path) {
		if (path.prefixAny(include)) {
			return true;
		}
		return false;
	}
	
	//
	// Assign
	//
	
	public ProjectionOptions assign(Projection projection) {
		if (projection.complete()) {
			complete = true;
		}
		if (projection.include().length>0) {
			include = projection.include();
		}
		if (projection.exclude().length>0) {
			exclude = projection.exclude();
		}
		
		if (projection.includeAnnotated().length>0) {
			includeAnnotated = projection.includeAnnotated();
		}
		if (projection.excludeAnnotated().length>0) {
			excludeAnnotated = projection.excludeAnnotated();
		}
		
		if (projection.includeClasses().length>0) {
			includeClasses = projection.includeClasses();
		}
		if (projection.excludeClasses().length>0) {
			excludeClasses = projection.excludeClasses();
		}

		if (projection.includeOnly().length>0) {
			include = projection.includeOnly();
			complete = true;
		}
		if (projection.value().length>0) {
			include = projection.value();
			complete = true;
		}
		if (projection.includeOnlyAnnotated().length>0) {
			includeAnnotated = projection.includeOnlyAnnotated();
			complete = true;
		}
		if (projection.includeOnlyClasses().length>0) {
			includeClasses = projection.includeOnlyClasses();
			complete = true;
		}

		if (projection.includeMatching().length>0) {
			includeMatching = projection.includeMatching();
			complete = false;
		}
		if (projection.includeOnlyMatching().length>0) {
			includeMatching = projection.includeOnlyMatching();
			complete = true;
		}
		if (projection.excludeMatching().length>0) {
			excludeMatching = projection.excludeMatching();
		}

		return this;
	}
	
	//
	// Fluent API
	//

	/**
	 * Set the value of property {@code complete}.
	 *
	 * @param complete flag specifying if inclusion is strict (if <code>true</code>) or non-strict (if <code>false</code>)
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions complete(boolean complete) {
		this.complete = complete;
		return this;
	}

	/**
	 * Set the value of property {@code complete}.
	 *
	 * @param complete the complete to set
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions required(boolean required) {
		this.required = required;
		return this;
	}

	
	/**
	 * Set the value of property {@code include}.
	 *
	 * @param include a variable number of property names or paths to include
	 * @param complete flag specifying if inclusion is strict (if <code>true</code>) or non-strict (if <code>false</code>)
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions include(boolean complete, String... include) {
		this.complete = complete;
		this.include = include;
		return this;
	}

	/**
	 * Set the value of property {@code include}.
	 *
	 * @param include a variable number of property names or paths to include non-strictly
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions include(String... include) {
		this.complete = false;
		this.include = include;
		return this;
	}

	/**
	 * Set the value of property {@code includeOnly}.
	 *
	 * @param includeOnly a variable number of property names or paths to include strictly
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions includeOnly(String... include) {
		this.complete = true;
		this.include = include;
		return this;
	}
	
	/**
	 * Set the value of property {@code exclude}.
	 *
	 * @param exclude a variable number of property names or paths to exclude
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions exclude(String... exclude) {
		this.exclude = exclude;
		this.complete = false;
		return this;
	}
	
	/**
	 * Set the value of property {@code includeAnnotated}.
	 *
	 * @param includeAnnotated a variable number of annotation for non-strictly included properties
	 * @param complete flag specifying if inclusion is strict (if <code>true</code>) or non-strict (if <code>false</code>)
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions include(boolean complete, Class<? extends Annotation>... includeAnnotated) {
		this.complete = complete;
		this.includeAnnotated = includeAnnotated;
		return this;
	}
	
	/**
	 * Set the value of property {@code includeAnnotated}.
	 *
	 * @param includeAnnotated a variable number of annotation for non-strictly included properties
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions includeAnnotated(Class<? extends Annotation>... includeAnnotated) {
		this.complete = false;
		this.includeAnnotated = includeAnnotated;
		return this;
	}
	
	/**
	 * Set the value of property {@code includeOnlyAnnotated}.
	 *
	 * @param includeOnlyAnnotated a variable number of annotation for strictly included properties
	 * @return this {@code ProjectionOptions}
	 */
	@SuppressWarnings("unchecked")
	public ProjectionOptions includeOnlyAnnotated(Class<?>... includeAnnotated) {
		this.complete = true;
		this.includeAnnotated = (Class<? extends Annotation>[]) includeAnnotated;
		return this;
	}
	
	/**
	 * Set the value of property {@code excludeAnnotated}.
	 *
	 * @param excludeAnnotated a variable number of annotation for excluded properties
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions excludeAnnotated(Class<? extends Annotation>... excludeAnnotated) {
		this.excludeAnnotated = excludeAnnotated;
		return this;
	}

	/**
	 * Set the value of property {@code includeClasses}.
	 *
	 * @param includeClasses a variable number of types to include non-strictly
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions includeClasses(Class<?>... includeClasses) {
		this.complete = false;
		this.includeClasses = includeClasses;
		return this;
	}
	
	/**
	 * Set the value of property {@code includeOnlyClasses}.
	 *
	 * @param excludeClasses a variable number of types to include non-strictly
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions includeOnlyClasses(Class<?>... includeClasses) {
		this.complete = true;
		this.includeClasses = includeClasses;
		return this;
	}
	
	/**
	 * Set the value of property {@code excludeClasses}.
	 *
	 * @param excludeClasses a variable number of property types to exclude
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions excludeClasses(Class<?>... excludeClasses) {
		this.excludeClasses = excludeClasses;
		return this;
	}

	/**
	 * Set the value of property {@code includeOnlyMatching}.
	 *
	 * @param excludeClasses a variable number of types to include non-strictly
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions includeMatching(Class<?>... includeMatching) {
		this.complete = false;
		this.includeMatching = includeMatching;
		return this;
	}

	/**
	 * Set the value of property {@code includeOnlyMatching}.
	 *
	 * @param excludeClasses a variable number of types to include non-strictly
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions includeOnlyMatching(Class<?>... includeMatching) {
		this.complete = true;
		this.includeMatching = includeMatching;
		return this;
	}

	/**
	 * Set the value of property {@code excludeMatchingClasses}.
	 *
	 * @param excludeClasses a variable number of property types to exclude
	 * @return this {@code ProjectionOptions}
	 */
	public ProjectionOptions excludeMatchingClasses(Class<?>... excludeMatching) {
		this.excludeMatching = excludeMatching;
		return this;
	}

	//
	// Static Utility
	//
	
	public static ProjectionOptions newInstance() {
		return new ProjectionOptions().complete(true).required(false);
	}
	
	
	public static ProjectionOptions clone(ProjectionOptions options) {
		try {
			return (ProjectionOptions) options.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Check if a {@code Projection} annotation defines any constraints on fields or properties to include or exclude.
	 * 
	 * @return <code>true</code>, if the {@code Projection} annotation does not define any constraints;
	 *  <code>false</code>, otherwise.
	 */
	public static boolean isEmpty(Projection projection) {
		return ArrayUtil.isEmpty(projection.value()) && 
			ArrayUtil.isEmpty(projection.include()) && ArrayUtil.isEmpty(projection.exclude()) 
			&& ArrayUtil.isEmpty(projection.includeAnnotated()) && ArrayUtil.isEmpty(projection.excludeAnnotated())
			&& ArrayUtil.isEmpty(projection.includeClasses()) && ArrayUtil.isEmpty(projection.excludeClasses())
			&& ArrayUtil.isEmpty(projection.includeOnly())
			&& ArrayUtil.isEmpty(projection.includeOnlyAnnotated())
			&& ArrayUtil.isEmpty(projection.includeOnlyClasses())
			&& ArrayUtil.isEmpty(projection.includeMatching())
			&& ArrayUtil.isEmpty(projection.includeOnlyMatching())
			&& ArrayUtil.isEmpty(projection.excludeMatching());
	}

	/**
	 * Check if the specified {@code ProjectionOptions} is <code>null</code> or empty.
	 * 
	 * The check for the non <code>null</code> case is done by delegating to {@link ProjectionOptions#isEmpty()}.
	 * 
	 * @return <code>true</code>, if {@code ProjectionOptions} is <code>null</code> or empty; <code>false</code>, otherwise.
	 */
	public static boolean isEmpty(ProjectionOptions projection) {
		return projection==null || projection.isEmpty();
	}
}
