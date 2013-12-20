/**
 * 
 */
package org.einnovator.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.einnovator.util.types.TypeUtil;


/**
 * A ProxyUtil.
 *
 * @author Jorge Simão
 */
public class ProxyUtil {
	static public Annotation createAnnotation(final Class<? extends Annotation> qualifier) {
		return  (Annotation)Proxy.newProxyInstance(qualifier.getClassLoader(), new Class<?>[] {qualifier}, new InvocationHandler() {
			public Object invoke(Object proxy,
		              Method method,
		              Object[] args)
		              throws Throwable {
				if (method.getName().equals("annotationType")) {
					return qualifier;
				}
				if (method.getName().equals("hashCode")) {
					return 1;
				}

				if (method.getReturnType().equals(String.class)) {
					return "";
				}
				return TypeUtil.zero(method.getReturnType());
			}
		});
	}

}
