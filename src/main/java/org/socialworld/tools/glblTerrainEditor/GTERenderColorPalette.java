package org.socialworld.tools.glblTerrainEditor;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.socialworld.attributes.GroundMaterial;
import org.socialworld.visualize.SimColorConstants;

/**
 * Farbpalette (Version 3.3).
 * Enthält alle Farben und Namen für  14 GroundMaterials und die erweiterte Fauna.
 */
public class GTERenderColorPalette {

    public static Color getBaumColor(String baumType) {
        return switch (baumType) {
            case "EICHE"  -> SimColorConstants.COLOR_FORESTGREEN;   
            case "KIEFER" -> SimColorConstants.COLOR_DARKGREEN;    
            case "BIRKE"  -> SimColorConstants.COLOR_LIGHTGREEN;    
            case "BUCHE"  -> SimColorConstants.COLOR_MEDIUMSPRINGGREEN;    
            case "FICHTE" -> SimColorConstants.COLOR_PALEGREEN;    
            case "WEIDE"  -> SimColorConstants.COLOR_OLIVE;  
            default       -> SimColorConstants.COLOR_FORESTGREEN;
        };
    }

    public static Color getStrauchColor(String strauchType) {
        return switch (strauchType) {
            case "FARNE"         -> SimColorConstants.COLOR_LIMEGREEN;    
            case "ZIERSTRAUCH"   -> SimColorConstants.COLOR_LIME;   
            case "BEERENSTRAUCH" -> SimColorConstants.COLOR_MEDIUMORCHID;  
            case "BROMBEERE"     -> SimColorConstants.COLOR_DARKVIOLET;    
            case "HEIDEKRAUT"    -> SimColorConstants.COLOR_DARKMAGENTA;  
            case "GINSTER"       -> SimColorConstants.COLOR_PEACHPUFF;   
            default              -> SimColorConstants.COLOR_LIMEGREEN;
        };
    }

    public static Color getTerrainColor(String type) {
    	int code = GroundMaterial.fromName(type).getGteId();
        return switch (code) {
        	case 0       -> SimColorConstants.COLOR_MEDIUMBLUE;  
            case 1        -> SimColorConstants.COLOR_SLATEBLUE; 
            case 2         -> SimColorConstants.COLOR_LIGHT_YELLOW1;  
            case 3          -> SimColorConstants.COLOR_SADDLSEBROWN;     
            case 4 -> SimColorConstants.COLOR_LIGHTSLATEGRAY; 
            case 5       -> SimColorConstants.COLOR_SLATEGRAY; 
            case 6         -> SimColorConstants.COLOR_DARKGRAY;   
            case 7         -> SimColorConstants.COLOR_MEDIUMSEAGREEN;    
            case 8        -> SimColorConstants.COLOR_GREEN;   
            case 9      -> SimColorConstants.COLOR_BURLYWOOD; 
            case 10    -> SimColorConstants.COLOR_PERU;  
            case 11          -> SimColorConstants.COLOR_DIMGRAY;    
            case 12         -> SimColorConstants.COLOR_SNOW; 
            case 13          -> SimColorConstants.COLOR_POWDERBLUE;  
            default             -> SimColorConstants.COLOR_MEDIUMBLUE;    // Fallback: SaltWater
        };
    }

    public static String getTerrainNameFromCode(byte code) {
    	return GroundMaterial.fromGteId(code).toString().toUpperCase();
    }

    public static Color getDominantShrubColor(MacroMapCell cell, int mx, int my) {
        Map<String, Integer> counts = new HashMap<>();
        String dominant = "KEIN_STRAUCH";
        int max = 0;

        for (int lx = 0; lx < 9; lx++) {
            for (int ly = 0; ly < 9; ly++) {
                String type = cell.getMesoStrauchAusMischung(mx, my, lx, ly);
                if (type.equals("KEIN_STRAUCH")) continue;
                int count = counts.getOrDefault(type, 0) + 1;
                counts.put(type, count);
                if (count > max) { max = count; dominant = type; }
            }
        }
        return getStrauchColor(dominant);
    }
    
}
