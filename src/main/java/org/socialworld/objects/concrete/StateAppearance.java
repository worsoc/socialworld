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

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Dimension;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.properties.ColourSet;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;
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

	private static AccessTokenPackageConcrete token = AccessTokenPackageConcrete.getValid();

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
		tableState = TableStateAppearance.getInstance();
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > StateAppearance.init().loadForObjectID Start " + ActualTime.asTime().toString());
		int rowTable = tableState.getRowForID(objectID);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > StateAppearance.init().loadForObjectID Ende " + ActualTime.asTime().toString());
		if (rowTable >= 0) {
			
			int colourSetNumber;
			
			for (PropertyName propName : colourSetPropNames) {
				
				if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > StateAppearance.init().mapCSPN2CSN " + propName.toString() +  " "+ ActualTime.asTime().toString());
				colourSetNumber = mapCSPN2CSN(propName);
				if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > StateAppearance.init().getColourSetFromRow() " + ActualTime.asTime().toString());
				this.colourSets.set(colourSetNumber,  tableState.getColourSetFromRow(rowTable, colourSetNumber + 1));
				
			}
			
			this.dimension = tableState.getDimensionFromRow(rowTable);
			
		}
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > StateAppearance.init() vor returnFromInit() " + ActualTime.asTime().toString());
		return returnFromInit(tableState, rowTable);
	}
	
	private StateAppearance( StateAppearance original, PropertyProtection protectionOriginal, IAccessToken token) {
		super(protectionOriginal, token);
		if (this.colourSets == null) {
			this.colourSets = new ArrayList<ColourSet>();
		}
			
		for (int i = 0; i < original.colourSetPropNames.length; i++) {
			this.colourSets.add((ColourSet) original.colourSets.get(i).copyForProperty(token));
		}
		this.dimension = (Dimension) original.dimension.copyForProperty(token);
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
	/////////////////////////  implementing  ISavedValue  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public State copyForProperty(IAccessToken token) {
		return new StateAppearance(this, getPropertyProtection(), token);
	}

	@Override
	public ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
		switch (propName) {
		case stateAppearance_colourFrontside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourFrontside)).copyForProperty(token));
		case stateAppearance_colourBackside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBackside)).copyForProperty(token));
		case stateAppearance_colourLeftside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLeftside)).copyForProperty(token));
		case stateAppearance_colourRightside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourRightside)).copyForProperty(token));
		case stateAppearance_colourUpperside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourUpperside)).copyForProperty(token));
		case stateAppearance_colourLowerside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLowerside)).copyForProperty(token));
		case stateAppearance_colourInside:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourInside)).copyForProperty(token));
			
		case stateAppearance_colourHead:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourHead)).copyForProperty(token));
		case stateAppearance_colourBreast:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBreast)).copyForProperty(token));
		case stateAppearance_colourBack:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBack)).copyForProperty(token));
		case stateAppearance_colourTail:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourTail)).copyForProperty(token));
		case stateAppearance_colourLegs:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLegs)).copyForProperty(token));
		case stateAppearance_colourArms:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourArms)).copyForProperty(token));

		case stateAppearance_colourSkin:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourSkin)).copyForProperty(token));
		case stateAppearance_colourHair:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourHair)).copyForProperty(token));
		case stateAppearance_colourBeard:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBeard)).copyForProperty(token));
		case stateAppearance_colourEye:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourEye)).copyForProperty(token));
			
		case stateAppearance_colourLeave:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourLeave)).copyForProperty(token));
		case stateAppearance_colourFruit:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourFruit)).copyForProperty(token));
		case stateAppearance_colourBlossom:
			return new ValueProperty(Type.simObjProp, valueName, 
					this.colourSets.get(mapCSPN2CSN(PropertyName.stateAppearance_colourBlossom)).copyForProperty(token));
			
		case stateAppearance_mainColour:
			return getMainColour_(token, valueName);
			
		case stateAppearance_dimension:
			return new ValueProperty(Type.simObjProp, valueName, this.dimension.copyForProperty(token));

		default:
			return ValueProperty.getInvalid();
		}
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {
		switch (propName) {
		case stateAppearance_colourFrontside:
		case stateAppearance_colourBackside:
		case stateAppearance_colourLeftside:
		case stateAppearance_colourRightside:
		case stateAppearance_colourUpperside:
		case stateAppearance_colourLowerside:
		case stateAppearance_colourInside:
		case stateAppearance_colourHead:
		case stateAppearance_colourBreast:
		case stateAppearance_colourBack:
		case stateAppearance_colourTail:
		case stateAppearance_colourLegs:
		case stateAppearance_colourArms:
		case stateAppearance_colourSkin:
		case stateAppearance_colourHair:
		case stateAppearance_colourBeard:
		case stateAppearance_colourEye:
		case stateAppearance_colourLeave:
		case stateAppearance_colourFruit:
		case stateAppearance_colourBlossom:
			this.colourSets.set(mapCSPN2CSN(propName),objectRequester.requestColourSet(token, property, this) );
			break;
		case stateAppearance_dimension:
			this.dimension = objectRequester.requestDimension(token, property, this);
			break;
		default:
			break;

		}

	}
	

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateAppearance methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	protected ValueProperty getMainColour() {
		return getColourSetProperty(getPropertyProtection().getToken(), PropertyName.colourSet_mainColour, VALUENAME_MAIN_COLOR);
	//	return new ValueProperty(Type.object, VALUENAME_MAIN_COLOR, Colour.black);
	}

	private ValueProperty getMainColour_(IAccessToken token, String valueName) {
		return getColourSetProperty(token, PropertyName.colourSet_mainColour, valueName);
	}

	private ValueProperty getColourSetProperty(IAccessToken token, PropertyName propName, String valueName) {
		return this.colourSets.get(0).getProperty(token, propName, valueName);
	}

}
