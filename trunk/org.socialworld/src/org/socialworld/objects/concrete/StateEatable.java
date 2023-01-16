/*
* Social World
* Copyright (C) 2020  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects.concrete;


import java.util.List;

import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.ReturnCode;
import org.socialworld.datasource.tablesSimulation.states.TableStateEatable;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateEatable extends State {

	public static final String VALUENAME_MAIN_NUTRIENT = "mainNutrient";
	public static final String VALUENAME_MAIN_TASTE = "mainTaste";

	public static final String VALUENAME_NUTRIENT_SET = "nutrientSet";
	public static final String VALUENAME_TASTE_SET = "tasteSet";

	public static final String METHODNAME_GET_MAIN_NUTRIENT = "getMainNutrient";
	public static final String METHODNAME_GET_MAIN_TASTE = "getMainTaste";

	public static final String METHODNAME_GET_NUTRIENT_SET = "getNutrientSet";
	public static final String METHODNAME_GET_TASTE_SET = "getTasteSet";

	public static final String METHODNAME_SET_NUTRIENT_SET = "setNutrientSet";
	public static final String METHODNAME_SET_TASTE_SET = "setTasteSet";

	private NutrientSet nutrientSet;
	private TasteSet tasteSet;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{};
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {
		new StringTupel(new String[] {"Nutrient", METHODNAME_GET_MAIN_NUTRIENT, VALUENAME_MAIN_NUTRIENT}),
		new StringTupel(new String[] {"Taste", METHODNAME_GET_MAIN_TASTE, VALUENAME_MAIN_TASTE}),
		new StringTupel(new String[] {Type.simObjProp.getIndexWithSWTPraefix(), METHODNAME_GET_NUTRIENT_SET, VALUENAME_NUTRIENT_SET}),
		new StringTupel(new String[] {Type.simObjProp.getIndexWithSWTPraefix(), METHODNAME_GET_TASTE_SET, VALUENAME_TASTE_SET})
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

	private static StateEatable objectNothing;
	
	public  ISimProperty getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StateEatable();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StateEatable() {
	
	}


	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateEatable(SimulationObject object) 
	{
		super(object);
	}

	protected  ReturnCode init() {
		
		int objectID = getObjectID();
		
		TableStateEatable tableState = null;
		long lockingID = 0;
		while (lockingID == 0) {
			tableState = TableStateEatable.getInstance();
			lockingID = tableState.lock();
		}
		int rowTable = tableState.loadForObjectID(objectID) ;
		if (rowTable >= 0) {
			nutrientSet = tableState.getNutrientSetFromRow(rowTable);
			tasteSet = tableState.getTasteSetFromRow(rowTable);
		}
		return returnFromInit(tableState, lockingID, rowTable);
	
	}

	private StateEatable( StateEatable original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateEatable);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public State copyForProperty(SimulationCluster cluster) {
		return new StateEatable(this, getPropertyProtection(), cluster);
	}

	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case stateEatable_tasteSet:
			return new ValueProperty(Type.simObjProp, valueName, tasteSet.copyForProperty(cluster));
		case stateEatable_mainTaste:
			return getMainTaste(cluster, valueName);
		case stateEatable_nutrientSet:
			return new ValueProperty(Type.simObjProp, valueName, nutrientSet.copyForProperty(cluster));
		case stateEatable_mainNutrient:
			return getMainNutrient(cluster, valueName);

		default:
			return ValueProperty.getInvalid();
		}
	}

	protected void setProperty(PropertyName propName, ValueProperty property) {
	}


	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateEatable methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	

	
	protected ValueProperty getMainTaste() {
		return getTasteSetProperty(getPropertyProtection().getCluster(), PropertyName.tasteSet_mainTaste, VALUENAME_MAIN_TASTE);
	}

	private ValueProperty getMainTaste(SimulationCluster cluster, String valueName) {
		return getTasteSetProperty(cluster, PropertyName.tasteSet_mainTaste, valueName);
	}

	private ValueProperty getTasteSetProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		return this.tasteSet.getProperty(cluster, propName, valueName);
	}
	
	
	protected ValueProperty getMainNutrient() {
		return getNutrientSetProperty(getPropertyProtection().getCluster(), PropertyName.nutrientSet_mainNutrient, VALUENAME_MAIN_NUTRIENT);
	}

	private ValueProperty getMainNutrient(SimulationCluster cluster, String valueName) {
		return getNutrientSetProperty(cluster, PropertyName.nutrientSet_mainNutrient, valueName);
	}

	private ValueProperty getNutrientSetProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		return this.nutrientSet.getProperty(cluster, propName, valueName);
	}
	
	
	protected ValueProperty getNutrientSet() {
		return new ValueProperty(Type.simObjProp, VALUENAME_NUTRIENT_SET, nutrientSet.copyForProperty(getPropertyProtection().getCluster()));
	}
	
	protected void setNutrientSet(NutrientSet changed) {  }
	
	
	protected ValueProperty getTasteSet() {
		return new ValueProperty(Type.simObjProp, VALUENAME_TASTE_SET, tasteSet.copyForProperty(getPropertyProtection().getCluster()));
	}
	
	protected void setTasteSet(TasteSet changed) {  }

}
