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

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;

public interface ISavedValues {

	// protection
	public abstract boolean hasPropertyProtection();
	public abstract PropertyProtection getPropertyProtection();
	public abstract void setPropertyProtection(PropertyProtection propertyProtection);
	public abstract boolean checkHasGetPermission(SimulationCluster cluster);
	public abstract boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission);

	// getting a copy as ValueProperty for a simulation cluster (intersection from argument cluster and the "parent" protection)
	public abstract ValueProperty getAsValue(SimulationCluster cluster);
	public abstract ValueProperty getAsValue(SimulationCluster cluster, String valueName);
	public abstract ISavedValues copyForProperty(SimulationCluster cluster); 
	
	// getting a property with inherited protection
	public abstract ValueProperty getProperty (PropertyName propName, String valueName);
	public abstract ValueProperty getPropertyFromMethod (String methodName, String valueName);
	
	// getting a property with protection intersection from argument cluster and the "parent" protection
	public abstract ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName);
	public abstract ValueProperty getPropertyFromMethod(SimulationCluster cluster, String methodName, String valueName);

	
}
