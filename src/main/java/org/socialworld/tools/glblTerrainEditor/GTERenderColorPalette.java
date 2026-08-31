package org.socialworld.tools.glblTerrainEditor;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.socialworld.attributes.GroundMaterial;

/**
 * Farbpalette (Version 3.3).
 * Enthält alle Farben und Namen für  13 GroundMaterials und die erweiterte Fauna.
 */
public class GTERenderColorPalette {

    public static Color getBaumColor(String baumType) {
        return switch (baumType) {
            case "EICHE"  -> new Color(34, 110, 34);    // Sattes Waldgrün
            case "KIEFER" -> new Color(15, 75, 45);     // Dunkles Nadelgrün
            case "BIRKE"  -> new Color(95, 165, 80);    // Helles Frühlingsgrün
            case "BUCHE"  -> new Color(50, 120, 20);    // Klassisches Laubgrün
            case "FICHTE" -> new Color(20, 60, 40);     // Bläuliches Dunkelgrün
            case "WEIDE"  -> new Color(110, 150, 100);  // Silbrig-blasses Grün
            default       -> new Color(40, 120, 50);
        };
    }

    public static Color getStrauchColor(String strauchType) {
        return switch (strauchType) {
            case "FARNE"         -> new Color(0, 210, 100);     // Leuchtendes Farn-Smaragdgrün
            case "ZIERSTRAUCH"   -> new Color(190, 240, 0);     // Knalliges Neongrün/Limette
            case "BEERENSTRAUCH" -> new Color(220, 20, 60);     // Kräftiges Beeren-Rot
            case "BROMBEERE"     -> new Color(160, 30, 220);    // Intensives Violett
            case "HEIDEKRAUT"    -> new Color(240, 80, 180);    // Knalliges lila-pink
            case "GINSTER"       -> new Color(255, 215, 0);     // Leuchtendes Goldgelb
            default              -> new Color(60, 130, 50);
        };
    }

    public static Color getTerrainColor(String type) {
    	int code = GroundMaterial.fromName(type).getGteId();
        return switch (code) {
            case 1        -> new Color(30, 100, 200);   // Blau
            case 2         -> new Color(225, 205, 135);  // Helles Gelb
            case 3          -> new Color(90, 65, 40);     // Schlamm-Braun
            case 4 -> new Color(150, 145, 140);  // Helles Schotter-Grau
            case 5       -> new Color(110, 110, 110);  // Kiesel-Dunkelgrau
            case 6         -> new Color(80, 85, 90);     // Fels-Anthrazit
            case 7         -> new Color(35, 140, 45);    // Moos-Giftgrün
            case 8        -> new Color(50, 150, 70);    // Standard-Gras
            case 9      -> new Color(140, 95, 40);    // Herbstlaub-Braun
            case 10    -> new Color(105, 85, 65);    // Reisig-Holzbraun
            case 11          -> new Color(55, 55, 55);     // Asche-Schwarzgrau
            case 12         -> new Color(245, 245, 250);  // Reines Schnee-Weiß
            case 13          -> new Color(175, 215, 230);  // Gletscher-Eis
            default             -> new Color(30, 100, 200);    // Fallback: Water
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
