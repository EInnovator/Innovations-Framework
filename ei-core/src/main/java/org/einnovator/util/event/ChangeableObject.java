package org.einnovator.util.event;

import java.util.EventListener;
import java.util.EventObject;

import org.einnovator.util.event.AbstractEventSource;
import org.einnovator.util.event.ChangeEvent;
import org.einnovator.util.event.ChangeListener;



public class ChangeableObject extends AbstractEventSource {
	public ChangeableObject() {		
	}

	public void fireChangeEvent() {
		fireChangeEvent(this, null);
	}

	public void fireChangeEvent(Object data) {
		fireChangeEvent(this, data);
	}

	public void fireChangeEvent(Object src, Object data) {
		if (getListenerCount(ChangeListener.class)>0) {
			fireEvent(new ChangeEvent(src, data), ChangeListener.class);			
		}
	}

	public void fireChangeEvent(ChangeEvent ev) {
		fireEvent(ev, ChangeListener.class);
	}
	
	public void addChangeListener(ChangeListener li) {
		addListener(ChangeListener.class, li);	
	}

	public void removeChangeListener(ChangeListener li) {
		removeListener(ChangeListener.class, li);	
	}

	protected void deliverEvent(EventObject ev, EventListener evl) {
		if (ev instanceof ChangeEvent && evl instanceof ChangeListener) {
			((ChangeListener)evl).stateChanged((ChangeEvent)ev);
		}
	}

}
