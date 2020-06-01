/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
import org.socialworld.datasource.pool.FunctionPool;

public class FunctionDynamic extends FunctionBase {

	// TODO ? use Functions instead of FunctionsPool ?  is class Functions obsolete ?
	private static FunctionPool functions = FunctionPool.getInstance();
	
	private static FunctionDynamic instance;
 
	private FunctionDynamic() {
		
	}
	
	public static FunctionDynamic getInstance() {
		if (instance == null) {
			instance = new FunctionDynamic();
		}
		return instance;
	}

	@Override
	public  Value calculate(ValueArrayList arguments) {

		if (arguments.size() >= 2) {
			int func_id = (int) arguments.get(0).getValue();
			FunctionBase function = functions.getFunction(func_id);
			
			if (function != null) {
				return function.calculate((ValueArrayList) arguments.get(1).getValue());
			}
			else {
				return calculation.nothing;
			}
		}
		else {
			return calculation.nothing;
		}
		
	}

}
