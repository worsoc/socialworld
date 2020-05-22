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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventPerceptionDescription;
import org.socialworld.calculation.expressions.GetValue;
import org.socialworld.core.EventType;

public class EventPerceptionDescriptionPool extends DescriptionPool {

	public static final int COUNT_FbE_TEST_ENTRIES = 4;		// Anzahl Testeintraege FunctionByExpression

	private static EventPerceptionDescriptionPool instance;
	
	private EventPerceptionDescription descriptions[];
	private FunctionByExpression expressions[];
	
	private EventPerceptionDescriptionPool () {
		
		sizeDescriptionsArray = EventType.MAX_EVENT_TYPE * GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY;
		descriptions = new EventPerceptionDescription[sizeDescriptionsArray];

		expressions = new FunctionByExpression[COUNT_FbE_TEST_ENTRIES];
		
		initialize();
	}
	
	public static EventPerceptionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventPerceptionDescriptionPool();
		}
		return instance;
	}

	public EventPerceptionDescription getDescription(int eventType,	int perceptionType) {
		int index;

		EventPerceptionDescription description = null;
		
		index = eventType *  GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY + perceptionType ;
		
		if (index >= 0 & sizeDescriptionsArray > index) 	
			description = descriptions[index];
		else
			// create a dummy description with an expression that returns the invalid "nothing" value
			description = new EventPerceptionDescription();
			
		return description;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

		Expression startExpression;
		List<FunctionByExpression> result;
		result = new ArrayList<FunctionByExpression>();
		
		startExpression = new GetValue(GetValue.getValue(Value.VALUE_BY_NAME_ACTION_DIRECTION)  ,  Value.VALUE_BY_NAME_EVENT_DIRECTION );
		result.add( new FunctionByExpression(startExpression) );
		
	}

}
