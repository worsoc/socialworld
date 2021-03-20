package org.socialworld.objects.concrete;


import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.Material;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;
import org.socialworld.tools.Generation;

public class StateComposition extends State {

	public static final String VALUENAME_MAIN_MATERIAL = "mainColour";

	public static final String METHODNAME_GET_MAIN_MATERIAL = "getMainMaterial";
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StateComposition singletonDummyForGenerationTools;
	private static List<String> listOfReturnableGetPropertyTypes;
	private boolean listOfReturnablePropertyTypesIsFilled = false;
	private static String[] returnableGetPropertyTypes = new String[]{} ;

	public static StateComposition getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StateComposition(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	private StateComposition(Generation calledFromGeneration) 
	{
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateComposition() 
	{
		super();
	}
	
	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateComposition);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public ISavedValues copyForProperty(SimulationCluster cluster) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {
		// TODO Auto-generated method stub

	}

	public List<String> getReturnableGetPropertyTypes() {
		if (!listOfReturnablePropertyTypesIsFilled) {
			List<String> result = super.getReturnableGetPropertyTypes();
			for (int indexAdd = 0; indexAdd < returnableGetPropertyTypes.length; indexAdd++) {
				result.add(returnableGetPropertyTypes[indexAdd]);
			}
			listOfReturnableGetPropertyTypes = result;
			listOfReturnablePropertyTypesIsFilled = true;
		}
		return new ArrayList<String>(listOfReturnableGetPropertyTypes);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateCopmosition methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public ValueProperty getMainMaterial() {
		return new ValueProperty(Type.integer, VALUENAME_MAIN_MATERIAL, Material.leather.getIndex());
	}
}
