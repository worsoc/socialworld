package org.socialworld.attributes;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;

public class PropertyProtection {

	private SimulationCluster cluster;
	private PropertyUsingAs[] useAsPermissions;
	private PropertyUsingAs usedAs = null;
 
	
	public PropertyProtection(SimulationCluster cluster) {
		
		if (cluster == SimulationCluster.total) {
			this.cluster = SimulationCluster.unknown;
		}
		else {
			this.cluster = cluster;
		}
	}
	
	public PropertyProtection(PropertyProtection original, SimulationCluster cluster, ISavedValues savedValue) {
		
		if (!savedValue.hasPropertyProtection()) {
			this.cluster = cluster;
			// TODO init property protection
			savedValue.setPropertyProtection(this);
		}
		else {
			this.cluster = SimulationCluster.unknown;
		}
	}
	
	public PropertyProtection(ISavedValues savedValue) {
		
		if (!savedValue.hasPropertyProtection()) {
			this.cluster = SimulationCluster.total;
			// TODO init property protection
			savedValue.setPropertyProtection(this);
		}
		else {
			this.cluster = SimulationCluster.unknown;
		}
	}

	public PropertyProtection(PropertyProtection original) {

		this.cluster = original.cluster;
		
		int size = original.useAsPermissions.length;
		this.useAsPermissions = new PropertyUsingAs[size];
		for (int i = 0; i < size; i++) {
			this.useAsPermissions[i] = original.useAsPermissions[i];
		}
		this.usedAs = null;
		
	}
	
	public boolean hasUnknownCluster() {
		return (this.cluster == SimulationCluster.unknown);
	}
	
	public SimulationCluster getCluster() {
		return this.cluster;
	}

	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		for (int index = 0; index < useAsPermissions.length; index++) {
			if  (useAsPermissions[index].equals(useAsPermission)) return true;
		}
		
		return false;
		
	}

}
