/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
import org.socialworld.calculation.descriptions.DescriptionBase;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.calculation.expressions.ChangeAttributes;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.core.EventType;
import org.socialworld.datasource.tablesPool.TablePoolEID;

public class EventInfluenceDescriptionPool extends DescriptionPool {

	
	private static EventInfluenceDescriptionPool instance;
	
	
	private EventInfluenceDescriptionPool () {
		
		super(EventType.MAX_EVENT_TYPE, GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY);

		this.descriptions = new EventInfluenceDescription[sizeDescriptionsArray];

		initialize();
	}
	
	public static EventInfluenceDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventInfluenceDescriptionPool();
		}
		return instance;
	}
	

	public void setDescription(int eventType,	int influenceType, EventInfluenceDescription eid) {
		int index;
			
		index = eventType * GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY + influenceType;
		
		if (index >= 0 & sizeDescriptionsArray > index) 
			 descriptions[index] = eid;
		
	}

	
	protected final void initialize() {
		initializeFromLines();
	}

	@Override
	protected void initializeWithTestData(InitializeDataModus modus) {
		
		switch (modus) {
		case lines: 
			initializeWithTestData_Lines();
		
		default:
			// do nothing
		}
	}

	protected  final DescriptionBase getNewDescription() {
		return new EventInfluenceDescription();
	}

	protected final DescriptionBase getDescription(String description) {
		return new EventInfluenceDescription(description);
	}
	
	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return new ChangeAttributes(lines4OneExpression);
	}
	
	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		return  Nothing.getInstance();
	}
	
	private void initializeWithTestData_Lines() {
		
		List<Lines> allLines;
		allLines = new ArrayList<Lines>();
		
		Lines4EventType lines4EventType;

		int influenceType;

		lines4EventType = new Lines4EventType(EventType.candidatesMoveWalk, rangeSecondIndex);
		for ( influenceType = 0; influenceType < rangeSecondIndex; influenceType++) {
			lines4EventType.add(influenceType, 0, "WENN mood >= 45 & mood < 52  DANN <MOOD><MX+N>0;1.2;23</MX+N></MOOD><COURAGE><MX+1>1;1;1</MX+N></COURAGE>");
			lines4EventType.add(influenceType, 1, "WENN mood >= 70 & mood < 85  DANN <MOOD><MX+N>0;0.9;-10</MX+N></MOOD><COURAGE><MX+1>1;1;-1</MX+N></COURAGE>");
		}
		allLines.add(lines4EventType);
		
		lines4EventType = new Lines4EventType(EventType.candidatesMoveWalk, rangeSecondIndex);
		for ( influenceType = 0; influenceType < rangeSecondIndex; influenceType++) {
			lines4EventType.add(influenceType, 0, "WENN power >= 80 & morals >= 60 DANN <POWER><MX+N>8;1;-1</MX+N></POWER><MORALS><MX+1>2;1;-1</MX+N></MORALS>");
		}
		allLines.add(lines4EventType);
		

		createExpressions(allLines);
		
	}
	


	
	private void loadFromDB() {
		TablePoolEID tableEID;

		int rowCountEID;
		int rowEID;
		
		int eventType;
		int influenceType;
		String jsonEID;
		
		
		Expression[] expressions = new Expression[1];
		
		EventInfluenceDescription eid;
		Expression exp;
		
		tableEID = new TablePoolEID();


		tableEID.select(tableEID.SELECT_ALL_COLUMNS, "", "ORDER BY eventType, influenceType");
		rowCountEID = tableEID.rowCount();
		
		if (rowCountEID > 0) {
				
			for (rowEID = 0; rowEID < rowCountEID; rowEID++) {
				eventType = tableEID.getEventType(rowEID);
				influenceType = tableEID.getInfluenceType(rowEID);
				jsonEID = tableEID.getJsonEID(rowEID);
				
				eid = new EventInfluenceDescription(jsonEID);
				setDescription(eventType, influenceType, eid);
			}
		}
	}

	

}
