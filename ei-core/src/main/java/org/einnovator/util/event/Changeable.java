package org.einnovator.util.event;

import org.einnovator.util.event.ChangeEvent;
import org.einnovator.util.event.ChangeListener;


public interface Changeable {
	void fireChangeEvent();
	
	void fireChangeEvent(ChangeEvent ev);
	
	void addChangeListener(ChangeListener li);
	
	void removeChangeListener(ChangeListener li);
}
