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
			json = "{" + 
					"\"eventType\":\"candidatesMoveWalk\"," +
					"\"reactionType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"conditions\":\"3 " + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + " > 1 & mood >= 0 & mood < 90\"," +
						"\"actionType\":\"<Const>6</Const>\"," +
						"\"actionMode\":\"<Const>67</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>10000</Now+N>\"," +
						"\"priority\":\"<Const>55</Const>\"," +
						"\"intensity\":\"<MX+N>8;1.5;0</MX+N>\"," +
						"\"duration\":\"<Const>2000</Const>\"," +
						"\"target\":\"<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>\"" +
						"}" +
					"]" +
					"}";
			jsons4EventType.set(reactionType, json);
		}
//		allJsons.add(jsons4EventType);
		
		jsons4EventType = new Jsons4EventType(EventType.candidatesMoveRun, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
			json = "{" + 
					"\"eventType\":\"candidatesMoveRun\"," +
					"\"reactionType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"conditions\":\"3 " + Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY + " > 1 & mood >= 0 & mood < 90\"," +
						"\"actionType\":\"<Const>7</Const>\"," +
						"\"actionMode\":\"<Const>74</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>10000</Now+N>\"," +
						"\"priority\":\"<Const>62</Const>\"," +
						"\"intensity\":\"<MX+N>8;0.1;0</MX+N>\"," +
						"\"duration\":\"<Const>2000</Const>\"," +
						"\"target\":\"<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>\"" +
						"}" +
					"]" +
					"}";
			jsons4EventType.set(reactionType, json);
		}
//		allJsons.add(jsons4EventType);

		jsons4EventType = new Jsons4EventType(EventType.candidatesSayScream, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
			json = "{" + 
					"\"eventType\":\"candidatesSayScream\"," +
					"\"reactionType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"conditions\":\"3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS + " > 50 & mood >= 20 & mood < 55\"," +
						"\"actionType\":\"<Const>2</Const>\"," +
						"\"actionMode\":\"<Const>21</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>10000</Now+N>\"," +
						"\"priority\":\"<Const>60</Const>\"," +
						"\"intensity\":\"<Const>1</Const>\"," +
						"\"duration\":\"<Const>5000</Const>\"," +
						"\"target\":\"<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>\"" +
						"}," +
						"{" +
						"\"conditions\":\"3 " + Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS + " > 30 & mood >= 10 & mood < 65\"," +
						"\"actionType\":\"<Const>3</Const>\"," +
						"\"actionMode\":\"<Const>31</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>10000</Now+N>\"," +
						"\"priority\":\"<Const>58</Const>\"," +
						"\"intensity\":\"<MX+N>8;1.3;23</MX+N>\"," +
						"\"duration\":\"<Const>2000</Const>\"" +
						"}" +
					"]" +
					"}";
			jsons4EventType.set(reactionType, json);
		}
//		allJsons.add(jsons4EventType);
		
		jsons4EventType = new Jsons4EventType(EventType.percipientExistsDistance1000, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
			json = "{" + 
					"\"eventType\":\"percipientExistsDistance1000\"," +
					"\"reactionType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"conditions\":\"curiosity > 50 & hunger >= 35 & courage >= 35 & power >= 35\"," +
						"\"actionType\":\"<Const>4</Const>\"," +
						"\"actionMode\":\"<Const>41</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>5000</Now+N>\"," +
						"\"priority\":\"<Const>100</Const>\"," +
						"\"intensity\":\"<MX+N>7;1;0</MX+N>\"," +
						"\"duration\":\"<Const>1000</Const>\"," +
						"\"item\":\"<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>\"" +
						"}" +
					"]" +
					"}";
			jsons4EventType.set(reactionType, json);
		}
//		allJsons.add(jsons4EventType);

		jsons4EventType = new Jsons4EventType(EventType.percipientExistsDistance5000, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
			json = "{" + 
					"\"eventType\":\"percipientExistsDistance5000\"," +
					"\"reactionType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"conditions\":\"function:distance(event,simObj) < 2000 & morals < 35 & power >= 65\"," +
						"\"actionType\":\"<Const>6</Const>\"," +
						"\"actionMode\":\"<Const>67</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>5000</Now+N>\"," +
						"\"priority\":\"<Const>105</Const>\"," +
						"\"intensity\":\"<MX+N>8;1.5;0</MX+N>\"," +
						"\"duration\":\"<Const>1000</Const>\"," +
						"\"target\":\"<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_CAUSER + "</GetEvParm>\"" +
						"}" +
					"]" +
					"}";
			jsons4EventType.set(reactionType, json);
		}
		allJsons.add(jsons4EventType);
		
		jsons4EventType = new Jsons4EventType(EventType.selfInventoryTake, rangeSecondIndex);
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
			json = "{" + 
					"\"eventType\":\"selfInventoryTake\"," +
					"\"reactionType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"conditions\":\"materialism >= 50 & courage >= 25\"," +
						"\"actionType\":\"<Const>4</Const>\"," +
						"\"actionMode\":\"<Const>44</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>50000</Now+N>\"," +
						"\"priority\":\"<Const>188</Const>\"," +
						"\"intensity\":\"<MX+N>7;1;0</MX+N>\"," +
						"\"duration\":\"<Const>1000</Const>\"," +
						"\"item\":\"<GetEvParm>" + Value.VALUE_BY_NAME_EVENT_EQUIP_ITEM + "</GetEvParm>\"," +
						"\"inventoryPlace\":\"<Const>3</Const>\"" +
						"}" +
					"]" +
					"}";
			jsons4EventType.set(reactionType, json);
		}
//		allJsons.add(jsons4EventType);
		
		jsons4EventType = new Jsons4EventType(EventType.selfInventorySet, rangeSecondIndex);
//		"\"conditions\":\"21 " + Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISEATABLE + " == true & hunger >= 70 \"," +
		for ( reactionType = 0; reactionType < rangeSecondIndex; reactionType++) {
			json = "{" + 
					"\"eventType\":\"selfInventorySet\"," +
					"\"reactionType\":" + reactionType + "," +
					"\"entrys\":[" +
						"{" +
						"\"conditions\":\"hunger > 70\"," +
						"\"actionType\":\"<Const>0</Const>\"," +
						"\"actionMode\":\"<Const>3</Const>\"," +
						"\"minTime\":\"<Now+N>1000</Now+N>\"," +
						"\"maxTime\":\"<Now+N>20000</Now+N>\"," +
						"\"priority\":\"<Const>165</Const>\"," +
						"\"intensity\":\"<MX+N>7;1;0</MX+N>\"," +
						"\"duration\":\"<Const>1000</Const>\"" +
						"}" +
					"]" +
					"}";
			jsons4EventType.set(reactionType, json);
		}
//		allJsons.add(jsons4EventType);
		
		createDescriptionsAndExpressions(allJsons);
//		jsonObject = EventReactionDescriptionPool.getGsonInstance().fromJson(json, JsonEventReactionDescription.class);

	}


	
	private void initializeFromFile() {
		
	}

}
