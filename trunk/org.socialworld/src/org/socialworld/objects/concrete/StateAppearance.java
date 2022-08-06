package org.socialworld.objects.concrete;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.Colour;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

import java.util.List;

public class StateAppearance extends State {

	public static final String VALUENAME_MAIN_COLOR = "mainColour";

	public static final String METHODNAME_GET_MAIN_COLOR = "getMainColour";

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////  static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{};
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {
				new StringTupel(new String[] {"Colour", METHODNAME_GET_MAIN_COLOR, VALUENAME_MAIN_COLOR})
				};
	
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
						KnowledgeFact_Criterion.colour
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

	}
	

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateAppearance methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	protected ValueProperty getMainColour() {
		return new ValueProperty(Type.object, VALUENAME_MAIN_COLOR, Colour.black);
	}
}
