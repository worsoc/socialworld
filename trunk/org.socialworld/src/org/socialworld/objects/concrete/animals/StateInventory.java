package org.socialworld.objects.concrete.animals;

import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;

public class StateInventory extends State {

	public static final String METHOD_NAME_SETINVENTORY = "setInventory";
	
	private Inventory inventory;
	
	public StateInventory() {
		super();
	}
	
	private StateInventory( StateInventory original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		// TODO implement copy constructor
		setPropertyName(PropertyName.stateInventory);
	}

	@Override
	public ISavedValues copyForProperty(SimulationCluster cluster) {
		return new StateInventory(this, getPropertyProtection(), cluster);
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case inventory_shirt:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_trousers:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_shoes:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_cap:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_leftHand:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_rightHand:
			return this.inventory.getProperty(cluster, propName, valueName);
		default:
			return ValueProperty.getInvalid();
		}
	}

	protected void setProperty(PropertyName propName, ValueProperty property) {

		Object value;
		value = property.getValue();
		
		switch (propName) {
		case inventory_shirt:
			if (value instanceof SimulationObject) {
				this.inventory.setShirt((SimulationObject) value );
			}
			break;
		case inventory_trousers:
			if (value instanceof SimulationObject) {
				this.inventory.setTrousers((SimulationObject) value );
			}
			break;
		case inventory_shoes:
			if (value instanceof SimulationObject) {
				this.inventory.setShoes((SimulationObject) value );
			}
			break;
		case inventory_cap:
			if (value instanceof SimulationObject) {
				this.inventory.setCap((SimulationObject) value );
			}
			break;
		default:


		}
		
	}
	
	protected void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
