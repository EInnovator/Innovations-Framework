package org.einnovator.util.types;

import java.util.Collection;
import java.util.ArrayList;

import org.einnovator.util.types.TypedList;




public class TypedArrayList<T> extends ArrayList<T> implements TypedList<T> {
	private static final long serialVersionUID = 1L;

	public Class<T> componentType;

	static {
		//MetaClass.add(new MetaClass(XArrayList.class).setComponentType(ety));
	}
	
	public TypedArrayList() {
	}	
	
	public TypedArrayList(Class<T> componentType) {
		this.componentType = componentType;
	}
	
	public TypedArrayList(Class<T> componentType, int n) {
		super(n);
		this.componentType = componentType;
	}	
	
	public TypedArrayList(Class<T> componentType, Collection<? extends T> l) {
		super(l);
		this.componentType = componentType;
	}

	@Override
	public Class<T> getComponentType() {
		return componentType;
	}	

}
