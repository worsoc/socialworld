package org.socialworld.objects.concrete.animals;

import java.util.List;

import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringPair;

public class StateInventory extends State {

	
	private Inventory inventory;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringPair[] propertiesMetaInfos = new StringPair[]{
		new StringPair(Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_shirt.name()),
		new StringPair(Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_trousers.name()),
		new StringPair(Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_shoes.name()),
		new StringPair(Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_cap.name()),
		new StringPair(Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_leftHand.name()),
		new StringPair(Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_rightHand.name())
		} ;
	private static StringPair[] propMethodsMetaInfos = new StringPair[] {} ;
	
	public static List<StringPair> getPropertiesMetaInfos() {
		List<StringPair> listOfPropertyMetaInfo = State.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	public static List<StringPair> getPropMethodsMetaInfos() {
		List<StringPair> listOfPropMethodMetaInfo = State.getPropMethodsMetaInfos();
		for (int indexAdd = 0; indexAdd < propMethodsMetaInfos.length; indexAdd++) {
			listOfPropMethodMetaInfo.add(propMethodsMetaInfos[indexAdd]);
		}
		return listOfPropMethodMetaInfo;
	}
	
	private static KnowledgeFact_Criterion[] resultingKFCs = new KnowledgeFact_Criterion[] {
			KnowledgeFact_Criterion.colour,
			KnowledgeFact_Criterion.material
		};

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = State.getResultingKFCs();
		for (int indexAdd = 0; indexAdd < resultingKFCs.length; indexAdd++) {
		listOfResultingKFCs.add(resultingKFCs[indexAdd]);
		}
		return listOfResultingKFCs;
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateInventory() {
		super();
	}
	
	private StateInventory( StateInventory original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		setPropertyName(PropertyName.stateInventory);
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateInventory);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
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
		case stateInventory_inventory:
			if (value instanceof Inventory) {
				this.inventory = (Inventory) value;
			}
			break;
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

	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateInventory methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	

}
