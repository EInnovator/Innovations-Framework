package org.einnovator.util.event;

import java.util.EventListener;
import java.util.EventObject;

public interface EventAware extends EventListener {
	<T extends EventListener> void fireEvent(EventObject ev, Class<T> ty);
}
