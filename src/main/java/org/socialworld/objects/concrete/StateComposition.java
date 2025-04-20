package org.socialworld.objects.concrete;


import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.ReturnCode;
import org.socialworld.datasource.tablesSimulation.states.CacheTableStateComposition;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateComposition extends State {

	public static final String VALUENAME_MAIN_MATERIAL = "mainMaterial";

	public static final String METHODNAME_GET_MAIN_MATERIAL = "getMainMaterial";
	
	private MaterialSet materialSet;

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
	///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StateComposition objectNothing;
	
	public static StateComposition getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StateComposition();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StateComposition() {
		materialSet = MaterialSet.getObjectNothing();
	}
	

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
		
	public StateComposition(SimulationObject object) 
	{
		super(object);
		if (this.materialSet == null) this.materialSet = MaterialSet.getObjectNothing();
	}


	protected  ReturnCode init() {
		int objectID = getObjectID();
		
		CacheTableStateComposition tableState = null;
		tableState = CacheTableStateComposition.getInstance();
		int rowTable = tableState.getRowForID(objectID) ;
		if (rowTable >= 0) {
			materialSet = tableState.getMaterialSetFromRow(rowTable);
		}
		return returnFromInit(tableState, rowTable);
	}

	private StateComposition( StateComposition original, PropertyProtection protectionOriginal, IAccessToken token) {
		super(protectionOriginal, token);
		this.materialSet = (MaterialSet) original.materialSet.copyForProperty(token);
	}
	
	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateComposition);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValue  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public State copyForProperty(IAccessToken token) {
		return new StateComposition(this, getPropertyProtection(), token);
	}

	@Override
	public ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
		switch (propName) {
		case stateComposition_materialSet:
			return new ValueProperty(Type.simObjProp, valueName, materialSet.copyForProperty(token));
		case stateComposition_mainMaterial:
			return getMainMaterial_(token, valueName);

		default:
			return ValueProperty.getInvalid();
		}
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {

	}


	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateComposition methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	protected ValueProperty getMainMaterial() {
		return getMaterialSetProperty(getPropertyProtection().getToken(), PropertyName.materialSet_mainMaterial, VALUENAME_MAIN_MATERIAL);
	}

	private ValueProperty getMainMaterial_(IAccessToken token, String valueName) {
		return getMaterialSetProperty(token, PropertyName.materialSet_mainMaterial, valueName);
	}

	private ValueProperty getMaterialSetProperty(IAccessToken token, PropertyName propName, String valueName) {
		return this.materialSet.getProperty(token, propName, valueName);
	}
}
