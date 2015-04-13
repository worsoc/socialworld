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

import org.socialworld.datasource.pool.EventInfluenceDescriptionPool;


public class EventInfluenceAssignment {

	private static EventInfluenceAssignment eventInfluenceAssignment;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventInfluenceAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventInfluenceAssignment.
	 * 
	 * @return singleton object of EventInfluenceAssignment
	 */
	public static EventInfluenceAssignment getInstance() {
		if (eventInfluenceAssignment == null) {
			eventInfluenceAssignment = new EventInfluenceAssignment();
		}
		return eventInfluenceAssignment;
	}

	/**
	 * The method gets back the description how the event effects to the object.
	 * The description depends on the event type and the type of the influence to the object
	 * 
	 * @param eventType
	 * @param influenceType	 
	 * @return EventInfluenceDescription
	 */
	public EventInfluenceDescription getEventInfluenceDescription(
			int eventType,	int influenceType) {
		EventInfluenceDescription eventInfluenceDescription;
		eventInfluenceDescription = 
			EventInfluenceDescriptionPool.getInstance().getDescription(eventType, influenceType);
		return eventInfluenceDescription;
	}
}
