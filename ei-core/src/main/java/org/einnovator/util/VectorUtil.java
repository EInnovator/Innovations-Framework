package org.einnovator.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.einnovator.util.types.TypeUtil.cast;

/**
 * A VectorUtil.
 *
 * @author Jorge Simão
 */
public class VectorUtil {

	public static Class<?> getComponentType(Object l) {	
		if (l==null) {
			return Object.class;
		}
		return getComponentType(l.getClass());
	}

	public static Class<?> maxtype(Class<?> ty0, Class<?> ty1) {
		if (ty0.equals(ty1)) {
			return ty0;
		}
		return Object.class;
	}
	
	public static Class<?> maxtype(Object l0, Object l1) {
		Class<?> ty0 = l0!=null ? l0.getClass() : Object.class;
		Class<?> ty1 = l1!=null ? l1.getClass() : Object.class;
		return maxtype(ty0, ty1);
	}
	
	public static Class<?> maxetype(Class<?> ty0, Class<?> ty1) {
		Class<?> ety0 = getComponentType(ty0);
		Class<?> ety1 = getComponentType(ty1);
		return maxtype(ety0, ety1);
	}	
	public static Class<?> maxetype(Object l0, Object l1) {
		Class<?> ty0 = l0!=null ? l0.getClass() : Object.class;
		Class<?> ty1 = l1!=null ? l1.getClass() : Object.class;
		return maxetype(ty0, ty1);
	}	

	public static int size(Object l) {
		if (l==null) {
			return 0;
		}
		if (l instanceof Collection) {
			return ((Collection<?>)l).size();			
		}
		if (l.getClass().isArray()) {
			return Array.getLength(l);
		}
		return 1;
	}

	public static Object get(int i, Object l) {
		if (l instanceof List<?>) {
			return ((List<?>)l).get(i);	
		}
		if (l.getClass().isArray()) {
			return Array.get(l, i);
		}
		return l;
	}
	public static int getInt(int i, Object l) {
		return ((Integer)cast(get(i,l), Integer.class)).intValue();
	}
	
	public static double getDouble(int i, Object l) {
		return ((Double)cast(get(i,l), Double.class)).doubleValue();
	}
	
	public static Object xget(int i, Object l) {
		int n = size(l);
		if (n==0) {
			return null;
		}
		return get(i%n, l);
	}
	
	@SuppressWarnings("unchecked")
	public static void set(int i, Object l, Object v) {
		if (l instanceof List<?>) {
			((List<Object>)l).set(i, v);
			return;
		}	
		if (l.getClass().isArray()) {
			Array.set(l, i, v);
		}
	}
	
	public static void xset(int i, Object l, Object v) {
		int n = size(l);
		if (n==0) {
			return;
		}
		set(i%n, l, v);
	}

	public static double sum(Object l) {
		int n = size(l);
		if (n==0) {
			return Double.NaN;
		}
		double s = 0;
		for (int i=0; i<n; i++) {
			Double d = (Double) cast(get(i,l), Double.class);
			if (d==null) {
				continue;
			}
			s += d.doubleValue();
		}
		return s;
	}
	
	public static Object sum(Object l0, Object l1) {
		int n0 = size(l0);
		int n1 = size(l1);
		int n = Math.max(n0, n1);		
		Object l = newList(maxetype(l0, l1), n);
		for (int i=0; i<n; i++) {
			Object k0 = xget(i, l0);
			Object k1 = xget(i, l1);
			Object v = null;
			if (k0==null) v = k1;
			else if (k1==null) v = k0;
			else {
				Double d0 = (Double) cast(k0, Double.class);
				Double d1 = (Double) cast(k1, Double.class);
				v = d0 + d1; //todo: concat if strings
				Class<?> type = maxtype(k0, k1);
				v = cast(v, type);
			}
			set(i, l, v);			
		}
		return l;
	}

