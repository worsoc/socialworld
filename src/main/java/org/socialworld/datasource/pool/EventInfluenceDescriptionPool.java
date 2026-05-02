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

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.descriptions.DescriptionBase;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.calculation.expressions.ChangeAttributes;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.calculation.functions.FunctionMXplusN;
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
		//initializeFromLines();
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
	    List<Lines> allLines = new ArrayList<Lines>();
	    Lines4EventType lines4EventType;
	    int totalRulesGenerated = 0;

	    // 1. Prepare Memory and Time Measurement
	    Runtime runtime = Runtime.getRuntime();
	    runtime.gc(); 
	    long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
	    long startTime = System.currentTimeMillis();

	    System.out.println("--- Starting Role-Aware Generation with Fine-Tuning ---");

	    for (EventType type : EventType.values()) {
	        if (type == EventType.nothing) continue;

	        lines4EventType = new Lines4EventType(type, rangeSecondIndex);
	        String typeName = type.name();

	        // Identify role
	        boolean isSelf = typeName.startsWith("self");
	        boolean isTarget = typeName.startsWith("target");
	        boolean isCandidate = typeName.startsWith("candidates");
	        boolean isPercipient = typeName.startsWith("percipient");

	        for (int influenceType = 0; influenceType < rangeSecondIndex; influenceType++) {
	            
	            // 2. Fine-Tuning: Attribute Selection based on Role
	            Attribute condAttr;
	            Attribute targetAttr;

	            if (isPercipient) {
	                // Observers react to social/mental triggers and change their mind/mood
	                condAttr = (influenceType % 2 == 0) ? Attribute.curiosity : Attribute.spirituality;
	                targetAttr = (influenceType % 2 == 0) ? Attribute.mood : Attribute.morals;
	            } else if (isSelf) {
	                // Actors are triggered by and change physical needs
	                condAttr = (influenceType % 2 == 0) ? Attribute.hunger : Attribute.tiredness;
	                targetAttr = (influenceType % 2 == 0) ? Attribute.power : Attribute.courage;
	            } else {
	                // Targets and Candidates use the full spectrum
	                condAttr = Attribute.getAttributeName(influenceType % 9);
	                targetAttr = Attribute.getAttributeName((influenceType + 3) % 9);
	            }

	            String targetTag = targetAttr.toString().toUpperCase();
	            int basisAttrIndex = (influenceType + 5) % 9;

	            // 3. Mathematical Logic with Qualitative Bias
	            int seed = type.ordinal() + influenceType;
	            int min = 10 + (seed % 50);
	            int max = min + 20;

	            double m = 0.5 + ((seed % 15) * 0.1);
	            double n = -10.0 + (seed % 21);

	            // Role-based scaling
	            if (isSelf) {
	                m *= 1.3; n += 2.0; // Strong impact on self
	            } else if (isTarget) {
	                m *= 0.7; n -= 12.0; // Significant negative impact for targets
	            } else if (isPercipient) {
	                m *= 0.3; n *= 0.5; // Very subtle ripples for observers
	            }

	            String line = String.format(
	                "WENN %s >= %d & %s < %d DANN <%s><MX+N>%d;%.2f;%.2f</MX+N></%s>",
	                condAttr.toString(), min, condAttr.toString(), max, 
	                targetTag, basisAttrIndex, m, n, targetTag
	            );

	            lines4EventType.add(influenceType, 0, line);
	            totalRulesGenerated++;
	            
	            // OPTIONAL: Log a sample rule for every 500th generation to keep console clean
	            if (totalRulesGenerated % 500 == 0) {
	                System.out.println(String.format("[Sample %d] Type: %s | Rule: %s", totalRulesGenerated, type.name(), line));
	            }

	        }
	        allLines.add(lines4EventType);
	    }

	    // 4. Heavy Parsing Step
	    createExpressions(allLines);
	    
	    // 5. Final Analytics
	    long duration = System.currentTimeMillis() - startTime;
	    System.gc();
	    long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
	    double memoryUsedMB = (memoryAfter - memoryBefore) / (1024.0 * 1024.0);

	    System.out.println(String.format("--- Generation Finished ---"));
	    System.out.println(String.format("Rules: %d | Time: %d ms", totalRulesGenerated, duration));
	    System.out.println(String.format("Memory Used: %.2f MB (Avg: %.1f Bytes/Rule)", 
	                       memoryUsedMB, (double)(memoryAfter - memoryBefore) / totalRulesGenerated));
	    System.out.println(String.format("Unique MX+N Instances: %d", FunctionMXplusN.getPoolSize()));
	    System.out.println(String.format("Reduction Factor: %.2f", (double)totalRulesGenerated / FunctionMXplusN.getPoolSize()));
	}

	private void initializeWithTestData_Json() {
	    TablePoolEID tableEID = new TablePoolEID();
	    
	    // 1. Messung vorbereiten
	    Runtime runtime = Runtime.getRuntime();
	    runtime.gc(); 
	    long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
	    long startTime = System.currentTimeMillis();
	    int totalRulesLoaded = 0;

	    System.out.println("--- Starting JSON-DB Loading & Parsing ---");

	    // Datenbank-Abfrage
	    tableEID.select(tableEID.SELECT_ALL_COLUMNS, "", "ORDER BY eventType, influenceType");
	    int rowCountEID = tableEID.rowCount();
	    
	    if (rowCountEID > 0) {
	        for (int rowEID = 0; rowEID < rowCountEID; rowEID++) {
	            
	            int eventType = tableEID.getEventType(rowEID);
	            int influenceType = tableEID.getInfluenceType(rowEID);
	            String jsonEID = tableEID.getJsonEID(rowEID); // Holt das JSON-Feld

	            if (jsonEID != null && !jsonEID.isEmpty()) {
	                // Erstellung der EID basierend auf dem JSON-String
	                // Hier greift im Hintergrund dein JEID-Mechanismus
	                EventInfluenceDescription eid = new EventInfluenceDescription(jsonEID);
	                eid.setFunctions();
	                
	                // In den Pool speichern
	                setDescription(eventType, influenceType, eid);
	                totalRulesLoaded++;
	            }

	            // Fortschritts-Log alle 1000 Regeln
	            if (totalRulesLoaded % 1000 == 0) {
	                System.out.println(String.format("[DB-Load] %d / %d Regeln verarbeitet...", totalRulesLoaded, rowCountEID));
	            }
	        }
	    }

	    // 2. Finales Benchmarking
	    long duration = System.currentTimeMillis() - startTime;
	    runtime.gc(); // Kurze Pause für den GC, um echte Belegung zu sehen
	    long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
	    double memoryUsedMB = (memoryAfter - memoryBefore) / (1024.0 * 1024.0);

	    System.out.println("--- JSON Loading Finished ---");
	    System.out.println(String.format("Rules Loaded: %d | Time: %d ms", totalRulesLoaded, duration));
	    System.out.println(String.format("Memory Used: %.2f MB (Avg: %.1f Bytes/Rule)", 
	                       memoryUsedMB, (double)(memoryAfter - memoryBefore) / totalRulesLoaded));
	    
	    // Vergleichswert zum MX+N Pool
	    System.out.println(String.format("Unique MX+N Instances: %d", FunctionMXplusN.getPoolSize()));
	}
	
/*	
	private void initializeWithTestData_Json() {
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
*/
	

}
