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

public class EventReactionDescription extends DescriptionBase {
	
	private List<FunctionByExpression> functionsCreateReaction;
	
	public EventReactionDescription() {
		functionsCreateReaction = new  ArrayList<FunctionByExpression>();
	}
	
	public EventReactionDescription(Expression startExpression) {
		
		functionsCreateReaction = new  ArrayList<FunctionByExpression>();
		FunctionByExpression f_createRection = new FunctionByExpression(startExpression);
		functionsCreateReaction.add(f_createRection);

	}

	public EventReactionDescription(FunctionByExpression function) {

		functionsCreateReaction = new  ArrayList<FunctionByExpression>();
		functionsCreateReaction.add(function);

	}

	public void addFunctionCreateReaction(FunctionByExpression function) {

		functionsCreateReaction.add(function);

	}
	
	public FunctionByExpression getFunctionCreateAction(int index) {
		
		return functionsCreateReaction.get(index);
		
	}
	
	public int countFunctions() {
		
		return functionsCreateReaction.size();
		
	}

}
