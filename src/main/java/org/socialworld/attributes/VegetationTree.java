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
 * The enumeration VegetationTree holds all tree vegetation types
 *   that can be set to a map's property
 * 
 * @author Mathias Sikos
 *
 */
public enum VegetationTree {
    nothing(0),
    eiche(1),
    kiefer(2),
    fichte(3),
    birke(4),
    buche(5),
    weide(6);

    private static final List<String> UPPERCASE_NAMES;
    private static final Set<String> LOWERCASE_NAMES;
    private static final Map<Integer, VegetationTree> INDEX_MAP;
    private static final List<String> CACHED_NAME_LIST;

    static {
        List<String> upper = new ArrayList<>();
        Set<String> lower = new HashSet<>();
        Map<Integer, VegetationTree> indices = new HashMap<>();
        List<String> list = new ArrayList<>();

        for (VegetationTree a : VegetationTree.values()) {
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
    VegetationTree(int gteId) {
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
     * Findet das passende VegetationTree anhand der eingelesenen GTE-ID.
     * 
     * @param id Die aus der .map-Datei gelesene ID (0 bis 6)
     * @return Das passende VegetationTree oder kein_baum als Fallback
     */
    public static VegetationTree fromGteId(int id) {
        for (VegetationTree tree : VegetationTree.values()) {
            if (tree.getGteId() == id) {
                return tree;
            }
        }
        return nothing; // Fallback, falls ein ungültiger Code eingelesen wird
    }
    
    public static VegetationTree fromName(String name) {
        if (name == null) return null;
        try {
            return VegetationTree.valueOf(name.toLowerCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static String[] getUpperCaseNames() {
        String[] result = new String[count()];
        int index = 0;
        for (VegetationTree tree : VegetationTree.values()) {
            result[index] = tree.toString().toUpperCase();
            index++;
        }
        return result;
    }
    
    public static int count() {
        return VegetationTree.values().length;
    }
    
    public static String getAbbreviation(int code) {
        return switch (code) {
            case 0  -> "NO";   // NOTHING
            case 1  -> "EI";   // EICHE
            case 2  -> "KI";   // KIEFER
            case 3  -> "FI";   // FICHTE
            case 4  -> "BI";   // BIRKE
            case 5  -> "BU";   // BUCHE
            case 6  -> "WE";   // WEIDE
            default -> "NO";   // Fallback: NOTHING
        };
    }
}
