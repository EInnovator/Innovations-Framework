/**
 * 
 */
package org.einnovator.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.einnovator.meta.MetaUtil;
import org.einnovator.util.types.TypeUtil;
import org.junit.Ignore;
import org.junit.Test;


/**
 * A {@code TypeUtil}.
 *
 * @author Jorge Simão
 */
public class TypeUtilTests {

	class A<T> {
		T x;
	}
	interface I<K> {
		K getK();
	}
	abstract class C<T,K> extends A<T> implements I<K> {		
		K y;

		public K getK() { return null; }

	}

	class B extends A<Long> implements I<String> {
		public String getK() { return null; }
	}

	class D extends C<Long, String> {
	}
	
	@Test
	public void getTypeTest() {
		Field field = MetaUtil.getRequiredField(B.class, "x");
		System.out.println(TypeUtil.getType(B.class, field));
		
		field = MetaUtil.getRequiredField(D.class, "y");
		System.out.println(TypeUtil.getType(D.class, field));

		Method method = MetaUtil.getGetter(D.class, "K");
		System.out.println(TypeUtil.getType(B.class, method));
	
	}
	
	@Test
	@Ignore
	public void test() {
		Field field = MetaUtil.getRequiredField(B.class, "x");
		
		TypeVariable<?> ftype = (TypeVariable<?> )field.getGenericType();
		System.out.println(ftype + " " + ftype.getClass() + " " + ftype.getGenericDeclaration());
		for (Type gtype: ftype.getBounds()) {
			System.out.println(gtype + " " + gtype.getClass());	
		}
		System.out.println("---");

		for (TypeVariable<?> gtype: B.class.getTypeParameters()) {
			System.out.println(gtype + " " + gtype.getName());	
		}
		System.out.println("---");

		for (Type gtype: B.class.getGenericInterfaces()) {
			System.out.println(gtype + " " + gtype.getClass());	
		}
		ParameterizedType gtype0 = (ParameterizedType) B.class.getGenericSuperclass();
		System.out.println(gtype0);	
		System.out.println(gtype0.getRawType());	
		System.out.println(gtype0.getOwnerType());	
		System.out.println(gtype0.getClass());	
		System.out.println(TypeUtil.getTypeArgument(gtype0, 0));
		for (Type gtype: gtype0.getActualTypeArguments()) {
			System.out.println(gtype + " " + gtype.getClass() + " ");	
		}
		System.out.println(TypeUtil.getTypeArgument(field));
	}

}
