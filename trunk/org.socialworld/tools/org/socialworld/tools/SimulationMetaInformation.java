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
	
	public List<StringPair> getPropertiesMetaInfosForClass(String className, Generation calledFromGeneration) {

		List<StringPair> result;
		Type classNameToType = Type.getTypeForSWTPraefixedName(className);
		
		if (classNameToType != Type.nothing) {
			switch(classNameToType) {
			case attributeArray: result = AttributeArray.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			//case integer: result = new ArrayList<String>(); break;
			//case floatingpoint: result = new ArrayList<String>(); break;
				
			default:	
				result = new ArrayList<StringPair>();
			}
		}
		else {
			switch (className) {
			case "Inventory":	result = Inventory.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "Direction":	result = Direction.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "Position":	result = Position.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "SVVector":	result = SVVector.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "StateBody":	result = StateBody.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "StateInventory":	result = StateInventory.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "StateSeer":	result = StateSeer.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "StateAppearance":	result = StateAppearance.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "StateComposition":	result = StateComposition.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "StateEatable":	result = StateEatable.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			case "StatePerceptible":	result = StatePerceptible.getInstance(calledFromGeneration).getPropertiesMetaInfos(); break;
			default:
				result = new ArrayList<StringPair>();
			}
		}
		
		return result;
	}
	
	public List<StringPair> getPropMethodsMetaInfosForClass(String className, Generation calledFromGeneration) {
		
		List<StringPair> result;
		Type classNameToType = Type.getTypeForSWTPraefixedName(className);
		
		if (classNameToType != Type.nothing) {
			switch(classNameToType) {
			case attributeArray: result = AttributeArray.getInstance(calledFromGeneration).getPropMethodsMetaInfos(); break;
				
			default:	
				result = new ArrayList<StringPair>();
			}
		}
		else {
			switch (className) {
			case "Inventory":	result = Inventory.getInstance(calledFromGeneration).getPropMethodsMetaInfos(); break;
			default:
				result = new ArrayList<StringPair>();
			}
		}
		
		return result;
	}

}
