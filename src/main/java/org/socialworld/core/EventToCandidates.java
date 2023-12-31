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
package org.socialworld.core;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class EventToCandidates extends Event {

	
	/**
	 * Constructor
	 */
	public EventToCandidates(int eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType,   causer,  time,  position,	  performer);
		
	}
	
	/**
	 * Constructor
	 */
	public EventToCandidates(EventType eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType.getIndex(),   causer,  time,  position,	  performer);

	}

	/**
	 * Constructor
	 */
	public EventToCandidates(int eventType, int priority,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType, priority,  causer,  time,  position,	  performer);

	}

	
	public final ValueArrayList getProperties() {
		
		return getOptionalParam().getParamList();
	}

}
