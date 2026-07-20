/*
 * Social World
 * Copyright (C) 2020  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.calculation.descriptions;

import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;
import org.socialworld.calculation.functions.FunctionByExpression;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventPerceptionDescription;
import com.google.gson.Gson;

public class EventPerceptionDescription extends DescriptionBase {

	private EventType eventType;
	private int perceptionType;
	
	private List<EventPerceptionDescriptionEntry> entrys;

	public EventPerceptionDescription() {
		super();
		setRelevanceThreshold(100); // Sicherer Fallback-Standardwert
	}

	public EventPerceptionDescription(Gson gson, String json) {
		super();
		loadFromJson(gson, json);
	}

	private void loadFromJson(Gson gson, String json) {
		JsonEventPerceptionDescription jsonObject;
		jsonObject = gson.fromJson(json, JsonEventPerceptionDescription.class);
		
		this.eventType = EventType.fromName(jsonObject.eventType);
		this.perceptionType = jsonObject.perceptionType;
		this.entrys = jsonObject.entrys;
		
		// Schreibt den ermittelten Wert direkt in das protected/private Feld von DescriptionBase
		setRelevanceThreshold(calculateFinalPerceptionThreshold(jsonObject));
	}

	@Override
	public void setFunctions() {
		// 1. Defensiver Schutz: Gibt es überhaupt geladene Einträge im JSON?
		if (this.entrys == null || this.entrys.isEmpty()) {
			return;
		}
		
		// 2. Wir gehen die geladenen JSON-Entries durch (analog zu den Reactions)
		for (EventPerceptionDescriptionEntry entry : this.entrys) {
			
			// 3. Native Erzeugung der EINEN, kombinierten Gesamt-Expression für dieses Entry
			// Hier übergeben wir das Entry an unseren neuen, fetten GSON-Konstruktor
			Expression startExpression = new CreateKnowledgeElementExpression(entry);
			
			// 4. Die lauffähige Funktion allokationsfrei in der Basisklasse registrieren
			if (startExpression.isValid()) {
				addFunction(new FunctionByExpression(startExpression));
			}
		}
	}
	
	/**
	 * Ermittelt das Maximum aus den String-Entries und gleicht es mit dem 
	 * int-Feld des Top-Level-Objekts für die Wahrnehmung ab.
	 */
	private int calculateFinalPerceptionThreshold(JsonEventPerceptionDescription jsonObject) {
	    int maxThreshold = 0;
	    
	    // 1. Stufe: Entries der Wahrnehmung prüfen (Typ: String)
	    if (this.entrys != null && !this.entrys.isEmpty()) {
	        for (EventPerceptionDescriptionEntry entry : this.entrys) {
	            if (entry.relevanceThreshold != null && !entry.relevanceThreshold.trim().isEmpty()) {
	                try {
	                    int parsedValue = Integer.parseInt(entry.relevanceThreshold.trim());
	                    if (parsedValue > maxThreshold) {
	                        maxThreshold = parsedValue;
	                    }
	                } catch (NumberFormatException e) {
	                    // Defensiver Schutz gegen fehlerhafte String-Einträge in den XMLs/JSONs
	                }
	            }
	        }
	    }
	    
	    // 2. Stufe: Wenn in den Entries nichts gefunden wurde, greift das Top-Level Feld (Typ: int)
	    if (maxThreshold == 0 && jsonObject != null) {
	        // Da es ein primitiver int ist, prüfen wir auf > 0 (0 bedeutet "nicht gesetzt" / Default)
	        if (jsonObject.relevanceThreshold > 0) {
	            maxThreshold = jsonObject.relevanceThreshold;
	        }
	    }
	    
	    // 3. Stufe: Globaler Fallback, falls absolut kein Wert definiert wurde
	    return (maxThreshold > 0) ? maxThreshold : 100; 
	}

	public EventType getEventType() {
		return this.eventType;
	}

	public int getPerceptionType() {
		return this.perceptionType;
	}
}


/*
{
  "eventType": "percipientExistsDistance5000",
  "perceptionType": 0,
  "relevanceThreshold": 50,
  "entrys": [
    {
      "relevanceThreshold": "10",
      "knowledgeSourceType": 1,
      "knowledgeSourcePath": ["GETVal(myself)"],
      "knowledgeSubjectPath": [
        "GETVal(event_params)",
        "GETVal(event_causer)"
      ],
      "knowledgeProperties": [
        {
          "targetProperty": "inventory_shirt.mainColour",
          "extractionPath": [
            "GETVal(event_params)",
            "GETVal(event_causer)#IsElem(50331647)",
            "GETProp(stateInventory)",
            "GETFctVal(getMainColour)"
          ]
        },
        {
          "targetProperty": "inventory_shirt.mainMaterial",
          "extractionPath": [
            "GETVal(event_params)",
            "GETProp(stateInventory)",
            "GETFctVal(getMainMaterial)"
          ]
        }
      ]
    }
  ]
}

 */
 

