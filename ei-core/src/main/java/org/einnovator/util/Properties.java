/**
 * 
 */
package org.einnovator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Set;

import org.einnovator.util.wrapper.MapWrapper;


/**
 *
 * A Properties.
 * 
 * (Documentation and API partially based/inspired or Cut&past from java.util.Properties API and documentation).
 * 
 * The Properties class represents a persistent set of properties. 
 * The Properties can be saved to a stream or loaded from a stream. 
 * Each key and its corresponding value in the property map is a string.
 *
 * A property list can contain another property list as its "parent"; 
 * the parent property list is searched if the property key is not found in the original property list.
 *
 *
 * The load(Reader) / store(Writer, String) methods load and store properties from and to a
 * character based stream in a simple line-oriented format specified below. 
 * The load(InputStream) / store(OutputStream, String) methods work the same way
 * as the load(Reader)/store(Writer, String) pair, except the input/output stream
 * is encoded in ISO 8859-1 character encoding. Characters that cannot be directly
 * represented in this encoding can be written using Unicode escapes ;
 * only a single 'u' character is allowed in an escape sequence.
 * 
 * The native2ascii tool can be used to convert property files to and from other character encodings.
 *
 * The loadFromXML(InputStream) and storeToXML(OutputStream, String, String) methods 
 * load and store properties in a simple XML format. 
 * By default the UTF-8 character encoding is used, however a specific encoding may be
 * specified if required. An XML properties document has the following DOCTYPE declaration:
 *
 * <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
 *
 *
 * Note that the system URI (http://java.sun.com/dtd/properties.dtd) is not accessed when exporting or importing properties; it merely serves as a string to uniquely identify the DTD, which is:
 *
 *    <?xml version="1.0" encoding="UTF-8"?>
 *
 *   <!-- DTD for properties -->
 *
 *   <!ELEMENT properties ( comment?, entry* ) >
 *
 *   <!ATTLIST properties version CDATA #FIXED "1.0">
 *
 *   <!ELEMENT comment (#PCDATA) >
 *
 *  <!ELEMENT entry (#PCDATA) >
 *
 *  <!ATTLIST entry key CDATA #REQUIRED>
 *
 *
 * This class is thread-safe: multiple threads can share a single Properties object without the need for external synchronization.
 *
 *
 * @author Jorge Simão
 */
public class Properties extends MapWrapper<String, String> {

	protected Properties parent;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of Properties.
	 *
	 * @param delegate
	 */
	public Properties(Map<String, String> delegate) {
		super(delegate);
	}
	
	/**
	 * Create instance of Properties.
	 *
	 * @param delegate
	 * @param parent
	 */
	public Properties(Map<String, String> delegate, Properties parent) {
		super(delegate);
		this.parent = parent;
	}
	
	//
	// Properties methods
	//
	
	/**
	 * Searches for the property with the specified key in this property map.
	 * If the key is not found in this property map, the default property map, and its defaults, recursively, are then checked. 
	 * The method returns {@code null} if the property is not found. 
	 * 
	 * @param key the property key
	 * @return the value in this property map with the specified key value
	 * @see {@code getProperty(key, defaultValue)}
	 */
	public String getProperty(String key) {
		String value = delegate.get(key);
		return value!=null ? value : (parent!=null ? parent.getProperty(key) : null);
	}

	/**
	 * Searches for the property with the specified key in this property map.
	 * If the key is not found in this property map, the default property map, 
	 * and its defaults, recursively, are then checked.
	 * The method returns the default value argument if the property is not found.
	 * 
	 * @param key key the property key
	 * @param defaultValue a default value
	 * @return the value in this property map with the specified key value
	 */
	public String getProperty(String key, String defaultValue) {
		String value = get(key);
		return value!=null ? value :
			(parent!=null ? parent.getProperty(key, defaultValue) : defaultValue);
	}


	/**
	 * Calls the map method put. 
	 * Provided for parallelism with the getProperty method. 
	 * Enforces use of strings for property keys and values. 
	 * The value returned is the result of the map call to put.
	 * 
	 * @param key the key to be placed into this property map
	 * @param value the value corresponding to key
	 * @return the previous value of the specified key in this property map, 
	 * or null if it did not have one
	 */
	public Object setProperty(String key, String value) {
		return delegate.put(key, value);
	}
	
