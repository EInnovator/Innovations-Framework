package org.einnovator.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.einnovator.meta.criteria.FieldCriteria;
import org.einnovator.meta.criteria.GetterCriteria;
import org.einnovator.meta.criteria.MethodCriteria;
import org.einnovator.meta.criteria.SetterCriteria;
import org.einnovator.util.StringUtil;
import org.einnovator.util.types.TypeUtil;
import org.einnovator.util.types.TypedArrayList;
import org.einnovator.util.types.TypedList;



/**
 * Miscellaneous reflection utilities.
 * 
 * @author jsimao
 */
public class MetaUtil {

	
	/**
	 * Find an annotation with a certain type in an array of annotations.
	 * 
	 * @param annotationType the annotation type
	 * @param annotations the array of annotations
	 * @return the found annotation; <code>null</code>, if none with matching type is found.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotation(Class<T> annotationType, Annotation[] annotations) {
		for (Annotation annotation: annotations) {
			if (annotationType.isAssignableFrom(annotation.getClass())) {
				return (T)annotation;
			}
		}
		return null;
	}

	/**
	 * Find an annotation transitively from type.
	 * 
	 * @param type the type
	 * @param annotationType the annotation type
	 * @return the found annotation; <code>null</code>, if none with matching type is found.
	 */
	public static <T extends Annotation> T getAnnotationTransitive(Class<?> type, Class<T> annotationType) {
		List<Class<?>> checkedAnnotationTypes = new ArrayList<Class<?>>();
		return getAnnotationTransitive(type, annotationType, checkedAnnotationTypes);
	}

	private static <T extends Annotation> T getAnnotationTransitive(Class<?> type, Class<T> annotationType, List<Class<?>> checkedAnnotationTypes) {
		Annotation[] annotations = type.getAnnotations();
		T annotation = getAnnotation(annotationType, annotations);
		if (annotation!=null) {
			return annotation;
		}
		for (Annotation annotation2: annotations) {
			if (annotation2.annotationType()!=type && !checkedAnnotationTypes.contains(annotation2.annotationType())) {
				checkedAnnotationTypes.add(annotation2.annotationType());
				annotation = getAnnotationTransitive(annotation2.annotationType(), annotationType, checkedAnnotationTypes);
				if (annotation!=null) {
					return annotation;
				}			
			}
		}
		return null;
	}

	/**
	 * Get qualifier annotation for a type.
	 * 
	 * A qualifier is an annotation meta-annotated with the specified qualifying annotation.
	 * 
	 * @param type the type
	 * @param qualifierAnnotationType the type of the qualifying annotation
	 * @return an array with the qualifying annotation
	 */
	public static Annotation[] getQualifiers(Class<?> type, Class<? extends Annotation> qualifierAnnotationType) {
		List<Class<?>> checkedAnnotationTypes = new ArrayList<Class<?>>();
		List<Annotation> qualifiers =new ArrayList<Annotation>();
		getQualifiers(type, qualifierAnnotationType, qualifiers, checkedAnnotationTypes);
		return qualifiers.toArray(new Annotation[qualifiers.size()]);
	}

	private static void getQualifiers(Class<?> type, Class<? extends Annotation> qualifierAnnotationType, List<Annotation> qualifiers, List<Class<?>> checkedAnnotationTypes) {
		for (Annotation annotation: type.getAnnotations()) {
			if (annotation.annotationType().getAnnotation(qualifierAnnotationType)!=null) {
				qualifiers.add(annotation);
				checkedAnnotationTypes.add(annotation.annotationType());
			} else {
				for (Annotation annotation2: annotation.annotationType().getAnnotations()) {
					if (annotation2.annotationType()!=type && !checkedAnnotationTypes.contains(annotation2.annotationType())) {
						checkedAnnotationTypes.add(annotation2.annotationType());
						getQualifiers(annotation2.annotationType(), qualifierAnnotationType, qualifiers, checkedAnnotationTypes);
					}
				}
				
			}
		}
	}


