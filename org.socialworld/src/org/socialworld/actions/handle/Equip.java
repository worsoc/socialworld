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
package org.socialworld.actions.handle;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

public class Equip extends ActionPerformer {

	public Equip(ActionEquip action) {
		super(action);
	}
	
	@Override
	protected void perform() {

		if (!isEvaluated()) {
			setEvaluated();
		}

	}

	@Override
	protected void choosePropertiesFromPropertyList(ValueArrayList properties) {


		Value property;
    	
    	property = properties.getValue(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES);
    	if (property.isValid()) {
    		addProperty(property);
    	}
 
      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_INTENSITY);
    	if (property.isValid()) {
    		addProperty(property);
    	}

	}

}
