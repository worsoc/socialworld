package org.socialworld.tools;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.SVVector;
import org.socialworld.attributes.properties.Colour;
import org.socialworld.attributes.properties.Material;
import org.socialworld.calculation.Type;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
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
import org.socialworld.objects.concrete.eatable.Fruit;

public class SimulationMetaInformation {

	
	public static final String CLASSNAME_ENUM_INDEX = "enumIndex";
	public static final String METHODNAME_ENUM_GETINDEX = "getIndex";

	public static final String CLASSNAME_SWT_SIMOBJECT = Type.simulationObject.getIndexWithSWTPraefix();

	public static final String PRAEFIX_GROUPING_NUMBER = "GN__";

	private static SimulationMetaInformation instance;
	
	
	private SimulationMetaInformation() {
		
	}
	
	public static SimulationMetaInformation getInstance() {
		if (instance == null) {
			instance = new SimulationMetaInformation();
		}
		return instance;
	}
	
	public List<StringPair> getPropertiesMetaInfosForClass(String className) {

		List<StringPair> result;
		Type classNameToType = Type.getTypeForSWTPraefixedName(className);
		
		if (classNameToType != Type.nothing) {
			switch(classNameToType) {
			case attributeArray: result = AttributeArray.getPropertiesMetaInfos(); break;
			//case integer: result = new ArrayList<String>(); break;
			//case floatingpoint: result = new ArrayList<String>(); break;
				
			default:	
				result = new ArrayList<StringPair>();
			}
		}
		else {
			if (className.startsWith(PRAEFIX_GROUPING_NUMBER)) {
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

					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_EATABLES: 
						// TEMP_SOLUTION
						result = Fruit.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FRUITS: result = Fruit.getPropertiesMetaInfos(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_VEGGIES: 
						// TEMP_SOLUTION
						result = Fruit.getPropertiesMetaInfos(); break;

					default: result = new ArrayList<StringPair>();
				}
			}
			else {
				switch (className) {
					case "Inventory":	result = Inventory.getPropertiesMetaInfos(); break;
					case "Direction":	result = Direction.getPropertiesMetaInfos(); break;
					case "Position":	result = Position.getPropertiesMetaInfos(); break;
					case "SVVector":	result = SVVector.getPropertiesMetaInfos(); break;
					case "StateBody":	result = StateBody.getPropertiesMetaInfos(); break;
					case "StateInventory":	result = StateInventory.getPropertiesMetaInfos(); break;
					case "StateSeer":	result = StateSeer.getPropertiesMetaInfos(); break;
					case "StateAppearance":	result = StateAppearance.getPropertiesMetaInfos(); break;
					case "StateComposition":	result = StateComposition.getPropertiesMetaInfos(); break;
					case "StateEatable":	result = StateEatable.getPropertiesMetaInfos(); break;
					case "StatePerceptible":	result = StatePerceptible.getPropertiesMetaInfos(); break;
					default:
						result = new ArrayList<StringPair>();
				}
			}
		}
		
		return result;
	}
	
	public List<StringPair> getPropMethodsMetaInfosForClass(String className) {
		
		List<StringPair> result;
		Type classNameToType = Type.getTypeForSWTPraefixedName(className);
		
		if (classNameToType != Type.nothing) {
			switch(classNameToType) {
			case attributeArray: result = AttributeArray.getPropMethodsMetaInfos(); break;
				
			default:	
				result = new ArrayList<StringPair>();
			}
		}
		else {
			switch (className) {
			case "Colour":	result = Colour.getPropMethodsMetaInfos(); break;
			case "Material":	result = Material.getPropMethodsMetaInfos(); break;
			case "Inventory":	result = Inventory.getPropMethodsMetaInfos(); break;
			case "StateAppearance":	result = StateAppearance.getPropMethodsMetaInfos(); break;
			case "StateComposition":	result = StateComposition.getPropMethodsMetaInfos(); break;
			case "StateBody":	result = StateBody.getPropMethodsMetaInfos(); break;
			case "StateInventory":	result = StateInventory.getPropMethodsMetaInfos(); break;
			default:
				result = new ArrayList<StringPair>();
			}
		}
		
		return result;
	}

