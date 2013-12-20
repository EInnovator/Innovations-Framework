package org.einnovator.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * A {@code FileUtil}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FileUtil {
	static public final String EXTENTION_SEPARATOR = ".";
	
	/**
	 * Get extension for filename.
	 * 
	 * @param filename the filaname
	 * @return the extension
	 */
	public static String getExtention(String filename)  {	
		int i = filename.lastIndexOf(EXTENTION_SEPARATOR);
		if (i<0) {
			return null;
		}
		return filename.substring(i);
	}

	/**
	 * Remove extension from filename.
	 * 
	 * @param filename the filename
	 * @return the filename without the extension
	 */
	public static String removeExtention(String filename)  {	
		int i = filename.lastIndexOf(".");
		if (i<0) {
			return filename;
		}
		return filename.substring(0, i);
	}

	/**
	 * Replace file extension by another.
	 * 
	 * @param filename the filename
	 * @param ext the new extention
	 * @return the filename with modified extension
	 */
	public static String replaceExtention(String filename, String ext)  {	
		return addExtention(removeExtention(filename), ext);
	}
	
	public static String absolute(String filename)  {	
		try {
			return new File(filename).getCanonicalPath();
		} catch (IOException e) { return filename; }
	}
	
	public static boolean mkdirs(String s)  {
		return mkdirs(s, File.separator);
	}
	
	public static boolean mkdirs(String s, String separator)  {
		try {
			File f = new File(s).getCanonicalFile();
			s = f.getCanonicalPath();
			if (separator.equals("\\")) {
				separator = "\\" + separator;
			}
			String[] l = s.split(separator);
			if (l.length>0 && l[l.length-1].contains(".")) {
				int i = s.lastIndexOf(File.separator);
				if (i>0) {
					s = s.substring(0, i);
					f = new File(s);
				}
			}
			return f.mkdirs();
		} catch (IOException ex) { return false; }
	}
	
	public static String pathConcat(String path0, String path1)  {	
		return StringUtil.sconcat(path0, path1, File.separator);
	}

	public static String addExtention(String path, String ext)  {	
		return StringUtil.sconcat(path, ext, ".");
	}

	public static String extractDir(String path)  {	
		return extractDir(path, File.separator);
	}
	
	public static String extractDir(String path, String separator)  {	
		int i= path.lastIndexOf(separator);
		return i>=0 ? path.substring(0, i+1) : "";
	}

	public static String replaceFilename(String path, String filename)  {	
		return pathConcat(extractDir(path), filename);
	}

	public static String replaceFilename(String path, String filename, String separator)  {	
		return StringUtil.sconcat(extractDir(path, separator), filename, separator);
	}

	static public String decodeFileName(URL url) {
		String filePath = url.getFile();
		//MS-WINDOWS HACK
		if(filePath.indexOf("%20") > 0) {
			filePath = filePath.replaceAll("%20", " ");
		}
		return filePath;
	}


	static public void copy(InputStream in, OutputStream out) throws IOException {
		int c;
		BufferedInputStream in2 = new BufferedInputStream(in);
		BufferedOutputStream out2 = new BufferedOutputStream(out);
		while ((c= in2.read())>=0) {
			out2.write(c);
		}
		out2.flush();
	}
}
