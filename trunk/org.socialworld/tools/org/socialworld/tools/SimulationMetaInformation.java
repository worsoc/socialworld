package org.socialworld.tools;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.handle.Inventory;

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
		switch (className) {
		case "Inventory":	result = Inventory.getInstance(calledFromGeneration).getReturnableGetPropertyTypes(); break;
		default:
			result = new ArrayList<String>();
		}
		
		return result;
	}
	
	public List<String> getPropertyFromMethodTypesForClass(String className, Generation calledFromGeneration) {
		
		List<String> result;
		switch (className) {
		case "Inventory":	result = Inventory.getInstance(calledFromGeneration).getReturnableGetPropertyFromMethodTypes(); break;
		default:
			result = new ArrayList<String>();
		}
		
		return result;
	}

}
