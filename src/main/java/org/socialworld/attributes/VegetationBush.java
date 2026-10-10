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
 * The enumeration VegetationBush holds all bush vegetation types
 *   that can be set to a map's property
 * 
 * @author Mathias Sikos
 *
 */
public enum VegetationBush {
    nothing(0),
    zierstrauch(1),
    farne(2),
    beerenstrauch(3),
    brombeere(4),
    heidekraut(5),
    ginster(6);

    private static final List<String> UPPERCASE_NAMES;
    private static final Set<String> LOWERCASE_NAMES;
    private static final Map<Integer, VegetationBush> INDEX_MAP;
    private static final List<String> CACHED_NAME_LIST;

    static {
        List<String> upper = new ArrayList<>();
        Set<String> lower = new HashSet<>();
        Map<Integer, VegetationBush> indices = new HashMap<>();
        List<String> list = new ArrayList<>();

        for (VegetationBush a : VegetationBush.values()) {
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
    VegetationBush(int gteId) {
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
     * Findet das passende VegetationBush anhand der eingelesenen GTE-ID.
     * 
     * @param id Die aus der .map-Datei gelesene ID (0 bis 6)
     * @return Das passende VegetationBush oder kein_strauch als Fallback
     */
    public static VegetationBush fromGteId(int id) {
        for (VegetationBush bush : VegetationBush.values()) {
            if (bush.getGteId() == id) {
                return bush;
            }
        }
        return nothing; // Fallback, falls ein ungültiger Code eingelesen wird
    }
    
    public static VegetationBush fromName(String name) {
        if (name == null) return null;
        try {
            return VegetationBush.valueOf(name.toLowerCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static String[] getUpperCaseNames() {
        String[] result = new String[count()];
        int index = 0;
        for (VegetationBush bush : VegetationBush.values()) {
            result[index] = bush.toString().toUpperCase();
            index++;
        }
        return result;
    }
    
    public static int count() {
        return VegetationBush.values().length;
    }
    
    public static String getAbbreviation(int code) {
        return switch (code) {
            case 0  -> "NO";   // NOTHING
            case 1  -> "ZI";   // ZIERSTRAUCH
            case 2  -> "FA";   // FARNE
            case 3  -> "BE";   // BEERENSTRAUCH
            case 4  -> "BR";   // BROMBEERE
            case 5  -> "HE";   // HEIDEKRAUT
            case 6  -> "GI";   // GINSTER
            default -> "NO";   // Fallback: nothing
        };
    }
}
