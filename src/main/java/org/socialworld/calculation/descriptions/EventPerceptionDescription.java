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
		// TODO: Hier folgt analog zu den Reactions die Verknüpfung der Expressions, 
		// sobald die Auswertung für den KnowledgeCalculator/Wahrnehmungs-Output steht.
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
