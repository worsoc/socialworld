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
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.core.EventType;
import org.socialworld.datasource.tablesPool.TablePoolEPD;

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

	public void setDescription(int eventType, int perceptionType, EventPerceptionDescription epd) {
		int index;
			
		// O(1)-Index-Berechnung basierend auf dem Wahrnehmungstyp-Array-Limit
		index = eventType * GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY + perceptionType;
		
		// Unbestechlicher Index-Schutz (Verwendet ein bitweises &, falls du das '&' aus der Vorlage behalten willst)
		if (index >= 0 & sizeDescriptionsArray > index) {
			descriptions[index] = epd;
		}
	}


	protected final void initialize() {
		//initializeFromLines();
		initializeFromJson();
	}

	@Override
	protected void initializeWithTestData(InitializeDataModus modus) {
		switch (modus) {
		case lines: 
			initializeWithTestData_Lines();
			break;
		case json: 
			initializeWithTestData_Json(); // Ruft unsere neue Benchmark-Lade-Methode
			break;
		default:
			// do nothing
		}
	}
	
	
	private void initializeWithTestData_Json() {
	    TablePoolEPD tableEPD = new TablePoolEPD();
	    
	    // 1. Messung vorbereiten (Exakt wie bei den Influences)
	    Runtime runtime = Runtime.getRuntime();
	    runtime.gc(); 
	    long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
	    long startTime = System.currentTimeMillis();
	    int totalRulesLoaded = 0;

	    System.out.println("--- Starting PERCEPTION JSON-DB Loading & Parsing ---");

	    // Datenbank-Abfrage: Sortiert nach Event-Typ und Wahrnehmungs-Typ für das O(1)-Mapping
	    tableEPD.select(tableEPD.SELECT_ALL_COLUMNS, "", "ORDER BY eventType, perceptionType");
	    int rowCountEPD = tableEPD.rowCount();
	    
	    if (rowCountEPD > 0) {
	        for (int rowEPD = 0; rowEPD < rowCountEPD; rowEPD++) {
	            
	            int eventType = tableEPD.getEventType(rowEPD);
	            int perceptionType = tableEPD.getPerceptionType(rowEPD);
	            String jsonEPD = tableEPD.getJsonEPD(rowEPD); // Holt den JSON-String aus der DB

	            if (jsonEPD != null && !jsonEPD.isEmpty()) {
	                // Erstellung der EPD basierend auf dem GSON-Instanz-Loader der Basisklasse
	                EventPerceptionDescription epd = new EventPerceptionDescription(getGsonInstance(), jsonEPD);
	                epd.setFunctions();
	                
	                // In den Pool speichern (Nutzt die interne Index-Formel aus dem DescriptionPool)
	                setDescription(eventType, perceptionType, epd);
	                totalRulesLoaded++;
	            }

	            // Fortschritts-Log alle 1000 Regeln
	            if (totalRulesLoaded % 1000 == 0) {
	                System.out.println(String.format("[DB-Perception-Load] %d / %d Regeln verarbeitet...", totalRulesLoaded, rowCountEPD));
	            }
	        }
	    }

	    // 2. Finales Benchmarking
	    long duration = System.currentTimeMillis() - startTime;
	    runtime.gc(); 
	    long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
	    double memoryUsedMB = (memoryAfter - memoryBefore) / (1024.0 * 1024.0);

	    System.out.println("--- PERCEPTION JSON Loading Finished ---");
	    System.out.println(String.format("Perception Rules Loaded: %d | Time: %d ms", totalRulesLoaded, duration));
	    System.out.println(String.format("Memory Used: %.2f MB", memoryUsedMB));
	}
	
	protected  final DescriptionBase getNewDescription() {
		return new EventPerceptionDescription();
	}

	protected final DescriptionBase getDescription(String description) {
		return new EventPerceptionDescription(getGsonInstance(), description);
	}

	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return new CreateKnowledgeElementExpression(lines4OneExpression.get(0));
	}

	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		return Nothing.getInstance();
		// TODO imlement abstract method EventPerceptionDescriptionPool.getStartExpressionForIDs(List<Integer> ids4OneExpression)
	/*	
		int[] ids = new int[ids4OneExpression.size()];
		int index = 0;
		for (int id : ids4OneExpression) {
			ids[index] = id;
			index++;
		}
		return  new CreateKnowledgeElementExpression(ids);
	*/
	}
	
