package org.einnovator.util;

import org.einnovator.util.StringUtil;


public class NameUtil {
	static public String sepXML = "-";
		
	static public String transformNameXML2Java(String s, boolean capitalize) {
		return transformNameXML2Java(s, sepXML, capitalize);
	}
	
	static public String transformNameXML2Java(String s, String sep, boolean capitalize) {
		StringBuffer sb = new StringBuffer();
		String[] l = s.split(sep);
		int i = 0;
		for (String si: l) {
			if (i==0 && !capitalize) sb.append(si);
			sb.append(StringUtil.capitalize(si));
			i++;
		}
		return sb.toString();
	}

	/**
	 * Ref XML tag name to Java name.
	 * @param s name to transform
	 * @return the transformed name
	 */
	static public String transformNameJava2XML(String s) {
		return transformNameJava2XML(s, sepXML);
	}

	/**
	 * Ref XML tag name to Java name.
	 * @param s name to transform
	 * @param sepXML XML name separator
	 * @return the transformed name
	 */
	static public String transformNameJava2XML(String s, String sepXML) {
		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i=0; i<n; i++) {
			int c = s.charAt(i);
			if (i>0 && Character.isUpperCase(c) && !Character.isUpperCase(s.charAt(i-1))) {
				sb.append('-');
			}
			c = Character.toLowerCase(c);
			sb.append((char)c);
		}
		return sb.toString();
	}
	
}
