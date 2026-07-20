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

import org.socialworld.calculation.descriptions.ExtractionSlot;

/**
 * Bildet eine einzelne zu extrahierende Eigenschaft  ab.
 */
public class JsonExtractionStep {
    
	
	public int orderNr;
	
    // Die strukturelle Kategorie (z. B. "property", "value", "element")
    public String category;

    // Der sprechende Ziel-Slot aus dem universellen Enum
    public ExtractionSlot slot;

    // Eine ID oder ein Kriterium für spezifische Abgleiche (z. B. dein altes kfc)
    public int criterion;

    // Ein optionaler, benutzerdefinierter Name für das Ziel-Feld
    public String customLabel;

    // Der geordnete Pfad zur Laufzeit (Mix aus GETVal, GETProp, GETFctVal)
    public List<String> pathSteps;
}
