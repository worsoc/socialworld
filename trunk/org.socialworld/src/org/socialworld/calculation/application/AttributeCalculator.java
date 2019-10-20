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
package org.socialworld.calculation.application;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventInfluenceAssignment;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.access.HiddenAnimal;


public  class AttributeCalculator extends SocialWorldThread {

	public static final int ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS = 0;
	public static final int ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT = 1;

	private static AttributeCalculator instance;

	private List<Event> events;
	private List<StateAnimal> states4Influence;
	private List<HiddenAnimal> hiddenAnimals4Influence;

	private List<StateAnimal> states4Change;
	private List<HiddenAnimal> hiddenAnimals4Change;

	private List<StateAnimal> states4Refresh;
	private List<HiddenAnimal> hiddenAnimals4Refresh;
	
	/**
	 * private Constructor. 
	 */
	private AttributeCalculator() {

		this.events = new ArrayList<Event>();
		this.states4Influence = new ArrayList<StateAnimal>();
		this.hiddenAnimals4Influence = new ArrayList<HiddenAnimal>();

		this.states4Change = new ArrayList<StateAnimal>();
		this.hiddenAnimals4Change = new ArrayList<HiddenAnimal>();

		this.states4Refresh = new ArrayList<StateAnimal>();
		this.hiddenAnimals4Refresh = new ArrayList<HiddenAnimal>();
		
	}

	public static AttributeCalculator getInstance() {
		if (instance == null) {
			instance = new AttributeCalculator();
		}
		return instance;
	}
	