	public static double sum(double[] x) {
		if (x.length==0) {
			return Double.NaN;
		}
		double s = 0;
		for (int i=0; i<x.length; i++) {
			s += x[i];
		}
		return s;
	}
	
	public static double min(double[] x) {
		if (x.length==0) {
			return Double.NaN;
		}
		double min = x[0];
		for (int i=1; i<x.length; i++) {
			if (x[i]<min) min = x[i];
		}
		return min;
	}
	
	public static double min(Object l) {
		int n = size(l);
		if (n==0) return Double.NaN;
		Double min = null;
		for (int i=1; i<n; i++) {
			Double d = (Double) cast(get(i,l), Double.class);	
			if (d!=null && (min==null || d.doubleValue()<min.doubleValue())) {
				min = d;
			}
		}
		return min!=null ? min : Double.NaN;
	}

	public static double max(double[] x) {
		if (x.length==0) {
			return Double.NaN;
		}
		double max = x[0];
		for (int i=1; i<x.length; i++) {
			if (x[i]>max) max = x[i];
		}
		return max;
	}
	
	public static double max(Object l) {
		int n = size(l);
		if (n==0) return Double.NaN;
		Double max = null;
		for (int i=1; i<n; i++) {
			Double d = (Double) cast(get(i,l), Double.class);	
			if (d!=null && (max==null || d.doubleValue()>max.doubleValue())) {
				max = d;
			}
		}
		return max!=null ? max : Double.NaN;
	}

	public static double mean(double[] x) {
		if (x.length==0) {
			return Double.NaN;
		}
		return sum(x)/x.length;
	}
	
	public static double mean(Object x) {
		int n = size(x);
		if (n==0) {
			return Double.NaN;
		}
		return sum(x)/n;
	}
	
	public static Object seq(double x0, double dx, int n) {
		Object l = newList(double.class, n);
		double x=x0;
		for (int i=0; i<n; x+=dx, i++) { set(i, l, x); }
		return l;
	}

	public static Object seq(int i0, int step, int n) {
		Object l = newList(Object.class, n);
		for (int i=i0, j=0; j<n; i+=step,j++) { set(j, l, i); }
		return l;
	}
	
	public static Object seq(int i0, int n) {
		Object l = newList(Object.class, n);
		for (int i=i0, j=0; j<n; i++,j++) {
			set(j, l, i);
		}
		return l;
	}
	
	public static Object seq(int n) {
		return seq(0, n);
	}

	public static Object cp(Object l, int n) {
		if (l instanceof Object[]) {
			return Arrays.copyOf((Object[])l, n);
		}
		if (l instanceof int[]) {
			return Arrays.copyOf((int[])l, n);
		}
		if (l instanceof short[]) {
			return Arrays.copyOf((short[])l, n);
		}
		if (l instanceof long[]) {
			return Arrays.copyOf((long[])l, n);
		}
		if (l instanceof byte[]) {
			return Arrays.copyOf((byte[])l, n);
		}
		if (l instanceof double[]) {
			return Arrays.copyOf((double[])l, n);
		}
		if (l instanceof float[]) {
			return Arrays.copyOf((float[])l, n);
		}
		if (l instanceof boolean[]) {
			return Arrays.copyOf((boolean[])l, n);
		}
		Object[] l1 = new Object[n];
		l1[0] = l;
		return l1;
	}
	public static Object cp(Object l0, int i0, Object l1, int j0, int n) {
		for (int i=i0, j=j0; i<i0+n; i++, j++) {
			set(j, l1, get(i, l0));
		}
		return l1;
	}
	public static Object cp(Object l0, int i0, Object l1, int n) {
		return cp(l0, i0, l1, 0, n);
	}
	
	public static Object cp(Object l0, Object l1, int n) {
		return cp(l0, 0, l1, 0, n);
	}
	
	public static Object cp(Object l0, Object l1, int j0, int n) {
		return cp(l0, 0, l1, j0, n);
	}
	
