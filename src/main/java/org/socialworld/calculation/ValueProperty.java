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


import org.socialworld.attributes.ISavedValue;
import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.NoSavedValue;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.objects.SimulationObject;


public class ValueProperty extends Value {

	
	private static ValueProperty invalid;
	
	private PropertyProtection protection;
	private boolean isSavedValues = false;
	private boolean isSimProperty = false;
	
	//private boolean isCopy = false;
	
	private PropertyUsingAs usedAs = null;
	
	public static ValueProperty getInvalid() {
		if (invalid == null) {
			invalid = new ValueProperty();
		}
		return invalid;
	}

	private ValueProperty() {
		super();
	}

	
	public ValueProperty(Type type, String name, Object value) {

		super(type, name, value);
		
		protection = PropertyProtection.getTotalPermission();
		
		if (value instanceof ISavedValue) {
			isSavedValues = true;
			protection =  PropertyProtection.getProtection((ISavedValue) value);
		}
		protection.addProtectedValueProperty(this);
		
		if (value instanceof ISimProperty) {
			isSimProperty = true;
		}

		valid = true;
	}

	
	public ValueProperty(Type type, PropertyProtection protection, String name, Object value) {

		super(type, name, value);
		
		if (value instanceof ISavedValue) {
			isSavedValues = true;
			protection =  PropertyProtection.getProtection(protection, (ISavedValue)value);
		}
		else {
			this.protection = protection;
		}
		protection.addProtectedValueProperty(this);
		
		if (value instanceof ISimProperty) {
			isSimProperty = true;
		}

		valid = true;
	}

	public ValueProperty(Type type, SimulationCluster cluster, PropertyName propertyName, String name, Object value) {

		super(type, name, value);
		
		if (value instanceof ISavedValue) {
			isSavedValues = true;
			protection =  PropertyProtection.getProtection(cluster, (ISavedValue)value);
		}
		else {
			protection =  PropertyProtection.getProtection(cluster);
		}
		protection.addProtectedValueProperty(this);
		
		if (value instanceof ISimProperty) {
			isSimProperty = true;
		}
		
		valid = true;
	}
	

	public ValueProperty(Type type, PropertyUsingAs[] useAsPermissions, String name, Object value) {

		super(type, name, value);

		if (value instanceof ISavedValue) {
			isSavedValues = true;
			protection =  PropertyProtection.getProtection(useAsPermissions, (ISavedValue)value);
		}
		else {
			protection =  PropertyProtection.getProtection(useAsPermissions);
		}
		protection.addProtectedValueProperty(this);

		if (value instanceof ISimProperty) {
			isSimProperty = true;
		}

		valid = true;
	}
	
	
	
	
	public Object getObject(Type type) { 
		
		Object result;
		
		if (isProtected()) {
			if (isSavedValues) {
				result = NoSavedValue.getValueNothing();
			}
			else {
				result = NoObject.getNoObject(NoObjectReason.valueIsProtectedAndNotPermitted);
			}
		}
		else {
			result = super.getObject(type);
		}
		
		return result;
		
	}

	public Object getObject(SimulationCluster cluster, Type type) {
		
		Object result; 
		if (isProtected()) {
			if (checkHasPermission(cluster)) {
				result = super.getObject(type);
			}
			else {
				if (isSavedValues) {
					result = NoSavedValue.getValueNothing();
				}
				else {
					result = NoObject.getNoObject(NoObjectReason.valueIsProtectedAndNotPermitted);
				}
			}
		}
		else {
			result = super.getObject(type);
		}
		
		
		return result;

	}
	
	public int requestObject(IObjectReceiver receiver, int requestID, Type type) {

		if (this.getType().equals(type)) {
		
			IObjectSender object;
			
			if (isProtected()) {
				return IObjectSender.OBJECT_NOT_SENDED_BECAUSE_PROTECTED;
			}
			else {
				object = super.getObjectSender();
				return object.sendYourselfTo(receiver, requestID);
			}
			
		}
		else {
			return IObjectSender.OBJECT_NOT_SENDED_BECAUSE_WRONG_TYPE;
		}
		
	}

	public int requestObject(SimulationCluster cluster, IObjectReceiver receiver, int requestID, Type type) {

		if (this.getType().equals(type)) {
		
			IObjectSender object;
			
			if (isProtected()) {
				if (checkHasPermission(cluster)) {
					object = super.getObjectSender();
					return object.sendYourselfTo(cluster, receiver, requestID);
				}
				else {
					return IObjectSender.OBJECT_NOT_SENDED_BECAUSE_NO_PERMISSION;
				}
			}
			else {
				object = super.getObjectSender();
				return object.sendYourselfTo(cluster, receiver, requestID);
			}

		}
		else {
			return IObjectSender.OBJECT_NOT_SENDED_BECAUSE_WRONG_TYPE;
		}
		
		
	}

	public boolean isProtected() {
		return true;
	}
	
	public boolean checkHasPermission(SimulationCluster cluster)  {
		return true;
	}
	

	
	public ValueProperty getSubPropertyFromMethod(String methodName, String valueName) {
		
		ValueProperty result = getInvalid();
		
		if (isSavedValues) {
			ISavedValue savedValue = (ISavedValue) super.getOriginal();
			result = savedValue.getPropertyFromMethod(methodName, valueName);
		}

/* 	03.06.2020	
		if (isSimProperty) {
			ISimProperty simProperty = (ISimProperty) super.getOriginalValue();
			result = simProperty.getPropertyFromMethod(cluster, methodName, valueName);
		}
*/		
		return result;
		
	}

	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		if (isSavedValues) {
			return ((ISavedValue) super.getObject(getType())).checkHasUseAsPermission(useAsPermission);
		}
		if (super.getObject(getType()) instanceof SimulationObject) {
			// TODO checkHasUseAsPermission for SimulationObject(s)
			return true;
		}
		return true;
		// TODO return false
		//return false;
		
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