	/**
	 *
	 * Reads a property list (key and element pairs) from the input character stream in a simple line-oriented format.
	 * Properties are processed in terms of lines.
	 * There are two kinds of line, natural lines and logical lines.
	 * 
	 * A natural line is defined as a line of characters that is terminated either by
	 * a set of line terminator characters (\n or \r or \r\n) or by the end of the stream. 
	 * A natural line may be either a blank line, a comment line,
	 * or hold all or some of a key-element pair. 
	 * 
	 * A logical line holds all the data of a key-element pair, which may be spread out across
	 * several adjacent natural lines by escaping the line terminator sequence with a
	 * backslash character \.
	 * Note that a comment line cannot be extended in this manner;
	 * every natural line that is a comment must have its own comment indicator, as described below.
	 * Lines are read from input until the end of the stream is reached.
	 * 
	 * A natural line that contains only white space characters is considered blank and is ignored. A comment line has an ASCII '#' or '!' as its first non-white space character; comment lines are also ignored and do not encode key-element information. In addition to line terminators, this format considers the characters space (' ', '\u0020'), tab ('\t', '\u0009'), and form feed ('\f', '\u000C') to be white space.
	 * If a logical line is spread across several natural lines, the backslash escaping the line
	 * terminator sequence, the line terminator sequence, and any white space at the start of the
	 * following line have no affect on the key or element values. 
	 * The remainder of the discussion of key and element parsing (when loading) will assume
	 *  all the characters constituting the key and element appear on a single natural line after
	 *  line continuation characters have been removed. 
	 * 
	 *  Note that it is not sufficient to only examine the character preceding a line terminator 
	 *  sequence to decide if the line terminator is escaped; 
	 *  there must be an odd number of contiguous backslashes for the line terminator to be escaped.
	 *  Since the input is processed from left to right, a non-zero even number of 2n contiguous backslashes
	 *  before a line terminator (or elsewhere) encodes n backslashes after escape processing.
	 * 
	 * The key contains all of the characters in the line starting with the first non-white space
	 * character and up to, but not including, the first unescaped '=', ':', or white space character other than a line terminator. All of these key termination characters may be included in the key by escaping them with a preceding backslash character; for example,
	 * \:\=
	 * would be the two-character key ":=".
	 *  Line terminator characters can be included using \r and \n escape sequences. Any white space after the key is skipped; if the first non-white space character after the key is '=' or ':', then it is ignored and any white space characters after it are also skipped. All remaining characters on the line become part of the associated element string; if there are no remaining characters, the element is the empty string "". Once the raw character sequences constituting the key and element are identified, escape processing is performed as described above.
	 * 
	 * As an example, each of the following three lines specifies the key "Truth" and
	 *  the associated element value "Beauty":
	 * 
	 * Truth = Beauty
	 * Truth:Beauty
	 * Truth                    :Beauty  
	 * 
	 * As another example, the following three lines specify a single property:
	 * 
	 * fruits            apple, banana, pear, \
	 * 					cantaloupe, watermelon, \
	 * 					kiwi, mango
	 * 	 
	 * The key is "fruits" and the associated element is:
	 * "apple, banana, pear, cantaloupe, watermelon, kiwi, mango"
	 *  Note that a space appears before each \ so that a space will appear after each comma in the final result; the \, line terminator, and leading white space on the continuation line are merely discarded and are not replaced by one or more other characters.
	 *   
	 *   As a third example, the line:
	 *   
	 *   cheeses
	 *   
	 *   specifies that the key is "cheeses" and the associated element is the empty string "".
	 *   
	 *   Characters in keys and elements can be represented in escape sequences similar to those used for character and string literals (see §3.3 and §3.10.6 of the Java Language Specification). The differences from the character escape sequences and Unicode escapes used for characters and strings are:
	 *   * Octal escapes are not recognized.
	 *   * The character sequence \b does not represent a backspace character.
	 *   * The method does not treat a backslash character, \, before a non-valid escape character as an error; the backslash is silently dropped. For example, in a Java string the sequence "\z" would cause a compile time error. In contrast, this method silently drops the backslash. Therefore, this method treats the two character sequence "\b" as equivalent to the single character 'b'.
	 *   * Escapes are not necessary for single and double quotes; however, by the rule above, single and double quote characters preceded by a backslash still yield single and double quote characters, respectively.
	 *   * Only a single 'u' character is allowed in a Uniocde escape sequence. 
	 *   The specified stream remains open after this method returns.
	 *   
	 * @param in the input character stream
	 * @throws IOException if an error occurred when reading from the input stream
	 * @throws IllegalArgumentException if a malformed Unicode escape appears in the input.
	 */
	public void load(Reader in) throws IOException {
	
		BufferedReader reader = in instanceof BufferedReader ? (BufferedReader) in:
			new BufferedReader(in);

		String line;
	    while ((line = readLine(reader)) != null) {
	    	if (!line.isEmpty() && !isComment(line)) {
	    		parseLine(line);
	    	}
	    }
	}
		
