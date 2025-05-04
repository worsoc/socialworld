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


import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueTransferCode;
import org.socialworld.calculation.descriptions.EventInfluenceAssignment;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.calculation.functions.FunctionByExpression;
import org.socialworld.calculation.functions.FunctionByMatrix;
import org.socialworld.calculation.functions.FunctionByMatrix_Matrix;
import org.socialworld.collections.CapacityQueue;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.access.HiddenAnimal;


public  class AttributeCalculator extends SocialWorldThread {

	public static final int ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS = 2;
	public static final int ATTRIBUTE_CALCULATOR_RETURNS_NO_CHANGES = 1;
	public static final int ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT = 3;

	private static AttributeCalculator instance;

	private static AccessTokenTalkCalculator token = AccessTokenTalkCalculator.getValid();

	private CapacityQueue<CollectionElementSimObjInfluenced> influenced;
	private CapacityQueue<CollectionElementSimObjChanged> changed;
	private CapacityQueue<CollectionElementSimObjRefreshed> refreshed;

	/**
	 * private Constructor. 
	 */
	private AttributeCalculator() {

		this.sleepTime = SocialWorldThread.SLEEPTIME_ATTRIBUTE_CALCULATOR;
		
		this.influenced = new CapacityQueue<CollectionElementSimObjInfluenced>("influenced", 1000);

		this.changed = new CapacityQueue<CollectionElementSimObjChanged>("changed", 1000);

		this.refreshed = new CapacityQueue<CollectionElementSimObjRefreshed>("refreshed", 1000);

	}

	public static AttributeCalculator getInstance() {
		if (instance == null) {
			instance = new AttributeCalculator();
		}
		return instance;
	}
	
