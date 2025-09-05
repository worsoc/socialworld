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
package org.socialworld.calculation.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.functions.FunctionByExpression;

import com.google.gson.Gson;

public abstract class DescriptionBase {

	private static Gson gson = null;
	protected static Gson getGsonInstance() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	private List<FunctionByExpression> functions;
	
	public DescriptionBase() {
		
		functions = new  ArrayList<FunctionByExpression>();
		
	}
	
	public abstract void setFunctions();
	
	public List<FunctionByExpression> getFunctions() {
		return functions;
	}

	
	public void addFunction(FunctionByExpression function) {

		functions.add(function);

	}
	
	public FunctionByExpression getFunction(int index) {
		
		return functions.get(index);
		
	}
	
	public int countFunctions() {
		
		return functions.size();
		
	}
	
	public boolean isEmpty() {
		return (functions.size() == 0);
	}

}
