/**
 * 
 */
package org.einnovator.util;

/**
 * A {@code ErrorUtil}.
 *
 * @author Jorge Simão
 */
public class ErrorUtil {

	
	public static Throwable unwrapThrowable(Throwable throwable) {
		while (throwable.getCause()!=null) {
			throwable = throwable.getCause();
		}
		return throwable;
	}

	public static Exception unwrapException(Exception exception) {
		while (exception.getCause()!=null) {
			Throwable throwable = exception.getCause();
			if (throwable instanceof Exception) {
				exception = (Exception)throwable;
			} else {
				return exception;
			}
		}
		return exception;
	}


}
