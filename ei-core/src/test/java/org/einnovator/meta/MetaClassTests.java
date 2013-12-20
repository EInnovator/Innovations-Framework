package org.einnovator.meta;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;

import javax.management.MXBean;

import org.einnovator.convert.NumberFormat;
import org.einnovator.meta.MetaClass;
import org.einnovator.meta.Property;
import org.junit.Test;



public class MetaClassTests {


	@Test 
	public void metaClassTests() {
		
		MetaClass<A> mcA = checkMetaClass(A.class, null, 2, 2, MXBean.class);
		checkProperty(mcA, "name", String.class);
		checkProperty(mcA, "balance", Double.TYPE, NumberFormat.class);

		MetaClass<B> mcB = checkMetaClass(B.class, A.class, 2, 3);
		checkProperty(mcB, "name", String.class);
		checkProperty(mcB, "rating", Integer.TYPE, NumberFormat.class);
		checkProperty(mcB, "balance", BigDecimal.class);
		
		A a = mcA.newInstance();
		assertNotNull(a);

		B b = mcB.newInstance();
		assertNotNull(b);


	}

	protected <T> MetaClass<T> checkMetaClass(Class<T> type, Class<?> parent, int numberDeclaredProperties, int numberAllProperties, Class<?>... annotationClasses) {
		MetaClass<T> metaClass = MetaOperations.getMetaClass(type);
		assertNotNull(metaClass);
		assertEquals(type, metaClass.getTheClass());
		assertEquals(type.getName(), metaClass.getName());
		if (parent!=null) {
			assertNotNull(metaClass.getParent());
			assertEquals(parent.getName(), metaClass.getParent().getName());
		}
		assertNotNull(metaClass.getAllProperties());
		//System.out.println(Arrays.toString(metaClass.getDeclaredProperties()));
		//System.out.println(Arrays.toString(metaClass.getAllProperties()));
		assertEquals(numberDeclaredProperties, metaClass.getDeclaredProperties().length);
		assertEquals(numberAllProperties, metaClass.getAllProperties().length);
		assertNotNull(metaClass.getAnnotations());
		assertEquals(annotationClasses.length, metaClass.getAnnotations().length);
		for (Class<?> annotationClass: annotationClasses) {
			@SuppressWarnings("unchecked")
			Class<? extends Annotation> annotationClass2 = (Class<? extends Annotation>)annotationClass;
			assertNotNull(metaClass.getAnnotation(annotationClass2));
			
		}
		return metaClass;
	}
		
	protected <T> Property<T> checkProperty(MetaClass<?> metaClass, String name, Class<T> type, Class<?>... annotationClasses) {
		Property<T> property = metaClass.getProperty(name, type);
		assertNotNull(property);
		assertEquals(name, property.getName());
		assertEquals(type, property.getType());
		assertNotNull(property.getAnnotations());
		assertEquals(annotationClasses.length, property.getAnnotations().length);
		for (Class<?> annotationClass: annotationClasses) {
			@SuppressWarnings("unchecked")
			Class<? extends Annotation> annotationClass2 = (Class<? extends Annotation>)annotationClass;
			assertNotNull(property.getAnnotation(annotationClass2));
			
		}
		return property;
	}
}
