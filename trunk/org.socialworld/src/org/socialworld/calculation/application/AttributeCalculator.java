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

import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventInfluenceAssignment;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.core.Event;
import org.socialworld.objects.Animal;


public  class AttributeCalculator {



	/**
	 * The method sets the event influence description to an instance variable
	 * and starts the calculation.
	 * 
	 * @param eventInfluence
	 *            the informations how an event effects to the attributes.
	 */
	public static Value getAttributesChangedByEvent(Event event, Animal animal) {
		EventInfluenceDescription eventInfluenceDescription = null;
		int eventType;
		int eventInfluenceType;
	
		FunctionByExpression f_EventInfluence = null;
		Value[] arguments;
	
		eventType = event.getEventTypeAsInt();
		eventInfluenceType = animal.getInfluenceType(eventType);
		
		eventInfluenceDescription = 
			EventInfluenceAssignment.getInstance().getEventInfluenceDescription(
				eventType, eventInfluenceType	);


		f_EventInfluence = eventInfluenceDescription.getFunctionEventInfluence();
			
		arguments = new Value[1];
		arguments[0] = new Value(Type.attributeArray, animal.getAttributes());
	
		return  f_EventInfluence.calculate(arguments);
	
	}


	public static Value getAttributesChangedByMatrix(Animal animal) {
		FunctionByMatrix f_AttributesByMatrix;
		Value[] arguments;
	

		arguments = new Value[2];
		arguments[0] = new Value(Type.attributeArray, animal.getAttributes());
		arguments[1] = new Value(Type.integer, FunctionByMatrix_Matrix.MATRIX_CALCULATION_COMPLEX );
		
		// TODO get the function object from animal (not the matrix!)
		//      --> the function object shouldn't be created here
		f_AttributesByMatrix = new FunctionByMatrix(animal.getMatrix());
		return  f_AttributesByMatrix.calculate(arguments);
	}
	
	public static Value getAttributesChangedByRefresh(Animal animal) {
		FunctionByMatrix f_AttributesByMatrix;
		Value[] arguments;
	
		arguments = new Value[2];
		arguments[0] = new Value(Type.attributeArray, animal.getAttributes());
		arguments[1] = new Value(Type.integer, FunctionByMatrix_Matrix.MATRIX_CALCULATION_SIMPLE);
		
		// TODO get the function object from animal (not the matrix!)
		//      --> the function object shouldn't be created here
		f_AttributesByMatrix = new FunctionByMatrix(animal.getMatrix());
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

