package org.einnovator.util.event;

import java.util.EventListener;

import org.einnovator.util.event.SelectionEvent;



public interface SelectionListener extends EventListener {
	public void selectionChanged(SelectionEvent ev);
}
