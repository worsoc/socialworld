/*
* Social World
* Copyright (C) 2025  Mathias Sikos
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

import java.util.List;
import java.util.ArrayList;

import org.socialworld.calculation.Expression_Function;
import org.socialworld.datasource.parsing.JsonTerm;
import org.socialworld.datasource.parsing.JsonFunctionArg;

public class Term {
	int termNr;
	Expression_Function function;
	List<FunctionArg> functionArgs;
	
	public Term(JsonTerm jsonObject) {
		termNr = jsonObject.termNr;
		function = Expression_Function.fromName(jsonObject.function);
		functionArgs = new ArrayList<FunctionArg>();
		for (JsonFunctionArg jfa : jsonObject.functionArgs) {
			functionArgs.add(new FunctionArg(jfa));
		}
	}
	
	public String toString() {
		return "termNr:" + termNr + ",function:" + function.toString() +  ",functionArgs:" + functionArgs.toString();
		
	}
	
}