	protected void parseLine(String line) {
		int i= line.indexOf(":");
		if (i<0) {
			i = line.indexOf("=");
		}
		String key;
		String value;
		
		if (i<0) {
			key = line;
			value = "";
		} else {
			key = line.substring(0,i);
			value = line.substring(i+1);
		}
	
		key = key.trim();
		value = value.trim();
		
		setProperty(key, value);
	}
	
	protected String readLine(BufferedReader reader) {
	    String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				return line;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);	
		}
		return null;		
	}
		
	protected boolean isComment(String s) {
		s = s.trim();
		return s.startsWith("#") || s.startsWith("//");
	}
	
	/**
	 * Reads a property list (key and element pairs) from the input byte stream. 
	 * The input stream is in a simple line-oriented format as specified in load(Reader) and is assumed to use the ISO 8859-1 character encoding; that is each byte is one Latin1 character. Characters not in Latin1, and certain special characters, are represented in keys and elements using Unicode escapes.
	 *
	 * The specified stream remains open after this method returns.
	 * 
	 * @param inStream the input stream.
	 * @throws IOException if an error occurred when reading from the input stream. 
	 * @throws IllegalArgumentException if the input stream contains a malformed Unicode escape sequence.
	 *
	 * 
	 */
	public void load(InputStream inStream) throws IOException {
		load(new InputStreamReader(inStream));
	}
 
	/**
	 * Writes this property list (key and element pairs) in this Properties table to the
	 * output character stream in a format suitable for using the load(Reader) method.
	 * 
	 * Properties from the parent table of this Properties table (if any) are not written
	 *  out by this method.
	 * If the comments argument is not null, then an ASCII # character, the comments string, 
	 * and a line separator are first written to the output stream. 
	 * Thus, the comments can serve as an identifying comment.
	 * Any one of a line feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately by a line feed in comments is replaced by a line separator generated by the Writer and if the next character in comments is not character # or character ! then an ASCII # is written out after that line separator.
	 * Next, a comment line is always written, consisting of an ASCII # character, the current date and time (as if produced by the toString method of Date for the current time), and a line separator as generated by the Writer.
	 * Then every entry in this Properties table is written out, one per line. For each entry the key string is written, then an ASCII =, then the associated element string. For the key, all space characters are written with a preceding \ character. For the element, leading space characters, but not embedded or trailing space characters, are written with a preceding \ character. The key and element characters #, !, =, and : are written with a preceding backslash to ensure that they are properly loaded.
	 * After the entries have been written, the output stream is flushed. The output stream remains open after this method returns.
	 * 
	 * @param out an output character stream writer
	 * @param comments a description of the property list
	 * @throws IOException  if writing this property list to the specified output stream throws an IOException. 
	 * @throws NullPointerException if writer is null.
	 */
	public void store(Writer out, String comments) throws IOException {
		if (comments!=null) {
			out.write("#" + comments + "\n");
		}
		for (Map.Entry<String, String> e: delegate.entrySet()) {
			out.write(e.getKey() + "=" + e.getValue() + "\n");
		}		
	}
 
	/**
	 * Writes this property list (key and element pairs) in this Properties table to the output stream in a format suitable for loading into a Properties table using the load(InputStream) method.
	 * 
	 * Properties from the defaults table of this Properties table (if any) are not written out by this method.
	 * 
	 * This method outputs the comments, properties keys and values in the same format as
	 *  specified in store(Writer), with the following differences:
	 * 
	 * * The stream is written using the ISO 8859-1 character encoding.
	 * * Characters not in Latin-1 in the comments are written as \\uxxxx  for their appropriate unicode hexadecimal value xxxx.
	 * * Characters less than \\u0020 and characters greater than \\u007E in property keys or values are written as \\uxxxx for the appropriate hexadecimal value xxxx. 
	 * 
	 * After the entries have been written, the output stream is flushed. The output stream remains open after this method returns.
	 * 
	 * @param out an output stream
	 * @param comments a description of the property list
	 * @throws IOException if writing this property list to the specified output stream throws an IOException
	 * @throws NullPointerException if out is null
	 */
	public void store(OutputStream out, String comments) throws IOException {
		store(new OutputStreamWriter(out), comments);
	}


	/**
	 * Loads all of the properties represented by the XML document on the specified input stream into this properties table.
	 * The XML document must have the following DOCTYPE declaration:
	 *
	 * <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
	 *
	 * Furthermore, the document must satisfy the properties DTD described above.
	 *
	 * The specified stream is closed after this method returns.
	 *
	 * @param in the input stream from which to read the XML document.
	 * @throws IOException if reading from the specified input stream results in an IOException.
	 * @throws InvalidPropertiesFormatException Data on input stream does not constitute a valid XML document with the mandated document type.
	 */
	public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Emits an XML document representing all of the properties contained in this table.
	 *
	 * An invocation of this method of the form props.storeToXML(os, comment) behaves in exactly the same way as the invocation props.storeToXML(os, comment, "UTF-8");.
	 *
	 * @param out the output stream on which to emit the XML document.
	 * @param comment a description of the property list, or null if no comment is desired.
	 * @throws IOException if writing to the specified output stream results in an IOException.
	 * @throws NullPointerException - if out is null
	 */
	public void storeToXML(OutputStream out, String comment) throws IOException {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Emits an XML document representing all of the properties contained in this table, using the specified encoding.
	 *
	 * The XML document will have the following DOCTYPE declaration:
	 *
	 * <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
	 *
	 * If the specified comment is null then no comment will be stored in the document.
	 *
	 * The specified stream remains open after this method returns.
	 *
	 * @param os the output stream on which to emit the XML document.
	 * @param comment a description of the property list, or null if no comment is desired. 
	 * @param encoding
	 * @throws IOException if writing to the specified output stream results in an IOException.
	 * @throws NullPointerException if os is null, or if encoding is null.
	 */
	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
		throw new RuntimeException("Not implemented");
	}

 	

	/**
	 * Returns an enumeration of all the keys in this property list,
	 * including distinct keys in the default property list if a key of the same name
	 * has not already been found from the main properties list.
	 *
	 * @return an enumeration of all the keys in this property list, including the keys in the default property list.
	 */
	public Enumeration<String> propertyNames() {
		return Collections.enumeration(delegate.keySet());
	}

    
	/**
	 * Returns a set of keys in this property list where the key and its corresponding value are strings, including distinct keys in the default property list if a key of the same name has not already been found from the main properties list. Properties whose key or value is not of type String are omitted.
     * The returned set is not backed by the Properties object. Changes to this Properties are not reflected in the set, or vice versa.
     * 
	 * @return a set of keys in this property list where the key and its corresponding value are strings, including the keys in the default property list.
	 */
	public Set<String> stringPropertyNames() {
		return delegate.keySet();
	}        
	
	/**
	 * Prints this property list out to the specified output stream. This method is useful for debugging.
	 * 
	 * @param out an output stream.
	 */
	public void list(PrintStream out) {
		for (Map.Entry<String, String> e: delegate.entrySet()) {
			out.println(e.getKey() + "=" + e.getValue());
		}
	}

	/**
	 * Prints this property list out to the specified output stream.
	 * This method is useful for debugging.
	 *
	 * @param out an output stream
	 */
	public void list(PrintWriter out) {
		for (Map.Entry<String, String> e: delegate.entrySet()) {
			out.println(e.getKey() + "=" + e.getValue());
		}		
	}
	
	/**
	 * Prints this property list to System.out.
	 * This method is useful for debugging.
	 *
	 */
	public void list() {
		list(System.out);
	}
}
