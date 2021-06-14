package org.socialworld.tools;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.SVVector;
import org.socialworld.calculation.Type;
import org.socialworld.objects.Animal;
import org.socialworld.objects.God;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.Item;
import org.socialworld.objects.Magic;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.animals.Mammal;
import org.socialworld.objects.concrete.animals.StateBody;
import org.socialworld.objects.concrete.animals.StateInventory;
import org.socialworld.objects.concrete.animals.StateSeer;
import org.socialworld.objects.concrete.animals.mammals.Dog;

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
			if (className.startsWith("GN__")) {
				List<StringPair> resultA;
				List<StringPair> resultB;
				int groupingNumber = (int) Integer.parseInt(className.substring(4)); 
				switch (groupingNumber) {
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_OBJECTS: result = SimulationObject.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS: 
						resultA = Animal.getPropertiesMetaInfos(); 
						resultB = Item.getPropertiesMetaInfos(); 
						result = getPropertiesMetaInfosIntersect(resultA, resultB);
						break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PLANTS_AND_ITEMS: 
						//resultA = Plant.getPropertiesMetaInfos(); 
						// TEMP_SOLUTION
						resultA = Animal.getPropertiesMetaInfos(); 
						resultB = Item.getPropertiesMetaInfos(); 
						result = getPropertiesMetaInfosIntersect(resultA, resultB);
						break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS: result = Animal.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PLANTS:
						//result = Plant.getPropertiesMetaInfos(); 
						// TEMP_SOLUTION
						result = Animal.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ITEMS: result = Item.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_GODS: result = God.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MAGICS: result = Magic.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MAMMALS: result = Mammal.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_DOGS: result = Dog.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CATS: 
						//result = Cat.getPropertiesMetaInfos(); 
						// TEMP_SOLUTION
						result = Animal.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_BIRDS: 
						//result = Bird.getPropertiesMetaInfos(); 
						// TEMP_SOLUTION
						result = Animal.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FISHS: 
						//result = Fish.getPropertiesMetaInfos(); 
						// TEMP_SOLUTION
						result = Animal.getPropertiesMetaInfos(); break;

					default: result = new ArrayList<StringPair>();
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

	private List<StringPair> getPropertiesMetaInfosIntersect(List<StringPair> a, List<StringPair> b) {
		List<StringPair> result = new ArrayList<StringPair>();
		int indexA;
		int indexB;
		String a_right;
		String b_right;
		for (indexA = 0; indexA < a.size(); indexA++) {
			a_right = a.get(indexA).getRight();
			for (indexB = 0; indexB < b.size(); indexB++) {
				b_right = b.get(indexB).getRight();
				if (a_right.equals(b_right)) {
					result.add(a.get(indexA));
					break;
				}
			}
		}
		return result;
	}
}
