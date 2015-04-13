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
package org.socialworld.calculation.descriptions;

import org.socialworld.datasource.pool.State2ActionDescriptionPool;

public class State2ActionAssignment {
	private static State2ActionAssignment instance;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private State2ActionAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventReactionAssignment.
	 * 
	 * @return singleton object of eventReactionAssignment
	 */
	public static State2ActionAssignment getInstance() {
		if (instance == null) {
			instance = new State2ActionAssignment();
		}
		return instance;
	}

	/**
	 * The method gets back the description how an object reacts to the event.
	 * The description depends on the event type and the object' reaction type.
	 * 
	 * @param eventType
	 * @param reactionType	 
	 * @return EventReactionDescription
	 */
	public State2ActionDescription getState2ActionDescription(int state2ActionType) {
		State2ActionDescription state2ActionDescription;
		state2ActionDescription = 
			State2ActionDescriptionPool.getInstance().getDescription(state2ActionType);
		
		return state2ActionDescription;
	}

}
