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


import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.objects.State;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringPair;

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

	private static StateSeer singletonDummyForGenerationTools;
	private static List<StringPair> listOfPropertyMetaInfo;
	private boolean listOfPropertyMetaInfoIsFilled = false;
	private static StringPair[] propertiesMetaInfos = new StringPair[]{
			new StringPair("Direction", PropertyName.stateSeer_directionView.name()),
			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingEvents.name()),
			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingEventsInRadians.name()),
			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingObjects.name()),
			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_angleViewPerceivingObjectsInRadians.name()),
			new StringPair(Type.integer.getIndexWithSWTPraefix(), PropertyName.stateSeer_bestPercipiencePerpendicular.name()),
			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.stateSeer_sizeDistanceRelationThreshold.name())} ;

	public static StateSeer getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StateSeer(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	private StateSeer(Generation calledFromGeneration) 
	{
		
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
		// TODO implement copy constructor
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

	public List<StringPair> getPropertiesMetaInfos() {
		if (!listOfPropertyMetaInfoIsFilled) {
			List<StringPair> result = super.getPropertiesMetaInfos();
			for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
				result.add(propertiesMetaInfos[indexAdd]);
			}
			listOfPropertyMetaInfo = result;
			listOfPropertyMetaInfoIsFilled = true;
		}
		return new ArrayList<StringPair>(listOfPropertyMetaInfo);
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
