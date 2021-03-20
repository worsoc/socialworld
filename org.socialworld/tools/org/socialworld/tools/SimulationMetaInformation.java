package org.socialworld.tools;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.SVVector;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.animals.StateBody;
import org.socialworld.objects.concrete.animals.StateInventory;
import org.socialworld.objects.concrete.animals.StateSeer;

public class SimulationMetaInformation {

	
	private static SimulationMetaInformation instance;
	
	
	private SimulationMetaInformation() {
		
	}
	
	public static SimulationMetaInformation getInstance() {
		if (instance == null) {
			instance = new SimulationMetaInformation();
		}
		return instance;
	}
	
	public List<String> getPropertyTypesForClass(String className, Generation calledFromGeneration) {

		List<String> result;
		Type classNameToType = Type.getTypeForSWTPraefixedName(className);
		
		if (classNameToType != Type.nothing) {
			switch(classNameToType) {
			case attributeArray: result = AttributeArray.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			//case integer: result = new ArrayList<String>(); break;
			//case floatingpoint: result = new ArrayList<String>(); break;
				
			default:	
				result = new ArrayList<String>();
			}
		}
		else {
			switch (className) {
			case "Inventory":	result = Inventory.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "Direction":	result = Direction.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "Position":	result = Position.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "SVVector":	result = SVVector.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "StateBody":	result = StateBody.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "StateInventory":	result = StateInventory.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "StateSeer":	result = StateSeer.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "StateAppearance":	result = StateAppearance.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "StateComposition":	result = StateComposition.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "StateEatable":	result = StateEatable.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			case "StatePerceptible":	result = StatePerceptible.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
			default:
				result = new ArrayList<String>();
			}
		}
		
		return result;
	}
	
	public List<String> getPropertyFromMethodTypesForClass(String className, Generation calledFromGeneration) {
		
		List<String> result;
		Type classNameToType = Type.getTypeForSWTPraefixedName(className);
		
		if (classNameToType != Type.nothing) {
			switch(classNameToType) {
			case attributeArray: result = AttributeArray.getInstance(calledFromGeneration).getReturnableGetPropertyFromMethodTypes(); break;
				
			default:	
				result = new ArrayList<String>();
			}
		}
		else {
			switch (className) {
			case "Inventory":	result = Inventory.getInstance(calledFromGeneration).getReturnableGetPropertyFromMethodTypes(); break;
			default:
				result = new ArrayList<String>();
			}
		}
		
		return result;
	}

}
