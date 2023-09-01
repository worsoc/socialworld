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
import org.socialworld.calculation.descriptions.EventPerceptionDescription;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;
import org.socialworld.core.EventType;

public class EventPerceptionDescriptionPool extends DescriptionPool {


	private static EventPerceptionDescriptionPool instance;
	
	
	
	private EventPerceptionDescriptionPool () {

		super(EventType.MAX_EVENT_TYPE, GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY);

		this.descriptions = new EventPerceptionDescription[sizeDescriptionsArray];

		
		initialize();
		
	}
	
	public static EventPerceptionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventPerceptionDescriptionPool();
		}
		return instance;
	}

/*	
	public void setDescription(int eventType,	int perceptionType, EventPerceptionDescription epd) {
		int index;
			
		index = eventType * GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY + perceptionType;
		
		if (index >= 0 & sizeDescriptionsArray > index) 
			 _descriptions[index] = epd;
		
	}
*/

	protected  final DescriptionBase getNewDescription() {
		return new EventPerceptionDescription();
	}

	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return new CreateKnowledgeElementExpression(lines4OneExpression.get(0));
	}

	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		int[] ids = new int[ids4OneExpression.size()];
		int index = 0;
		for (int id : ids4OneExpression) {
			ids[index] = id;
			index++;
		}
		return  new CreateKnowledgeElementExpression(ids);
	}
	
/*	
	String gegenProbezuLineID_8607 =		"KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);KProp(0,inventory_shirt.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour).GETFctVal(getIndex)";
	exp = new CreateKnowledgeElementExpression(gegenProbezuLineID_8607);
*/
	
	protected void initializeWithTestData_FunctionByExpression() {

		List<Lines> allLines;
		allLines = new ArrayList<Lines>();
		
		Lines4EventType lines4EventType;
		int perceptionType;

//		String gegenProbezuLineID_8607_with_getIndex =		"KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);KProp(0,inventory_shirt.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour).GETFctVal(getIndex)";
		String gegenProbezuLineID_8607 =		"KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);KProp(0,inventory_shirt.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour)";

		lines4EventType = new Lines4EventType(EventType.percipientExists, rangeSecondIndex);
		for ( perceptionType = 0; perceptionType < rangeSecondIndex; perceptionType++) {
			lines4EventType.add(perceptionType, 0, gegenProbezuLineID_8607);
		}
		allLines.add(lines4EventType);

		
		
		createExpressions(allLines);
		
	}
	
	
	protected void _20230729_initializeWithTestData_FunctionByExpression() {

		List<DescriptionIDs> allIDs;
		allIDs = new ArrayList<DescriptionIDs>();
		
		DescriptionIDs4EventType ids4EventType;
		int perceptionType;
	
		// TODO for other event types
		ids4EventType = new DescriptionIDs4EventType(EventType.percipientExists, rangeSecondIndex);
		
		for ( perceptionType = 0; perceptionType < rangeSecondIndex; perceptionType++) {
			ids4EventType.add(perceptionType, 0, 8607);
		}
		
		allIDs.add(ids4EventType);
		
		createExpressionsForIDs(allIDs);
		
	}
	
	protected void _20230724_initializeWithTestData_FunctionByExpression() {

		List<Lines> allLines;
		allLines = new ArrayList<Lines>();
		
		Lines4EventType lines4EventType;
		int perceptionType;

		lines4EventType = new Lines4EventType(EventType.candidatesMoveWalk, rangeSecondIndex);
		for ( perceptionType = 0; perceptionType < rangeSecondIndex; perceptionType++) {
			lines4EventType.add(perceptionType, 0, "KSbj:GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_CAUSER + ");" +
													"KSrcT:1," +
													"KSrc:GETVal(" + Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF + ");" +
													"KVal:GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_CAUSER + ")#IsElem(1).GETProp(simobj_position).GETProp(position_vector)");
		}
		allLines.add(lines4EventType);

		
/*
		// example for developing
		descriptions[1] = 
		"KSbj:GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ");" +
		"KSrcT:1," +
		"KSrc:GETVal(" + Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF + ");" +
		"KProp(0):GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ").GETProp(stateInventory).GETProp(inventory_shirt).GETProp(stateAppearance).GETFctVal(" + StateAppearance.METHODNAME_GET_MAIN_COLOR + ")," +
		"KProp(0):GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ").GETProp(stateBody).GETFctVal(getFaceColour)," +
		"KProp(0):GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ").GETProp(stateBody).GETFctVal(getHairColour);" +
		"KProp(1):GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ").GETProp(stateInventory).GETProp(inventory_shirt).GETProp(stateComposition).GETFctVal(" + StateComposition.METHODNAME_GET_MAIN_MATERIAL + ")," +
		"KProp(1):GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ").GETProp(stateInventory).GETProp(inventory_trousers).GETProp(stateComposition).GETFctVal(" + StateComposition.METHODNAME_GET_MAIN_MATERIAL + ")," +
		"KProp(1):GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ").GETProp(stateInventory).GETProp(inventory_shoes).GETProp(stateComposition).GETFctVal(" + StateComposition.METHODNAME_GET_MAIN_MATERIAL + ")," +
		"KProp(1):GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_TARGET + ").GETProp(stateInventory).GETProp(inventory_cap).GETProp(stateComposition).GETFctVal(" + StateComposition.METHODNAME_GET_MAIN_MATERIAL + ")" ;
*/
		
		createExpressions(allLines);
		
	}
	


}
