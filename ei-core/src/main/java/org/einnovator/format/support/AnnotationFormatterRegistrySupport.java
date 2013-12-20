/**
 * 
 */
package org.einnovator.format.support;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.HashMap;

import org.einnovator.format.AnnotationFormatter;
import org.einnovator.format.AnnotationFormatterRegistry;


/**
 * AA AnnotationFormatterRegistrySupport.
 *
 * @author Jorge Simão
 */
public class AnnotationFormatterRegistrySupport extends FormatterRegistrySupport 
	implements AnnotationFormatterRegistry {

	protected Map<Class<? extends Annotation>, AnnotationFormatter<?,?>> annotationFormatterMap = 
			new HashMap<Class<?  extends Annotation>, AnnotationFormatter<?,?>>();


	//
	// Constructors
	//
	
	/**
	 * Create instance of AnnotationFormatterRegistrySupport.
	 *
	 */
	public AnnotationFormatterRegistrySupport() {
	}

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of annotationFormatterMap.
	 *
	 * @return the annotationFormatterMap
	 */
	public Map<Class<? extends Annotation>, AnnotationFormatter<?, ?>> getAnnotationFormatterMap() {
		return annotationFormatterMap;
	}

	/**
	 * Set the value of annotationFormatterMap.
	 *
	 * @param annotationFormatterMap the annotationFormatterMap to set
	 */
	public void setAnnotationFormatterMap(
			Map<Class<? extends Annotation>, AnnotationFormatter<?, ?>> annotationFormatterMap) {
		this.annotationFormatterMap = annotationFormatterMap;
	}	

	//
	// AnnotationFormatterRegistry implementation
	//

	@Override
	public void addAnnotationFormatter(AnnotationFormatter<?, ?> formatter,
			Class<? extends Annotation> annotationType) {
		annotationFormatterMap.put(annotationType, formatter);		
	}



	@Override
	public AnnotationFormatter<?, ?> getAnnotationFormatter(Annotation annotation) {
		return annotationFormatterMap.get(annotation.annotationType());
	}


	@Override
	public void removeAnnotationFormatter(AnnotationFormatter<?, ?> formatter) {
		annotationFormatterMap.remove(formatter);
	}

	@Override
	public void clear() {
		super.clear();
		annotationFormatterMap.clear();
	}

}
