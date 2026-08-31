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
package org.socialworld.attributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The enumeration GroundMaterial holds all ground materials
 *   that can be set to a map's ground property
 * 
 * @author Mathias Sikos
 *
 */
public enum GroundMaterial {
    water(1),        // GTE-ID 1 für WATER
    sand(2), 
    mud(3),          // German: Schlamm
    crushedrock(4),  // German: Schotter
    stones(5), 	
    rock(6),
    moss(7),         // German: Moos
    grass(8),
    foliage(9),      // German: Laub
    brushwood(10),   // German: Reisig
    ash(11),         // German: Asche
    snow(12),
    ice(13);

    private static final List<String> UPPERCASE_NAMES;
    private static final Set<String> LOWERCASE_NAMES;
    private static final Map<Integer, GroundMaterial> INDEX_MAP;
    private static final List<String> CACHED_NAME_LIST;

    static {
        List<String> upper = new ArrayList<>();
        Set<String> lower = new HashSet<>();
        Map<Integer, GroundMaterial> indices = new HashMap<>();
        List<String> list = new ArrayList<>();

        for (GroundMaterial a : GroundMaterial.values()) {
            String name = a.toString();
                upper.add(name.toUpperCase());
                lower.add(name.toLowerCase());
                list.add(name);
            indices.put(a.getGteId(), a);
        }

        UPPERCASE_NAMES = Collections.unmodifiableList(upper);
        LOWERCASE_NAMES = Collections.unmodifiableSet(lower);
        INDEX_MAP = Collections.unmodifiableMap(indices);
        CACHED_NAME_LIST = Collections.unmodifiableList(list);
    }

    // Das feste ID-Feld für das GlobalTerrainEditor-System
    private final int gteId;

    // Konstruktor für die Zuweisung
    GroundMaterial(int gteId) {
        this.gteId = gteId;
    }

    /**
     * Liefert die ID, die im GTE-Editor (Positivliste/Dateiformat) genutzt wird.
     */
    public int getGteId() {
        return gteId;
    }

    /**
     * Statische Hilfsmethode für deinen Importer im Hauptprojekt.
     * Findet das passende GroundMaterial anhand der eingelesenen GTE-ID.
     * 
     * @param id Die aus der .map-Datei gelesene ID (1 bis 13)
     * @return Das passende GroundMaterial oder grass als Fallback
     */
    public static GroundMaterial fromGteId(int id) {
        for (GroundMaterial material : GroundMaterial.values()) {
            if (material.getGteId() == id) {
                return material;
            }
        }
        return water; // Fallback, falls ein ungültiger Code eingelesen wird
    }
    
	public static GroundMaterial fromName(String name) {
        if (name == null) return null;
        try {
            return GroundMaterial.valueOf(name.toLowerCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
	}

    public static String[] getUpperCaseNames() {
    	String[] result = new String[count()];
    	int index = 0;
        for (GroundMaterial material : GroundMaterial.values()) {
        	result[index] = material.toString().toUpperCase();
        	index++;
        }
        return result;
    }
    
    public static int count() {
    	return GroundMaterial.values().length;
    }
}

