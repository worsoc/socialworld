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

public class PropertyProtection {

	private SimulationCluster cluster;
	private PropertyUsingAs[] useAsPermissions;
	private PropertyUsingAs usedAs = null;
 
	
	public PropertyProtection(SimulationCluster cluster) {
		
		if (cluster == SimulationCluster.total) {
			// TODO what to do for SimulationCluster.total
			this.cluster = SimulationCluster.unknown;
		}
		else {
			this.cluster = cluster;
			this.useAsPermissions = cluster.getPossibleUsingAs();
		}
	}
	
	public PropertyProtection(PropertyProtection original, SimulationCluster cluster, ISavedValues savedValue) {
		
		if (!savedValue.hasPropertyProtection()) {
			this.cluster = cluster;
			this.useAsPermissions = original.getIntersection(cluster.getPossibleUsingAs());
			savedValue.setPropertyProtection(this);
		}
		else {
			this.cluster = SimulationCluster.unknown;
			this.useAsPermissions = new PropertyUsingAs[0];
		}
	}
	
	public PropertyProtection(ISavedValues savedValue) {
		
		if (!savedValue.hasPropertyProtection()) {
			this.cluster = SimulationCluster.total;
			this.useAsPermissions = this.cluster.getPossibleUsingAs();
			savedValue.setPropertyProtection(this);
		}
		else {
			this.cluster = SimulationCluster.unknown;
			this.useAsPermissions = new PropertyUsingAs[0];
		}
	}

	public PropertyProtection(PropertyProtection original) {

		this.cluster = original.cluster;
		
		int size = original.useAsPermissions.length;
		this.useAsPermissions = new PropertyUsingAs[size];
		for (int index = 0; index < size; index++) {
			this.useAsPermissions[index] = original.useAsPermissions[index];
		}
		this.usedAs = null;
		
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
		
		return false;
		
	}
	
	public boolean checkHasGetPermission(SimulationCluster cluster) {
		
		// TODO check whether there is needed an extra method for checking get permission
		
		// there is a get permission if this.useAsPermissions and cluster's useAsPermissions are not intersection-free
		
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
		
	}

}
