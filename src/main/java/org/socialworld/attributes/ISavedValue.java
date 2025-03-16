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
package org.socialworld.attributes;

import java.util.List;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;

public interface ISavedValue {

	// protection
	public abstract boolean isProtected();
	public abstract boolean hasPropertyProtection();
	public abstract PropertyProtection getPropertyProtection();
	public abstract void setPropertyProtection(PropertyProtection propertyProtection);
	public abstract boolean checkHasGetPermission(IAccessToken token);
	public abstract boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission);
	public abstract List<PropertyUsingAs> getReducedUseAsPermissions(List<PropertyUsingAs> useAsPermissions);
	public abstract boolean checkUseAsPermissionsReductionNecessary(List<PropertyUsingAs> useAsPermissions);

	// getting a copy as ValueProperty for an access token (intersection from argument token and the "parent" protection)
	public abstract ValueProperty getAsValue(IAccessToken token);
	public abstract ValueProperty getAsValue(IAccessToken token, String valueName);
	public abstract ISavedValue copyForProperty(IAccessToken token); 
	
	// getting a property with inherited protection
	public abstract ValueProperty getProperty (PropertyName propName, String valueName);
	public abstract ValueProperty getPropertyFromMethod (String methodName, String valueName);
	
	// getting a property with protection intersection from argument token and the "parent" protection
	public abstract ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName);
	public abstract ValueProperty getPropertyFromMethod(IAccessToken token, String methodName, String valueName);

	public abstract boolean checkIsObjectNothing();

}
