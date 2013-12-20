package org.einnovator.util.types;

import java.awt.Color;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.einnovator.meta.MetaException;
import org.einnovator.meta.MetaUtil;



/**
 * Miscellaneous type utility operations.
 *  
 * @author Jorge Simão
 */
public class TypeUtil {
	
	public static boolean isCollection(Class<?> type) {
		return type.isArray() || isResizableCollection(type);
	}

	public static boolean isResizableCollection(Class<?> type) {
		return Collection.class.isAssignableFrom(type) || TypedCollection.class.isAssignableFrom(type);
	}

	public static boolean isIndexedCollection(Class<?> type) {
		if (type.isArray()) {
			return true;
		}
		if (List.class.isAssignableFrom(type)) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object value) {
		return sizeSafe(value)==0;
	}

	public static int size(Object obj) {
		Class<?> type = obj.getClass();
		if (type.isArray()) {
			return Array.getLength(obj);
		}
		if (Collection.class.isAssignableFrom(type)) {
			return ((Collection<?>)obj).size();
		}
		return -1;
	}

	public static int sizeSafe(Object obj) {
		int n = size(obj);
		if (n>=0) {
			return n;
		}
		throw new RuntimeException("Object of type '" + obj.getClass().getName() + "' is not an array or collection");
	}

	public static Object getComponent(Object obj, int index) {
		Class<?> type = obj.getClass();
		if (type.isArray()) {
			return Array.get(obj, index);
		}
		if (List.class.isAssignableFrom(type)) {
			return ((List<?>)obj).get(index);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void setComponent(Object obj, int index, Object value) {
		Class<?> type = obj.getClass();
		if (type.isArray()) {
			Array.set(obj, index, value);
		} else if (List.class.isAssignableFrom(type)) {
			((List<Object>)obj).set(index, value);
		}
	}
	
	public static Class<?> getComponentType(Member member) {	
		Class<?> type = MetaUtil.getType(member);
		if (type.isArray()) {
			return type.getComponentType();
		}
		return getTypeArgument(member, 0);
	}

	public static Class<?> getMapKeyType(Member member) {	
		return getTypeArgument(member, 0);
	}

	public static Class<?> getMapValueType(Member member) {	
		return getTypeArgument(member, 0);
	}

	public static Class<?> getTypeArgument(Member member, int index) {	
		Type gtype = MetaUtil.getGenericType(member);
		return getTypeArgument(gtype, index);
	}

	public static Class<?> getTypeArgument(Field field) {	
		Type gtype = field.getGenericType();
		return getTypeArgument(gtype, 0);
	}

	public static Class<?> getTypeArgument(Method method) {	
		Type gtype = method.getGenericReturnType();
		return getTypeArgument(gtype, 0);
	}

	
	public static Class<?> getTypeArgument(Type gtype, int index) {	
		if (gtype instanceof ParameterizedType) {
			Type[] actualTypes = ((ParameterizedType)gtype).getActualTypeArguments();
			if (actualTypes.length>index && actualTypes[index] instanceof Class) {
				return (Class<?>)actualTypes[index];
			}
		}
		return null;
	}

	public static Type getGenericInterface(Class<?> type, Class<?> interfaceType) {	
		Type[] gtypes = type.getGenericInterfaces();
		for (Type gtype: gtypes) {
			if (gtype instanceof ParameterizedType)  {
				ParameterizedType ptype = (ParameterizedType)gtype;
				if (ptype.getRawType().equals(interfaceType)) {
					return gtype;
				}
			}
		}
		return null;
	}
	
	public static Class<?> getType(Class<?> theClass, Field field) {
		Type gtype = field.getGenericType();
		if (gtype!=null && gtype instanceof TypeVariable) {
			TypeVariable<?> var = (TypeVariable<?>)gtype;
			Class<?> type = getGenericParameterType(theClass, var.getName());
			if (type!=null) {
				return type;
			}
		}
		return field.getType();
	}

	public static Class<?> getType(Class<?> theClass, Method method) {
		Type gtype = method.getGenericReturnType();
		if (gtype!=null && gtype instanceof TypeVariable) {
			TypeVariable<?> var = (TypeVariable<?>)gtype;
			Class<?> type = getGenericParameterType(theClass, var.getName());
			if (type!=null) {
				return type;
			}
		}
		return method.getReturnType();
	}

	public static Class<?> getGenericParameterType(Class<?> type, String name) {
		if (type.getSuperclass()!=null) {
			TypeVariable<?>[] vars = type.getSuperclass().getTypeParameters();			
			int i = 0;
			for (TypeVariable<?> var: vars) {
				if (var.getName().equals(name)) {
					Type gtype = type.getGenericSuperclass();
					if (gtype instanceof ParameterizedType) {
						Type[] types = ((ParameterizedType)gtype).getActualTypeArguments();
						Type atype = types[i];
						if (atype instanceof Class) {
							return (Class<?>)atype;
						}
						return null;
						//throw new MetaException("Unable to determined actual type for generic type variable " 
						//+ name + " in " + type);
					}
				}
				i++;
			}
		}
		return null;
	}

	public static Class<?> getComponentType(Object obj) {
		Class<?> type = obj.getClass();
		if (type.isArray()) {
			return type.getComponentType();
		}
		if (TypedCollection.class.isAssignableFrom(type)) {
			return ((TypedCollection<?>)obj).getComponentType();
		}
		if (List.class.isAssignableFrom(type)) {
			if (((List<?>)obj).size()>0) {
				return ((List<?>)obj).get(0).getClass();
			}
		}
		
		if (Collection.class.isAssignableFrom(type)) {
			Iterator<?> it = ((Collection<?>)obj).iterator();
			return it.hasNext() ? it.next().getClass() : Object.class;
		}

		if (Collection.class.isAssignableFrom(type)) {
			return Object.class; //hack: no way to get generic component type
		}
		return null;
	}

	
	public static Class<?> getComponentType(Class<?> type) {
		if (type.isArray()) {
			return type.getComponentType();
		}
		if (Collection.class.isAssignableFrom(type)) {
			return Object.class; //hack: no way to get generic component type			
		}
		return type;
	}

	public static boolean isPrimitiveWrapper(Class<?> type) {
		return type.equals(Integer.class) || type.equals(Short.class) ||
		type.equals(Long.class) || type. equals(Byte.class) ||
		type.equals(Float.class) || type.equals(Double.class) ||
		type.equals(Character.class) || type.equals(Boolean.class);
	}

	public static boolean isPrimitive(Class<?> type) {
		return type.isPrimitive() || isPrimitiveWrapper(type);
	}

	public static boolean isCharacter(Class<?> type) {
		return String.class.equals(Character.class) || 
			String.class.equals(Character.TYPE);
	}

	public static boolean isText(Class<?> type) {
		return String.class.equals(type) || StringBuffer.class.equals(type) || StringBuilder.class.equals(type);
	}
	
	public static boolean isSimple(Class<?> type) {
		return isNumeric(type) || isBool(type) || isCharacter(type)
				|| String.class.isAssignableFrom(type) 
				|| Date.class.isAssignableFrom(type) 
				|| type==Color.class 
				|| type.isEnum();
	}
	
	public static boolean isPrimitiveOrSimple(Class<?> type) {
		return type.isPrimitive() || isPrimitiveWrapper(type) || isSimple(type);
	}

	public static boolean isNumeric(Class<?> type) {
		return isNumber(box(type));
	}

	public static boolean isFixedSizeNumber(Class<?> type) {
		return isDecimal(type) || isInt(type);
	}

	public static boolean isNumber(Class<?> type) {
		return Number.class.isAssignableFrom(type);
	}
	
	public static boolean isDecimal(Class<?> type) {
		return type==Float.TYPE || type==Float.class || type==Double.TYPE || type==Double.class;
	}

	public static boolean isFloat(Class<?> type) {
		return type==Float.TYPE || type==Float.class;
	}

	public static boolean isDouble(Class<?> type) {
		return type==Double.TYPE || type==Double.class;
	}

	public static boolean isInt(Class<?> type) {
		return type==Short.TYPE || type==Short.class ||
		type==Integer.TYPE || type==Integer.class ||
		type==Long.TYPE || type==Long.class ||
		type==Byte.TYPE || type==Byte.class;
	}

	public static boolean isLong(Class<?> type) {
		return type==Long.TYPE || type==Long.class;
	}

	public static boolean isShort(Class<?> type) {
		return type==Short.TYPE || type==Short.class ;
	}

	public static boolean isInteger(Class<?> type) {
		return type==Integer.TYPE || type==Integer.class;
	}

	public static boolean isByte(Class<?> type) {
		return type==Byte.TYPE || type==Byte.class;
	}

	public static boolean isBool(Class<?> type) {
		return type==Boolean.TYPE || type==Boolean.class;
	}

	public static boolean isFalse(Object value) {
		return !isTrue(value);
	}
	
	public static boolean isTrueStrict(String s) {
		s = s.toLowerCase();
		return s.equals("true");
	}

	public static boolean isTrue(String s) {
		return isTrue(s, true);
	}
	
	public static boolean isTrue(String s, boolean ignoreCase) {
		if (s==null) {
			return false;
		}
		s = s.trim();
		if (s.isEmpty()) {
			return false;
		}
		if (ignoreCase && s.equalsIgnoreCase("false") ||
			!ignoreCase && s.equals("false")) {
			return false;
		}
		return true;
	}

	static public boolean isTrue(Object value) {
		if (value==null) {
			return false;
		}
		if (value instanceof Boolean) {
			return (Boolean)value;
		}
		if (value instanceof String) {
			return isTrue((String)value);
		}
		return cast(value, Boolean.class);
	}

	public static boolean isZeroOrFalse(Object v) {
		if (v==null) return false;
		return isZero(v) || isFalse(v);
	}
	
	public static boolean isZero(Object v) {
		if (v==null) {
			return false;
		}
		Object zero = zero(v.getClass());
		if (zero==null) {
			return false;
		}
		return zero.equals(v);
	}
	
	public static Object zero(Class<?> type) {
		if (isInteger(type)) {
			return 0;
		}
		if (isLong(type)) {
			return 0L;
		}
		if (isShort(type)) {
			return (short)0;
		}
		if (isByte(type)) {
			return (byte)0;
		}
		if (isDouble(type)) {
			return (double)0.0;
		}
		if (isFloat(type)) {
			return (float)0.0;
		}	
		if (isCharacter(type)) {
			return '\0';
		}
		if (isBool(type)) {
			return false;
		}
		return null;
	}
	
	public static boolean isPrimitiveSimilar(Class<?> ty0, Class<?> ty1) {
		if (isByte(ty0) && isByte(ty1)) return true;
		if (isInteger(ty0) && isInteger(ty1)) return true;
		if (isShort(ty0) && isShort(ty1)) return true;
		if (isLong(ty0) && isLong(ty1)) return true;
		if (isFloat(ty0) && isFloat(ty1)) return true;
		if (isDouble(ty0) && isDouble(ty1)) return true;		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj, Class<? extends T> type) {
		if (Object.class.equals(type)) {
			return (T)obj;
		}
		if (obj==null) {
			return null;
		}
		if (type==null) {
			return (T)obj;
		}
		Class<?> type0 = obj.getClass(); 
		if (type0==type) {
			return (T)obj;
		}
		if (type0.isAssignableFrom(type)) {
			return (T)obj;
		}
		if (isBool(type0) && isBool(type)) {
			return (T)obj;
		}
		if (isNumeric(type0) && isNumeric(type)) {
			if (isByte(type)) {
				if (isByte(obj.getClass())) return (T)obj;
				if (obj instanceof Double) return (T)(new Byte(((Double)obj).byteValue()));
				if (obj instanceof Float) return (T)(new Byte(((Float)obj).byteValue()));
				if (obj instanceof Integer) return (T)(new Byte(((Integer)obj).byteValue()));
				if (obj instanceof Long) return (T)(new Byte(((Long)obj).byteValue()));
				if (obj instanceof Short) return (T)(new Byte(((Short)obj).byteValue()));
			}
			if (isInteger(type)) {
				if (isInteger(obj.getClass())) return (T)obj;
				if (obj instanceof Double) return (T)(new Integer(((Double)obj).intValue()));
				if (obj instanceof Float) return (T)(new Integer(((Float)obj).intValue()));
				if (obj instanceof Byte) return (T)(new Integer(((Byte)obj).intValue()));
				if (obj instanceof Long) return (T)(new Integer(((Long)obj).intValue()));
				if (obj instanceof Short) return (T)(new Integer(((Short)obj).intValue()));
			}
			if (isShort(type)) {
				if (isShort(obj.getClass())) return (T)obj;
				if (obj instanceof Double) return (T)(new Short(((Double)obj).shortValue()));
				if (obj instanceof Float) return (T)(new Short(((Float)obj).shortValue()));
				if (obj instanceof Byte) return (T)(new Short(((Byte)obj).shortValue()));
				if (obj instanceof Long) return (T)(new Short(((Long)obj).shortValue()));
				if (obj instanceof Integer) return (T)(new Short(((Integer)obj).shortValue()));
			}
			if (isLong(type)) {
				if (isLong(obj.getClass())) return (T)obj;
				if (obj instanceof Double) return (T)(new Long(((Double)obj).longValue()));
				if (obj instanceof Float) return (T)(new Long(((Float)obj).longValue()));
				if (obj instanceof Byte) return (T)(new Long(((Byte)obj).longValue()));
				if (obj instanceof Short) return (T)(new Long(((Short)obj).longValue()));
				if (obj instanceof Integer) return (T)(new Long(((Integer)obj).longValue()));
			}
			if (isDouble(type)) {
				if (isDouble(obj.getClass())) return (T)obj;
				if (obj instanceof Float) return (T)(new Double(((Float)obj).doubleValue()));
				if (obj instanceof Byte) return (T)(new Double(((Byte)obj).doubleValue()));
				if (obj instanceof Short) return (T)(new Double(((Short)obj).doubleValue()));
				if (obj instanceof Integer) return (T)(new Double(((Integer)obj).doubleValue()));
				if (obj instanceof Long) return (T)(new Double(((Long)obj).doubleValue()));
			}
			if (isFloat(type)) {
				if (isFloat(obj.getClass())) return (T)obj;	
				if (obj instanceof Double) return (T)(new Float(((Double)obj).floatValue()));
				if (obj instanceof Byte) return (T)(new Float(((Byte)obj).floatValue()));
				if (obj instanceof Short) return (T)(new Float(((Short)obj).floatValue()));
				if (obj instanceof Integer) return (T)(new Float(((Integer)obj).floatValue()));
				if (obj instanceof Long) return (T)(new Float(((Long)obj).floatValue()));
			}
		}
		if (type.equals(String.class)) return (T)obj.toString();
		if (type0.equals(String.class) && isNumeric(type)) {
			String s = (String) obj;
			return (T)parse(type, s);
		}
		if (isBool(type0)) {
			if (isInt(type)) return (T)new Integer((((Boolean)obj).booleanValue() ? 1 : 0));
			if (isShort(type)) return (T)new Short((((Boolean)obj).booleanValue() ? (short)1 : (short)0));
			if (isLong(type)) return (T)new Long((((Boolean)obj).booleanValue() ? (long)1 : (long)0));
			if (isByte(type)) return (T)new Byte((((Boolean)obj).booleanValue() ? (byte)1: (byte)0));
			if (isFloat(type)) return (T)new Float((((Boolean)obj).booleanValue() ? (float)1: (float)0));
			if (isDouble(type)) return (T)new Double((((Boolean)obj).booleanValue() ? (double)1: (double)0));
		}
		
		return null;
	}


	public static Object parse(Class<?> type, String s) {
		if (type.equals(String.class)) {
			return s;
		}
		if (s.equalsIgnoreCase("null")) {
			return null;
		}

		if (isBool(type)) return Boolean.parseBoolean(s);		
		if (isByte(type)) return Byte.decode(s);
		if (isLong(type)) return Long.decode(s);
		if (isInteger(type)) return Integer.decode(s);
		if (isShort(type)) return Short.decode(s);		
		if (isFloat(type)) return Float.parseFloat(s);
		if (isDouble(type)) return Double.parseDouble(s);	

		if (isNumber(type)) {
			float f =  Float.parseFloat(s);	
			if (f==(int)f) {
				if ((int)f==(long)f) return new Integer((int)f);
				return new Long((long)f);
			}
		}

		if (type.isEnum()) {
			Object[] lv = type.getEnumConstants();
			for (Object v: lv) {
				String vs = String.format("%s", v);
				if (s.equalsIgnoreCase(vs)) { return v; }
			}
		}

		throw new RuntimeException("parse error:" + type + " " + s);
	}

	/**
	 * Find the enumerated value with given ordinal.
	 * 
	 * @param type the enumerated type
	 * @param n the enumerated ordinal value
	 * @return the enumerate value
	 */
	public static Enum<?> getEnumValueFromOrdinal(Class<Enum<?>> type, int n) {
		Enum<?>[] lv = type.getEnumConstants();
		for (Enum<?> v: lv) {
			if (v.ordinal()==n) {
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Find the enumerated value with matching name (case ignored).
	 * 
	 * @param type the enumerated type
	 * @param text the text representation of the enumerated value
	 * @return the enumerate value
	 */
	public static Enum<?> getEnumValueFromString(Class<Enum<?>> type, String text) {
		for (Enum<?> v: type.getEnumConstants()) {
			if (text.equalsIgnoreCase(v.toString())) {
				return (Enum<?>)v;
			}
		}
		return null;
	}


	private static Map<Class<?>, Class<?>> boxMap, unboxMap;
	
	static {
		boxMap = new HashMap<Class<?>, Class<?>>();
		unboxMap = new HashMap<Class<?>, Class<?>>();

		boxMap.put(Integer.TYPE, Integer.class);
		boxMap.put(Long.TYPE, Long.class);
		boxMap.put(Byte.TYPE, Byte.class);
		boxMap.put(Short.TYPE, Short.class);
		boxMap.put(Boolean.TYPE, Boolean.class);
		boxMap.put(Character.TYPE, Character.class);
		boxMap.put(Float.TYPE, Float.class);
		boxMap.put(Double.TYPE, Double.class);

		unboxMap.put(Integer.class, Integer.TYPE);
		unboxMap.put(Long.class, Long.TYPE);
		unboxMap.put(Byte.class, Byte.TYPE);
		unboxMap.put(Short.class, Short.TYPE);
		unboxMap.put(Boolean.class, Boolean.TYPE);
		unboxMap.put(Character.class, Character.TYPE);
		unboxMap.put(Float.class, Float.TYPE);
		unboxMap.put(Double.class, Double.TYPE);

	}
	
	public static Class<?> box(Class<?> type) {
		Class<?> boxed = boxMap.get(type);
		return boxed!=null ? boxed : type;
	}
	
	public static Class<?> unbox(Class<?> type) {
		Class<?> unboxed = unboxMap.get(type);
		return unboxed!=null ? unboxed : type;
	}
	
	public static String[] getClassNames(Class<?>[] classes) {
		String[] classNames = new String[classes.length];
		int i = 0;
		for (Class<?> klass: classes) {
			classNames[i++] = klass.getName();
		}
		return classNames;
	}

	/**
	 * Get the uni-dimensional array type for a component type.
	 * 
	 * @param componentType the component type
	 * @return the array type.
	 */
	public static Class<?> getArrayType(Class<?> componentType) {
		return MetaUtil.forName("[L" + componentType.getName() + ";");
	}

	/**
	 * Get the uni-dimensional array type for a component type.
	 * 
	 * @param componentType the component type
	 * @return the array type.
	 * @throws MetaException if the array type can not be found
	 */
	public static Class<?> getRequiredArrayType(Class<?> componentType) {
		return MetaUtil.forNameRequired("[L" + componentType.getName() + ";");
	}
	
	/**
	 * Check whether an array with the (formal) parameter types of one method
	 * is compatible with (actual) parameter of types specified by a second array.
	 * 
	 * @param formalTypes formal types
	 * @param actualTypes the actual types
	 * @return <code>true</code>, if the actual parameter types are compatible with the
	 * actual parameterTypes; <code>false</code>, otherwise.
	 */
	public static boolean assignableFrom(Class<?>[] formalParameterTypes, Class<?>[] actualParameterTypes) {
		if (formalParameterTypes.length!=actualParameterTypes.length) {
			return false;
		}
		for (int i=0; i<formalParameterTypes.length; i++) {
			if (formalParameterTypes[i].isAssignableFrom(actualParameterTypes[i])) {
				return false;
			}
		}
		return true;
	}

}