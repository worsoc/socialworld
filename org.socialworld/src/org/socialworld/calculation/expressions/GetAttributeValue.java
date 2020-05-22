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
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class GetAttributeValue extends Expression {

	private static GetAttributeValue instance0;
	private static GetAttributeValue instance1;
	private static GetAttributeValue instance2;
	private static GetAttributeValue instance3;
	private static GetAttributeValue instance4;
	private static GetAttributeValue instance5;
	private static GetAttributeValue instance6;
	private static GetAttributeValue instance7;
	private static GetAttributeValue instance8;
	
	private GetAttributeValue(int attributeIndex) {
		super();
		setOperation(Expression_Function.attributeValue);
		setValue(new Value(Type.integer, attributeIndex));
		
		setValid();
	}
	
	public static GetAttributeValue getInstance(int attributeIndex) {
		
		switch (attributeIndex) {
		case 0:
		
			if (instance0 == null) {
				instance0 = new GetAttributeValue(attributeIndex);
			}
			return instance0;
		case 1:
			
			if (instance1 == null) {
				instance1 = new GetAttributeValue(attributeIndex);
			}
			return instance1;
		case 2:
			
			if (instance2 == null) {
				instance2 = new GetAttributeValue(attributeIndex);
			}
			return instance2;
		case 3:
			
			if (instance3 == null) {
				instance3 = new GetAttributeValue(attributeIndex);
			}
			return instance3;
		case 4:
			
			if (instance4 == null) {
				instance4 = new GetAttributeValue(attributeIndex);
			}
			return instance4;
		case 5:
			
			if (instance5 == null) {
				instance5 = new GetAttributeValue(attributeIndex);
			}
			return instance5;
		case 6:
			
			if (instance6 == null) {
				instance6 = new GetAttributeValue(attributeIndex);
			}
			return instance6;
		case 7:
			
			if (instance7 == null) {
				instance7 = new GetAttributeValue(attributeIndex);
			}
			return instance7;
		case 8:
			
			if (instance8 == null) {
				instance8 = new GetAttributeValue(attributeIndex);
			}
			return instance8;

		}
		
		return null;
	}

	
	/*
	public GetAttributeValue(int attributeIndex ) {
		
		super();
		
		setOperation(Expression_Function.attributeValue);
		setValue(new Value(Type.integer, attributeIndex));
		
		setValid();
		
	}
	
	*/
}
