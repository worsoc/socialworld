package org.socialworld.tools.glblTerrainEditor;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.socialworld.attributes.GroundMaterial;
import org.socialworld.attributes.VegetationBush;
import org.socialworld.attributes.VegetationTree;
import org.socialworld.visualize.SimColorConstants;

/**
 * Farbpalette (Version 3.4).
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

    public static Color getTerrainColor(String type) {
        int code = GroundMaterial.fromName(type).getGteId();
        return switch (code) {
            case 0  -> SimColorConstants.COLOR_MEDIUMBLUE;  
            case 1  -> SimColorConstants.COLOR_SLATEBLUE; 
            case 2  -> SimColorConstants.COLOR_LIGHT_YELLOW1;  
            case 3  -> SimColorConstants.COLOR_SADDLSEBROWN;     
            case 4  -> SimColorConstants.COLOR_LIGHTSLATEGRAY; 
            case 5  -> SimColorConstants.COLOR_SLATEGRAY; 
            case 6  -> SimColorConstants.COLOR_DARKGRAY;   
            case 7  -> SimColorConstants.COLOR_MEDIUMSEAGREEN;    
            case 8  -> SimColorConstants.COLOR_GREEN;   
            case 9  -> SimColorConstants.COLOR_BURLYWOOD; 
            case 10 -> SimColorConstants.COLOR_PERU;  
            case 11 -> SimColorConstants.COLOR_DIMGRAY;    
            case 12 -> SimColorConstants.COLOR_SNOW; 
            case 13 -> SimColorConstants.COLOR_POWDERBLUE;  
            default -> SimColorConstants.COLOR_MEDIUMBLUE;    // Fallback: SaltWater
        };
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