	public void run() {

		while (isRunning()) {
			
			if (this.influenced.size() > 0) calculateAttributesChangedByEvent();
			if (this.changed.size() > 0) calculateAttributesChangedByComplexMatrix();
			if (this.refreshed.size() > 0) calculateAttributesChangedBySimpleMatrix();
			
			try {
				sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	final void calculateAttributesChangedByEvent(Event event, StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		if (event != null && stateAnimal != null && hiddenWriteAccess != null) {
			if (!this.influenced.add(new CollectionElementSimObjInfluenced(event, stateAnimal, hiddenWriteAccess))) {
				// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
			};
		}
	}

	final void calculateAttributesChangedByComplexMatrix(StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		if (stateAnimal != null && hiddenWriteAccess != null) {
			if (!this.changed.add(new CollectionElementSimObjChanged(stateAnimal, hiddenWriteAccess))) {
				// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
			};
		}
	}
	
	final void calculateAttributesChangedBySimpleMatrix(StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		if (stateAnimal != null && hiddenWriteAccess != null) {
			if (!this.refreshed.add(new CollectionElementSimObjRefreshed(stateAnimal, hiddenWriteAccess))) {
				// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
			};
		}
	}
	
	private final int calculateAttributesChangedByEvent() {
		
		CollectionElementSimObjInfluenced influenced = this.influenced.remove();
		if (influenced != null) {

			Event event = influenced.getEvent();
			StateAnimal stateAnimal  = (StateAnimal) influenced.getState();
			HiddenAnimal hiddenWriteAccess =  (HiddenAnimal) influenced.getHidden();

			Value resultAttributeArray;

			
//			resultAttributeArray = getAttributesChangedByEvent(event, stateAnimal);
//			TEMP_SOLUTION
			resultAttributeArray = getAttributesChangedBySimpleMatrix(stateAnimal);
			
			if (resultAttributeArray.isValid()) {
				if (resultAttributeArray.getTransferCode() == ValueTransferCode.noChanges) {
					return ATTRIBUTE_CALCULATOR_RETURNS_NO_CHANGES;
				}
				return hiddenWriteAccess.setAttributes(resultAttributeArray);
			}
			else
				return ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT;
		}
		else {
			System.out.println("AttributeCalculator.calculateAttributesChangedByEvent(): influenced is null");
			return ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS;
		}
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
		Value oldAttributes;
		Value newAttributes = Value.getValueNothing();
		
		FunctionByExpression f_EventInfluence = null;
		ValueArrayList arguments;
	
		eventType = event.getEventTypeAsInt();
		eventInfluenceType = stateAnimal.getInfluenceType(eventType);
		
		eventInfluenceDescription = 
			EventInfluenceAssignment.getInstance().getEventInfluenceDescription(
				eventType, eventInfluenceType	);

		int count = eventInfluenceDescription.countFunctions();
		
		arguments = new ValueArrayList();

		oldAttributes =  stateAnimal.getProperty(token, PropertyName.simobj_attributeArray);
		arguments.add( oldAttributes );

		if (event.hasOptionalParam()) {
			arguments.add( event.getOptionalParam().getParamListAsValue());
		}
		else {
			arguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, event.getProperties()));
		}

		for (int index = 0; index < count; index++){
			
			f_EventInfluence = eventInfluenceDescription.getFunction(index);
			newAttributes = f_EventInfluence.calculate(arguments);
			arguments.set(0,  newAttributes);
			
		}
	  
		
		if (newAttributes.isValid()){
			if (oldAttributes.equals(newAttributes)) {
				newAttributes.setTransferCode(ValueTransferCode.noChanges);
			}
			else {
			}
			return newAttributes;
		}
		else {
			oldAttributes.setTransferCode(ValueTransferCode.noChanges);
			return oldAttributes;
		}
	}

	private final int calculateAttributesChangedByComplexMatrix() {
		
		CollectionElementSimObjChanged changed = this.changed.remove();
		if (changed != null) {

			StateAnimal stateAnimal  = (StateAnimal) changed.getState();
			HiddenAnimal hiddenWriteAccess = (HiddenAnimal) changed.getHidden();

			Value resultAttributeArray;
	
			resultAttributeArray = getAttributesChangedByComplexMatrix(stateAnimal);
			
			if (resultAttributeArray.isValid()) {
				if (resultAttributeArray.getTransferCode() == ValueTransferCode.noChanges) {
					return ATTRIBUTE_CALCULATOR_RETURNS_NO_CHANGES;
				}
				return hiddenWriteAccess.setAttributes(resultAttributeArray);
			}
			else
				return ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT;
		}
		else {
			System.out.println("AttributeCalculator.calculateAttributesChangedByComplexMatrix(): changed is null");
			return ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS;
		}
		
	}

	private static Value getAttributesChangedByComplexMatrix(StateAnimal stateAnimal) {
		FunctionByMatrix f_AttributesByMatrix;
		ValueArrayList arguments;
		Value oldAttributes;
		Value newAttributes = Value.getValueNothing();
	
		oldAttributes =  stateAnimal.getProperty(token, PropertyName.simobj_attributeArray);

		arguments = new ValueArrayList();
		arguments.add( oldAttributes );
		arguments.add( new Value(Type.integer, FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_COMPLEX ) );
		
		f_AttributesByMatrix = stateAnimal.getMatrix();
		newAttributes = f_AttributesByMatrix.calculate(arguments);;
		
		if (newAttributes.isValid()){
			if (oldAttributes.equals(newAttributes)) {
				newAttributes.setTransferCode(ValueTransferCode.noChanges);
			}
			else {
			}
			return newAttributes;
		}
		else {
			oldAttributes.setTransferCode(ValueTransferCode.noChanges);
			return oldAttributes;
		}
	}
	
	private final int calculateAttributesChangedBySimpleMatrix() {
		
		CollectionElementSimObjRefreshed refreshed = this.refreshed.remove();
		if (refreshed != null) {

			StateAnimal stateAnimal  = (StateAnimal) refreshed.getState();
			HiddenAnimal hiddenWriteAccess =  (HiddenAnimal) refreshed.getHidden();
			
			Value resultAttributeArray;
			
			resultAttributeArray = getAttributesChangedBySimpleMatrix(stateAnimal);
			
			if (resultAttributeArray.isValid()) {
				if (resultAttributeArray.getTransferCode() == ValueTransferCode.noChanges) {
					return ATTRIBUTE_CALCULATOR_RETURNS_NO_CHANGES;
				}
				return hiddenWriteAccess.setAttributes(resultAttributeArray);
			}
			else {
				return ATTRIBUTE_CALCULATOR_RETURNS_INVALID_RESULT;
			}
		}
		else {
			System.out.println("AttributeCalculator.calculateAttributesChangedBySimpleMatrix(): refreshed is null");
			return ATTRIBUTE_CALCULATOR_RETURNS_EMPTY_LISTS;
		}
		
	}
	
	private static Value getAttributesChangedBySimpleMatrix(StateAnimal stateAnimal) {
		FunctionByMatrix f_AttributesByMatrix;
		ValueArrayList arguments;
		Value oldAttributes;
		Value newAttributes = Value.getValueNothing();
	
		oldAttributes =  stateAnimal.getProperty(token, PropertyName.simobj_attributeArray);

		arguments = new ValueArrayList();
		arguments.add( oldAttributes );
		arguments.add( new Value(Type.integer, FunctionByMatrix_Matrix.CALCULATION_MODE_MATRIX_X_VECTOR_SIMPLE) );
		
		f_AttributesByMatrix = stateAnimal.getMatrix();
		newAttributes = f_AttributesByMatrix.calculate(arguments);;
		
		if (!newAttributes.isInvalidOrNothing()){
			if (oldAttributes.equals(newAttributes)) {
				newAttributes.setTransferCode(ValueTransferCode.noChanges);
			}
			else {
			}
			return newAttributes;
		}
		else {
			oldAttributes.setTransferCode(ValueTransferCode.noChanges);
			return oldAttributes;
		}
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

