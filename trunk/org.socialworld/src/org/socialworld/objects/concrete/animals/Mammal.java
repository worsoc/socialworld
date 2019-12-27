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
package org.socialworld.objects.concrete.animals;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.Animal;
import org.socialworld.objects.State;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class Mammal extends Animal {

 
    
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE    /////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
    
	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
		
		System.out.println("Mammal.createAddOnStates");
		
		return result;
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}

  
}
