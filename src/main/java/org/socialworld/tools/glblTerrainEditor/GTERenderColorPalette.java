package org.socialworld.tools.glblTerrainEditor;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Farbpalette (Version 3.3).
 * Enthält alle Farben und Namen für deine 13 GroundMaterials und die erweiterte Fauna.
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
        return switch (type) {
            case "WATER"        -> new Color(30, 100, 200);   // Blau
            case "SAND"         -> new Color(225, 205, 135);  // Helles Gelb
            case "MUD"          -> new Color(90, 65, 40);     // Schlamm-Braun
            case "CRUSHED_ROCK" -> new Color(150, 145, 140);  // Helles Schotter-Grau
            case "STONES"       -> new Color(110, 110, 110);  // Kiesel-Dunkelgrau
            case "ROCK"         -> new Color(80, 85, 90);     // Fels-Anthrazit
            case "MOSS"         -> new Color(35, 140, 45);    // Moos-Giftgrün
            case "GRASS"        -> new Color(50, 150, 70);    // Standard-Gras
            case "FOLIAGE"      -> new Color(140, 95, 40);    // Herbstlaub-Braun
            case "BRUSHWOOD"    -> new Color(105, 85, 65);    // Reisig-Holzbraun
            case "ASH"          -> new Color(55, 55, 55);     // Asche-Schwarzgrau
            case "SNOW"         -> new Color(245, 245, 250);  // Reines Schnee-Weiß
            case "ICE"          -> new Color(175, 215, 230);  // Gletscher-Eis
            default             -> new Color(50, 150, 70);    // Fallback: GRASS
        };
    }

    public static String getTerrainNameFromCode(byte code) {
        return switch (code) {
            case 1  -> "WATER";
            case 2  -> "SAND";
            case 3  -> "MUD";
            case 4  -> "CRUSHED_ROCK";
            case 5  -> "STONES";
            case 6  -> "ROCK";
            case 7  -> "MOSS";
            case 8  -> "GRASS";
            case 9  -> "FOLIAGE";
            case 10 -> "BRUSHWOOD";
            case 11 -> "ASH";
            case 12 -> "SNOW";
            case 13 -> "ICE";
            default -> "GRASS";
        };
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
