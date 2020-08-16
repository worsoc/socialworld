/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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

import org.socialworld.datasource.pool.EventPerceptionDescriptionPool;

public class EventPerceptionAssignment {

	private static EventPerceptionAssignment eventPerceptionAssignment;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventPerceptionAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventPerceptionAssignment.
	 * 
	 * @return singleton object of eventPerceptionAssignment
	 */
	public static EventPerceptionAssignment getInstance() {
		if (eventPerceptionAssignment == null) {
			eventPerceptionAssignment = new EventPerceptionAssignment();
		}
		return eventPerceptionAssignment;
	}

	/**
	 * The method gets back the description how an object perceives an event.
	 * The description depends on the event type and the object' perception type.
	 * 
	 * @param eventType
	 * @param perceptionType	 
	 * @return EventPerceptionDescription
	 */
	public EventPerceptionDescription getEventPerceptionDescription(
			int eventType,	int perceptionType) {
		EventPerceptionDescription eventPerceptionDescription;
		eventPerceptionDescription = (EventPerceptionDescription)
				EventPerceptionDescriptionPool.getInstance().getDescription(eventType, perceptionType);
		
		return eventPerceptionDescription;
	}

}
