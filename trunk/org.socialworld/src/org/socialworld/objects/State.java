package org.socialworld.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueProperty;

public abstract class State implements ISimProperty {

	private PropertyName propertyName = PropertyName.unknown;
	
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
	
	public final void setPropertyName(PropertyName prop) {
		if (this.propertyName == PropertyName.unknown) {
			this.propertyName = prop;
		}
	}
	
	public final PropertyName getPropertyName() {
		return this.propertyName;
	}

	public final  ValueProperty getAsValue(SimulationCluster cluster) {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new ValueProperty(propertyType, cluster, this.propertyName, this.propertyName.toString(), copyForProperty(propertyType));
	}
	
	public final ValueProperty getAsValue(SimulationCluster cluster, String name) {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new ValueProperty(propertyType, cluster, this.propertyName, name, copyForProperty(propertyType));
	}

	////////////////////////////////////////////
	
	public final ValueProperty getProperty(SimulationCluster cluster, PropertyName prop) {
		String name;
		name = prop.toString();
		return getProperty(cluster, prop, name);
	}
	
	//public abstract ValueProperty getProperty(PropertyName prop, String name);
	
	public final ValueProperty getProperty(SimulationCluster cluster, String methodName, String name) {
		
		Object got = null;
		ValueProperty result = ValueProperty.getInvalid();
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
				
				if (got instanceof ValueProperty) {  
					
					result = ( ValueProperty ) got ;
					result.setName(name);
					result.setCluster(cluster);
				}
				
			}
		}

		return result;
		
	}

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
