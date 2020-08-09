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
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.core.EventType;

public class EventReactionDescriptionPool extends DescriptionPool {

	
	private static EventReactionDescriptionPool instance;
	
	private EventReactionDescription descriptions[];
	private List<List<FunctionByExpression>> expressions;
	
	private EventReactionDescriptionPool () {
		
		sizeDescriptionsArray = EventType.MAX_EVENT_TYPE * GaussPoolReactionType.CAPACITY_GPRT_ARRAY;
		descriptions = new EventReactionDescription[sizeDescriptionsArray];

		expressions = new ArrayList<List<FunctionByExpression>>(sizeDescriptionsArray);
		ArrayList<FunctionByExpression> nothing;
		nothing = new  ArrayList<FunctionByExpression>();
		for (int i = 0; i < sizeDescriptionsArray; i++) {
			expressions.add(nothing);
		}
		
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

		int index;
		
		EventReactionDescription description;
		List<FunctionByExpression> oneDescriptionExpressions;
		
		initializeWithTestData_FunctionByExpression();
		initializeFromFile();

		for (int indexEventType = 0; indexEventType < EventType.MAX_EVENT_TYPE; indexEventType++) {
		
			for (int indexReactionType = 0; indexReactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; indexReactionType++) {
				
				index = indexEventType * GaussPoolReactionType.CAPACITY_GPRT_ARRAY + indexReactionType;
				oneDescriptionExpressions = expressions.get(index);
				
				description = new EventReactionDescription();
				
				if (oneDescriptionExpressions != null) {
					
					for (int i = 0; i < oneDescriptionExpressions.size(); i++) {
						description.addFunctionCreateReaction(oneDescriptionExpressions.get(i));
					}
					
				}
				
				descriptions[index] = description;
		
			}
			
		}
			
	}
	
	private void initializeWithTestData_FunctionByExpression() {
		
		List<Strings4EventType> allLines;
		allLines = new ArrayList<Strings4EventType>();
		
		Strings4EventType lines4EventType;

		int reactionType;
		
		lines4EventType = new Strings4EventType(EventType.candidatesMoveWalk, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		for ( reactionType = 0; reactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + " > 11 & mood >= 60 & mood < 90 DANN <TYPE><Const>6</Const></TYPE><MODE><Const>67</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>155</Const></PRIORITY><INTENSITY><MX+N>8;1.5;0</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION><TARGET><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></TARGET>");
		allLines.add(lines4EventType);
		
		lines4EventType = new Strings4EventType(EventType.candidatesMoveRun, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		for ( reactionType = 0; reactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + " > 11 & mood >= 45 & mood < 60 DANN <TYPE><Const>7</Const></TYPE><MODE><Const>74</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>162</Const></PRIORITY><INTENSITY><MX+N>8;0.1;0</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION><TARGET><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></TARGET>");
		allLines.add(lines4EventType);

		lines4EventType = new Strings4EventType(EventType.candidatesSayScream, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		for ( reactionType = 0; reactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; reactionType++) {
			lines4EventType.add(reactionType, 0, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS + " > 50 & mood >= 20 & mood < 55 DANN <TYPE><Const>2</Const></TYPE><MODE><Const>21</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>160</Const></PRIORITY><INTENSITY><Const>1</Const></INTENSITY><DURATION><Const>5000</Const></DURATION><TARGET><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></TARGET>");
			lines4EventType.add(reactionType, 1, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS + " > 50 & mood >= 20 & mood < 55 DANN <TYPE><Const>3</Const></TYPE><MODE><Const>31</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>158</Const></PRIORITY><INTENSITY><MX+N>8;1.3;23</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION>");
		}
		allLines.add(lines4EventType);
		
		lines4EventType = new Strings4EventType(EventType.percipientExists, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		for ( reactionType = 0; reactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN hunger >= 45 & courage >= 35 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>41</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>100</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION><ITEM><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></ITEM>");
		allLines.add(lines4EventType);
		
		lines4EventType = new Strings4EventType(EventType.selfInventoryTake, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		for ( reactionType = 0; reactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN hunger >= 60 & courage >= 45 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>44</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>99</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION><ITEM><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_EQUIP_ITEM + "</GetEvParm></ITEM><INVENTORYPLACE><Const>3</Const></INVENTORYPLACE>");
		allLines.add(lines4EventType);
		
		lines4EventType = new Strings4EventType(EventType.selfInventorySet, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		for ( reactionType = 0; reactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN 21 " + Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISEATABLE + " == false & hunger >= 70 DANN <TYPE><Const>0</Const></TYPE><MODE><Const>3</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>2000</Now+N></MAXTIME><PRIORITY><Const>165</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION>");
		allLines.add(lines4EventType);
		
		int indexExpressions;
		int i;
		Expression startExpression;
		List<FunctionByExpression> oneDescriptionExpressions;
		List<List<String>> lines4OneDescriptionExpressions;

		for (int index = 0; index < allLines.size(); index++ ) {
			lines4EventType = allLines.get(index);
			indexExpressions = lines4EventType.getEventType().getIndex() * GaussPoolReactionType.CAPACITY_GPRT_ARRAY;
			for ( reactionType = 0; reactionType < GaussPoolReactionType.CAPACITY_GPRT_ARRAY; reactionType++) {
				lines4OneDescriptionExpressions = lines4EventType.getLines(reactionType);
				oneDescriptionExpressions = new ArrayList<FunctionByExpression>();
				for (i = 0; i < lines4OneDescriptionExpressions.size(); i++) {
					startExpression = new CreateActionExpression(lines4OneDescriptionExpressions.get(i), CreateActionExpression.MODUS_CREATE_REACTION);
					oneDescriptionExpressions.add(new FunctionByExpression(startExpression));
				}
				this.expressions.set(indexExpressions + reactionType, oneDescriptionExpressions);
			}
		}

	}
	

	private void initializeFromFile() {
		
	}

}
