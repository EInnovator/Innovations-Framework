package org.einnovator.util.event;

import java.util.EventListener;

import org.einnovator.util.event.ChangeEvent;



public interface ChangeListener extends EventListener {
	public void stateChanged(ChangeEvent ev);
}
