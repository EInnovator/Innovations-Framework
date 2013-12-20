package org.einnovator.util.event;

import java.util.EventListener;
import java.util.EventObject;

import org.einnovator.util.event.EventAware;


public interface EventSource extends EventAware {
	<T extends EventListener> void addListener(Class<T> ty, T evl);

	<T extends EventListener> void removeListener(Class<T> ty, T evl);

	int getListenerCount();
	
	int getListenerCount(Class<?> ty);

	Object[] getListenerList();
	
	<T  extends EventListener> T[] getListeners(Class<T> ty);

	@Override
	<T extends EventListener> void fireEvent(EventObject ev, Class<T> ty);
}