	public static boolean isEmpty(Object l) {
		return size(l)==0;
	}
	
	public static Object[] empty() {
		return new Object[0];
	}

	public static Object cons(Object l1) {
		Object[] l = new Object[1];
		l[0] = l1;
		return l;
	}

	public static Object cons(Object l0, Object l1) {
		Object l = newList(maxtype(l0,l1), 2);
		set(0, l, l0);
		set(1, l, l1);
		return l;
	}
	
	public static Object append(Object l0, Object o) {
		int n = size(l0);
		Object l = cp(l0, n+1);
		set(n, l,  o);
		return l;
	}

	public static Object newList(int n) {
		return newList(Object.class, n);
	}

	public static Object newList(Object l, int n) {
		Class<?> type = l!=null ? l.getClass() : null;
		return newList(type, n);
	}
	
	public static Object newList(Class<?> type, int n) {
		if (type==null) return new Object[n];
		if (type.equals(int.class)) {
			return new int[n];	
		}
		Class<?> componentType;
		if (type.isArray()) {
			componentType = type.getComponentType();
		} else {
			componentType = type;
		}
		return Array.newInstance(componentType, n);
	}
	

	public static Object union(Object l1, Object l2) {
		int n1 = size(l1), n2 = size(l2);
		Object l = newList(maxetype(l1,l2), n1+n2);
		int j = 0;
		for (int i=0; i<n1; i++, j++) {
			set(j, l, get(i, l1));
		}
		for (int i=0; i<n2; i++, j++) {
			set(j, l, get(i, l2));
		}
		return l;
	}

	public interface Mergable {
		Object merge(Object obj);
	}
	
	public static Object merge(Object l0, int i, int j) {
		int n = size(l0);
		if (i==j || n<=1) return l0;
		if (j<i) { int k = j; j = i; i = k; }
		Object l = newList(getComponentType(l0), n-1);
		if (i>0) cp(l0,l,0,i);
		Object oi = get(i, l0), oj = get(j, l0);
		Object ij = (oi instanceof Mergable && oj instanceof Mergable) ? 
				((Mergable)oi).merge(oj) :
				union(get(i, l0), get(j, l0));
		//System.out.printf("%s %s = %s %s %s %s%n", oi, oj, ij, ij.getClass(), size(ij), toString(ij));			
		//println(ij);
		set(i, l, ij);
		if (i+1<n && j-i-1>0) cp(l0,i+1, l, i+1, j-i-1);
		if (j+1<n && n-j-1>0) cp(l0,j+1, l, j, n-j-1);
		return l;
	}
	
	public static String toString(Object l) {
		if (l==null) return "null";
		int n = size(l);
		if (n==0) return "[]";
		if (n==1) {
			if (get(0, l)==get(0, get(0, l))) {
				return l.toString();	
			}
		}
		StringBuffer sb = new StringBuffer("[");
		for (int i=0; i<n; i++) {
			sb.append(toString(get(i, l)));
			if (i<n-1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static void println(Object l) {
		print(l);
		System.out.println();
	}
	
	public static void print(Object l) {
		System.out.print(toString(l));
	}
	
	public static void main_(String[] args) {

		Object[] a = new Integer[]{1,2,3};
		Object[] b = new Integer[]{4,5,6};
		Object c = union(a, b);
		println(c);
		System.out.println(sum(c));
		c = union(c, 7);
		println(c);
		System.out.println(c.getClass());
		
		Object ia = new int[]{1,2,3};
		Object ib = new int[]{4,5,6};
		Object ic = union(ia, ib);
		println(ic);
		System.out.println(sum(ic));		
		System.out.println(ic.getClass());
		
		Object m = merge(c, 1,2);
		println(m);
		println(sum(a,10));

		Object l = seq(3);
		println(l);
		l = merge(l, 0, 1);
		println(l);
	}
}