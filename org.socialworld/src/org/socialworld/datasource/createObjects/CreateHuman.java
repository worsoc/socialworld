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

import org.socialworld.SimpleClientActionHandler;
import org.socialworld.objects.Human;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.WriteAccessToHuman;
import org.socialworld.objects.access.HiddenHuman;

/**
 * @author Mathias Sikos
 *
 */
public class CreateHuman extends CreateAnimal {

	private static CreateHuman instance;
	private static boolean hasBeenCreatedYet = false;
	
	/**
	 * The method returns the only instance of the CreateHuman class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static CreateHuman getExlusiveInstance() {
		if (hasBeenCreatedYet == false) {
			instance = new CreateHuman();
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}

	@Override
	public Human getObject(int objectID) {
		HiddenHuman hiddenHuman = null;

		StateHuman state = new StateHuman();
		
		Human createdHuman = new Human(objectID);
		
		// the constructor "returns" the hidden human object
		new WriteAccessToHuman(createdHuman, state, hiddenHuman);

		initState(hiddenHuman);
		initObject(hiddenHuman);	

		SimpleClientActionHandler.getInstance().setHumanWrite(objectID, hiddenHuman);

		return createdHuman;
	}

}
