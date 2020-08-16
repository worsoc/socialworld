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
package org.socialworld.calculation.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;

/**
 * The class holds all informations for
 *         describing how an event influences a simulation object.

 * @author Mathias Sikos (tyloesand)   
 */
public class EventInfluenceDescription extends DescriptionBase {

	private List<FunctionByExpression> functionsEventInfluence;
	
	public EventInfluenceDescription() {
		functionsEventInfluence = new  ArrayList<FunctionByExpression>();
	}
	
	public EventInfluenceDescription(Expression startExpression) {
		
		functionsEventInfluence = new  ArrayList<FunctionByExpression>();
		FunctionByExpression f_eventInfluence = new FunctionByExpression(startExpression);
		functionsEventInfluence.add(f_eventInfluence);

	}
	
	public EventInfluenceDescription(FunctionByExpression function) {

		functionsEventInfluence = new  ArrayList<FunctionByExpression>();
		functionsEventInfluence.add(function);

	}
	
	public void addFunctionEventInfluence(FunctionByExpression function) {

		functionsEventInfluence.add(function);

	}
	
	public FunctionByExpression getFunctionEventInfluence(int index) {
		return functionsEventInfluence.get(index);
	}


	public int countFunctions() {
		
		return functionsEventInfluence.size();
		
	}
}
