package org.einnovator.util.event;

public class SelectionEvent extends java.util.EventObject {
	private static final long serialVersionUID = 1L;

	protected Object data;
	
	public SelectionEvent(Object source) {
		super(source);
	}
	
	public SelectionEvent(Object source, Object data) {
		super(source);
		this.data = data;
	}
	
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toString() {
		return super.toString() + (data!=null ? " " + data : "");
	}	
}
