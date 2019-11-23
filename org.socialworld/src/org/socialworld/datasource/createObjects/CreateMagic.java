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

import org.socialworld.objects.Magic;
import org.socialworld.objects.StateMagic;
import org.socialworld.objects.WriteAccessToMagic;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenMagic;
import org.socialworld.objects.concrete.spells.Lightning;

/**
 * @author Mathias Sikos
 *
 */
public class CreateMagic extends CreateSimulationObjects {

	private static CreateMagic instance;
	private static boolean hasBeenCreatedYet = false;
	
	/**
	 * The method returns the only instance of the CreateMagic class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static CreateMagic getExlusiveInstance() {
		if (hasBeenCreatedYet == false) {
			instance = new CreateMagic();
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
	}

	@Override
	public Magic getObject(int objectID) {
		WriteAccessToMagic wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenMagic hiddenMagic = null;

		StateMagic state = new StateMagic();
		
		Magic createdMagic = new Lightning(objectID);
		
		wa = new WriteAccessToMagic(createdMagic, state);
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenMagic = wa.getMeHidden(propertiesToInit);
		
		initState(hiddenMagic);
		initObject(hiddenMagic);	
		
		return createdMagic;
	}

}
