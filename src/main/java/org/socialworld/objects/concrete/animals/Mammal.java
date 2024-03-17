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
package org.socialworld.objects.concrete.animals;

import java.util.List;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.Animal;
import org.socialworld.objects.State;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumMammal;
import org.socialworld.objects.statics.Mapping_Mammal2LexemIDLowerPart;
import org.socialworld.tools.StringTupel;

/**
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public abstract class Mammal extends Animal implements IRunning{

	protected EnumMammal belongsTo;
	
	private StateRunning stateRunning = StateRunning.getObjectNothing();

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = Animal.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	private static PropertyName[] usedStateAppearanceColourPropertyNames = new PropertyName[] {
			PropertyName.stateAppearance_colourHead,
			PropertyName.stateAppearance_colourBreast,
			PropertyName.stateAppearance_colourBack,
			PropertyName.stateAppearance_colourTail,
			PropertyName.stateAppearance_colourLegs,
			PropertyName.stateAppearance_colourArms
	};

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
   
	public Mammal() {
		super();
		setBaseSimObjEnum(EnumBaseSimObj.Mammal);
	}


	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_Mammal2LexemIDLowerPart.getInstance().get(belongsTo);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE    /////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
    
	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
				
		return result;
		
	}

	public PropertyName[] getUsedStateAppearanceColourPropertyNames() {
		return usedStateAppearanceColourPropertyNames;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunning ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getSpeed() {return 27; };
	public  float getNumberLegs() {return 2; };
	
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunning_directionRun , new Vector(1.3F, 1.4F, 1.5F),30.6F);
	}
	
	
	public StateRunning getSavedStateRunning(SimulationCluster cluster) {
		//make a copy as ValueProperty
		ValueProperty vp = this.stateRunning.getAsValue(cluster);
		//the copy is permitted for cluster only
		return objectRequester.requestStateRunning(cluster, vp, this);
	}
	
	public ValueProperty getStateRunningAsProperty(SimulationCluster cluster, String name) {
		return this.stateRunning.getAsValue(cluster, name);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(SimulationCluster cluster, IEventParam paramObject) {
	
		super.requestPropertyList(cluster, paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}

  
}
