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
package org.socialworld.datasource.pool;

import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.core.EventType;

public class EventReactionDescriptionPool extends DescriptionPool {

	private static EventReactionDescriptionPool instance;
	
	private static EventReactionDescription descriptions[];
	
	private EventReactionDescriptionPool () {
		
		sizeDescriptionsArray = EventType.MAX_EVENT_TYPE * GaussPoolReactionType.CAPACITY_GPRT_ARRAY;
		descriptions = new EventReactionDescription[sizeDescriptionsArray];

		initialize();
	}
	
	public static EventReactionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventReactionDescriptionPool();
		}
		return instance;
	}
	
	public EventReactionDescription getDescription(int eventType,	int reactionType) {
		int index;

		EventReactionDescription description = null;
		
		index = eventType *  GaussPoolReactionType.CAPACITY_GPRT_ARRAY + reactionType ;
		
		if (index >= 0 & sizeDescriptionsArray > index) 	
			description = descriptions[index];
		else
			// create a dummy description with an expression that returns the invalid "nothing" value
			description = new EventReactionDescription();
			
		return description;
	}

	protected void initialize() {
		// initialize with  dummy descriptions with an expression that returns the invalid "nothing" value
		for (int index = 0; index < sizeDescriptionsArray; index++)
			descriptions[index] = new EventReactionDescription();
		
		initializeFromFile();
	}
	
	private void initializeFromFile() {
		
	}

}
