package org.einnovator.format.simple;

import java.awt.Color;
import java.util.Locale;

import org.einnovator.convert.ConversionException;
import org.einnovator.format.Formatter;



/**
 * AA ColorFormatter.
 *
 * @author Jorge Simão
 */
public class ColorFormatter implements Formatter<Color> {

	//
	// SimpleFormatter<Color> implementation
	//
	
	@Override
	public String print(Color value, Locale locale) {
		Color color = (Color)value;
		return "#" + format(color.getRed())  + format(color.getGreen()) + format(color.getBlue());		
	}

	private String format(int value) {
		String s = Integer.toString(value, 16);
		if (s.length()==1) {
			s = "0" + s;
		}
		return s;
	}
	@Override
	public Color parse(String text, Locale locale) {
		text = text.toLowerCase().trim();
		if (text.startsWith("#")) {
			return (Color)parseHexColor(text);
		} else if (text.startsWith("rgb(")){
			return (Color)parseRGB(text);
		} else if (text.startsWith("hsb(")){
			return (Color)parseHSB(text);			
		} else if (text.startsWith("0")){
			return (Color)Color.decode(text);
		} else {
			Color c = Color.getColor(text);
			if (c!=null) {
				return (Color) c;
			}
			throw new ConversionException("Syntax error while parsing color format string:'" + text + "'");			
		}
	}

	protected Color parseHexColor(String text) {
		String s = text.substring(1);
		try {
			if (s.length()==3 || s.length()==4) {
				int r = getByte(s.charAt(0));
				int g = getByte(s.charAt(1));
				int b = getByte(s.charAt(2));
				if (s.length()==4) {
					int a = getByte(text.charAt(3));
					return new Color(r, g, b, a);
				} else {
					return new Color(r, g, b);					
				}
			} else if (s.length()==6 || s.length()==8) {
				int r = getByte(s.charAt(0), s.charAt(1));
				int g = getByte(s.charAt(2), s.charAt(3));
				int b = getByte(s.charAt(4), s.charAt(5));
				if (s.length()==8) {
					int a = getByte(text.charAt(6), text.charAt(7));
					return new Color(r, g, b, a);
				} else {
					return new Color(r, g, b);					
				}			
			}
		} catch (ConversionException e) {	
			throw new ConversionException("Syntax error while parsing color format string:'" + text + "'", e);
		}
		throw new ConversionException("Syntax error while parsing color format string:'" + text + "'");				
	}
	
	protected Color parseRGB(String text) {
		String[] a = parseColor(text);
		try {
			int[] c = new int[a.length];
			for (int i=0; i<c.length; i++) {
				c[i] = Integer.parseInt(a[i]);
			}
			if (c.length==3) {				
				return new Color(c[0], c[1], c[2]);
			} else {
				return new Color(c[0], c[1], c[2], c[3]);
			}
		} catch (Exception e) {
		}
		float[] cf = new float[a.length];
		for (int i=0; i<cf.length; i++) {
			cf[i] = Integer.parseInt(a[i]);
		}
		if (cf.length==3) {				
			return new Color(cf[0], cf[1], cf[2]);
		} else {
			return new Color(cf[0], cf[1], cf[2], cf[3]);
		}

	}

	protected Color parseHSB(String text) {
		String[] a = parseColor(text);
		float[] cf = new float[a.length];
		for (int i=0; i<cf.length; i++) {
			cf[i] = Float.parseFloat(a[i]);
		}
		if (cf.length==3) {				
			return Color.getHSBColor(cf[0], cf[1], cf[2]);
		} else {
			throw new ConversionException("Syntax error while parsing color format string:'" + text + "'");				
		}
	}

	protected String[] parseColor(String text) {
		String s = text.substring("rgb(".length());
		if (!s.endsWith(")")) {
			throw new ConversionException("Syntax error while parsing color format string:'" + text + "'");
		}
		s = s.substring(0, s.length()-1);
		String[] a = s.split(",");
		if (a.length!=3 && a.length!=4) {
			throw new ConversionException("Syntax error while parsing color format string:'" + text + "'");				
		}
		return a;
	}
	
	protected int getByte(char hi) {
		char hi2 = Character.toUpperCase(hi);
		int v = hi2 - '0';
		if (v<0 || v>15) {
			throw new ConversionException("Invalid value for color component:'" + hi + "'");
		}
		return v*16;
	}

	protected int getByte(char hi, char low) {
		return Integer.valueOf(hi + "" + low, 16);
	}

}