	public void run() {
		int i=0;
		while (isRunning()) {
			
			i++;
			if (i < 1000) {
				Scheduler.getInstance().setThreadName("Attributes ");
				Scheduler.getInstance().increment();
			}

			calculateAttributesChangedByEvent();
			calculateAttributesChangedByComplexMatrix();
			calculateAttributesChangedBySimpleMatrix();
			
			try {
				sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	final void calculateAttributesChangedByEvent(Event event, StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		this.events.add(event);
		this.states4Influence.add(stateAnimal);
		this.hiddenAnimals4Influence.add( hiddenWriteAccess);
	}

	final void calculateAttributesChangedByComplexMatrix(StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		this.states4Change.add(stateAnimal);
		this.hiddenAnimals4Change.add( hiddenWriteAccess);
	}
	
	final void calculateAttributesChangedBySimpleMatrix(StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		this.states4Refresh.add(stateAnimal);
		this.hiddenAnimals4Refresh.add( hiddenWriteAccess);
	}
	
	private final int calculateAttributesChangedByEvent() {
		
		if (this.hiddenAnimals4Influence.size() == 0) return ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS;
		
		Event event = this.events.remove(0);
		StateAnimal stateAnimal  = this.states4Influence.remove(0);
		HiddenAnimal hiddenWriteAccess = this.hiddenAnimals4Influence.remove(0);
		
		Value resultAttributeArray;

		resultAttributeArray = getAttributesChangedByEvent(event, stateAnimal);
		
		if (resultAttributeArray.isValid()) {
			AttributeArray attributes = (AttributeArray) resultAttributeArray.getValue();
			System.out.println("AttributeCalculator.calculateAttributesChangedByEvent() nachher: " + attributes.toString());
			return hiddenWriteAccess.setAttributes(attributes);
		}
		else
			return ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT;
		
	}
	
	
	/**
	 * The method sets the event influence description to an instance variable
	 * and starts the calculation.
	 * 
	 * @param eventInfluence
	 *            the informations how an event effects to the attributes.
	 */
	private static Value getAttributesChangedByEvent(Event event, StateAnimal stateAnimal) {
		EventInfluenceDescription eventInfluenceDescription = null;
		int eventType;
		int eventInfluenceType;
		Value newAttributes;
		
		FunctionByExpression f_EventInfluence = null;
		ValueArrayList arguments;
	
		eventType = event.getEventTypeAsInt();
		eventInfluenceType = stateAnimal.getInfluenceType(eventType);
		
		eventInfluenceDescription = 
			EventInfluenceAssignment.getInstance().getEventInfluenceDescription(
				eventType, eventInfluenceType	);

		int count = eventInfluenceDescription.countFunctions();
		
		arguments = new ValueArrayList();
		arguments.add( new Value(Type.attributeArray, Value.ARGUMENT_VALUE_BY_NAME_ATTRIBUTES, stateAnimal.getAttributes()) );

		System.out.println("AttributeCalculator.calculateAttributesChangedByEvent() vorher: " + stateAnimal.getAttributes().toString());
		
		for (int index = 0; index < count; index++){
			
			f_EventInfluence = eventInfluenceDescription.getFunctionEventInfluence(index);
			newAttributes = f_EventInfluence.calculate(arguments);
			arguments.set(0,  newAttributes);
			
		}
	  
		return arguments.get(0);
	}

	private final int calculateAttributesChangedByComplexMatrix() {
		
		if (this.hiddenAnimals4Change.size() == 0) return ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS;
		
		StateAnimal stateAnimal  = this.states4Change.remove(0);
		HiddenAnimal hiddenWriteAccess = this.hiddenAnimals4Change.remove(0);
		
		Value resultAttributeArray;

		resultAttributeArray = getAttributesChangedByComplexMatrix(stateAnimal);
		
		if (resultAttributeArray.isValid()) 
			return hiddenWriteAccess.setAttributes((AttributeArray) resultAttributeArray.getValue());
		else
			return ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT;
		
	}

	private static Value getAttributesChangedByComplexMatrix(StateAnimal stateAnimal) {
		FunctionByMatrix f_AttributesByMatrix;
		ValueArrayList arguments;
	
		arguments = new ValueArrayList();
		arguments.add( new Value(Type.attributeArray, stateAnimal.getAttributes()) );
		arguments.add( new Value(Type.integer, FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_COMPLEX ) );
		
		f_AttributesByMatrix = stateAnimal.getMatrix();
		return  f_AttributesByMatrix.calculate(arguments);
	}
	
	private final int calculateAttributesChangedBySimpleMatrix() {
		
		if (this.hiddenAnimals4Refresh.size() == 0) return ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS;
		
		StateAnimal stateAnimal  = this.states4Refresh.remove(0);
		HiddenAnimal hiddenWriteAccess = this.hiddenAnimals4Refresh.remove(0);
		
		Value resultAttributeArray;
		
		resultAttributeArray = getAttributesChangedBySimpleMatrix(stateAnimal);
		
		if (resultAttributeArray.isValid()) 
			return hiddenWriteAccess.setAttributes((AttributeArray) resultAttributeArray.getValue());
		else
			return ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT;
		
	}
	
	private static Value getAttributesChangedBySimpleMatrix(StateAnimal stateAnimal) {
		FunctionByMatrix f_AttributesByMatrix;
		ValueArrayList arguments;
	
		arguments = new ValueArrayList();
		arguments.add( new Value(Type.attributeArray, stateAnimal.getAttributes()) );
		arguments.add( new Value(Type.integer, FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_SIMPLE) );
		
		f_AttributesByMatrix = stateAnimal.getMatrix();
		return  f_AttributesByMatrix.calculate(arguments);
	}

	/**
	 * The method checks whether the calculation loop continues. The calculation
	 * loop stops if for every attribute the value changes by less than 1.
	 * 
	 * @return true if the calculation loop continues and false if the
	 *         calculation loop stops
	 */
/*	private boolean checkEpsilon() {
		int row = 0;

		while ((attributesNew[row] + 1) > (float) this.attributes.get(row)
				|| (attributesNew[row] - 1) < (float) this.attributes.get(row)) {
			row++;
			if (row == this.numberOfAttributes)
				return false;
		}
		return true;
	}
*/

}

