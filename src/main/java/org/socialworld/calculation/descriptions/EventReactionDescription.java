/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
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
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.functions.FunctionByExpression;
import org.socialworld.datasource.parsing.JsonEventReactionDescription;
import com.google.gson.Gson;

public class EventReactionDescription extends DescriptionBase {
	
	private EventType eventType;
	private int reactionType;
	
	
	private List<EventReactionDescriptionEntry> entrys;
	
	public EventReactionDescription() {
		super();
		setRelevanceThreshold(100); // Sicherer Fallback-Standardwert
	}

	public EventReactionDescription(Gson gson, String json) {
		super();
		loadFromJson(gson, json);
	}

	private void loadFromJson(Gson gson, String json) {
		JsonEventReactionDescription jsonObject;
		jsonObject = gson.fromJson(json, JsonEventReactionDescription.class);
		
		this.eventType = EventType.fromName(jsonObject.eventType);
		this.reactionType = jsonObject.reactionType;
		this.entrys = jsonObject.entrys;
		
		setRelevanceThreshold(calculateFinalThreshold(jsonObject));
	}

	/**
	 * Ermittelt das Maximum aus den String-Entries und gleicht es mit dem 
	 * int-Feld des Top-Level-Objekts ab.
	 */
	private int calculateFinalThreshold(JsonEventReactionDescription jsonObject) {
		int maxThreshold = 0;
		
		// 1. Stufe: Entries prüfen (Typ: String)
		if (this.entrys != null && !this.entrys.isEmpty()) {
			for (EventReactionDescriptionEntry entry : this.entrys) {
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

	@Override
	public void setFunctions() {
		Expression startExpression = CreateActionExpression.createActionExpression(this.entrys, true /* dummy */);
		addFunction(new FunctionByExpression(startExpression));
	}
	
		
	public EventType getEventType() {
		return this.eventType;
	}

	public int getReactionType() {
		return this.reactionType;
	}
}

