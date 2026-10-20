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
package org.socialworld.tools.glblTerrainEditor;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.socialworld.attributes.GroundMaterial;
import org.socialworld.attributes.VegetationBush;
import org.socialworld.attributes.VegetationTree;
import org.socialworld.visualize.SimColorConstants;

/**
 * Farbpalette (Version 3.5 - Refactored für int-IDs).
 * Enthält alle Farben und Namen für 14 GroundMaterials und die erweiterte Fauna.
 */
public class GTERenderColorPalette {

    public static Color getBaumColor(int baumId) {
        VegetationTree treeType = VegetationTree.fromGteId(baumId);
        if (treeType == null) return SimColorConstants.COLOR_FORESTGREEN;

        return switch (treeType) {
            case eiche  -> SimColorConstants.COLOR_FORESTGREEN;   
            case kiefer -> SimColorConstants.COLOR_DARKGREEN;    
            case birke  -> SimColorConstants.COLOR_LIGHTGREEN;    
            case buche  -> SimColorConstants.COLOR_MEDIUMSPRINGGREEN;    
            case fichte -> SimColorConstants.COLOR_PALEGREEN;    
            case weide  -> SimColorConstants.COLOR_OLIVE;  
            default     -> SimColorConstants.COLOR_FORESTGREEN;
        };
    }

    public static Color getStrauchColor(int strauchId) {
        VegetationBush bushType = VegetationBush.fromGteId(strauchId);
        if (bushType == null) return SimColorConstants.COLOR_LIMEGREEN;

        return switch (bushType) {
            case farne         -> SimColorConstants.COLOR_LIMEGREEN;    
            case zierstrauch   -> SimColorConstants.COLOR_LIME;   
            case beerenstrauch -> SimColorConstants.COLOR_MEDIUMORCHID;  
            case brombeere     -> SimColorConstants.COLOR_DARKVIOLET;    
            case heidekraut    -> SimColorConstants.COLOR_DARKMAGENTA;  
            case ginster       -> SimColorConstants.COLOR_PEACHPUFF;   
            default            -> SimColorConstants.COLOR_LIMEGREEN;
        };
    }

    /**
     * Holt die Farbe direkt über die numerische GTE-ID (terrainID 0-13).
     * Analog zu getBaumColor und getStrauchColor aufgebaut.
     */
    public static Color getTerrainColor(int terrainID) {
        return switch (terrainID) {
            case 0  -> SimColorConstants.COLOR_MEDIUMBLUE;       // saltwater
            case 1  -> SimColorConstants.COLOR_SLATEBLUE;        // water
            case 2  -> SimColorConstants.COLOR_LIGHT_YELLOW1;    // sand
            case 3  -> SimColorConstants.COLOR_SADDLSEBROWN;     // mud
            case 4  -> SimColorConstants.COLOR_LIGHTSLATEGRAY;   // crushedrock
            case 5  -> SimColorConstants.COLOR_SLATEGRAY;        // stones
            case 6  -> SimColorConstants.COLOR_DARKGRAY;         // rock
            case 7  -> SimColorConstants.COLOR_MEDIUMSEAGREEN;   // moss
            case 8  -> SimColorConstants.COLOR_GREEN;            // grass
            case 9  -> SimColorConstants.COLOR_BURLYWOOD;        // foliage
            case 10 -> SimColorConstants.COLOR_PERU;             // brushwood
            case 11 -> SimColorConstants.COLOR_DIMGRAY;          // ash
            case 12 -> SimColorConstants.COLOR_SNOW;             // snow
            case 13 -> SimColorConstants.COLOR_POWDERBLUE;       // ice
            default -> SimColorConstants.COLOR_MEDIUMBLUE;       // Fallback: SaltWater
        };
    }
    /**
     * Beibehalten für Abwärtskompatibilität, nutzt jetzt aber intern die ID.
     */
    public static Color getTerrainColor(String type) {
        GroundMaterial material = GroundMaterial.fromName(type);
        if (material == null) return SimColorConstants.COLOR_MEDIUMBLUE;
        return getTerrainColor(material.getGteId());
    }

    public static String getTerrainNameFromCode(byte code) {
        return GroundMaterial.fromGteId(code).toString().toUpperCase();
    }

    /**
     * Ermittelt die dominante Strauchfarbe in einer Meso-Zelle über die numerischen IDs.
     */
    public static Color getDominantShrubColor(MacroMapCell cell, int mx, int my) {
        Map<Integer, Integer> counts = new HashMap<>();
        int dominantId = 0; // 0 entspricht nothing
        int max = 0;

        for (int lx = 0; lx < 9; lx++) {
            for (int ly = 0; ly < 9; ly++) {
                int strauchId = cell.getMesoStrauchAusMischung(mx, my, lx, ly);
                if (strauchId == 0) continue; // nothing überspringen
                
                int count = counts.getOrDefault(strauchId, 0) + 1;
                counts.put(strauchId, count);
                
                if (count > max) { 
                    max = count; 
                    dominantId = strauchId; 
                }
            }
        }
        return getStrauchColor(dominantId);
    }
}
