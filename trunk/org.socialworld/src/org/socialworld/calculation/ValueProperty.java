package org.socialworld.calculation;

import java.util.List;

import org.socialworld.attributes.SimPropertyName;

public class ValueProperty extends Value {

	private static ValueProperty inValid = new ValueProperty();
	
	private List<PropertyUsingAs> useAsPermissions;
	private PropertyUsingAs usedAs = null;
	
	private ValueProperty() {
		super();
	}

	public ValueProperty(Type type, SimPropertyName simPropertyName, String name, Object value) {

		super(type, name, value);
		
		setUseAsPermissions(simPropertyName);
		
		valid = true;
	}

	public ValueProperty(Type type, List<PropertyUsingAs> useAsPermissions, String name, Object value) {

		super(type, name, value);

		this.useAsPermissions = useAsPermissions;
		
		valid = true;
	}
	
	public static ValueProperty getInvalid() {
		return inValid;
	}

	private void setUseAsPermissions(SimPropertyName simPropertyName) {
		
	}
	
	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		for (int index = 0; index < useAsPermissions.size(); index++) {
			if  (useAsPermissions.get(index).equals(useAsPermission)) return true;
		}
		
		return false;
		
	}
	
	public void setUsing(PropertyUsingAs usedAs) {
		this.usedAs = usedAs;
	}
	
	public PropertyUsingAs getUsing( ) {
		return this.usedAs;
	}
	
}
