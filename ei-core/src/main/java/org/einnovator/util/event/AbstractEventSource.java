package org.einnovator.util.event;

import java.util.EventListener;
import java.util.EventObject;
import javax.swing.event.EventListenerList;

import org.einnovator.util.event.ChangeEvent;
import org.einnovator.util.event.ChangeListener;
import org.einnovator.util.event.EventAware;
import org.einnovator.util.event.EventSource;
import org.einnovator.util.event.SelectionEvent;
import org.einnovator.util.event.SelectionListener;



public abstract class AbstractEventSource implements EventSource, EventAware {
	private EventListenerList listeners;

	public AbstractEventSource() {
	}

	public <T extends EventListener> void addListener(Class<T> type, T listener) {
	 	if (listeners==null) {
	 		listeners = new EventListenerList();
	 	}
	 	listeners.add(type, listener);
		//System.out.printf("%s.addListener: %s %s%n", this, type, listener);		
	}

	public <T extends EventListener> void removeListener(Class<T> type, T listener) {
		if (listeners!=null) {
			listeners.remove(type, listener);
		}
	}
	 
	public int getListenerCount() {
		return listeners!=null ? listeners.getListenerCount() : 0;
	}
	
	public int getListenerCount(Class<?> type) {
		return listeners!=null ? listeners.getListenerCount(type) : 0;
	}

	public Object[] getListenerList() {
		return listeners!=null ? listeners.getListenerList() : null;
	}

	public <T  extends EventListener> T[] getListeners(Class<T> type) {
		return listeners!=null ? listeners.getListeners(type) : null;
	}
		 
	public <T extends EventListener> void fireEvent(EventObject ev, Class<T> type) {
		if (listeners==null) {
			return;
		}
/*		Object[] l = getListeners(type);		
		if (l==null) return;
		for (int i = 0, n = l.length; i < n; i++) {
	    	EventListener listener = (EventListener) l[i];
	    	deliverEvent(ev, listener);
	    }*/
		Object[] l = listeners.getListenerList();
		if (l==null) {
			return;
		}
		//System.out.printf("!!%s.fireEvent: %s%n", this, ev);				
		for (int i = l.length-2; i>=0; i-=2) {
	        if (l[i]==type) {
	    		//System.out.printf("%s.fireEvent: %s %s%n", this, ev, l[i+1]);		
	        	deliverEvent(ev, ((EventListener)l[i+1]));
	        }
		}
		
	}

	protected void deliverEvent(EventObject ev, EventListener listener) {
		if (ev instanceof ChangeEvent && listener instanceof ChangeListener) {
			((ChangeListener)listener).stateChanged((ChangeEvent)ev);
		} else if (ev instanceof SelectionEvent && listener instanceof SelectionListener) {
			((SelectionListener)listener).selectionChanged((SelectionEvent)ev);
		}
		
	}
	
	//public String toString() { return lel!=null ? lel.toString() : ""; }		  		 
}
