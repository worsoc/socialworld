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
package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Expression_Function;

public class Junction extends Expression {

	public Junction(Expression_ConditionOperator logicalOperator, Expression[] junctionParts) {
		
		super();
		
		if (junctionParts.length > 1) {
			setOperation(Expression_Function.condition);
			setOperator(logicalOperator);
			setExpression1(junctionParts[0]);
			
			if (junctionParts.length > 2) 
				setExpression2(getJunctionTail(logicalOperator, 1, junctionParts));
			else
				setExpression2(junctionParts[1]);
			
			setValid();
			
		}
		
	}
	
	private Expression getJunctionTail(Expression_ConditionOperator logicalOperator, int index, Expression[] junctionParts) {
		
		Expression junction = new Expression();
		
		junction.setOperation(Expression_Function.condition);
		junction.setOperator(logicalOperator);
		
		if (index == (junctionParts.length - 2))
			junction.setExpression2(junctionParts[index + 1]);
		else	
			junction.setExpression2(getJunctionTail(logicalOperator, index + 1, junctionParts));
		
		junction.setExpression1(junctionParts[index]);
		
		return junction;
		
	}
	
}
