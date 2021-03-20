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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.Animal;
import org.socialworld.objects.State;
import org.socialworld.tools.Generation;

public class StatePerceptible extends State {


	
	private Percipience percipience;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StatePerceptible singletonDummyForGenerationTools;
	private static List<String> listOfReturnableGetPropertyTypes;
	private boolean listOfReturnablePropertyTypesIsFilled = false;
	private static String[] returnableGetPropertyTypes = new String[]{
			Type.vector.getIndexWithSWTPraefix()   ,"Position"} ;

	public static StatePerceptible getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StatePerceptible(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	private StatePerceptible(Generation calledFromGeneration) 
	{
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public StatePerceptible(Percipience percipience) {
		super();
		this.percipience = percipience;
	}
	
	private StatePerceptible( StatePerceptible original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		// TODO implement copy constructor
	}
	
	
	protected  void initPropertyName() {
		setPropertyName(PropertyName.statePerceptible);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public State copyForProperty(SimulationCluster cluster) {
		return new StatePerceptible(this, getPropertyProtection(), cluster);
	}

	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String name) {
		switch (propName) {
		default:
			return ValueProperty.getInvalid();
		}
	}

	protected void setProperty(PropertyName propName, ValueProperty property) {
		Object value;
		value = property.getValue();
		
		switch (propName) {
		case statePerceptible_position:
			if (value instanceof Position) {
				this.percipience.setPosition((Position) value);
			}
		case statePerceptible_cuboid:
			if (value instanceof Vector) {
				this.percipience.setCuboid((Vector) value);
			}
		default:
			
		}
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
	/////////////////////////  implementing  StatePerceptible methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean checkIsPossiblePercipient(Animal possiblePercipient) {
		return this.percipience.checkIsPossiblePercipient(possiblePercipient);
	}

	public boolean checkChanceToBeSeen(Animal possibleSeer) {
		return this.percipience.checkChanceToBeSeen(possibleSeer);
	}
	
	public boolean checkIsPossibleSeer(Animal possibleSeer) {
		return this.percipience.checkIsPossibleSeer(possibleSeer);
	}

	


}
