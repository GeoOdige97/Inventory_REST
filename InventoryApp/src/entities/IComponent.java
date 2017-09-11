package entities;

import java.lang.reflect.InvocationTargetException;

public interface IComponent<T> {
	
	public int getComponentID() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public Class getClassType() ;
	public T getComponent();
	public void setComponent(T component);
	public void setComponentID(int componentId) throws NoSuchMethodException, SecurityException,  IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
