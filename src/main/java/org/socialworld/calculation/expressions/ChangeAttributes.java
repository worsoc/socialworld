/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.calculation.expressions;

import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.descriptions.EventInfluencesAttributeEntry;

import java.util.ArrayList;

public class ChangeAttributes extends Sequence {

    /**
     * Factory-Methode: Erzeugt eine Sequenz aus einer Liste von JSON-Einträgen.
     * Jeder Eintrag wird zu einem granularen ChangeAttribute-Objekt.
     */
    public static Expression fromJsonEntries(List<EventInfluencesAttributeEntry> entries) {
        if (entries == null) {
            return Nothing.getInstance();
        }
        if (entries.isEmpty()) {
            // a neutral rule: it changes nothing, the attribute array comes back as it is
            return new GetArgumentByName(PropertyName.simobj_attributeArray.toString());
        }

        List<Expression> attributeChanges = new ArrayList<>();
        
        for (EventInfluencesAttributeEntry entry : entries) {
            // Erzeugt für jeden Eintrag ein atomares ChangeAttribute-Objekt
            // (Inklusive WENN-DANN-SONST Logik für genau dieses Attribut)
            attributeChanges.add(ChangeAttribute.fromJsonEntry(entry));
        }

        // Nutzt den Sequence-Konstruktor, um den ternären Baum (count > 3 / > 6) aufzubauen
        return new ChangeAttributes(attributeChanges);
    }

 
    /**
     * Alternativ: Erzeugung aus der alten List<String> Struktur
     */
    public static Expression fromLines(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return Nothing.getInstance();
        }

        List<Expression> attributeChanges = new ArrayList<>();
        for (String line : lines) {
            List<String> singleLine = new ArrayList<>();
            singleLine.add(line);
            // Nutzt die granulare fromLines-Methode mit dem break-Fix
            attributeChanges.add(ChangeAttribute.fromLines(singleLine));
        }

        return new ChangeAttributes(attributeChanges);
    }
    
    /**
     * Privater Konstruktor, nutzt die Logik der Basisklasse Sequence
     */
    private ChangeAttributes(List<Expression> expressions) {
        super(expressions);
    }

}

