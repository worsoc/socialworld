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
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.core.EventType;

public class EventReactionDescriptionPool extends DescriptionPool {

	public static final int COUNT_FbE_TEST_ENTRIES = 2;		// Anzahl Testeintraege FunctionByExpression
	
	private static EventReactionDescriptionPool instance;
	
	private EventReactionDescription descriptions[];
	private FunctionByExpression expressions[];
	
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
		
		initializeWithTestData_FunctionByExpression();
		initializeFromFile();
	}
	
	
	private void initializeWithTestData_FunctionByExpression() {
		
		
		int 		expressionsCount = COUNT_FbE_TEST_ENTRIES;
		List<List<String>> expressions = new ArrayList<List<String>>(expressionsCount);
		List<String> lines;
		Expression startExpression = Nothing.getInstance();

		lines = new ArrayList<String>(1);
		lines.add("WENN mood > 10 DANN <ACTIONTYPE><Const>1</Const></ACTIONTYPE><ACTIONMODE><Const>14</Const></ACTIONMODE><MINTIME><Now+N>10000</Now+N></MINTIME><MAXTIME><Now+N>100000</Now+N></MAXTIME><PRIORITY><Const>150</Const></PRIORITY><INTENSITY><MX+N>5;1.5;23</MX+N></INTENSITY><DURATION><Const>10000</Const></DURATION>");
		expressions.add(lines);
		
		lines = new ArrayList<String>(1);
		lines.add("WENN tiredness == 41 DANN <ACTIONTYPE><Const>0</Const></ACTIONTYPE><ACTIONMODE><Const>2</Const></ACTIONMODE><MINTIME><Now+N>10000</Now+N></MINTIME><MAXTIME><Now+N>100000</Now+N></MAXTIME><PRIORITY><Const>50</Const></PRIORITY><INTENSITY><MX+N>4;1.5;0</MX+N></INTENSITY><DURATION><Const>610000</Const></DURATION>");
		expressions.add(lines);


		for (int i = 0; i <  expressionsCount; i++) {
			
			startExpression = new CreateActionExpression(expressions.get(i), CreateActionExpression.MODUS_CREATE_REACTION);
			this.expressions[i] = new FunctionByExpression(startExpression);

		}
		
	}

	private void initializeFromFile() {
		
	}

}
