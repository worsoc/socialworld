package org.socialworld.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.SimPropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public abstract class State implements ISimProperty {

	private SimPropertyName propertyName = SimPropertyName.unknown;
	
	protected abstract State copyForProperty(Type propertyType);

	protected final Method getMethod(String method) {
		Method[] allMethods = this.getClass().getDeclaredMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
			if (mname.equals(method)) return m;
		}
		return null;
	}
	
	/////////////////////////////////////////////
	
	public final void setPropertyName(SimPropertyName prop) {
		if (this.propertyName == SimPropertyName.unknown) {
			this.propertyName = prop;
		}
	}
	
	public final SimPropertyName getPropertyName() {
		return this.propertyName;
	}

	public final  Value getAsValue() {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new Value(propertyType, this.propertyName.toString(), copyForProperty(propertyType));
	}
	
	public final Value getAsValue(String name) {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new Value(propertyType, name, copyForProperty(propertyType));
	}

	////////////////////////////////////////////
	
	public final Value getProperty(SimPropertyName prop) {
		String name;
		name = prop.toString();
		return getProperty(prop, name);
	}
	
	public abstract Value getProperty(SimPropertyName prop, String name);
	
	public final Value getValue(String methodName, String name) {
		
		Object got = null;
		Value result = new Value();
		Method method;
		method = getMethod(methodName);
		
		if (method != null) {
			try {
				method.setAccessible(true);
				got = method.invoke(this);

			// Handle any exceptions thrown by method to be invoked.
			}
			catch (InvocationTargetException ite) {
			    Throwable cause = ite.getCause();
			    System.out.println( cause.getMessage());
			}
			catch (IllegalAccessException iae) {
				iae.printStackTrace();
			} 
			catch (IllegalArgumentException iarge) {
				iarge.printStackTrace();
			}
			
			if (got != null) {
				
				result = new Value(Type.object, name, got );
				
			}
		}

		return result;
		
	}



}
