/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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
package org.socialworld.datasource.parsing;

import java.util.List;
import org.socialworld.calculation.descriptions.EventPerceptionDescriptionEntry;

/**
 * Reines Daten-Transfer-Objekt (DTO) für GSON, um die Top-Level-Struktur 
 * einer Event-Wahrnehmungsbeschreibung aus den JSON-Dateien zu laden.
 */
public class JsonEventPerceptionDescription {

    // Name oder ID des Event-Typs 
    public String eventType;

    // Der numerische Typ der Wahrnehmung (wird in O(1)-Arrays gemappt)
    public int perceptionType;

    // Optionale globale Relevanz-Schwelle direkt auf Wurzel-Ebene der JSON
    public int relevanceThreshold;

    // Die Liste der tieferen Filter- und Berechnungs-Einträge
    public List<EventPerceptionDescriptionEntry> entrys;
}

/*
 {
  "eventType": "ANIMAL_SOUND",
  "perceptionType": 2,
  "relevanceThreshold": 10,
  "entrys": [
    {
      "relevanceThreshold": "8"
    },
    {
      "relevanceThreshold": "12"
    }
  ]
} 
 */ 
