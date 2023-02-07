package org.socialworld.objects.concrete.animals;

import java.util.List;

import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.ReturnCode;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.clothes.Cap;
import org.socialworld.objects.concrete.clothes.Shirt;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.objects.concrete.clothes.Trousers;
import org.socialworld.tools.StringTupel;

public class StateInventory extends State {

	
	private Inventory inventory = Inventory.getObjectNothing();
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		new StringTupel("Inventory", PropertyName.stateInventory_inventory.name()),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_shirt.name(), PropertyName.inventory_shirt.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_trousers.name(), PropertyName.inventory_trousers.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_leftShoe.name(), PropertyName.inventory_leftShoe.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_rightShoe.name(), PropertyName.inventory_rightShoe.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_leftSock.name(), PropertyName.inventory_leftSock.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_rightSock.name(), PropertyName.inventory_rightSock.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_cap.name(), PropertyName.inventory_cap.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_leftHand.name(), PropertyName.inventory_leftHand.toString()}),
		new StringTupel(new String[] {Type.simulationObject.getIndexWithSWTPraefix(), PropertyName.inventory_rightHand.name(), PropertyName.inventory_rightHand.toString()})
		};
	
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {} ;
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = State.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	public static List<StringTupel> getPropMethodsMetaInfos() {
		List<StringTupel> listOfPropMethodMetaInfo = State.getPropMethodsMetaInfos();
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
	///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StateInventory objectNothing;
	
	public static StateInventory getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StateInventory();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StateInventory() {
	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateInventory(SimulationObject object) 
	{
		super(object);
	}

	protected  ReturnCode init() {
		this.inventory = Inventory.getTestInventory(SimulationObject_Type.human);
		return ReturnCode.todo;
		
	}

	private StateInventory( StateInventory original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		this.inventory = Inventory.getTestInventory(SimulationObject_Type.human);
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
		case stateInventory_inventory:
			return this.inventory.getAsValue(cluster, valueName);
		case inventory_shirt:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_trousers:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_leftShoe:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_rightShoe:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_leftSock:
			return this.inventory.getProperty(cluster, propName, valueName);
		case inventory_rightSock:
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
			if (value instanceof Shirt) {
				this.inventory.setShirt((Shirt) value );
			}
			break;
		case inventory_trousers:
			if (value instanceof Trousers) {
				this.inventory.setTrousers((Trousers) value );
			}
			break;
		case inventory_leftShoe:
			if (value instanceof Shoe) {
				this.inventory.setLeftShoe((Shoe) value );
			}
			break;
		case inventory_rightShoe:
			if (value instanceof Shoe) {
				this.inventory.setRightShoe((Shoe) value );
			}
			break;
		case inventory_leftSock:
			if (value instanceof Sock) {
				this.inventory.setLeftSock((Sock) value );
			}
			break;
		case inventory_rightSock:
			if (value instanceof Sock) {
				this.inventory.setRightSock((Sock) value );
			}
			break;
		case inventory_cap:
			if (value instanceof Cap) {
				this.inventory.setCap((Cap) value );
			}
			break;
		default:


		}
		
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateInventory methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	

}
