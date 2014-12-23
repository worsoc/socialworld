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

import org.socialworld.datasource.EventReactionDescriptionPool;

public class EventReactionAssignment {
	private static EventReactionAssignment eventReactionAssignment;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventReactionAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventReactionAssignment.
	 * 
	 * @return singleton object of eventReactionAssignment
	 */
	public static EventReactionAssignment getInstance() {
		if (eventReactionAssignment == null) {
			eventReactionAssignment = new EventReactionAssignment();
		}
		return eventReactionAssignment;
	}

	/**
	 * The method gets back the description how an object reacts to the event.
	 * The description depends on the event type and the object' reaction type.
	 * 
	 * @param eventType
	 * @param reactionType	 
	 * @return EventReactionDescription
	 */
	public EventReactionDescription getEventReactionDescription(
			int eventType,	int reactionType) {
		EventReactionDescription eventReactionDescription;
		eventReactionDescription = 
			EventReactionDescriptionPool.getInstance().getDescription(eventType, reactionType);
		
		return eventReactionDescription;
	}

}
