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

/**
 *  The enumeration holds all operators that
 *         can be used in conditions.

 * @author Mathias Sikos (tyloesand) 
 */
public enum Expression_ConditionOperator {
	notEqual(0), equal(1), less(2), lessEqual(3), greaterEqual(4), greater(5),
	and(6), or(7);

	public static final int NUMBER_OF_COMPARISON_OPERATORS = 6;
	
	private int arrayIndex;

	private Expression_ConditionOperator(int index) {
		this.arrayIndex = index;
	}
	
	/**
	 * The method returns the expression condition operator name which belongs to the parameter
	 * operator index.
	 * 
	 * @param arrayIndex
	 *            operator index
	 * @return expression condition operator name
	 */
	public static Expression_ConditionOperator getName(int arrayIndex) {
		for (Expression_ConditionOperator operator : Expression_ConditionOperator.values())
			if (operator.arrayIndex == arrayIndex)
				return operator;
		return null;
	}	
	
	public String toString() {
	
		switch (this.arrayIndex) {
		case 0: return "!="; 
		case 1: return "==";
		case 2: return "<"; 
		case 3: return "=<";
		case 4: return ">=";
		case 5: return ">"; 
		case 6: return "&";
		case 7: return "|"; 
		default: return "";
		}
		
	}
	
}
