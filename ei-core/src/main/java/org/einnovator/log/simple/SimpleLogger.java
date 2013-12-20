package org.einnovator.log.simple;


import java.io.PrintStream;
import java.util.Arrays;

import org.einnovator.format.FormatContext;
import org.einnovator.format.FormatUtil;
import org.einnovator.format.FormattedObject;
import org.einnovator.log.Level;
import org.einnovator.log.support.NamedLogger;
import org.einnovator.meta.MetaUtil;
import org.einnovator.util.types.TypeUtil;


/**
 * A SimpleLogger.
 *
 * @author Jorge Simão
 */
public class SimpleLogger extends NamedLogger {
	protected int typeFormat;
	
	protected int sourceFormat;

	protected PrintStream flush = System.out;		
	
	protected PrintStream out = System.err;		

	//
	// Constructors
	//
	
	public SimpleLogger() {
	}
	
	/**
	 * Create instance of {@code SimpleLogger}.
	 *
	 * @param type
	 * @param level
	 */
	public SimpleLogger(Class<?> type, Level level) {
		super(type, level);
	}

	/**
	 * Create instance of {@code SimpleLogger}.
	 *
	 * @param type
	 * @param format
	 */
	public SimpleLogger(Class<?> type, Object... format) {
		super(type);
		setFormat(format);
		name = formatTypeName(type);
	}
	
	public SimpleLogger(String name, Object... format) {
		super(name);
		setFormat(format);		
	}
	
	protected void setFormat(Object[] format) {
		if (format.length>0) {
			typeFormat = TypeUtil.cast(format[0], Integer.class);
		}
		if (format.length>1) {
			sourceFormat = TypeUtil.cast(format[1], Integer.class);
		}		
	}
	
	//
	// Getters and setters
	//
	

	/**
	 * Get the value of typeFormat.
	 *
	 * @return the typeFormat
	 */
	public int getTypeFormat() {
		return typeFormat;
	}

	/**
	 * Get the value of sourceFormat.
	 *
	 * @return the sourceFormat
	 */
	public int getSourceFormat() {
		return sourceFormat;
	}

	/**
	 * Set the value of sourceFormat.
	 *
	 * @param sourceFormat the sourceFormat to set
	 */
	public void setSourceFormat(int sourceFormat) {
		this.sourceFormat = sourceFormat;
	}

	/**
	 * Get the value of out.
	 *
	 * @return the out
	 */
	public PrintStream getOut() {
		return out;
	}

	/**
	 * Set the value of out.
	 *
	 * @param out the out to set
	 */
	public void setOut(PrintStream out) {
		this.out = out;
	}
	
	// 
	// Logger implementation
	//
	
	@Override
	public void log(Level level, Object source, String meth, Object... objs) {
		//System.out.println(this + " " + level + " " + (level.compareTo(this.level)<0) + " " + level.compareTo(this.level));
		FormatContext context = null;
		if (!level.enabled(this.level)) {
			return;
		}
		printf("%s: ", level);
		
		if (source==null && name!=null) {
			if (meth!=null) {
				printf("%s", name);				
			} else {
				printf("%s", name);
			}
		}

		if (source!=null) {
			printf("%s", formatSource(source));			
		}
		if (meth!=null) {
			printf(".%s", meth);
		}
		printf(":");	
		if (objs!=null) {
			for (Object obj: objs) {
				if (context==null && obj instanceof FormattedObject) {
					context = new FormatContext().level(level);
				}
				printf(" %s", format(obj, context));				
			}			
		} else {
			printf(" null");
		}
		println();
	}

	private String formatTypeName(Class<?> type) {
		switch (typeFormat) {
			case 0:
				return type.getName();
			default:
				return MetaUtil.getUnqualifiedName(type);

		}
	}
	
	private String formatSource(Object source) {
		switch (sourceFormat) {
			case 0:
				return MetaUtil.getUnqualifiedName(source.getClass()) + "@" + source.hashCode();
			default:
				return source.toString();
		}
	}

	private void println() {
		printf("\n");
	}
	
	private void printf(String format, Object... args) {
		if (flush!=null) {
			flush.flush();
		}
		out.printf(format, args);
	}
	
	private String format(Object obj, FormatContext context) {
		if (obj==null) {
			return null;
		}
		if (obj.getClass().isArray()) {
			return Arrays.toString((Object[])obj);
		}
		if (obj instanceof FormattedObject) {
			return FormatUtil.toString(obj, context);
		}
		return obj.toString();
	}
}
