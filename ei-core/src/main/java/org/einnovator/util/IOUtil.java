package org.einnovator.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Miscellaneous utilities for reading and writing on stream.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class IOUtil {

	public static final int DEFAULT_BUFFER_SIZE = 4096;
	
	private static int bufferSize = DEFAULT_BUFFER_SIZE;

	
	
	/**
	 * Get the value of property {@code bufferSize}.
	 *
	 * @return the bufferSize
	 */
	public static int getBufferSize() {
		return bufferSize;
	}

	/**
	 * Set the value of property {@code bufferSize}.
	 *
	 * @param bufferSize the bufferSize to set
	 */
	public static void setBufferSize(int bufferSize) {
		IOUtil.bufferSize = bufferSize;
	}

	public static String toString(InputStream in) {
		return new String(readBytes(in));
	}
	
	public static byte[] readBytesSafe(InputStream in) throws IOException {
		return readBytesSafe(in, bufferSize);
	}

	public static byte[] readBytes(InputStream in) {
		return readBytes(in, bufferSize);
	}

	public static byte[] readBytesSafe(InputStream in, int bufferSize) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		writeBytesSafe(in, out, bufferSize);
		return out.toByteArray();
	}
	
	public static byte[] readBytes(InputStream in, int bufferSize) {
		try {
			return readBytesSafe(in, bufferSize);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeBytes(InputStream in, OutputStream out) {
		writeBytes(in, out, bufferSize);
	}
	
	public static void writeBytes(InputStream in, OutputStream out, int bufferSize) {
		try {
			writeBytesSafe(in, out, bufferSize);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeBytesSafe(InputStream in, OutputStream out) throws IOException {
		writeBytesSafe(in, out, bufferSize);
	}
	
	public static void writeBytesSafe(InputStream in, OutputStream out, int bufferSize) throws IOException {
		int n;
		byte[] b = new byte[bufferSize];
		try {
			while ((n = in.read(b))>0) {
				out.write(b, 0, n);
			}			
		} finally {
			in.close();
		}		
	}

	public static void writeBytes(InputStream in, Writer out) {
		writeBytes(in, out, bufferSize);
	}

	public static void writeBytesSafe(InputStream in, Writer out) throws IOException {
		writeBytesSafe(in, out, bufferSize);
	}

	public static void writeBytes(InputStream in, Writer out, int bufferSize) {
		try {
			writeBytesSafe(in, out, bufferSize);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeBytesSafe(InputStream in, Writer out, int bufferSize) throws IOException {
		int n;
		byte[] b = new byte[bufferSize];
		try {
			while ((n = in.read(b))>0) {
				char[] c = new String(b).toCharArray();
				out.write(c, 0, n);
			}			
		} finally {
			in.close();
		}
	}
}
