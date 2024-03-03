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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;

public class PropertyProtection {

	private SimulationCluster cluster;
	private PropertyUsingAs[] useAsPermissions;
	private PropertyUsingAs usedAs = null;
 
	boolean withTotalPermissions = false;
	
	List<ValueProperty> protectedValues;
	boolean allowOneAdding = false;
	
	private static PropertyProtection totalPermission;

	public static PropertyProtection getTotalPermission() {
		if (totalPermission == null) {
			totalPermission = new PropertyProtection();
		}
		return totalPermission;
	}

	private PropertyProtection() {
		cluster = SimulationCluster.total;
		withTotalPermissions = true;
		this.allowOneAdding = false;
		this.useAsPermissions = new PropertyUsingAs[0];
		this.protectedValues = new ArrayList<ValueProperty>();
	}
	
	
	private PropertyProtection(SimulationCluster cluster, PropertyUsingAs[] useAsPermissions) {
		this.cluster = cluster;
		this.useAsPermissions = useAsPermissions;
		this.usedAs = null;
		this.allowOneAdding = true;
		this.protectedValues = new ArrayList<ValueProperty>();
	}
	
	public static PropertyProtection getProtection(SimulationCluster cluster) {
		
		return new PropertyProtection(cluster, cluster.getPossibleUsingAs());
		
	}

	public static PropertyProtection getProtection(PropertyUsingAs[] useAsPermissions) {
		
		return new PropertyProtection(SimulationCluster.total, useAsPermissions);
		
	}

	public static PropertyProtection getProtection(PropertyProtection original) {
		
		PropertyUsingAs[] useAsPermissionsOriginal = original.getUseAsPermissions();
		return getProtection(useAsPermissionsOriginal);
		
	}

	public static PropertyProtection getProtection(ISavedValue value) {
		
		if (!value.isProtected()) {
			return getTotalPermission();
		}
		else {
			return new PropertyProtection(SimulationCluster.total, SimulationCluster.total.getPossibleUsingAs());
		}
		
	}

	public static PropertyProtection getProtection(SimulationCluster cluster, ISavedValue value) {
		
		if (!value.isProtected() && cluster.isWithoutRestrictions()) {
			return getTotalPermission();
		}
		else {
			PropertyUsingAs[] useAsPermissionsCluster = cluster.getPossibleUsingAs();
			PropertyUsingAs[] reducedUseAsPermissions = value.getReducedUseAsPermissions(useAsPermissionsCluster);
			return new PropertyProtection(cluster, reducedUseAsPermissions);
		}
		
	}
	
	public static PropertyProtection getProtection(PropertyUsingAs[] useAsPermissions, ISavedValue value) {
				
		PropertyUsingAs[] reducedUseAsPermissions = value.getReducedUseAsPermissions(useAsPermissions);
		return new PropertyProtection(SimulationCluster.total, reducedUseAsPermissions);
		
	}

	public static PropertyProtection getProtection(PropertyProtection original, ISavedValue value) {
		
		
		PropertyUsingAs[] useAsPermissionsOriginal = original.getUseAsPermissions();
		boolean noMatch = value.checkUseAsPermissionsReductionNecessary(useAsPermissionsOriginal);
		if (noMatch) {
			return getProtection(useAsPermissionsOriginal, value);
		}
		else {
			original.allowOneAdding = true;
			return original;
		}
		
	}
	
	public static PropertyProtection getProtection(PropertyProtection original, SimulationCluster cluster, ISavedValue value) {
		
		PropertyUsingAs[] useAsPermissionsCluster = cluster.getPossibleUsingAs();
		PropertyUsingAs[] useAsPermissionsIntersect = original.getIntersection(useAsPermissionsCluster);
		PropertyUsingAs[] reducedUseAsPermissions = value.getReducedUseAsPermissions(useAsPermissionsIntersect);
		return new PropertyProtection(cluster, reducedUseAsPermissions);
		
	}

	public void addProtectedValueProperty(ValueProperty vp) {
		if (allowOneAdding) {
			protectedValues.add(vp);
		}
		allowOneAdding = false;
	}
	
	private PropertyUsingAs[] getIntersection(PropertyUsingAs[] toIntersectWith) {
		
		PropertyUsingAs[] result;
		List<PropertyUsingAs> intersection = new ArrayList<PropertyUsingAs>();
		
		for (int indexA = 0; indexA < this.useAsPermissions.length; indexA++) {
			
			for (int indexB = 0; indexB < toIntersectWith.length; indexB++) {
			
				if (this.useAsPermissions[indexA].equals(toIntersectWith[indexB])) {
					
					intersection.add(toIntersectWith[indexB]);
					
				}
				
			}
			
		}
		
		result = intersection.toArray(new PropertyUsingAs[intersection.size()]);
		
		return result;
		
	}
	
	private PropertyUsingAs[] getUseAsPermissions() {
		
		PropertyUsingAs[] copy = new PropertyUsingAs[this.useAsPermissions.length];
		
		for (int index = 0; index < this.useAsPermissions.length; index++) {
			copy[index] = useAsPermissions[index];
		}
		
		return copy;
		
	}

	public boolean hasRestrictions() {
		return (!withTotalPermissions);
	}

	public boolean hasUnknownCluster() {
		return (this.cluster == SimulationCluster.unknown);
	}
	
	public SimulationCluster getCluster() {
		return this.cluster;
	}

	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		for (int index = 0; index < this.useAsPermissions.length; index++) {
			if  (this.useAsPermissions[index].equals(useAsPermission)) return true;
		}
		// TEMP_SOLUTION
		return true;
// --> 		return false;
		
	}
	
	public boolean checkHasGetPermission(SimulationCluster cluster) {
		
		
		// TODO check whether there is needed an extra method for checking get permission
		
		// there is a get permission if this.useAsPermissions and cluster's useAsPermissions are not intersection-free
		
		
		// TEMP_SOLUTION
		return true;

/*	--> wieder rein	
		PropertyUsingAs[] tmp;
		tmp = cluster.getPossibleUsingAs();
		
		for (int indexA = 0; indexA < this.useAsPermissions.length; indexA++) {
			
			for (int indexB = 0; indexB < tmp.length; indexB++) {
			
				if (this.useAsPermissions[indexA].equals(tmp[indexB])) {
					
					return true;
					
				}
				
			}
			
		}

		return false;
*/		
	}

}
