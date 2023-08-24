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

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.FunctionSetAttributeValue;

public class SetAttributeValue extends Expression {

	public SetAttributeValue(int indexAttribute, Expression expGetAttributeArray, Expression expCalculateAttributeValue) {
		
		super();
		init( indexAttribute, expGetAttributeArray, expCalculateAttributeValue);
		
	}

	public SetAttributeValue(Attribute attribute, Expression expGetAttributeArray, Expression expCalculateAttributeValue) {
		
		super();
		init( attribute.getIndex(), expGetAttributeArray, expCalculateAttributeValue);
		
	}

	private void init(int indexAttribute, Expression expGetAttributeArray, Expression expCalculateAttributeValue) {
		FunctionSetAttributeValue function = new FunctionSetAttributeValue(indexAttribute);
		
		setFunction(function);
		setExpression1(expGetAttributeArray);
		setExpression2(expCalculateAttributeValue);
		setExpression3(Nothing.getInstance());
		setOperation(Expression_Function.function);
		
		setValid();
	}
}
