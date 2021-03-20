package org.socialworld.objects.concrete;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;
import org.socialworld.tools.Generation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class StateAppearance extends State {

	public static final String VALUENAME_MAIN_COLOR = "mainColour";

	public static final String METHODNAME_GET_MAIN_COLOR = "getMainColour";

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StateAppearance singletonDummyForGenerationTools;
	private static List<String> listOfReturnableGetPropertyTypes;
	private boolean listOfReturnablePropertyTypesIsFilled = false;
	private static String[] returnableGetPropertyTypes = new String[]{} ;

	public static StateAppearance getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StateAppearance(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	private StateAppearance(Generation calledFromGeneration) 
	{
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateAppearance() 
	{
		super();
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateAppearance);
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
		switch (propName) {
		default:
			return ValueProperty.getInvalid();
		}
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
	/////////////////////////  implementing  StateAppearance methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	protected ValueProperty getMainColour() {
		return new ValueProperty(Type.integer, VALUENAME_MAIN_COLOR, Color.RED.getRGB());
	}
}
