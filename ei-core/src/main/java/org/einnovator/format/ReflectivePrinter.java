/**
 * 
 */
package org.einnovator.format;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import org.einnovator.format.PrinterBase;
import org.einnovator.meta.MetaUtil;
import org.einnovator.util.ArrayUtil;



/**
 * A FieldFormater.
 *
 * @author Jorge Simão
 */
public class ReflectivePrinter extends PrinterBase {
	//
	// Constructors
	//

	/**
	 * Create instance of FieldFormater.
	 *
	 * @param s initial string
	 */
	public ReflectivePrinter(String s) {
		super(s);
	}

	/**
	 * Create instance of FieldFormater.
	 *
	 */
	public ReflectivePrinter() {
		super((String)null);
	}

	/**
	 * Create instance of FieldFormater.
	 *
	 * @param type type name is the initial string
	 */
	public ReflectivePrinter(Class<?> type) {
		super(type);
	}

	/**
	 * Create instance of FieldFormater.
	 *
	 * @param obj type and hash value of object used to generate initial string
	 */
	public ReflectivePrinter(Object obj) {
		super(obj);
		begin(obj);
		addInspectedFields(obj);
	}

	/**
	 * Create instance of ReflectiveFieldFormater.
	 *
	 * @param obj
	 * @param inline
	 */
	public ReflectivePrinter(Object obj, boolean inline) {
		super(obj, inline);
		begin(obj);
		addInspectedFields(obj);
	}

	/**
	 * Create instance of ReflectiveFieldFormater.
	 *
	 * @param obj
	 * @param fieldInclude
	 * @param fieldExclude
	 */
	public ReflectivePrinter(Object obj, String[] fieldInclude, String[] fieldExclude) {
		super(obj);
		begin(obj);
		addInspectedFields(obj, fieldInclude, fieldExclude);
	}
	
	
	//
	// Getters and setters
	//
	
	
	// 
	// Public methods
	//


	private boolean addInspectedFields(Object obj, Class<?> type, boolean first, String[] fieldInclude, String[] fieldExclude) {		
		Class<?> superType = type.getSuperclass();
		if (superType!=null) {
			if (addInspectedFields(obj, superType, first, fieldInclude, fieldExclude)) {
				first = false;			
			}
		}
		Field[] fields = type.getDeclaredFields();
		for (Field field: fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod)) {
				continue;
			}
			if (fieldInclude!=null && fieldInclude.length>0) {
				if (!ArrayUtil.contains(fieldInclude, field.getName())) {
					continue;
				}
			}
			if (fieldExclude!=null && fieldExclude.length>0) {
				if (ArrayUtil.contains(fieldExclude, field.getName())) {
					continue;
				}
			}
			if (addInspectedField(field, obj, !first)) {
				first = false;
			}
		}
		return !first;
	}

	public boolean addInspectedField(Field field, Object obj, boolean sepBefore) {
		if (MetaUtil.isOuter(field)) {
			return false;
		}
		Format output = field.getAnnotation(Format.class);
		if (output!=null) {
			if (output.ignore()==true) { 
				return false;
			}
			Object value = MetaUtil.getFieldValue(field, obj);
			if (value==null) {
				if (output.ignoreNull()==true) {
					return false;
				}
			} else {
				if (output.ignoreEmpty()) {
					if (value.getClass().isArray()) {
						if (Array.getLength(value)==0) {
							return false;					
						}
					} else if (value instanceof Collection<?>) {
						if (((Collection<?>)value).isEmpty()) {
							return false;
						}
					}					
				}
			}
			String name = output.label().isEmpty() ? field.getName() : output.label(); 
			if (value!=null /*&& output.include().length>0 || output.exclude().length>0*/) {
				if (!beginField(name, value, sepBefore)) {
					return false;
				}
				beginObject(value);
				boolean b = addInspectedFields(value, value.getClass(), true, null, null /*output.include(), output.exclude()*/);
				endObject();
				endField(false);
				return b;
			} else {
				return addField(name, value, true, sepBefore, false);
			}
		} else {
			Object value = MetaUtil.getFieldValue(field, obj);
			return addField(field.getName(), value, true, sepBefore, false);
		}
	}

	public void addInspectedFields(Object obj) {
		if (obj==null) {
			return;
		}
		addInspectedFields(obj, obj.getClass(), true, null, null);
	}

	public void addInspectedFields(Object obj, String[] fieldInclude, String[] fieldExclude) {
		if (obj==null) {
			return;
		}
		addInspectedFields(obj, obj.getClass(), true, fieldInclude, fieldExclude);
	}

	public String format(Object obj) {
		if (obj==null) {
			sb = null;
			return "null";
		}
		begin(obj);
		addInspectedFields(obj);
		return build();
	}

}
