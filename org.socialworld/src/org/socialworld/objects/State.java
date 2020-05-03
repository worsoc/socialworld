package org.socialworld.objects;

import java.lang.reflect.Method;

import org.socialworld.attributes.SimPropertyName;
import org.socialworld.calculation.Value;

public abstract class State {

	Method getMethod(String method) {
		Method[] allMethods = this.getClass().getDeclaredMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
			if (mname.equals(method)) return m;
		}
		return null;
	}
	
	
	public final Value getProperty(SimPropertyName prop) {
		String name;
		name = prop.toString();
		return getProperty(prop, name);
	}
	
	public Value getProperty(SimPropertyName prop, String name) {
			return new Value();
	}

}
