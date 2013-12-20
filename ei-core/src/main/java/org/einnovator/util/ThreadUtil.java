package org.einnovator.util;

public class ThreadUtil {
	
	static public void waitForever(Object obj) {
		while (true) {
			synchronized (obj) {
				try {
				 obj.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	static public void wait(Object obj, long time) {
		synchronized (obj) {
			try {
				obj.wait(time);
			} catch (InterruptedException e) {
			}
		}
	}
	

}
