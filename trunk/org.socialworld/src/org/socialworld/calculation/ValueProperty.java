package org.socialworld.calculation;


import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;

public class ValueProperty extends Value {

	private static ValueProperty invalid = new ValueProperty();
	
	private boolean isSavedValues = false;
	private boolean isSimProperty = false;
	
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
		
		init(value);

		valid = true;
	}
	

	public ValueProperty(Type type, PropertyUsingAs[] useAsPermissions, String name, Object value) {

		super(type, name, value);

		this.useAsPermissions = useAsPermissions;
		
		init(value);

		valid = true;
	}
	
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
	
	public Object getValue() { 
		
		Object result = super.getValue();
		
		if (isSavedValues) {
			ISavedValues savedValues = (ISavedValues) result;
			if (!savedValues.checkHasPropertyProtection()) {
				savedValues.setPropertyProtection(this);
			}
		}
		
		return result;
		
	}

	public ValueProperty getSubProperty(PropertyName propName, String valueName) {
		
		ValueProperty result = invalid;
		
		if (isSavedValues) {
			ISavedValues savedValues = (ISavedValues) super.getValue();
			result = savedValues.getProperty(propName, valueName);
			result.overridePermissions(this.cluster, this.useAsPermissions);
		}

		if (isSimProperty) {
			ISimProperty simProperty = (ISimProperty) super.getValue();
			result = simProperty.getProperty(cluster, propName, valueName);
		}
		
		return result;
		
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
	
}
