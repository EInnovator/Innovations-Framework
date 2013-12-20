/**
 * 
 */
package org.einnovator.format;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;

import org.einnovator.meta.MetaUtil;
import org.einnovator.util.types.TypeUtil;


/**
 * A FieldFormater.
 *
 * @author Jorge Simão
 */
public class PrinterBase {
	static public final String NULL = "null";
	
	static public final String FIELD_SEP = ",";
	
	static public final String VALUE_SEP = "=";
	
	static public final String BEGIN_SEP = "(";
	
	static public final String END_SEP = ")";
	
	static public final String QUOTE_SEP = "'";	
		
	protected boolean inline = true;

	protected boolean sync;

	protected boolean ignoreNull = true;
	
	protected boolean ignoreEmpty = true;
	
	protected boolean quote = true;
	
	protected Appendable sb;

	//
	// Constructors
	//

	/**
	 * Create instance of FieldFormater.
	 *
	 * @param s initial string
	 */
	public PrinterBase(String s) {
		begin(s);
	}

	/**
	 * Create instance of FieldFormater.
	 *
	 */
	public PrinterBase() {
		begin((String)null);
	}

	/**
	 * Create instance of FieldFormater.
	 *
	 * @param type type name is the initial string
	 */
	public PrinterBase(Class<?> type) {
		begin(type);
	}

	/**
	 * Create instance of FieldFormater.
	 *
	 * @param obj type and hash value of object used to generate initial string
	 */
	public PrinterBase(Object obj) {
		begin(obj);
	}

	/**
	 * Create instance of FieldFormater.
	 *
	 * @param inline
	 * @param obj type and hash value of object used to generate initial string
	 */
	public PrinterBase(Object obj, boolean inline) {
		this.inline = inline;
		begin(obj);
	}

	/**
	 * Create instance of DefaultFieldFormater.
	 *
	 * @param inline
	 */
	public PrinterBase(boolean inline) {
		this.inline = inline;
	}


	//
	// Getters and setters
	//
	
	/**
	 * Get the value of inline.
	 *
	 * @return the inline
	 */
	public boolean isInline() {
		return inline;
	}

	/**
	 * Set the value of inline.
	 *
	 * @param inline the inline to set
	 */
	public void setInline(boolean inline) {
		this.inline = inline;
	}

	/**
	 * Get the value of sync.
	 *
	 * @return the sync
	 */
	public boolean isSync() {
		return sync;
	}

	/**
	 * Set the value of sync.
	 *
	 * @param sync the sync to set
	 */
	public void setSync(boolean sync) {
		this.sync = sync;
	}

	/**
	 * Get the value of ignoreNull.
	 *
	 * @return the ignoreNull
	 */
	public boolean isIgnoreNull() {
		return ignoreNull;
	}

	/**
	 * Set the value of ignoreNull.
	 *
	 * @param ignoreNull the ignoreNull to set
	 */
	public void setIgnoreNull(boolean ignoreNull) {
		this.ignoreNull = ignoreNull;
	}

	/**
	 * Get the value of quote.
	 *
	 * @return the quote
	 */
	public boolean isQuote() {
		return quote;
	}

	/**
	 * Set the value of quote.
	 *
	 * @param quote the quote to set
	 */
	public void setQuote(boolean quote) {
		this.quote = quote;
	}


	// 
	// FieldFormatter implementation
	//
	
	public void addField(String name, Object value) {
		addField(name, value, true, false, false);		
	}
	
	public boolean addField(String name, Object value, boolean format, boolean sepBefore, boolean sepAfter) {
		if (!beginField(name, value, sepBefore)) {
			return false;
		}
		if (format) {
			append(formatValue(value));		
		} else {
			append(valueToString(value));				
		}
		endField(sepAfter);
		return true;
	}

	protected boolean beginField(String name, Object value, boolean sepBefore) {
		if (value==null) {
			if (ignoreNull) {
				return false;
			}
		} else {
			if (ignoreEmpty) {
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
		
		try {
			if (sepBefore) {
				String sep = getFieldSeparator();
				if (sep!=null) {
					sb.append(sep);
				}
			}
			sb.append(name);
			String sep = getValueSeparator();
			if (sep!=null) {
				sb.append(sep);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	protected void endField(boolean sepAfter) {
		try {
			if (inline) {
				if (sepAfter) {
					String sep = getFieldSeparator();
					if (sep!=null) {
						sb.append(sep);
					}
				}
			} else {
				sb.append("\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}

	protected String formatValue(Object value) {
		if (value!=null) {
			if (TypeUtil.isText(value.getClass()) && quote) {
				String sep = getQuoteSeparator();
				if (sep!=null) {
					return sep + valueToString(value) + sep;
				} else {
					return valueToString(value);
				}
			} else {
				return valueToString(value);
			}
		} else {
			return NULL;
		}
	}
	
	protected String valueToString(Object value) {
		if (!inline) {
			if (TypeUtil.isCollection(value.getClass())) {
				append("\n");
				for (int i=0; i<TypeUtil.size(value); i++) {
					append(TypeUtil.getComponent(value, i).toString());
					append("\n");
				}
			}
			
		}

		if (value.getClass().isArray()) {
			StringBuffer sb = new StringBuffer("[");
			for (int i=0; i<Array.getLength(value); i++) {
				sb.append(Array.get(value, i));
				if (i<Array.getLength(value)-1) {
					sb.append(',');
				}
			}
			sb.append("]");
			return sb.toString();
		}
		return value.toString();
	}
	
	public void append(String s) {
		if (sb==null) {
			sb = createAppendable(s);
		}
		try {
			sb.append(s);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	//
	// FieldFormatter
	//
	
	public void begin(String s) {
		sb = createAppendable(s);
	}

	public void begin() {
		begin((String)null);
	}

	public void begin(Class<?> type) {
		begin(MetaUtil.getUnqualifiedName(type));
	}

	public void begin(Object obj) {
		if (obj==null) {
			sb = null;
			return;
		}
		begin();
		beginObject(obj);
	}
	
	protected void beginObject(Object obj) {
		try {
			sb.append(MetaUtil.getUnqualifiedName(obj.getClass()));
			//if (hashCode) {
			//	sb.append("@" + obj.hashCode());
			//}
			String sep = getBeginSeparator();
			if (sep!=null) {
				sb.append(sep);
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
	}
	
	protected void endObject() {
		try {
			String sep = getEndSeparator();	
			if (sep!=null) {
				sb.append(sep);
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}		
	}
	
	public String build() {
		if (sb==null) {
			return NULL;
		}
		endObject();
		String s = sb.toString();
		sb = null;
		return s;
	}
	
	// 
	// Protected Methods
	//

	protected Appendable createAppendable(String s) {
		if (sync) {
			return s!=null ? new StringBuffer(s) : new StringBuffer(); 
		} else {
			return s!=null ? new StringBuilder(s) : new StringBuilder(); 			
		}
	}
	
	
	protected String getFieldSeparator() {
		return FIELD_SEP;
	}

	protected String getValueSeparator() {
		return VALUE_SEP;
	}

	protected String getBeginSeparator() {
		return BEGIN_SEP;
	}

	protected String getEndSeparator() {
		return END_SEP;
	}
	
	protected String getQuoteSeparator() {
		return QUOTE_SEP;
	}
	
}
