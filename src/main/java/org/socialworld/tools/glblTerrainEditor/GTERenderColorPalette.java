package org.socialworld.tools.glblTerrainEditor;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Ausgelagerte Farb- und Hilfsdaten für das Rendering.
 * Hält alle Farbtabellen zentral an einem Ort.
 */
public class GTERenderColorPalette {

    public static Color getBaumColor(String baumType) {
        return switch (baumType) {
            case "EICHE" -> new Color(34, 110, 34);    
            case "KIEFER" -> new Color(15, 75, 45);   
            case "BIRKE" -> new Color(95, 165, 80);   
            default -> new Color(40, 120, 50);
        };
    }

    public static Color getStrauchColor(String strauchType) {
        return switch (strauchType) {
            case "FARNE" -> new Color(46, 139, 87);         // Farn-Waldgrün
            case "ZIERSTRAUCH" -> new Color(154, 205, 50);  // Helles Blättergrün
            case "BEERENSTRAUCH" -> new Color(128, 0, 32);  // Weinrot
            default -> new Color(60, 130, 50);
        };
    }

    public static Color getTerrainColor(String type) {
        return switch (type) {
            case "WASSER" -> new Color(30, 100, 200);
            case "SAND" -> new Color(220, 200, 130);
            case "STEIN" -> new Color(120, 120, 120);
            case "SCHNEE" -> new Color(240, 240, 240);
            case "GRAS" -> new Color(50, 150, 70);
           default -> new Color(50, 150, 70); // GRAS
        };
    }

    public static String getTerrainNameFromCode(byte code) {
        return switch (code) {
            case 1 -> "WASSER";
            case 2 -> "SAND";
            case 3 -> "STEIN";
            case 4 -> "SCHNEE";
            case 5 -> "GRAS";   
            default -> "GRAS";  // Fallback
        };
    }

    /**
     * Berechnet die dominierende Strauchart für das Meso-Vorschauquadrat.
     */
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

