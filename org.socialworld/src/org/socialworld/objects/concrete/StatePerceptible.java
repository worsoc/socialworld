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
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.ReturnCode;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.Animal;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StatePerceptible extends State {


	
	private Percipience percipience;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[] {
			new StringTupel(new String[] {Type.vector.getIndexWithSWTPraefix() , PropertyName.statePerceptible_cuboid.name(), PropertyName.statePerceptible_cuboid.toString()}),
			new StringTupel(new String[] {"Position", PropertyName.statePerceptible_position.name(), PropertyName.statePerceptible_position.toString()})
			} ;
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {} ;
	
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

	private static StatePerceptible objectNothing;
	
	public  ISimProperty getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StatePerceptible();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StatePerceptible() {
	
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StatePerceptible(SimulationObject object) 
	{
		super(object);
	}

	protected  ReturnCode init() {
		this.percipience = new Percipience(PercipienceType.simobject, 100);

		//this.percipience = new Percipience(PercipienceType.simobject, 100); // for human
		//this.percipience = new Percipience(PercipienceType.dynamic); // for Lightning, Weather

		return ReturnCode.todo;
	}

	private StatePerceptible( StatePerceptible original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
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
