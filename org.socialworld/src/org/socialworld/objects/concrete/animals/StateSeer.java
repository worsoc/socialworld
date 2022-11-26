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
import org.socialworld.datasource.tablesSimulation.properties.TableDirection;
import org.socialworld.datasource.tablesSimulation.properties.TablePropsSeer;
import org.socialworld.datasource.tablesSimulation.states.TableStateSeer;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateSeer extends State {

	private Direction directionView ;
	private PropsSeer propsSeer;

	private int bestPercipiencePerpendicular;

	private TableStateSeer tableSeer;
	private TableDirection tableDirection;
	private TablePropsSeer tablePropsSeer;
	
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
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateSeer(SimulationObject object) {
		super(object);
	}
	
	protected  void init() {
		int objectID = getObjectID();
		int directionID;
		int propsSeerID;
		
		tableSeer.select(tableSeer.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		int rowTableAppearance = tableSeer.getIndexFor1PK(objectID);
		if (rowTableAppearance >= 0) {
			
			bestPercipiencePerpendicular = tableSeer.getBestPercipiencePerpendicular(rowTableAppearance);
			
			tableDirection = new TableDirection();
			directionID = tableSeer.getDirectionID(rowTableAppearance);
			directionView = tableDirection.getDirection(directionID, PropertyName.stateSeer_directionView);
			
			tablePropsSeer = new TablePropsSeer();
			propsSeerID = tableSeer.getPropsSeerID(rowTableAppearance);
			propsSeer = tablePropsSeer.getPropsSeer(propsSeerID,  PropertyName.stateSeer_propsSeer);
			
		}

	}

	private StateSeer( StateSeer original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
	}
	
	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateSeer);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public State copyForProperty(SimulationCluster cluster) {
		return new StateSeer(this, getPropertyProtection(), cluster);
	}

	
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		
		switch (propName) {
		case stateSeer_directionView:
			return this.directionView.getAsValue(cluster, valueName);
		case stateSeer_bestPercipiencePerpendicular:
			return new ValueProperty(Type.integer, valueName, this.bestPercipiencePerpendicular);
		case stateSeer_propsSeer:
			return this.propsSeer.getAsValue(cluster, valueName);
		default:
			return ValueProperty.getInvalid();
		}
	}

	protected void setProperty(PropertyName propName, ValueProperty property) {
		
		Object value;
		value = property.getValue();
	
		switch (propName) {
		case stateSeer_directionView:
			if (value instanceof Vector) {
				this.directionView = new Direction(PropertyName.stateSeer_directionView, (Vector) value);
			}
			if (value instanceof Direction) {
				this.directionView = (Direction) value;
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
				VectorMapper.getInstance().getBestVisibleAreaPerpendicular(this.directionView.getVector(SimulationCluster.todo));
	}
	
	public int getBestPercipiencePerpendicular() {
		return this.bestPercipiencePerpendicular;
	}

	
	private void setDirectionView(Vector directionView) {
		this.directionView = new Direction(PropertyName.stateSeer_directionView, directionView);
		setBestPercipiencePerpendicular();
	}

	
}
