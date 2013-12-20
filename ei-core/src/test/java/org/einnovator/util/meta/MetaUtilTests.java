/**
 * 
 */
package org.einnovator.util.meta;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.einnovator.meta.MetaUtil;
import org.junit.Test;


/**
 * A MetaUtilTests.
 *
 * @author Jorge Simão
 */
public class MetaUtilTests {

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public static @interface A {		
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@A
	public static @interface A2 {		
	}

	@A
	public static class B {
	}

	@A2
	public static class B2 {
	}

	@Test
	public void getAnnotationTransitiveTest() {
		assertNotNull(MetaUtil.getAnnotationTransitive(B.class, A.class));
		assertNotNull(MetaUtil.getAnnotationTransitive(B2.class, A2.class));
		assertNotNull(MetaUtil.getAnnotationTransitive(B2.class, A.class));
	}

	@Test
	public void getQualifiersAnnotationTest() {
		Annotation[] qualifiers = MetaUtil.getQualifiers(B2.class, A.class);
		assertEquals(1, qualifiers.length);
		assertEquals(A2.class, qualifiers[0].annotationType());
	}

	@Test
	public void getMetaAnnotationTest() {
		assertNotNull(MetaUtil.getMetaAnnotation(B.class, A.class));
		assertEquals(B.class, MetaUtil.getMetaAnnotation(B.class, A.class));
		assertNotNull(MetaUtil.getMetaAnnotation(B2.class, A2.class));
		assertEquals(B2.class, MetaUtil.getMetaAnnotation(B2.class, A2.class));
		assertNotNull(MetaUtil.getMetaAnnotation(B2.class, A.class));
		assertEquals(A2.class, MetaUtil.getMetaAnnotation(B2.class, A.class));
	}

}
