/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.percipience.PropsSeer;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.ReturnCode;
import org.socialworld.datasource.tablesSimulation.states.TableStateSeer;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateSeer extends State {

	private Direction directionView = Direction.getObjectNothing();
	private PropsSeer propsSeer = PropsSeer.getObjectNothing();

	private int bestPercipiencePerpendicular;

	private static AccessTokenPackageConreteAnimals token = AccessTokenPackageConreteAnimals.getValid();

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel("Direction", PropertyName.stateSeer_directionView.name()),
			new StringTupel(Type.integer.getIndexWithSWTPraefix(), PropertyName.stateSeer_bestPercipiencePerpendicular.name()),
			new StringTupel("PropsSeer", PropertyName.stateSeer_propsSeer.name())};
	
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

	private static StateSeer objectNothing;
	
	public static StateSeer getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StateSeer();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StateSeer() {
	
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateSeer(SimulationObject object) {
		super(object);
	}
	
	protected  ReturnCode init() {
		
		int objectID = getObjectID();
		
		TableStateSeer tableState = null;
		long lockingID = 0;
		while (lockingID == 0) {
			tableState = TableStateSeer.getInstance();
			lockingID = tableState.lock();
		}
		int rowTable = tableState.loadForObjectID(objectID) ;
		if (rowTable >= 0) {
			directionView = tableState.getDirectionViewFromRow(rowTable);
			propsSeer = tableState.getPropsSeerFromRow(rowTable);
		}
		return returnFromInit(tableState, lockingID, rowTable);
		
	}

	private StateSeer( StateSeer original, PropertyProtection protectionOriginal, IAccessToken token) {
		super(protectionOriginal, token);
	}
	
	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateSeer);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValue  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public State copyForProperty(IAccessToken token) {
		return new StateSeer(this, getPropertyProtection(), token);
	}

	
	public ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
		
		switch (propName) {
		case stateSeer_directionView:
			return this.directionView.getAsValue(token, valueName);
		case stateSeer_bestPercipiencePerpendicular:
			return new ValueProperty(Type.integer, valueName, this.bestPercipiencePerpendicular);
		case stateSeer_propsSeer:
			return this.propsSeer.getAsValue(token, valueName);
		default:
			return ValueProperty.getInvalid();
		}
	}

	protected void setProperty(PropertyName propName, ValueProperty property) {
		
	
		switch (propName) {
		case stateSeer_directionView:
			Vector vector = objectRequester.requestVector(token, property, this);
			if (vector.checkIsObjectNothing()) {
				Direction direction = objectRequester.requestDirection(token, property, this);
				this.directionView = direction;
			}
			else {
				this.directionView = new Direction(PropertyName.stateSeer_directionView, vector);
			}
			
			setBestPercipiencePerpendicular();
			
		default:
			
		}
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateSeer methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	

	private void setBestPercipiencePerpendicular() {
		this.bestPercipiencePerpendicular =  
				VectorMapper.getInstance().getBestVisibleAreaPerpendicular(this.directionView.getVector(token));
	}
	
	public int getBestPercipiencePerpendicular() {
		return this.bestPercipiencePerpendicular;
	}

	
	private void setDirectionView(Vector directionView) {
		this.directionView = new Direction(PropertyName.stateSeer_directionView, directionView);
		setBestPercipiencePerpendicular();
	}

	
}