/*	
	String gegenProbezuLineID_8607 =		"KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);KProp(0,inventory_shirt.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour).GETFctVal(getIndex)";
	exp = new CreateKnowledgeElementExpression(gegenProbezuLineID_8607);
*/
	
	private void initializeWithTestData_Lines() {

		List<Lines> allLines;
		allLines = new ArrayList<Lines>();
		
		Lines4EventType lines4EventType;
		int perceptionType;

		List<String> lines4OneKE = new ArrayList<String>();
		
		lines4OneKE.add("KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);" +
				"KProp(0,inventory_shirt.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour)&" +
				"KProp(0,inventory_trousers.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_trousers)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour)&" +
				"KProp(0,inventory_cap.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour);" +
				"KProp(1,inventory_shirt.SOGN_184549375.mainMaterial):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateComposition).GETFctVal(getMainMaterial)&" +
				"KProp(1,inventory_cap.SOGN_184549375.mainMaterial):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateComposition).GETFctVal(getMainMaterial)");

		lines4OneKE.add("KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);" +
				"KProp(0,inventory_cap.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour);" +
				"KProp(1,inventory_cap.SOGN_184549375.mainMaterial):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateComposition).GETFctVal(getMainMaterial)");
		
		lines4OneKE.add("KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);" +
				"KProp(1,inventory_shirt.SOGN_184549375.mainMaterial):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateComposition).GETFctVal(getMainMaterial)&" +
				"KProp(1,inventory_cap.SOGN_184549375.mainMaterial):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateComposition).GETFctVal(getMainMaterial)");

		lines4OneKE.add("KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);" +
				"KProp(0,inventory_cap.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour)&" +
				"KProp(0,inventory_shirt.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_shirt)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour)") ;
	
	
		lines4OneKE.add("KSrcT:1&KSrc:GETVal(myself);KSbj:GETVal(event_params).GETVal(event_causer);" +
				"KProp(0,inventory_cap.SOGN_184549375.mainColour):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour);" +
				"KProp(0,inventory_cap.SOGN_184549375.mainColour_2):GETVal(event_params).GETVal(event_causer)#IsElem(50331647).GETProp(stateInventory).GETProp(stateInventory_inventory).GETProp(inventory_cap)#IsElem(184549375).GETProp(stateAppearance).GETFctVal(getMainColour)") ;

		
		
		int index;
		int sizeListOfLines = lines4OneKE.size();
		
		lines4EventType = new Lines4EventType(EventType.percipientExistsDistance5000, rangeSecondIndex);
		for ( perceptionType = 0; perceptionType < rangeSecondIndex; perceptionType++) {
			index = perceptionType % sizeListOfLines;
			lines4EventType.add(perceptionType, lines4OneKE.get(index));
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
		ids4EventType = new DescriptionIDs4EventType(EventType.percipientExistsDistance5000, rangeSecondIndex);
		
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


/*
-- =========================================================================
-- SYSTEM-REGENERATION: 100 GAUSS-SLOTS FÜR EVENT 508 (swpool_epd)
-- =========================================================================

-- Multi-Insert Script für swpool_epd (Event 508, perceptionType 0 bis 99)
-- Generiert zyklisch aus deinen 5 logischen Schablonen.

INSERT INTO `swpool_epd` (`eventType`, `perceptionType`, `jsonEPD`) VALUES 
(508, 0, '{"eventType":"percipientExistsDistance5000","perceptionType":0,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),
(508, 1, '{"eventType":"percipientExistsDistance5000","perceptionType":1,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),
(508, 2, '{"eventType":"percipientExistsDistance5000","perceptionType":2,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),
(508, 3, '{"eventType":"percipientExistsDistance5000","perceptionType":3,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),
(508, 4, '{"eventType":"percipientExistsDistance5000","perceptionType":4,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),
(508, 5, '{"eventType":"percipientExistsDistance5000","perceptionType":5,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),
(508, 6, '{"eventType":"percipientExistsDistance5000","perceptionType":6,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),
(508, 7, '{"eventType":"percipientExistsDistance5000","perceptionType":7,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),
(508, 8, '{"eventType":"percipientExistsDistance5000","perceptionType":8,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),
(508, 9, '{"eventType":"percipientExistsDistance5000","perceptionType":9,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),
(508, 10, '{"eventType":"percipientExistsDistance5000","perceptionType":10,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),
(508, 11, '{"eventType":"percipientExistsDistance5000","perceptionType":11,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 12, '{"eventType":"percipientExistsDistance5000","perceptionType":12,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 13, '{"eventType":"percipientExistsDistance5000","perceptionType":13,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 14, '{"eventType":"percipientExistsDistance5000","perceptionType":14,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 15, '{"eventType":"percipientExistsDistance5000","perceptionType":15,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 16, '{"eventType":"percipientExistsDistance5000","perceptionType":16,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 17, '{"eventType":"percipientExistsDistance5000","perceptionType":17,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 18, '{"eventType":"percipientExistsDistance5000","perceptionType":18,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 19, '{"eventType":"percipientExistsDistance5000","perceptionType":19,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 20, '{"eventType":"percipientExistsDistance5000","perceptionType":20,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 21, '{"eventType":"percipientExistsDistance5000","perceptionType":21,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 22, '{"eventType":"percipientExistsDistance5000","perceptionType":22,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 23, '{"eventType":"percipientExistsDistance5000","perceptionType":23,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 24, '{"eventType":"percipientExistsDistance5000","perceptionType":24,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 25, '{"eventType":"percipientExistsDistance5000","perceptionType":25,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 26, '{"eventType":"percipientExistsDistance5000","perceptionType":26,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 27, '{"eventType":"percipientExistsDistance5000","perceptionType":27,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 28, '{"eventType":"percipientExistsDistance5000","perceptionType":28,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 29, '{"eventType":"percipientExistsDistance5000","perceptionType":29,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 30, '{"eventType":"percipientExistsDistance5000","perceptionType":30,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 31, '{"eventType":"percipientExistsDistance5000","perceptionType":31,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 32, '{"eventType":"percipientExistsDistance5000","perceptionType":32,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 33, '{"eventType":"percipientExistsDistance5000","perceptionType":33,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 34, '{"eventType":"percipientExistsDistance5000","perceptionType":34,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 35, '{"eventType":"percipientExistsDistance5000","perceptionType":35,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_trousers.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_trousers)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 36, '{"eventType":"percipientExistsDistance5000","perceptionType":36,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 37, '{"eventType":"percipientExistsDistance5000","perceptionType":37,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_shirt.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]},{"orderNr":3,"category":"property","criterion":1,"customLabel":"inventory_cap.SOGN_184549375.mainMaterial","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateComposition)","GETFctVal(getMainMaterial)"]}]}]}'),(508, 38, '{"eventType":"percipientExistsDistance5000","perceptionType":38,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_shirt.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_shirt)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}'),(508, 39, '{"eventType":"percipientExistsDistance5000","perceptionType":39,"relevanceThreshold":100,"entrys":[{"relevanceThreshold":"100","sourceCategory":1,"sourcePathSteps":["GETVal(myself)"],"subjectPathSteps":["GETVal(event_params)","GETVal(event_causer)"],"extractions":[{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]},{"orderNr":2,"category":"property","criterion":0,"customLabel":"inventory_cap.SOGN_184549375.mainColour_2","pathSteps":["GETVal(event_params)","GETVal(event_causer)#IsElem(50331647)","GETProp(stateInventory)","GETProp(stateInventory_inventory)","GETProp(inventory_cap)#IsElem(184549375)","GETProp(stateAppearance)","GETFctVal(getMainColour)"]}]}]}')
*/
 

