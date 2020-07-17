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

import org.socialworld.actions.ActionMode;
import org.socialworld.datasource.pool.Action2PerformerDescriptionPool;

public class Action2PerformerAssignment {

	
	private static Action2PerformerAssignment instance;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private Action2PerformerAssignment() {
	}

	/**
	 * The method returns back the only instance of the Action2PerformerAssignment.
	 * 
	 * @return singleton object of Action2PerformerAssignment
	 */
	public static Action2PerformerAssignment getInstance() {
		if (instance == null) {
			instance = new Action2PerformerAssignment();
		}
		return instance;
	}

	/**
	 * The method returns the description how an performer creates its properties (used as event params)
	 *  from action and actor properties.
	 * 
	 * @param ActionMode	 
	 * @return Action2PerformerDescription
	 */
	public Action2PerformerDescription getAction2PerformerDescription(ActionMode actionMode) {
		Action2PerformerDescription action2PerformerDescription;
		action2PerformerDescription = 
				Action2PerformerDescriptionPool.getInstance().getDescription(actionMode);
		
		return action2PerformerDescription;
	}

}
