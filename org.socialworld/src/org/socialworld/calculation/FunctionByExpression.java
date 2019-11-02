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
package org.socialworld.calculation;


import org.socialworld.collections.ValueArrayList;

public class FunctionByExpression extends FunctionBase {
	Expression startExpression;
	
	//  initialized with an expression that returns the invalid "nothing"-Value
	//  it is used to avoid null returns
	public FunctionByExpression() {
		startExpression = new Expression();
		returnInvalidNothingvalue = true;
	}
	
	public FunctionByExpression(Expression startExpression) {
		
		this.startExpression  = startExpression;
	}
	
	public void setStartExpression(Expression startExpression) {
		
		this.startExpression  = startExpression;
	}
	
	public Value calculate(ValueArrayList arguments) {
		if (returnInvalidNothingvalue) 
			return new Value();
		else
			return startExpression.evaluate(arguments);
	}
	

}