	public boolean checkClassForReturnableKFCs(String className, KnowledgeFact_Criterion kfc) {

		boolean result = false;
		List<KnowledgeFact_Criterion> returnableKFCs;
		
		Type classNameToType = Type.getTypeForSWTPraefixedName(className);
		
		if (classNameToType != Type.nothing) {
			switch(classNameToType) {
			case attributeArray: returnableKFCs = AttributeArray.getResultingKFCs(); break;
				
			default:	
				returnableKFCs = new ArrayList<KnowledgeFact_Criterion>();
			}
		}
		else {
			if (className.startsWith(PRAEFIX_GROUPING_NUMBER)) {
				List<KnowledgeFact_Criterion> returnableKFCsA;
				List<KnowledgeFact_Criterion> returnableKFCsB;
				int groupingNumber = (int) Integer.parseInt(className.substring(4)); 
				switch (groupingNumber) {
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_OBJECTS: returnableKFCs = SimulationObject.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS: 
						returnableKFCsA = Animal.getResultingKFCs(); 
						returnableKFCsB = Item.getResultingKFCs(); 
						returnableKFCs = getResultingKFCsIntersect(returnableKFCsA, returnableKFCsB);
						break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PLANTS_AND_ITEMS: 
						//returnableKFCsA = Plant.getResultingKFCs(); 
						// TEMP_SOLUTION
						returnableKFCsA = Animal.getResultingKFCs(); 
						returnableKFCsB = Item.getResultingKFCs(); 
						returnableKFCs = getResultingKFCsIntersect(returnableKFCsA, returnableKFCsB);
						break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS: returnableKFCs = Animal.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PLANTS:
						//returnableKFCs = Plant.getResultingKFCs(); 
						// TEMP_SOLUTION
						returnableKFCs = Animal.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ITEMS: returnableKFCs = Item.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_GODS: returnableKFCs = God.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MAGICS: returnableKFCs = Magic.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MAMMALS: returnableKFCs = Mammal.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_DOGS: returnableKFCs = Dog.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CATS: 
						//returnableKFCs = Cat.getResultingKFCs(); 
						// TEMP_SOLUTION
						returnableKFCs = Animal.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_BIRDS: 
						//returnableKFCs = Bird.getResultingKFCs(); 
						// TEMP_SOLUTION
						returnableKFCs = Animal.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FISHS: 
						//returnableKFCs = Fish.getResultingKFCs(); 
						// TEMP_SOLUTION
						returnableKFCs = Animal.getResultingKFCs(); break;

					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_EATABLES: 
						// TEMP_SOLUTION
						returnableKFCs = Fruit.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FRUITS: returnableKFCs = Fruit.getResultingKFCs(); break;
					case GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_VEGGIES: 
						// TEMP_SOLUTION
						returnableKFCs = Fruit.getResultingKFCs(); break;
					
						
					default: returnableKFCs = new ArrayList<KnowledgeFact_Criterion>();
				}
			}
			else {
				switch (className) {
					case "Colour":	returnableKFCs = Colour.getResultingKFCs(); break;
					case "Material":	returnableKFCs = Material.getResultingKFCs(); break;
					case "Inventory":	returnableKFCs = Inventory.getResultingKFCs(); break;
					case "Direction":	returnableKFCs = Direction.getResultingKFCs(); break;
					case "Position":	returnableKFCs = Position.getResultingKFCs(); break;
					case "SVVector":	returnableKFCs = SVVector.getResultingKFCs(); break;
					case "StateBody":	returnableKFCs = StateBody.getResultingKFCs(); break;
					case "StateInventory":	returnableKFCs = StateInventory.getResultingKFCs(); break;
					case "StateSeer":	returnableKFCs = StateSeer.getResultingKFCs(); break;
					case "StateAppearance":	returnableKFCs = StateAppearance.getResultingKFCs(); break;
					case "StateComposition":	returnableKFCs = StateComposition.getResultingKFCs(); break;
					case "StateEatable":	returnableKFCs = StateEatable.getResultingKFCs(); break;
					case "StatePerceptible":	returnableKFCs = StatePerceptible.getResultingKFCs(); break;
					default:
						returnableKFCs = new ArrayList<KnowledgeFact_Criterion>();
				}
			}
		}
		
		if (returnableKFCs.size() > 0) {
			int index;
			KnowledgeFact_Criterion returnableKFC;
			for (index = 0; index < returnableKFCs.size(); index++) {
				returnableKFC = returnableKFCs.get(index);
				if (returnableKFC.equals(kfc)) {
					result = true;
					break;
				}
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
	
	private List<KnowledgeFact_Criterion> getResultingKFCsIntersect(List<KnowledgeFact_Criterion> aList, List<KnowledgeFact_Criterion> bList) {
		List<KnowledgeFact_Criterion> result = new ArrayList<KnowledgeFact_Criterion>();
		int indexA;
		int indexB;
		KnowledgeFact_Criterion a;
		KnowledgeFact_Criterion b;
		for (indexA = 0; indexA < aList.size(); indexA++) {
			a = aList.get(indexA);
			for (indexB = 0; indexB < bList.size(); indexB++) {
				b = bList.get(indexB);
				if (a.equals(b)) {
					result.add(a);
					break;
				}
			}
		}
		return result;
	}
}
