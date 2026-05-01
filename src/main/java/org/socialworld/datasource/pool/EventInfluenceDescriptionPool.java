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
	    List<Lines> allLines = new ArrayList<Lines>();
	    Lines4EventType lines4EventType;

	    int totalRulesGenerated = 0;

	    // 1. Get Runtime instance and trigger GC for a clean baseline
	    Runtime runtime = Runtime.getRuntime();
	    runtime.gc(); 
	    long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

	    // 2. Capture start time and current wall-clock time
	    long startTime = System.currentTimeMillis();
	    java.time.LocalDateTime now = java.time.LocalDateTime.now();
	    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    System.out.println("--- Starting Rule Generation for EventInfluenceDescriptionPool ---");
	    
	    for (EventType type : EventType.values()) {
	        if (type == EventType.nothing) continue;

	        // rangeSecondIndex is the number of influence types (usually 100)
	        lines4EventType = new Lines4EventType(type, rangeSecondIndex);

	        // Evaluate event nature for meaningful value generation
	        String typeName = type.name().toLowerCase();
	        boolean isAggressive = typeName.contains("punch") || typeName.contains("attack") || typeName.contains("weapon");
	        boolean isUnpleasant = typeName.contains("scream") || typeName.contains("piss") || typeName.contains("shit");

	        for (int influenceType = 0; influenceType < rangeSecondIndex; influenceType++) {
	            
	            // 1. Condition Attribute (The trigger for the "IF" part, e.g., hunger)
	            Attribute condAttr = Attribute.getAttributeName(influenceType % 9);
	            
	            // 2. Target Attribute (The one that will be modified, e.g., morals)
	            // Offset by 2 to ensure it's usually different from condAttr
	            Attribute targetAttr = Attribute.getAttributeName((influenceType + 2) % 9);
	            String targetTag = targetAttr.toString().toUpperCase();

	            // 3. Calculation Basis (The "X" in MX+N, e.g., courage)
	            // Offset by 5 to create cross-attribute dependencies
	            int basisAttrIndex = (influenceType + 5) % 9;

	            // 4. Value Logic and Range
	            int seed = type.ordinal() + influenceType;
	            int min = 20 + (seed % 40); // Thresholds between 20 and 60
	            int max = min + 20;

	            // Linear parameters: m (slope) and n (offset)
	            double m = 0.4 + ((seed % 10) * 0.2); // m between 0.4 and 2.4
	            double n = -10.0 + (seed % 20);      // n between -10.0 and +10.0
	            
	            // Apply qualitative adjustments based on event nature
	            if (isAggressive || isUnpleasant) {
	                n -= 15.0; // Negative events cause significantly larger drops
	                m *= 0.7;  // And dampen positive growth
	            }

	            // 5. Construct line: WENN <cond> >= <min> & <cond> < <max> DANN <TARGET><MX+N>basisIndex;m;n</MX+N></TARGET>
	            String line = String.format(
	                "WENN %s >= %d & %s < %d DANN <%s><MX+N>%d;%.2f;%.2f</MX+N></%s>",
	                condAttr.toString(), min, condAttr.toString(), max, 
	                targetTag, basisAttrIndex, m, n, targetTag
	            );

	            // Add the generated rule to the event type's collection
	            lines4EventType.add(influenceType, 0, line);

	            totalRulesGenerated++;

	            // OPTIONAL: Log a sample rule for every 500th generation to keep console clean
	            if (totalRulesGenerated % 500 == 0) {
	                System.out.println(String.format("[Sample %d] Type: %s | Rule: %s", totalRulesGenerated, type.name(), line));
	            }
	        
	        }
	        allLines.add(lines4EventType);
	    }

	    // Capture time AFTER string generation but BEFORE parsing
	    long afterGenTime = System.currentTimeMillis();
	    System.out.println(String.format("--- Strings generated in %d ms. Now parsing expressions... ---", (afterGenTime - startTime)));
	    
	    // Finalize by creating expressions from the collected lines
	    createExpressions(allLines);
	    
	    // Capture final time
	    long endTime = System.currentTimeMillis();
	    System.out.println("--- Finished: " + new java.util.Date() + " ---");
	    System.out.println(String.format("--- Total duration (Gen + Parse): %d ms ---", (endTime - startTime)));

	    long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
	    long memoryUsedKBytes = (memoryAfter - memoryBefore) / 1024;
	    double memoryUsedMBytes = memoryUsedKBytes / 1024.0;

	    System.out.println(String.format("--- Memory Check ---"));
	    System.out.println(String.format("Rules in Memory: %d", totalRulesGenerated));
	    System.out.println(String.format("Memory Consumed: %.2f MB (%d KB)", memoryUsedMBytes, memoryUsedKBytes));
	    System.out.println(String.format("Avg per Rule: %.1f Bytes", (double)(memoryAfter - memoryBefore) / totalRulesGenerated));

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
