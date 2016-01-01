/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.datasource.createObjects;

import org.socialworld.objects.Item;
import org.socialworld.objects.StateItem;
import org.socialworld.objects.WriteAccessToItem;
import org.socialworld.objects.access.HiddenItem;

/**
 * @author Mathias Sikos
 *
 */
public class CreateItem extends CreateSimulationObjects {

	private static CreateItem instance;
	private static boolean hasBeenCreatedYet = false;
	
	/**
	 * The method returns the only instance of the CreateItem class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static CreateItem getExlusiveInstance() {
		if (hasBeenCreatedYet == false) {
			instance = new CreateItem();
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}
	
	@Override
	public Item getObject(int objectID) {
		HiddenItem hiddenItem = null;

		StateItem state = new StateItem();
		
		Item createdItem = new Item(objectID);
		
		// the constructor "returns" the hidden item object
		new WriteAccessToItem(createdItem, state, hiddenItem);
		

		initState(hiddenItem);
		initObject(hiddenItem);	
		
		return createdItem;
	}

}
