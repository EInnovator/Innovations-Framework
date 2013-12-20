package org.einnovator.format;

import java.lang.annotation.Annotation;


/**
 * AA FormatterRegistry.
 *
 * @author Jorge Simão
 */
public interface AnnotationFormatterRegistry {
	
	void addAnnotationFormatter(AnnotationFormatter<?,?> formatter, Class<? extends Annotation> annotationType);

	AnnotationFormatter<?,?> getAnnotationFormatter(Annotation annotation);
	
	void removeAnnotationFormatter(AnnotationFormatter<?,?> formatter);
	
	void clear();
}
