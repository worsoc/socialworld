/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.properties.ColourSet;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.ReturnCode;
import org.socialworld.datasource.tablesSimulation.states.TableStateAppearance;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

import java.util.List;

public class StateAppearance extends State {

	public static final String VALUENAME_MAIN_COLOR = "mainColour";

	public static final String METHODNAME_GET_MAIN_COLOR = "getMainColour";

	private ColourSet colourSet;

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
	///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StateAppearance objectNothing;
	
	public static StateAppearance getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StateAppearance();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StateAppearance() {
		this.colourSet = ColourSet.getObjectNothing();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public StateAppearance(SimulationObject object) 
	{
		super(object);
		if (this.colourSet == null) this.colourSet = ColourSet.getObjectNothing();
	}

	protected  ReturnCode init() {
		int objectID = getObjectID();
		
		TableStateAppearance tableState = null;
		long lockingID = 0;
		while (lockingID == 0) {
			tableState = TableStateAppearance.getInstance();
			lockingID = tableState.lock();
		}
		int rowTable = tableState.loadForObjectID(objectID) ;
		if (rowTable >= 0) {
			colourSet = tableState.getColourSetFromRow(rowTable);
			if (objectID > 3) {
				int myBreakpoint = 0;
				myBreakpoint++;
			}
		}
		return returnFromInit(tableState, lockingID, rowTable);
	}
	
	private StateAppearance( StateAppearance original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		// TODO copy colourSet?
		this.colourSet = original.colourSet;
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateAppearance);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public State copyForProperty(SimulationCluster cluster) {
		return new StateAppearance(this, getPropertyProtection(), cluster);
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case stateAppearance_colourSet:
			return new ValueProperty(Type.simObjProp, valueName, colourSet.copyForProperty(cluster));
		case stateAppearance_mainColour:
			return getMainColour_(cluster, valueName);

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
		return getColourSetProperty(getPropertyProtection().getCluster(), PropertyName.colourSet_mainColour, VALUENAME_MAIN_COLOR);
	//	return new ValueProperty(Type.object, VALUENAME_MAIN_COLOR, Colour.black);
	}

	private ValueProperty getMainColour_(SimulationCluster cluster, String valueName) {
		return getColourSetProperty(cluster, PropertyName.colourSet_mainColour, valueName);
	}

	private ValueProperty getColourSetProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		return this.colourSet.getProperty(cluster, propName, valueName);
	}

}