	/**
	 * Find a annotation itself annotated with a certain annotation.
	 * 
	 * @param type the type
	 * @param annotationType the annotation type
	 * @return the found annotation; <code>null</code>, if none with matching type is found.
	 */
	public static AnnotatedElement getMetaAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> annotationType) {
		List<Class<?>> checkedAnnotationTypes = new ArrayList<Class<?>>();
		return getMetaAnnotation(annotatedElement, annotationType, checkedAnnotationTypes);
	}

	private static AnnotatedElement getMetaAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> annotationType, List<Class<?>> checkedAnnotationTypes) {
		Annotation[] annotations = annotatedElement.getAnnotations();
		Annotation annotation = getAnnotation(annotationType, annotations);
		if (annotation!=null) {
			return annotatedElement;
		}
		for (Annotation annotation2: annotations) {
			if (!annotation2.annotationType().equals(annotatedElement) && !checkedAnnotationTypes.contains(annotation2.annotationType())) {
				checkedAnnotationTypes.add(annotation2.annotationType());
				annotatedElement = getMetaAnnotation(annotation2.annotationType(), annotationType, checkedAnnotationTypes);
				if (annotatedElement!=null) {
					return annotatedElement;
				}				
			}
		}
		return null;
	}

	/**
	 * Check if a type has all the specified annotation.
	 * 
	 * @param type the type
	 * @param annotations the array of annotations
	 * @return <code>true</code>, if the type as all annotation; <code>false</code>, otherwise.
	 */
	public static boolean hasAnnotations(Class<?> type, Class<? extends Annotation>... annotations) {
		for (Class<? extends Annotation> annotation: annotations) {
			if (type.getAnnotation(annotation)==null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get types of all objects in array.
	 * 
	 * @param lobj the array of objects.
	 * @return the array of object types.
	 */
	public static Class<?>[] getClasses(Object[] lobj) {
		Class<?>[] lcl = new Class<?>[lobj.length];
		int i = 0;
		for (Object obj:lobj) { 
			lcl[i] = obj!=null ? obj.getClass() : Object.class; i++;
		}
		return lcl;
	}
	
	
	/**
	 * Check if a class is present in the class-path (is loaded or can be loaded).
	 * 
	 * @param className the class name
	 * @return <code>true</code>, if the class is present; <code>false</code>, otherwise.
	 */
	public static boolean isPresent(String className) {
		return forName(className)!=null;
	}
	
	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T> forName(String className, Class<T> superType) {
		return (Class<? extends T>) forName(className);
	}

	public static Class<?> forNamePrimitive(String className) {
		if (className.equals("int")) {
			return Integer.TYPE;
		}
		if (className.equals("double")) {
			return Double.TYPE;
		}
		if (className.equals("float")) {
			return Float.TYPE;
		}
		if (className.equals("short")) {
			return Short.TYPE;
		}
		if (className.equals("long")) {
			return Long.TYPE;
		}
		if (className.equals("boolean")) {
			return Boolean.TYPE;
		}
		return null;
	}

	public static Class<?> forNameRequired(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new MetaException(e);
		}
	}

	/**
	 * Set field value for object.
	 * @param field the field.
	 * @param obj the object.
	 * @param value the value.
	 */
	public static void convertAndSetFieldValue(Field field, Object obj, Object value) {
		if (obj==null) {
			return;
		}
		if (value!=null) {
			Class<?> type = field.getType();
			if (!type.equals(value.getClass())) {
				value = TypeUtil.cast(value, type);
			}
		}
		setFieldValue(field, obj, value);	
	}

	public static void setFieldValue(Field field, Object obj, Object value) {
		if (obj==null) {
			return;
		}
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		}
	}
	
	/**
	 * Get field value for object.
	 * @param field the field.
	 * @param obj the object.
	 * @return the value.
	 */
	public static Object getFieldValue(Field field, Object obj) {
		try {
			field.setAccessible(true);	
			return field.get(obj);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		}
	}

	/**
	 * Get field value for object and smart cast to specified type..
	 * @param field the field.
	 * @param obj the object.
	 * @param ty1 the type to smart cast.
	 * @return the value.
	 */
	public static <T> T getFieldValue(Field field, Object obj, Class<T> type) {
		try {
			field.setAccessible(true);	
			Object value = field.get(obj);
			return TypeUtil.cast(value, type);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		}
	}
	
	public static <T> T getFieldValue(Class<?> ownerClass, String name, Class<T> fieldType, Object obj) {
		return getFieldValue(getField(ownerClass, name), obj, fieldType);
	}
	
	public static Field getField(Class<?> type, String name) {
		try {
			Field field = type.getDeclaredField(name);
			if (field!=null) {
				return field;
			}
		} catch (java.lang.NoSuchFieldException e) {
		}
		type = type.getSuperclass();
		return type!=null ? getField(type, name) : null;
	}

	public static Field getRequiredField(Class<?> type, String name) {
		Field field = getField(type, name);
		if (field==null) {
			throw new NoSuchFieldException(type.getName() + "." + name);
		}
		return field;
	}

	public static Field getPublicField(Class<?> type, String name) {
		try {
			return type.getField(name);
		} catch (java.lang.NoSuchFieldException ex) {
			return null;
		}
	}
	
	public static boolean isSetter(Method method) {
		String name = getUnqualifiedName(method.getName());
		return name.startsWith("set") && name.length()>3 && method.getGenericParameterTypes().length==1;
	}

	public static boolean isPublicSetter(Method method) {
		return Modifier.isPublic(method.getModifiers()) && isSetter(method);
	}

	public static boolean isGetter(Method method) {
		String name = getUnqualifiedName(method.getName());
		return ((name.startsWith("get") && name.length()>3) || (name.startsWith("is") && name.length()>2)) 
		&& method.getGenericParameterTypes().length==0;
	}
	
	public static Method getPropertyMethod(Class<?> type, String name, boolean set) {
		Method[] meths = type.getDeclaredMethods();
		int nargs = (set ? 1 : 0);
		if (set) {
			String methName = (set? "set" : "get") + StringUtil.capitalize(name);
			for (Method meth: meths) {
				if (meth.getParameterTypes().length==nargs && meth.getName().equals(methName)) {
					return meth;
				}
			}
		} else {
			String cname = StringUtil.capitalize(name);		
			String methName1 = "get" + cname;		
			String methName2 = "is" + cname;
			for (Method meth: meths) {
				if (meth.getParameterTypes().length==nargs &&
						(meth.getName().equals(methName1) || meth.getName().equals(methName2))) {
						return meth;
				}
			}			
		}
		if (type.getSuperclass()!=null) {
			return getPropertyMethod(type.getSuperclass(), name, set);			
		} else {
			return null;
		}
	}
	public static Method getPropertyMethod(Class<?> type, Class<?> propType, boolean set) {
		return getPropertyMethod(type, getUnqualifiedName(propType), propType, set);
	}
	
	public static Method getPropertyMethod(Class<?> type, String name, Class<?> propType, boolean set) {
		try {
			if (set) {
				String methName = "set" + StringUtil.capitalize(name);
				return type.getMethod(methName, propType);
			} else {
				String cname = StringUtil.capitalize(name);
				String methName = "get" + cname;
				Method getter = type.getMethod(methName);
				if (getter!=null) {
					return getter;
				}
				if (Boolean.class.isAssignableFrom(propType) || Boolean.TYPE.isAssignableFrom(propType)) {
					methName = "is" + cname;
					return type.getMethod(methName);
				}
				return null;
			}
		} catch (java.lang.NoSuchMethodException e) {
			return null;
		}
	}
	
	public static Class<?> getPropertyType(Class<?> type, String name, boolean set) {
		Method method = getPropertyMethod(type, name, set);
		if (method!=null) {
			return set ? method.getReturnType() : method.getParameterTypes()[0];			
		} else {
			return null;
		}
	}
	
	public static Method getGetter(Class<?> type, String name) {
		return getPropertyMethod(type, name, false);
	}

	public static Method getSetter(Class<?> type, String name) {
		return getPropertyMethod(type, name, true);
	}

	public static Method getGetter(Class<?> type, String name, Class<?> propType) {
		return getPropertyMethod(type, name, propType, false);
	}

	public static Method getSetter(Class<?> type, String name, Class<?> propType) {
		return getPropertyMethod(type, name, propType, true);
	}

	public static Method getGetter(Class<?> type, Class<?> propType) {
		return getPropertyMethod(type, propType, false);
	}

	public static Method getSetter(Class<?> type, Class<?> propType) {
		return getPropertyMethod(type, propType, true);
	}
	
	public static Object getGetterValue(Object obj, String name) {
		Method method = getGetter(obj.getClass(), name);
		if (method==null) {
			return null;
		}
		try {
			method.setAccessible(true);
			return method.invoke(obj);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			throw new MetaException(e);
		}
	}
	
	public static Object setValueSetter(Object obj, String name, Object value) {
		Method method = getSetter(obj.getClass(), name);
		if (method==null) {
			return null;
		}
		try {
			method.setAccessible(true);		
			return method.invoke(obj, value);
		} catch (IllegalArgumentException e) {
			throw new MetaException(e);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			throw new MetaException(e);
		}
	}
	
	public static Member getPropertyMember(Class<?> type, String name, boolean set) {
		Method method = set ? getSetter(type, name) : getGetter(type, name);
		if (method!=null) {
			return method;
		}
		return getField(type, name);
	}

	public static Class<?> getPropertyType(Class<?> type, String name) {
		Member member = getPropertyMember(type, name, false);
		return getPropertyType(member);
	}
	
	public static Class<?> getPropertyType(Member member) {
		if (member instanceof Method) {
			Method method = (Method)member;
			if (isGetter(method)) {
				return method.getReturnType();
			}
			if (isSetter(method)) {
				Class<?>[] args = method.getParameterTypes();
				return args[0];
			}
			return null;
		}
		else if (member instanceof Field) {
			return ((Field)member).getType();
		}
		return null;
	}
	
	public static Object getPropertyValue(Object obj, String name) {
		int i = name.indexOf(".");
		String prop = i<0 ? name : name.substring(0, i);
		Member member = getPropertyMember(obj.getClass(), prop, false);
		if (i<0) {
			return getPropertyValue(obj, member);
		}
		else {
			Object obj_ = getPropertyValue(obj, member);
			if (obj_==null) {
				return null;
			}
			return getPropertyValue(obj_, name.substring(i+1)); //consume and recurse		
		}
	}

	
	public static void setPropertyValue(Object obj, String name, Object value) {
		int i = name.indexOf(".");
		String prop = i<0 ? name : name.substring(0, i);
		Member member = getPropertyMember(obj.getClass(), prop, true);
		if (i<0) {
			setPropertyValue(obj, member, value);
			return;
		}
		Object obj_ = getPropertyValue(obj, member);
		if (obj_==null) {
			Class<?> mbty;
			if (member instanceof Field) {
				mbty = getType(member);
			}
			else {
				Member mb_ = getPropertyMember(obj.getClass(), prop, false);
				mbty = getType(mb_);
			}
			obj_ = newInstance(mbty);
			if (obj_==null) {
				return; //todo: throw MetaException
			}
			//System.out.printf("createInstance:%s -> %s %n", mbty, obj_);
			setPropertyValue(obj, member, obj_);
		}
		setPropertyValue(obj_, name.substring(i+1), value); //consume and recurse
	}
	
	public static Object getPropertyValue(Object obj, Member member) {
		if (member==null) {
			return null;
		}
		if (member instanceof Method) {
			return invoke(obj, (Method)member);
		} else if (member instanceof Field) {
			return getFieldValue((Field)member, obj);
		}
		return null;		
	}

	public static void setPropertyValue(Object obj, Member member, Object value) {
		if (member==null) {
			return;
		}
		if (member instanceof Method) {
			invoke(obj, (Method)member, new Object[]{value});
		} else if (member instanceof Field) {
			setFieldValue((Field)member, obj, value);
		}
	}
	

	public static Field getField(Class<?> type, String[] names) {
		for (String name: names) {
			try {
				return type.getField(name);
			} catch(java.lang.NoSuchFieldException e) {
				throw new MetaException(e);
			}
		}
		return null;
	}
	public static Method getPropertyMethod(Class<?> type, String[] names, boolean set) {
		for (String name: names) {
			Method method = getPropertyMethod(type, name, set);
			if (method!=null) return method;
		}
		return null;
	}

	public static Member getPropertyMember(Class<?> type, String[] mtNames, String[] fdNames, boolean set) {
		Member member = getPropertyMethod(type, mtNames, set);
		if (member==null) {
			member = getField(type, fdNames);
		}
		return member;
	}
	
	public static Object getPropertyValue(Object obj, String[] mtNames, String[] fdNames) {	
		Member member = getPropertyMember(obj.getClass(), mtNames, fdNames, false);
		return getPropertyValue(obj, member);
	}

	public static void setPropertyValue(Object obj, Object value, String[] mtNames, String[] fdNames) {	
		Member member = getPropertyMember(obj.getClass(), mtNames, fdNames, true);
		setPropertyValue(obj, member, value);
	}

	public static void setProperties(Object obj, String[] props, String[] values) {
		if (props==null || values==null) {
			return; 
		}
		int i=0;
		for (String prop: props) {
			if (i>=values.length) {
				break;
			}
			setPropertyValue(obj, prop, values[i]);
			i++;
		}
	}
	
	public static void setProperties(Object obj, String s) {
		if (obj==null || s==null) return;
		String[] lattrs = s.split(";");
		for (String attr: lattrs) {
			if (StringUtil.isEmpty(attr)) {
				continue;
			}
			int i = attr.indexOf(':');
			if (i<0) i = attr.indexOf('=');
			if (i<0) {
				continue;
			}
			String name = attr.substring(0, i);
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			name = name.trim();
			String value = attr.substring(i+1);
			if (StringUtil.isEmpty(value)) {
				continue;
			}
			value = value.trim();
			setPropertyValue(obj, name, value);
		}
	}	
	
	protected static Object parseValue(Class<?> type, String value) {
		return TypeUtil.parse(type, value);
	}

	public static void iterateFields(Class<?> type, FieldHandler fieldHandler) {
		iterateFields(type, fieldHandler, null, true, true);
	}
	
	public static void iterateFields(Class<?> type, FieldHandler fieldHandler, FieldCriteria fieldCriteria) {
		iterateFields(type, fieldHandler, fieldCriteria, true, true);
	}

	public static void iterateFields(Class<?> type, FieldHandler fieldHandler, boolean parents, boolean parentsFirst) {
		iterateFields(type, fieldHandler, null, parents, parentsFirst);
	}
	
	public static void iterateFields(Class<?> type, FieldHandler fieldHandler, FieldCriteria fieldCriteria, boolean parents, boolean parentsFirst) {
		if (type==null) return;
		if (parents && parentsFirst) {
			iterateFields(type.getSuperclass(), fieldHandler, fieldCriteria, parents, parentsFirst);
		}
		Field[] fds = type.getDeclaredFields();
		for (Field field: fds) {
			if (fieldCriteria!=null && !fieldCriteria.check(field)) {
				continue;			
			}
			fieldHandler.handle(field);
		}
		if (parents && !parentsFirst) {
			iterateFields(type.getSuperclass(), fieldHandler, fieldCriteria, parents, parentsFirst);		
		}
	}

	public static void iterateMethods(Class<?> type, MethodHandler methodHandler) {
		iterateMethods(type, methodHandler, null, true, true);
	}
	
	public static void iterateMethods(Class<?> type, MethodHandler methodHandler, MethodCriteria methodCriteria) {
		iterateMethods(type, methodHandler, methodCriteria, true, true);		
	}
	
	public static void iterateMethods(Class<?> type, MethodHandler methodHandler, MethodCriteria methodCriteria, boolean parents, boolean parentsFirst) {	
		if (type==null) {
			return;
		}
		if (parents && parentsFirst) {
			iterateMethods(type.getSuperclass(), methodHandler, methodCriteria, parents, parentsFirst);
		}
		Method[] mts = type.getDeclaredMethods();
		for (Method method: mts) {
			if (methodCriteria!=null && !methodCriteria.check(method)) {
				continue;
			}
			methodHandler.handle(method);
		}
		if (parents && !parentsFirst) {
			iterateMethods(type.getSuperclass(), methodHandler, methodCriteria, parents, parentsFirst);	
		}
	}

	public static Method[] getMethods(Class<?> type, MethodCriteria methodCriteria, boolean parents, boolean parentsFirst) {	
		return toArray(collectMethods(type, methodCriteria, parents, parentsFirst));
	}
	
	public static List<Method> collectMethods(Class<?> type, MethodCriteria methodCriteria, boolean parents, boolean parentsFirst) {	
		final List<Method> l = new ArrayList<Method>();
		MethodHandler methodHandler = new MethodHandler() {	
			@Override
			public void handle(Method method) {
				l.add(method);
			}
		};
		iterateMethods(type, methodHandler, methodCriteria, parents, parentsFirst);	
		return l;
	}

	public static Method[] getMethods(Class<?> type, MethodCriteria methodCriteria, boolean parents) {	
		return toArray(collectMethods(type, methodCriteria, parents));
	}
	
	public static List<Method> collectMethods(Class<?> type, MethodCriteria methodCriteria, boolean parents) {	
		return collectMethods(type, methodCriteria, parents, true);
	}

	public static Method[] getMethods(Class<?> type, MethodCriteria methodCriteria) {	
		return toArray(collectMethods(type, methodCriteria));
	}
	
	public static List<Method> collectMethods(Class<?> type, MethodCriteria methodCriteria) {	
		return collectMethods(type, methodCriteria, true, true);
	}

	public static Method[] getAllMethods(Class<?> type) {	
		return toArray(collectAllMethods(type));
	}

	static private Method[] toArray(List<Method> l) {	
		Method[] meths = new Method[l.size()];
		l.toArray(meths);
		return meths;
	}
	
	public static List<Method> collectAllMethods(Class<?> type) {	
		return collectMethods(type, null, true, true);
	}
	
	public static void iterateProperties(Class<?> type, Object obj, final MemberHandler memberHandler) {
		iterateProperties(type, obj, memberHandler, true, true);
	}
	
	public static void iterateProperties(Class<?> type, Object obj, final MemberHandler memberHandler, boolean parents, boolean parentsFirst) {
		if (type==null) return;
		if (parents && parentsFirst) {
			iterateProperties(type.getSuperclass(), obj, memberHandler, parents, parentsFirst);
		}

		FieldHandler fieldHandler = new FieldHandler() {
			@Override
			public void handle(Field field) {
				memberHandler.handle(field);
			}
		};
		iterateFields(type, fieldHandler, false, false);	

		MethodHandler mth = new MethodHandler() {
			@Override
			public void handle(Method method) {
				memberHandler.handle(method);
			}
		};
		iterateMethods(type, mth, SetterCriteria.criteria, false, false);
		iterateMethods(type, mth, GetterCriteria.criteria, false, false);

		if (parents && !parentsFirst) {
			iterateProperties(type.getSuperclass(), obj, memberHandler, parents, parentsFirst);	
		}
	}

	public static List<Member> collectAllPropertyMember(final Class<?> type, boolean set) {
		return collectAllPropertyMember(type, set, true, true);
	}
	
	public static List<Member> collectAllPropertyMember(final Class<?> type, boolean set, boolean parents, boolean parentsFirst) {
		final List<Member> props = new ArrayList<Member>();
		final List<String> names = new ArrayList<String>();
		MemberHandler memberHandler = new MemberHandler() {
			@Override
			public void handle(Member member) {
				String name = getPropertyName(member);
				if (!names.contains(name)) {
					names.add(name);
					props.add(member);
				}
				//System.out.printf("%s %s%n ", member, name);		
			}
		};
		MetaUtil.iterateProperties(type, null, memberHandler, parents, parentsFirst);
		return props;
	}
	
	public static List<String> collectAllPropertyNames(final Class<?> type) {
		return collectAllPropertyNames(type, true, true);
	}
	
	public static List<String> collectAllPropertyNames(final Class<?> type, boolean parents, boolean parentsFirst) {
		final List<String> props = new ArrayList<String>();
		MemberHandler memberHandler = new MemberHandler() {
			@Override
			public void handle(Member member) {
				String name = getPropertyName(member);
				if (!props.contains(name)) {
					props.add(name);
				}
			}
		};
		MetaUtil.iterateProperties(type, null, memberHandler, parents, parentsFirst);
		return props;
	}
	
	public static String getPropertyName(Method method) {
		String name = method.getName();
		if (isSetter(method)) {
			return StringUtil.decapitalize(name.substring(3));				
		} else if (isGetter(method)) {
			if (name.startsWith("is")) {
				return StringUtil.decapitalize(name.substring(2));
			} else {
				return StringUtil.decapitalize(name.substring(3));
			}
		}
		return null;
	}
	
	public static String getPropertyName(Method getter, Method setter) {
		if (getter!=null) {
			return MetaUtil.getPropertyName(getter);
		} else if (setter!=null) {
			return MetaUtil.getPropertyName(setter);			
		} else {
			return null;
		}

		
	}
	public static String getPropertyName(Member member) {
		if (member instanceof Method) {
			return getPropertyName((Method)member);
		} else if (member instanceof Field) {
			return ((Field)member).getName();
		}
		return null;
	}
	
	public static Map<String, Object> getAllPropertyValues(Object obj) {
		List<Member> l = collectAllPropertyMember(obj.getClass(), false);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Member member: l) {
			if (member instanceof Method && !isGetter((Method)member)) {
				continue;
			}
			Object value = getPropertyValue(obj, member);
			map.put(getPropertyName(member), value);
		}
		return map;
	}
	

	public static Field findField(Class<?> type, FieldCriteria fieldCriteria) {
		if (type==null) return null;
		Field[] fds = type.getDeclaredFields();
		for (Field field: fds) {
			if (fieldCriteria.check(field)) {
				return field;
			}
		}		
		return findField(type.getSuperclass(), fieldCriteria);
	}
	
	//
	// Naming
	//
	
	public static String getLabel(Class<?> type) {
		return getLabel(getUnqualifiedName(type));		
	}

	public static String getLabel(Member member) {
		return getLabel(getUnqualifiedName(member));		
	}
	
	public static String getLabel(String s) {
		return StringUtil.capitalize(s);		
	}
	
	public static String getName(Class<?> type) {
		if (type.isArray()) {
			return formatArrayType(type, false);
		}
		return type.getName();
	}
	
	public static String getUnqualifiedName(Class<?> type) {
		if (type.isArray()) {
			return formatArrayType(type, true);
		}
		return getUnqualifiedName(type.getSimpleName());
	}

	public static String formatArrayType(Class<?> type, boolean unqualified) {
		StringBuilder sb = new StringBuilder();
		Class<?> componentType = type.getComponentType();
		if (componentType.isArray()) {
			sb.append(formatArrayType(componentType, unqualified));
		} else {
			sb.append(unqualified ? getUnqualifiedName(componentType) : componentType.getName().toString());
		}
		sb.append("[]");
		return sb.toString();
	}

	public static String getUnqualifiedName(Member member) {
		return getUnqualifiedName(member.getName());
	}
	
	public static String getUnqualifiedName(String name) {	
		return StringUtil.substringAfterLastNonNull(StringUtil.substringAfterNonNull(name, "$"), ".");
	}

	public static String getNonGenericName(String name) {	
		return StringUtil.substringBeforeNonNull(name, "<");
	}

	public static String getUnqualifiedNonGenericName(String name) {	
		return getUnqualifiedName(getNonGenericName(name));
	}

	public static String getVariableName(String s) {	
		return StringUtil.decapitalize(getUnqualifiedName(s));
	}

	public static String getVariableName(Class<?> type) {	
		return getVariableName(type.getSimpleName());
	}

	public static String getPackageName(Class<?> type) {	
		return getPackageName(type.getName());
	}

	public static String getPackageName(String qname) {	
		int i = qname.indexOf("[L");
		if (i>=0) {
			qname = qname.substring(i+2);
		}
		return StringUtil.substringBeforeLastNonNull(qname, ".");
	}

	public static String qualify(String packageName, String unqualifiedName) {	
		return StringUtil.sconcat(packageName, unqualifiedName, ".");
	}

	public static String typesToString(Class<?>[] types, boolean qualified) {	
		if (types==null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<types.length; i++) {
			String name = types[i]==null ? "null" : (qualified ? types[i].getName() : getUnqualifiedName(types[i]));
			sb.append(name);
			if (i<types.length-1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}


	public static Class<?> getType(Member member) {
		if (member instanceof Field) {
			return ((Field)member).getType();
		}
		return ((Method)member).getReturnType();
	}
	
	public static Annotation[] getAnnotations(Member member) {
		return member instanceof AnnotatedElement ? ((AnnotatedElement)member).getAnnotations() : null;
	}
	
	//
	// Method invokation
	//
	
	public static Object invoke(Object obj, String methName, Object[] args) {
		try {
			Class<?>[] argTypes = getClasses(args);
			Method meth = obj.getClass().getDeclaredMethod(methName, argTypes);
			return invoke(obj, meth, args);
		} catch (java.lang.NoSuchMethodException e) {
			throw new MetaException(e);
		}
	}

	public static Object invoke(Object obj, String methodName) {
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName);
			return invoke(obj, method);
		} catch (java.lang.NoSuchMethodException e) {
			throw new MetaException(e);
		}
	}
	
	public static Object invoke(Object obj, Method method, Object[] args) {
		try {
			method.setAccessible(true);
			return method.invoke(obj, args);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace(System.err);
			throw new MetaException(e); //.getTargetException());
			//throw new MetaException(e.getTargetException().toString() + " invoking '" + method /*+" with " + Arrays.toString(args) + " on target of type '" + obj.getClass().getName() + "'"*/);
		}
	}

	public static Object invokeVArgs(Object obj, Method method, Object... args) {
		try {
			method.setAccessible(true);
			return method.invoke(obj, args);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			throw new MetaException(e); //.getTargetException());
			//throw new MetaException(e.getTargetException().toString() + " invoking '" + method /*+" with " + Arrays.toString(args) + " on target of type '" + obj.getClass().getName() + "'"*/);
		}
	}

	public static Object invoke(Object obj, Method method) {
		try {
			method.setAccessible(true);
			return method.invoke(obj);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			throw new MetaException(e); //.getTargetException());
			//throw new MetaException(e.getTargetException());
			//throw new MetaException(e.toString() + " invoking '" + method + " on target of type '" + obj.getClass() + "'");
		}				
	}	
	
	
	//
	// Types
	//
	
	public static Class<?>[] getInterfaces(Class<?> type, boolean parents) {
		if (!parents) {
			return type.getInterfaces();
		}
		Set<Class<?>> interfList = new HashSet<Class<?>>();
		collectAllInterfaces(type, interfList);
		return interfList.toArray(new Class<?>[interfList.size()]);
	}
	
	private static void collectAllInterfaces(Class<?> type, Set<Class<?>> interfList) {	
		Class<?>[] interfs = type.getInterfaces();
		for (Class<?> interf: interfs) {
			interfList.add(interf);
		}
		for (Class<?> interf: interfs) {
			collectAllInterfaces(interf, interfList);
		}
		Class<?> superClass = type.getSuperclass();
		if (superClass!=null) {
			collectAllInterfaces(superClass, interfList);
		}
	}
	
	
	//
	// Get methods
	//
	
	public static Method getPublicMethod(Class<?> type, String name, Class<?>[] parameterTypes) {
		try {
			return type.getMethod(name, parameterTypes);
		} catch (java.lang.NoSuchMethodException e) {
			return null;
		}
	}

	public static Method getMethod(Class<?> type, String name, Class<?>... parameterTypes) {
		try {
			return type.getDeclaredMethod(name, parameterTypes);
		} catch (java.lang.NoSuchMethodException e) {
			type = type.getSuperclass();
			return type!=null ? getMethod(type, name, parameterTypes) : null;
		}
	}

	public static Method getRequiredMethod(Class<?> type, String name, Class<?>... parameterTypes) {
		Method method = getMethod(type, name, parameterTypes);
		if (method==null) {
			throw new NoSuchMethodException(type.getName() + "." + name + StringUtil.argsToString(parameterTypes));
		}
		return method;
	}
		
	public static Method getMethod(Class<?> type, String name) {
		while (type!=null) {
			Method[] methods = type.getDeclaredMethods();
			for (Method method: methods) {
				if (method.getName().equals(name)) {
					return method;
				}
			}
			type = type.getSuperclass();
		}
		return null;
	}

	
	public static Method getUniqueMethod(Class<?> type, String name) {
		Method resultMethod = null;
		Class<?> type2 = type;
		while (type2!=null) {
			Method[] methods = type2.getDeclaredMethods();
			for (Method method: methods) {
				if (method.getName().equals(name)) {
					if (resultMethod!=null) {
						throw new MetaException("No unique method named '" + name + "' for '" + type2 + "'");
					}
					resultMethod = method;
				}
			}
			type2 = type2.getSuperclass();
		}
		if (type.isInterface()) {
			Class<?>[] types = type.getInterfaces();
			for (Class<?> itype: types) {
				Method method = getUniqueMethod(itype, name);
				if (method!=null) {
					if (resultMethod==null) {
						resultMethod = method;
					} else {
						throw new MetaException("No unique method named '" + name + "' for '" + type2 + "'");						
					}
				}
			}
		}
		return resultMethod;
	}
	
	public static Method[] getMethods(Class<?> type, String name) {
		List<Method> methodList = null;
		while (type!=null) {
			Method[] methods = type.getDeclaredMethods();
			for (Method method: methods) {
				if (method.getName().equals(name)) {
					if (methodList==null) {
						methodList = new ArrayList<Method>();
					}
					methodList.add(method);
				}
			}
			type = type.getSuperclass();
		}
		return methodList!=null ? methodList.toArray(new Method[methodList.size()]) : null;
	}
	
	public static Method getPublicMethod(Class<?> type, String name) {
		try {
			Method[] methods = type.getMethods();
			for (Method method: methods) {
				if (method.getName().equals(name)) {
					return method;
				}
				type = type.getSuperclass();
			}
			return null;
		} catch (SecurityException e) {
			throw new MetaException(e);
		}
	}

	//
	// Get constructors
	//
	
	public static Class<?>[] getTypes(Object[] values) {
		Class<?>[] types = new Class<?>[values.length];
		for (int i=0; i<types.length; i++) {
			types[i] = values[i].getClass();
		}
		return types;
	}
	
	public static <T> Constructor<T> getConstructor(Class<T> type, Object[] parameterValues) {
		return getConstructor(type, getTypes(parameterValues));
	}
	
	public static <T> Constructor<T> getConstructor(Class<T> type, Class<?>[] parameterTypes) {
		try {
			return type.getDeclaredConstructor(parameterTypes);
		} catch (java.lang.NoSuchMethodException e) {
			return null;
		}
	}

	public static <T> Constructor<T> getRequiredConstructor(Class<T> type, Class<?>[] parameterTypes) {
		try {
			return type.getDeclaredConstructor(parameterTypes);
		} catch (java.lang.NoSuchMethodException e) {
			throw new MetaException(e);
		}
	}

	/**
	 * Get the constructor for a type with the specified variable number of parameter types.
	 * 
	 * @param type the type
	 * @param parameterTypes the array of parameter types
	 * @return the no-arguments constructor; or <code>null</code>, if the type does not have none
	 */
	public static <T> Constructor<T> getConstructorVArgs(Class<T> type, Class<?>... parameterTypes) {
		try {
			return type.getDeclaredConstructor(parameterTypes);
		} catch (java.lang.NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * Get the constructor for a type with the specified variable number of parameter types.
	 * 
	 * @param type the type
	 * @param parameterTypes the array of parameter types
	 * @return the constructor
	 * @throws NoSuchMethodException if the type does not have a matching constructor
	 */
	public static <T> Constructor<T> getRequiredConstructorVArgs(Class<T> type, Class<?>... parameterTypes) {
		try {
			return type.getDeclaredConstructor(parameterTypes);
		} catch (java.lang.NoSuchMethodException e) {
			throw new NoSuchMethodException("Missing matching constructor for '" + type.getName() + "'");
		}		
	}

	/**
	 * Get the no-arguments constructor for a type.
	 * 
	 * @param type the type
	 * @return the no-arguments constructor; or <code>null</code>, if the type does not have none
	 */
	public static <T> Constructor<T> getNoArgsConstructor(Class<T> type) {
		try {
			return type.getDeclaredConstructor((Class<?>[])null);
		} catch (java.lang.NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Get the no-arguments constructor for a type.
	 * 
	 * @param type the type
	 * @throws NoSuchMethodException if the type does not have a no-arguments constructor
	 * @return the no-arguments constructor
	 */
	public static <T> Constructor<T> getRequiredNoArgsConstructor(Class<T> type) {
		try {
			return type.getDeclaredConstructor((Class<?>[])null);
		} catch (java.lang.NoSuchMethodException e) {
			throw new NoSuchMethodException("Missing no-arguments constructor for '" + type.getName() + "'");
		}
	}

	//
	// Create instances
	//

	/**
	 * Create a new instance of a type using the no-arguments constructor.
	 * 
	 * @param type the type
	 * @return the new instance
	 * @throws NoSuchMethodException if the type does not have a no-arguments constructor
	 * @throws MetaException wrapping the underlying Java exception, such as
	 * {@link InstantiationException}, {@link IllegalArgumentException},
	 * {@link InvocationTargetException}c
	 */
	public static <T> T newInstance(Class<T> type) {
		try {
			Constructor<T> constructor = getRequiredNoArgsConstructor(type);
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch(InstantiationException e) {
			throw new MetaException(e);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
			throw new MetaException(e);
		}

	}


	/**
	 * Create a new instance using a constructor and array of arguments.
	 * 
	 * @param constructor the constructor
	 * @param args the array arguments
	 * @return the new instance
	 * @throws MetaException wrapping the underlying Java exception, such as
	 * {@link InstantiationException}, {@link IllegalArgumentException},
	 * {@link InvocationTargetException}c
	 */
	public static <T> T newInstance(Constructor<T> constructor, Object[] args) {
		try {
			constructor.setAccessible(true);
			return constructor.newInstance(args);
		} catch (InstantiationException e) {
			throw new MetaException(e);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
			throw new MetaException(e);
		}
	}

	/**
	 * Create a new instance using a constructor and variable number of arguments.
	 * 
	 * @param constructor the constructor
	 * @param args the variable number of arguments
	 * @return the new instance
	 * @throws MetaException wrapping the underlying Java exception, such as
	 * {@link InstantiationException}, {@link IllegalArgumentException},
	 * {@link InvocationTargetException}c
	 */
	public static <T> T newInstanceVArgs(Constructor<T> constructor, Object... args) {
		try {
			constructor.setAccessible(true);
			return constructor.newInstance(args);
		} catch (InstantiationException e) {
			throw new MetaException(e);
		} catch (IllegalAccessException e) {
			throw new MetaException(e);
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
			throw new MetaException(e);
		}
	}

	/**
	 * Create a new instance of a class with specified type using the no-arguments constructor.
	 * 
	 * @param className the className
	 * @param type type to (upper-cast) the instance created
	 * @return the new instance
	 * @throws NoSuchMethodException if the type does not have a no-arguments constructor
	 * @throws MetaException wrapping the underlying Java exception, such as
	 * {@link InstantiationException}, {@link IllegalArgumentException},
	 * {@link InvocationTargetException}c
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className, Class<T> type) {
		return (T) newInstance(forNameRequired(className));
	}
	
	//
	// TODO: Move out
	//
	
	@SuppressWarnings("unchecked")
	public static <T> T newInterfaceInstance(Class<T> type) {		
		try {
			if (type.isInterface()) {
				if (TypeUtil.isResizableCollection(type)) {
					if (TypedList.class.isAssignableFrom(type)) {
						return (T) TypedArrayList.class.newInstance();
					} else if (List.class.isAssignableFrom(type)) {
						return (T) List.class.newInstance();
					}
				}			
				return null;
			}
			return type.newInstance();
		} catch(Exception e) {
			throw new MetaException(e);
		}
	}

	/**
	 * Check whether a field represents the a referent to the outer
	 * class of a non-static internal class.
	 * 
	 * @param field the field
	 * @return <code>true</code>, if the type name designates a inner type; <code>false</code>, otherwise.
	 */
	public static boolean isOuter(Field field) {
		return isInner(field.getName());
	}

	/**
	 * Check whether a type name designates a inner type.
	 * 
	 * The check simple looks for the presence of character '$' in the type name.
	 * 
	 * @param typeName the name of the type
	 * @return <code>true</code>, if the type name designates a inner type; <code>false</code>, otherwise.
	 */
	public static boolean isInner(String typeName) {
		return typeName.contains("$");
	}
	
	/**
	 * Get the generic type of a  member.
	 * 
	 * Only field and method members are allowed.
	 * 
	 * @param member the member
	 * @return the generic type
	 * @throws IllegalArgumentException if the member is not a field or method
	 */
	public static Type getGenericType(Member member) {
		if (member instanceof Field) {
			return ((Field)member).getGenericType();
		}
		if (member instanceof Method) {
			return ((Method)member).getGenericReturnType();			
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Get list of public methods of a type with a specified annotation.
	 * 
	 * @param type the type 
	 * @param annotationType the annotation type
	 * @return the list of methods
	 */
	public static List<Method> getAnnotatedPublicMethods(Class<?> type, Class<? extends Annotation> annotationType) {
		List<Method> methods = new ArrayList<Method>();
		for (Method method: type.getMethods()) {
			if (method.getAnnotation(annotationType)!=null) {
				methods.add(method);
			}
		}
		return methods;
	}
	
	/**
	 * Get list of public methods of a type with a specified annotation.
	 * 
	 * @param type the type 
	 * @param annotationType the annotation type
	 * @return the method; or <code>null</code> if none is found
	 * @throws AmbiguosMethodException if more than one method with the specified annotation is found
	 */
	public static Method getSinleAnnotatedPublicMethod(Class<?> type, Class<? extends Annotation> annotationType) {
		Method method = null;
		for (Method method2: type.getMethods()) {
			if (method2.getAnnotation(annotationType)!=null) {
				if (method2!=null) {
					if (method!=null) {
						throw new AmbiguosMethodException("Expected single method annotated with '" + annotationType.getName()
								+ "' in type " + type.getName()
								+ ", but found two: '" + method + "' and '" + method2 + "'");
					}
					method = method2;
				}
			}
		}
		return method;
	}

	/**
	 * Check whether a type has a public method with a specified annotation.
	 * 
	 * @param type the type 
	 * @param annotationType the annotation type
	 * @return <code>true</code>, if the type has a method as an annotated method; <code>false</code>, otherwise.
	 */
	public static boolean hasAnnotatedPublicMethod(Class<?> type, Class<? extends Annotation> annotationType) {
		for (Method method2: type.getMethods()) {
			if (method2.getAnnotation(annotationType)!=null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the list of methods in the super-classes and interfaces of
	 * of the declaring class that have the same signature has a method.
	 * 
	 * @param method the method
	 * @return the list of methods with the same signature (the input method itself is not included)
	 */
	public static List<Method> getSameSignatureMethods(Method method) {
		return getSameSignatureMethods(method, method.getDeclaringClass());
	}

	/**
	 * Get the list of methods in the super-classes and interfaces of
	 * of a type that have the same signature has a method.
	 * 
	 * @param method the method
	 * @param the type to search for super-classes and interfaces
	 * @return the list of methods with the same signature (the input method itself is not included)
	 */
	public static List<Method> getSameSignatureMethods(Method method, Class<?> type) {
		List<Method> methods = new ArrayList<Method>();
		getSameSignatureMethods(method, type, methods);
		return methods;
	}

	private static void getSameSignatureMethods(Method method, Class<?> type, List<Method> methods) {
		while (type!=null) {
			for (Method method2: type.getDeclaredMethods()) {
				if (method2!=method && sameSignature(method, method2)) {
					methods.add(method2);
				}
			}
			Class<?>[] interfs = type.getInterfaces();
			for (Class<?> interf: interfs) {
				getSameSignatureMethods(method, interf, methods);
			}
			type = type.getSuperclass();
		}
	}

	/**
	 * Get the a method in a type with the same signature has a given method.
	 * 
	 * @param method the method
	 * @param type the type to look for a same signature method
	 * @return the same signature method; or <code>null</code>, if not found.
	 */
	public static Method getSameSignatureMethod(Method method, Class<?> type) {
		while (type!=null) {
			for (Method method2: type.getDeclaredMethods()) {
				if (sameSignature(method, method2)) {
					return method2;
				}
			}
			type = type.getSuperclass();
		}
		return null;
	}

	/**
	 * Get the a method in a type with the same signature has a given method.
	 * 
	 * @param method the method
	 * @param type the type to look for a same signature method
	 * @return the same signature method
	 * @throws NoSuchMethodException if no same signature method is found
	 */
	public static Method getRequiredSameSignatureMethod(Method method, Class<?> type) {
		Method method2 = getSameSignatureMethod(method, type);
		if (method2==null) {
			throw new NoSuchMethodException(method.toString());
		}
		return method2;
	}

	/**
	 * Check whether two methods have the same signature.
	 * 
	 * Two methods are defined to have the same the same signature,
	 * if the name, number of parameters, and parameters types are the same.
	 * The returned type of the methods is not checked.
	 * 
	 * @param method0 the first method
	 * @param method1 the second method
	 * @return <code>true</code>, if methods have the same signature
	 */
	public static boolean sameSignature(Method method0, Method method1) {
		if (method0.getName()!=method1.getName()) {
			return false;
		}
		Class<?>[] parameterTypes0 = method0.getParameterTypes();
		Class<?>[] parameterTypes1 = method1.getParameterTypes();
		if (parameterTypes0.length!=parameterTypes1.length) {
			return false;
		}
		for (int i=0; i<parameterTypes0.length; i++) {
			if (parameterTypes0[i]!=parameterTypes1[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Expand a map with methods has keys to have additional entries for
	 * each of the methods with the same signature.
	 * 
	 * The value of the added entries is the same the one of the entry
	 * for which the same signature method was found.
	 * 
	 * The list of methods with the same signature is get by calling
	 * {@link #getSameSignatureMethods(Method)};
	 * 
	 * @param map the method map
	 * @param the type of the bean
	 * @return the expanded map with the original entries plus new entries
	 */
	public static <T> Map<Method, T> expandMethodMap(Map<Method, T> map, Class<?> type) {
		Map<Method, T> map2 = new HashMap<Method, T>();
		map2.putAll(map);
		for (Map.Entry<Method, T> e: map.entrySet()) {
			List<Method> methods = MetaUtil.getSameSignatureMethods(e.getKey(), type);
			//System.out.println("MetaUtil.expandMethodMap: " + e.getKey() + "::=" + methods);
			T value = e.getValue();
			for (int i=0; i<methods.size(); i++) {
				map2.put(methods.get(i), value);				
			}
		}
		return map2;
	}
	
	public static Method getSinglePublicMethod(Class<?> type) {
		Method[] methods = type.getMethods();
		if (methods.length==0) {
			throw new NoSuchMethodException(type + " has no methods");
		}
		if (methods.length>1) {
			throw new AmbiguosMethodException(type + " has multiple public methods");
		}

		return methods[0];
	}

	public static String signature(Method method, boolean qualified) {
		StringBuilder sb = new StringBuilder();
		Class<?>[] types = method.getParameterTypes();
		sb.append(qualified ? method.toString() : method.getName());
		sb.append("(");
		for (int i=0; i<types.length; i++) {
			if (i>0) {
				sb.append(", ");
			}
			sb.append(types[i].getName());
		}
		sb.append(")");
		return sb.toString();
	}
}
