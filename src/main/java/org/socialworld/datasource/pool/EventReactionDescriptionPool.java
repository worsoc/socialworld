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
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.DescriptionBase;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.core.EventType;


import org.socialworld.datasource.parsing.JsonEventReactionDescription;

public class EventReactionDescriptionPool extends DescriptionPool {

	
	private static EventReactionDescriptionPool instance;
	
	
	private EventReactionDescriptionPool () {
		
		super(EventType.MAX_EVENT_TYPE, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		
		this.descriptions = new EventReactionDescription[sizeDescriptionsArray];
		
		initialize();
	}
	
	public static EventReactionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventReactionDescriptionPool();
		}
		return instance;
	}
	
	protected final void initialize() {
		initializeFromJson();
	}

	@Override
	protected void initializeWithTestData(InitializeDataModus modus) {
		
		switch (modus) {
		case lines: 
			initializeWithTestData_Lines();
		case json: 
			initializeWithTestData_Json();
		
		default:
			// do nothing
		}
	}

	protected  final DescriptionBase getNewDescription() {
		return new EventReactionDescription();
	}

	protected final DescriptionBase getDescription(String description) {
		return new EventReactionDescription(getGsonInstance(), description);
	}

	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return new CreateActionExpression(lines4OneExpression);
	}

	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		return  Nothing.getInstance();
	}
	
	private void initializeWithTestData_Lines() {
		
		List<Lines> allLines;
		allLines = new ArrayList<Lines>();
		
		Lines4EventType lines4EventType;

		int reactionType;
		
		lines4EventType = new Lines4EventType(EventType.candidatesMoveWalk, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + " > 11 &mood >= 0 & mood < 90 DANN <TYPE><Const>6</Const></TYPE><MODE><Const>67</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>155</Const></PRIORITY><INTENSITY><MX+N>8;1.5;0</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION><TARGET><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></TARGET>");
		allLines.add(lines4EventType);
		
		lines4EventType = new Lines4EventType(EventType.candidatesMoveRun, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + " > 11 &mood >= 0 & mood < 99 DANN <TYPE><Const>7</Const></TYPE><MODE><Const>74</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>162</Const></PRIORITY><INTENSITY><MX+N>8;0.1;0</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION><TARGET><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></TARGET>");
		allLines.add(lines4EventType);
/*
		lines4EventType = new Lines4EventType(EventType.candidatesSayScream, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
			lines4EventType.add(reactionType, 0, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS + " > 50 & mood >= 20 & mood < 55 DANN <TYPE><Const>2</Const></TYPE><MODE><Const>21</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>160</Const></PRIORITY><INTENSITY><Const>1</Const></INTENSITY><DURATION><Const>5000</Const></DURATION><TARGET><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></TARGET>");
			lines4EventType.add(reactionType, 1, "WENN 3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS + " > 50 & mood >= 20 & mood < 55 DANN <TYPE><Const>3</Const></TYPE><MODE><Const>31</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>158</Const></PRIORITY><INTENSITY><MX+N>8;1.3;23</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION>");
		}
		allLines.add(lines4EventType);
*/
/*
		lines4EventType = new Lines4EventType(EventType.percipientExists, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN hunger >= 45 & courage >= 35 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>41</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>100</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION><ITEM><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></ITEM>");
		allLines.add(lines4EventType);

		
		lines4EventType = new Lines4EventType(EventType.selfInventoryTake, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN hunger >= 60 & courage >= 45 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>44</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>99</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION><ITEM><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_EQUIP_ITEM + "</GetEvParm></ITEM><INVENTORYPLACE><Const>3</Const></INVENTORYPLACE>");
		allLines.add(lines4EventType);
*/		
		lines4EventType = new Lines4EventType(EventType.selfInventorySet, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) 
			lines4EventType.add(reactionType, 0, "WENN 21 " + Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISEATABLE + " == false & hunger >= 70 DANN <TYPE><Const>0</Const></TYPE><MODE><Const>3</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>2000</Now+N></MAXTIME><PRIORITY><Const>165</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION>");
		allLines.add(lines4EventType);
		
		
		createExpressions(allLines);

	}
	
	private void initializeWithTestData_Json() {
		
		List<Jsons> allJsons;
		allJsons = new ArrayList<Jsons>();
		
		Jsons4EventType jsons4EventType;

		int reactionType;
		
		String json;
		JsonEventReactionDescription jsonObject;
		jsons4EventType = new Jsons4EventType(EventType.candidatesMoveWalk, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
//			json = "{\"eventType\":\"candidatesMoveWalk\",\"reactionTypeType\":" + reactionType + "," +
//					"\"entrys\":[" +
//					"\"WENN 3 " + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + " > 11 &mood >= 0 & mood < 90 DANN <TYPE><Const>6</Const></TYPE><MODE><Const>67</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>10000</Now+N></MAXTIME><PRIORITY><Const>155</Const></PRIORITY><INTENSITY><MX+N>8;1.5;0</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION><TARGET><GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm></TARGET>\"" +
//					"]}";
			json = "{" + 
					"\"eventType\":\"candidatesMoveWalk\"," +
					"\"reactionTypeType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"valueName\":\"" + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + "\"," +
						"\"valueType\":\"floatingpoint\"," +
						"\"actionType\":\"useWeapon\"," +
						"\"actionMode\":\"weaponClub\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>10000</Now+N>\"," +
						"\"priority\":\"<Const>155</Const>\"," +
						"\"intensity\":\"<MX+N>8;1.5;0</MX+N>\"," +
						"\"duration\":\"<Const>2000</Const>\"," +
						"\"target\":\"<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>\"" +
						"}" +
					"]" +
					"}";
			
			jsonObject = EventReactionDescriptionPool.getGsonInstance().fromJson(json, JsonEventReactionDescription.class);

		}
			
		createDescriptionsAndExpressions(allJsons);

	}


	
	private void initializeFromFile() {
		
	}

}
