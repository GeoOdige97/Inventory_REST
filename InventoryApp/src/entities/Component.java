package entities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Component<T>{
	
	private T component; 
	
	public Component(T component) {
		this.component = component;
	}
	
	public int getComponentID() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method m = this.component.getClass().getMethod("getId");
		int id = (Integer) m.invoke(this.component);
		return id;
	}

	public Class getClassType() {
		return component.getClass();
	}
	

	public T getComponent(){
		
		return component;
	}
	
	public void setComponent(T component){
		
		this.component = component;
	}
	
	public void setComponentID(int componentId) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{		Method m = this.component.getClass().getMethod("setId", int.class);
		m.invoke(this.component, componentId);
		
	}


}