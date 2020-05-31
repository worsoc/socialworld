package org.socialworld.calculation;


import org.socialworld.attributes.PropertyName;

public class ValueProperty extends Value {

	private static ValueProperty inValid = new ValueProperty();
	
	private SimulationCluster cluster;
	
	private PropertyUsingAs[] useAsPermissions;
	private PropertyUsingAs usedAs = null;
	
	private ValueProperty() {
		super();
	}

	public ValueProperty(Type type, SimulationCluster cluster, PropertyName propertyName, String name, Object value) {

		super(type, name, value);
		
		this.cluster = cluster;
		setUseAsPermissions(cluster, propertyName);
		
		valid = true;
	}
	

	public ValueProperty(Type type, PropertyUsingAs[] useAsPermissions, String name, Object value) {

		super(type, name, value);

		this.useAsPermissions = useAsPermissions;
		
		valid = true;
	}
	
	public static ValueProperty getInvalid() {
		return inValid;
	}

	private void setUseAsPermissions(SimulationCluster cluster, PropertyName propertyName) {
		
	}
	
	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		for (int index = 0; index < useAsPermissions.length; index++) {
			if  (useAsPermissions[index].equals(useAsPermission)) return true;
		}
		
		return false;
		
	}
	
	public void setUsing(PropertyUsingAs usedAs) {
		this.usedAs = usedAs;
	}
	
	public PropertyUsingAs getUsing( ) {
		return this.usedAs;
	}
	
	public void setCluster(SimulationCluster cluster) {
		
		if (this.cluster.equals(SimulationCluster.toBeSet)) {
			this.cluster = cluster;
			
			// TODO set useAsPermissions
			
		}
		
	}
	
}
