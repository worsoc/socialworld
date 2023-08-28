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
 *  The enumeration holds all possible
 *         expression types. There are mathematical comparison to a constant, an
 *         addition or multiplication by a constant or the replacement by a
 *         constant.
 * @author Mathias Sikos (MatWorsoc)
 */
public enum Expression_Function {
	oneExpression(1), branching(2), condition(3), comparison(4), replacement(5),	sequence(6), skip (9),
	addition(11), subtraction(12), multiplication(13), division(14),
	function(21), property(22), get(23),
	create(31),
	value(41), attributeValue(42), argumentValueByName(43), valueFromValueList(44),
	nothing(0);

	private int index;

	private Expression_Function(int index) {
		this.index = index;
	}
	
	/**
	 * The method returns the expression function name which belongs to the parameter
	 * function index.
	 * 
	 * @param arrayIndex
	 *            function index
	 * @return expression function name
	 */
	public static Expression_Function getName(int index) {
		for (Expression_Function function : Expression_Function.values())
			if (function.index == index)
				return function;
		return null;
	}	
	
}
