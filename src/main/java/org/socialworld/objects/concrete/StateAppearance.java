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

import org.socialworld.attributes.Dimension;
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

import java.util.ArrayList;
import java.util.List;

public class StateAppearance extends State {

	public static final int COUNT_COLOUR_SETS = 15;
	
	public static final String VALUENAME_MAIN_COLOR = "mainColour";

	public static final String METHODNAME_GET_MAIN_COLOR = "getMainColour";

	private Dimension dimension;
	private List<ColourSet> colourSets;

	private PropertyName[] colourSetPropNames;

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
		
		if (this.colourSets == null) {
			this.colourSets = new ArrayList<ColourSet>();
			for (int i = 0; i < COUNT_COLOUR_SETS; i++) {
				this.colourSets.add(ColourSet.getObjectNothing());
			}
		}
		if (this.dimension == null) {
			this.dimension = Dimension.getObjectNothing();
		}

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public StateAppearance(SimulationObject object) 
	{
		super(object);
		
		// assumption: init() is called by base class constructor --> this.colourSets is already initialized
		if (this.colourSets == null) {
			this.colourSets = new ArrayList<ColourSet>();
			for (int i = 0; i < COUNT_COLOUR_SETS; i++) {
				this.colourSets.add(ColourSet.getObjectNothing());
			}
		}
		if (this.dimension == null) {
			this.dimension = Dimension.getObjectNothing();
		}
		
	}

	protected  ReturnCode init() {
		int objectID = getObjectID();

		this.colourSetPropNames = getObject().getUsedStateAppearanceColourPropertyNames();

		if (this.colourSets == null) {
			this.colourSets = new ArrayList<ColourSet>();
			for (int i = 0; i < colourSetPropNames.length; i++) {
				this.colourSets.add(ColourSet.getObjectNothing());
			}
		}
		
		
		TableStateAppearance tableState = null;
		long lockingID = 0;
		while (lockingID == 0) {
			tableState = TableStateAppearance.getInstance();
			lockingID = tableState.lock();
		}
		int rowTable = tableState.loadForObjectID(objectID) ;
		if (rowTable >= 0) {
			
			int colourSetNumber;
			
			for (PropertyName propName : colourSetPropNames) {
				
				colourSetNumber = mapCSPN2CSN(propName);
				this.colourSets.set(colourSetNumber,  tableState.getColourSetFromRow(rowTable, colourSetNumber + 1));
				
			}
			
			this.dimension = tableState.getDimensionFromRow(rowTable);
			
		}
		return returnFromInit(tableState, lockingID, rowTable);
	}
	
	private StateAppearance( StateAppearance original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		if (this.colourSets == null) {
			this.colourSets = new ArrayList<ColourSet>();
		}
		
		// TODO using copy constructors?
		
		for (int i = 0; i < original.colourSetPropNames.length; i++) {
			this.colourSets.add(original.colourSets.get(i));
		}
		this.dimension = original.dimension;
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateAppearance);
	}

	// mapColourSetPropName2ColourSetNumber
	private int mapCSPN2CSN(PropertyName colourSetPropName) {
		for (int i = 0; i < colourSetPropNames.length; i++) {
			if (colourSetPropNames[i].equals(colourSetPropName)) return i;
		}
		return 0;

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
		case stateAppearance_colourFrontside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourFrontside)).copyForProperty(cluster));
		case stateAppearance_colourBackside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBackside)).copyForProperty(cluster));
		case stateAppearance_colourLeftside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLeftside)).copyForProperty(cluster));
		case stateAppearance_colourRightside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourRightside)).copyForProperty(cluster));
		case stateAppearance_colourUpperside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourUpperside)).copyForProperty(cluster));
		case stateAppearance_colourLowerside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLowerside)).copyForProperty(cluster));
		case stateAppearance_colourInside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourInside)).copyForProperty(cluster));
			
		case stateAppearance_colourHead:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourHead)).copyForProperty(cluster));
		case stateAppearance_colourBreast:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBreast)).copyForProperty(cluster));
		case stateAppearance_colourBack:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBack)).copyForProperty(cluster));
		case stateAppearance_colourTail:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourTail)).copyForProperty(cluster));
		case stateAppearance_colourLegs:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLegs)).copyForProperty(cluster));
		case stateAppearance_colourArms:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourArms)).copyForProperty(cluster));

		case stateAppearance_colourSkin:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourSkin)).copyForProperty(cluster));
		case stateAppearance_colourHair:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourHair)).copyForProperty(cluster));
		case stateAppearance_colourBeard:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBeard)).copyForProperty(cluster));
		case stateAppearance_colourEye:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourEye)).copyForProperty(cluster));
			
		case stateAppearance_colourLeave:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLeave)).copyForProperty(cluster));
		case stateAppearance_colourFruit:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourFruit)).copyForProperty(cluster));
		case stateAppearance_colourBlossom:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBlossom)).copyForProperty(cluster));
			
		case stateAppearance_mainColour:
			return getMainColour_(cluster, valueName);
			
		case stateAppearance_dimension:
			return new ValueProperty(Type.simObjProp, valueName, this.dimension.copyForProperty(cluster));

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
		return this.colourSets.get(0).getProperty(cluster, propName, valueName);
	}

}
