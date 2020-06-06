/*
* Social World
* Copyright (C) 2020  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.calculation;


import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;

public class ValueProperty extends Value {

	private static ValueProperty invalid = new ValueProperty();
	
	private PropertyProtection propertyProtection;
	private boolean isSavedValues = false;
	private boolean isSimProperty = false;
	
//	private SimulationCluster cluster = SimulationCluster.toBeSet;;
	
//	private PropertyUsingAs[] useAsPermissions;
	private PropertyUsingAs usedAs = null;
	
	private ValueProperty() {
		super();
	}

	
	public ValueProperty(Type type, String name, Object value) {

		super(type, name, value);
		
		
		init(value);

		valid = true;
	}

/*	
	public ValueProperty(Type type, PropertyProtection protection, String name, Object value) {

		super(type, name, value);
		
		this.propertyProtection = protection;
		
		init(value);

		valid = true;
	}

	public ValueProperty(Type type, SimulationCluster cluster, PropertyName propertyName, String name, Object value) {

		super(type, name, value);
		
		// TODO PropertyProtection
//		this.cluster = cluster;
		setUseAsPermissions(cluster, propertyName);
		
		init(value);

		valid = true;
	}
	

	public ValueProperty(Type type, PropertyUsingAs[] useAsPermissions, String name, Object value) {

		super(type, name, value);

		// TODO PropertyProtection
//		this.useAsPermissions = useAsPermissions;
		
		init(value);

		valid = true;
	}
	
*/	
	public static ValueProperty getInvalid() {
		return invalid;
	}

	
	private void init(Object value) {
		
		if (value instanceof ISavedValues) {
			isSavedValues = true;
		}
		
		if (value instanceof ISimProperty) {
			isSimProperty = true;
		}

	}
	
	private void setUseAsPermissions(SimulationCluster cluster, PropertyName propertyName) {
		
	}
	
/*	03.06.2020
	public Object getValue() { 
		
		Object result = super.getValue();
		
		if (isSavedValues) {
			ISavedValues savedValues = (ISavedValues) result;
			if (!savedValues.hasPropertyProtection()) {
				savedValues.setPropertyProtection(this);
			}
		}
		
		return result;
		
	}
*/
	
	public ValueProperty getSubProperty(PropertyName propName) {
		
		return getSubProperty(propName, propName.toString());
		
	}

	public ValueProperty getSubProperty(PropertyName propName, String valueName) {
		
		ValueProperty result = invalid;
		
		if (isSavedValues) {
			ISavedValues savedValues = (ISavedValues) super.getValue();
			result = savedValues.getProperty(propName, valueName);
		}

/* 	03.06.2020	
		if (isSimProperty) {
			ISimProperty simProperty = (ISimProperty) super.getValue();
			result = simProperty.getProperty(cluster, propName, valueName);
		}
*/		
		return result;
		
	}
	
	public ValueProperty getSubPropertyFromMethod(String methodName, String valueName) {
		
		ValueProperty result = invalid;
		
		if (isSavedValues) {
			ISavedValues savedValues = (ISavedValues) super.getValue();
			result = savedValues.getPropertyFromMethod(methodName, valueName);
		}

/* 	03.06.2020	
		if (isSimProperty) {
			ISimProperty simProperty = (ISimProperty) super.getValue();
			result = simProperty.getPropertyFromMethod(cluster, methodName, valueName);
		}
*/		
		return result;
		
	}

	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		if (isSavedValues) {
			return ((ISavedValues) super.getValue()).checkHasUseAsPermission(useAsPermission);
		}
		return false;
		
	}

/*	03.06.2020
	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		for (int index = 0; index < useAsPermissions.length; index++) {
			if  (useAsPermissions[index].equals(useAsPermission)) return true;
		}
		
		return false;
		
	}
*/	
	public void setUsing(PropertyUsingAs usedAs) {
		this.usedAs = usedAs;
	}
	
	public PropertyUsingAs getUsing( ) {
		return this.usedAs;
	}
	/*	03.06.2020
	
	private void overridePermissions(SimulationCluster cluster, PropertyUsingAs[] useAsPermissions) {
		
		this.cluster = cluster;
		this.useAsPermissions = useAsPermissions;

	}
	
	
	public void setCluster(SimulationCluster cluster) {
		
		if (this.cluster.equals(SimulationCluster.toBeSet)) {
			this.cluster = cluster;
			
			// TODO set useAsPermissions
			
		}
		
	}
	
	public SimulationCluster getCluster() {
		return this.cluster;
	}
	
	*/
}
