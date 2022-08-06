package org.socialworld.objects.concrete;


import java.util.List;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.Material;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateComposition extends State {

	public static final String VALUENAME_MAIN_MATERIAL = "mainMaterial";

	public static final String METHODNAME_GET_MAIN_MATERIAL = "getMainMaterial";
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{};
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {
		new StringTupel(new String[] {"Material", METHODNAME_GET_MAIN_MATERIAL, VALUENAME_MAIN_MATERIAL})
		} ;
	
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
		return null;
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		return null;
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {

	}


	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateCopmosition methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public ValueProperty getMainMaterial() {
		return new ValueProperty(Type.object, VALUENAME_MAIN_MATERIAL, Material.leather);
	}
}
