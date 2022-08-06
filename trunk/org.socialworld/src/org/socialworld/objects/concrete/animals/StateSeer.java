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
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateSeer extends State {

	private Direction directionView ;

	private float angleViewPerceivingEvents;
	private double  angleViewPerceivingEventsInRadians;
	private float angleViewPerceivingObjects;
	private double  angleViewPerceivingObjectsInRadians;

	private int bestPercipiencePerpendicular;

	private double sizeDistanceRelationThreshold;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel("Direction", PropertyName.stateSeer_directionView.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingEvents.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingEventsInRadians.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingObjects.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingObjectsInRadians.name()),
			new StringTupel(Type.integer.getIndexWithSWTPraefix(), PropertyName.stateSeer_bestPercipiencePerpendicular.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_sizeDistanceRelationThreshold.name())} ;
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
	
	public StateSeer() {
		super();
		setPropertyName(PropertyName.stateSeer);
		setDirectionView( new Vector(2,1,0));
		setAngleViewPerceivingObjects(20.0F);
		setAngleViewPerceivingEvents(60.0F);
	}
	
	private StateSeer( StateSeer original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		setPropertyName(PropertyName.stateSeer);
		this.angleViewPerceivingEvents = original.getAngleViewPerceivingEvents();
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
		case stateSeer_angleViewPerceivingEvents:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingEvents);
		case stateSeer_angleViewPerceivingEventsInRadians:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingEventsInRadians);
		case stateSeer_angleViewPerceivingObjects:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingObjects);
		case stateSeer_angleViewPerceivingObjectsInRadians:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingObjectsInRadians);
		case stateSeer_bestPercipiencePerpendicular:
			return new ValueProperty(Type.integer, valueName, this.bestPercipiencePerpendicular);
		case stateSeer_sizeDistanceRelationThreshold:
			return new ValueProperty(Type.floatingpoint, valueName, this.sizeDistanceRelationThreshold);
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
	

	public double getSizeDistanceRelationThreshold() {
		return this.sizeDistanceRelationThreshold;
	}
	

	
	public void setAngleViewPerceivingObjects(float angleView) {
		this.angleViewPerceivingObjects = angleView;
		this.angleViewPerceivingObjectsInRadians = Math.toRadians(angleView);
	}

	public void setAngleViewPerceivingEvents(float angleView) {
		this.angleViewPerceivingEvents = angleView;
		this.angleViewPerceivingEventsInRadians = Math.toRadians(angleView);
	}

	public float getAngleViewPerceivingObjects() {
		return this.angleViewPerceivingObjects;
	}

	public double getAngleViewPerceivingObjectsInRadians() {
		return this.angleViewPerceivingObjectsInRadians;
	}

	public float getAngleViewPerceivingEvents() {
		return this.angleViewPerceivingEvents;
	}

	public double getAngleViewPerceivingEventsInRadians() {
		return this.angleViewPerceivingEventsInRadians;
	}

	public int getBestPercipiencePerpendicular() {
		return this.bestPercipiencePerpendicular;
	}

	private void setBestPercipiencePerpendicular() {
		this.bestPercipiencePerpendicular =  
				VectorMapper.getInstance().getBestVisibleAreaPerpendicular(this.directionView.getVector(SimulationCluster.todo));

	}
	
	
	private void setDirectionView(Vector directionView) {
		this.directionView = new Direction(PropertyName.stateSeer_directionView, directionView);
		setBestPercipiencePerpendicular();
	}

	
}
