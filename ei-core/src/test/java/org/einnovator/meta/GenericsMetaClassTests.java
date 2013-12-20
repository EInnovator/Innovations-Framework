package org.einnovator.meta;

import java.util.Arrays;

import org.einnovator.meta.MetaClass;
import org.einnovator.meta.MetaClassResolver;
import org.junit.Test;

public class GenericsMetaClassTests {

	static class A<T> {
		T x;
	}
	interface I<K> {
		K getK();
	}
	static abstract class C<T,K> extends A<T> implements I<K> {		
		K y;

		public K getK() { return null; }

	}

	static class B extends A<Long> implements I<String> {
		public String getK() { return null; }
	}

	static class B2 extends B {
	}

	static class D extends C<Long, String> {
	}
	

	
	@Test 
	public void metaClassTests() {
		MetaClassResolver resolver = MetaOperations.getMetaClassResolver();
		//MetaClass<?> mcB = resolver.getMetaClass(B.class);
		//System.out.println(Arrays.toString(mcB.getAllProperties()));
		MetaClass<?> mcB2 = resolver.getMetaClass(B2.class);
		System.out.println(Arrays.toString(mcB2.getAllProperties()));

	}
}
